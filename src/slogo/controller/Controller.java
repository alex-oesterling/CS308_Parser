package slogo.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import slogo.exceptions.InvalidCommandException;
import slogo.model.Parser;
import slogo.model.Turtle;
import slogo.model.command.*;
import slogo.view.ViewExternal;

public class Controller {
    private static final String LANGUAGES_PACKAGE = "resources.languages.";
    private static final String INFORMATION_PACKAGE = "resources.information.";
    private static final String COMMAND_PACKAGE = "slogo.model.command.";
    private static final String WHITESPACE = "\\s+";
    private static final int ONE_DOUBLE = 1;
    private static final int TWO_DOUBLE = 2;
    private static final double ZERO_DOUBLE = 0;
    private static final double TURTLE = -0.5;
    private static boolean IS_VARIABLE = false;

    private List<Entry<String, Pattern>> mySymbols;
    private Stack<String> commandStack;
    private Stack<Double> argumentStack, parametersStack;
    private Map<String, List> userCreatedCommands;
    private Turtle turtle;// = new Turtle();
    private Map<String, Turtle> turtleMap;
    private String myCommands;
    private ViewExternal myView;
    private Parser commandParser, parametersParser, syntaxParser;

    /**
     * The constructor for controller class, initializes the view, list of
     * symbols that is being used for parsing, map of created variables,
     * all of the stacks used, and the parsers for each different stack
     * @param visualizer the view of the program
     * @param language the specific language being used (aka english, chinese, etc)
     */
    public Controller(ViewExternal visualizer, String language) {
        myView = visualizer;
        mySymbols = new ArrayList<>();
        userCreatedCommands = new HashMap<>();
        commandStack = new Stack<>();
        argumentStack = new Stack<>();
        parametersStack = new Stack<>();

        commandParser = new Parser(LANGUAGES_PACKAGE);
        commandParser.addPatterns(language);

        syntaxParser = new Parser(LANGUAGES_PACKAGE);
        syntaxParser.addPatterns("Syntax");

        parametersParser = new Parser(INFORMATION_PACKAGE);
        parametersParser.addPatterns("Parameters");

        turtleMap = new HashMap<>();
        addTurtle();
    }

    /**
     * add a new turtle to the map of turtles, change the current turtle to
     * this newly created turtle
     */
    public void addTurtle(){
        Turtle t = new Turtle();
        turtleMap.putIfAbsent(t.getName(), t);
        turtle = t;
    }

    /**
     * allows the user to pick a turtle to do work on
     * @param name turtle to become the current turtle
     */
    public void chooseTurtle(String name){
        turtle = turtleMap.get(name);
    }

    /**
     * Change to a new language of input
     * @param language input language: English, Spanish, Urdu, etc.
     */
    public void addLanguage(String language){
        commandParser = new Parser(LANGUAGES_PACKAGE);
        commandParser.addPatterns(language);
    }

    /**
     * Resets the turtle and clears all of the stacks
     */
    public void reset(){
        turtle = new Turtle();
        argumentStack.clear();
        commandStack.clear();
        parametersStack.clear();
    }

    /**
     * Receives the commands to be done from the view/UI
     * @param commands the commands the user typed in
     */
    public void sendCommands(String commands){
        myCommands = commands;
        parseText(syntaxParser, commandParser, parametersParser, asList(myCommands.split(WHITESPACE)));
    }

    private List<String> asList(String[] array){
        List<String> list = new ArrayList<>();
        for(String s : array){ list.add(s); }
        return list;
    }

    private List<Command> parseText (Parser syntax, Parser lang, Parser params, List<String> lines) {
        List<Command> commandList = new ArrayList<>();
        ListIterator<String> iterator = lines.listIterator();
        while(iterator.hasNext() && !IS_VARIABLE) {
            String line =  iterator.next();
            if (line.trim().length() > 0) {
                String commandSyntax = syntax.getSymbol(line); //get what sort of thing it is
                if(commandSyntax.equals("Command")){
                    doCommandWork(params, lang, commandList, line, commandSyntax, lines);
                } else if (commandSyntax.equals("Constant")){
                    doConstantWork(line, commandList);
                } else if (commandSyntax.equals("Variable")){
                    if(userCreatedCommands.containsKey(line)){
                        doVariable(line, syntax, lang, params, commandList);
                    }
                    else{
                        System.out.println("This variable does not exist yet");
                        throw new InvalidCommandException(new Throwable(), commandSyntax, line);
                        //FIXME Need to add an exception (better one) here
                    }
                }
            }
        }
        IS_VARIABLE = false;
        while(commandStack.size()>0 ){
            tryToMakeCommands(commandList);
        }
        executeCommandList(commandList);
        printCommandList(commandList); //we can get rid of this after submission
        return commandList;
    }

    private void doVariable(String line, Parser syntax, Parser lang, Parser params, List<Command> commandList){
        List<String> variableDoesWhat = userCreatedCommands.get(line);
        for (String s : variableDoesWhat){
            String comSyntax = syntax.getSymbol(s);
            if (comSyntax.equals("Command")){ doCommandWork(params, lang, commandList, s, comSyntax, variableDoesWhat); }
            else if (comSyntax.equals("Constant")){ doConstantWork(s, commandList); }
        }
    }

    private void doConstantWork(String line, List<Command> commandList){
        Double argumentValue = Double.parseDouble(line);
        argumentStack.push(argumentValue);
        tryToMakeCommands(commandList);
    }

    private void doCommandWork(Parser params, Parser lang, List<Command> commandList, String line, String commandSyntax, List<String> lines){
        String commandName = lang.getSymbol(line); //get the string name, such as "Forward" or "And"
        if (commandName.equals("NO MATCH")){
            throw new InvalidCommandException(new Throwable(), commandSyntax, line);
        }
        else if (commandName.equals("MakeVariable")){
            dealWithMakingVariables(lines, line, commandSyntax);
        }
        else {
            validCommand(params, commandName, commandList);
        }
    }

    private void dealWithMakingVariables(List<String> lines, String line, String commandSyntax){
        IS_VARIABLE = true;
        List<String> copyLines = new CopyOnWriteArrayList(lines);
        copyLines.remove(line);
        String variable = copyLines.get(0);

        copyLines.remove(variable);
        userCreatedCommands.put(variable, copyLines);
        System.out.println(userCreatedCommands);
    }

    private List<Command> validCommand(Parser params, String commandName, List<Command> commandList) {
        commandStack.push(commandName); //add string to stack
        String commandParams = params.getSymbol(commandName); //get Parameters string, such as "OneDouble" or "TurtleOneDouble"

        double paramsNeeded  = getParamsNeeded(commandParams); //convert that string to a double

        parametersStack.push(paramsNeeded); //add that value to the params stack
        return tryToMakeCommands(commandList);
    }

    private double getParamsNeeded(String commandParams){
        double numberOfParams = ZERO_DOUBLE;
        if (commandParams.contains("OneDouble")){
            numberOfParams = ONE_DOUBLE;
        }
        else if (commandParams.contains("TwoDouble")){
            numberOfParams = TWO_DOUBLE;
        }
        if (commandParams.contains("Turtle")){
            numberOfParams += TURTLE;
        }
        return numberOfParams;
    }

    private List<Command> tryToMakeCommands(List<Command> commandList){
        if(checkArgumentStack()){
            commandList.add(weHaveEnoughArgumentsToMakeACommand());
        }
        return commandList;
    }

    private boolean checkArgumentStack(){
        return !parametersStack.isEmpty() && argumentStack.size() >= parametersStack.peek();
    }

    private Command weHaveEnoughArgumentsToMakeACommand(){
        double numberOfParams = parametersStack.pop(); //to be used in creating the command
        String name = commandStack.pop();
        Command newCommand = getCommand(name, numberOfParams);
        if(commandStack.size()!=0){
            argumentStack.push(newCommand.getResult());
        }
        return newCommand;
    }

    private Command getCommand(String commandName, double numberOfParams){
        try{
            Class commandClass = Class.forName(COMMAND_PACKAGE+commandName);
            Constructor commandConstructor = getCommandConstructor(commandClass, numberOfParams);
            return makeCommand(commandName, numberOfParams, commandConstructor, commandClass);
        } catch (ClassNotFoundException e){
            System.out.println("ClassNotFoundException");
            e.printStackTrace();
            //FIXME !!!!!!!!!!!!
        } catch (NoSuchMethodException e){
            System.out.println("NoSuchMethodException!!");
            e.printStackTrace();
            //FIXME part 2
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException");
            e.printStackTrace();
            //FIXME
        } catch (InstantiationException e) {
            System.out.println("InstantiationException");
            e.printStackTrace();
            //FIXME
        } catch (InvocationTargetException e) {
            System.out.println("InvocationTargetException");
            e.printStackTrace();
            //FIXME
        }
        return new Not(1.0); //FIXME !!!!
    }

    private Constructor getCommandConstructor(Class command, double numberOfParams) throws NoSuchMethodException {
        if(numberOfParams == ONE_DOUBLE){
            return command.getConstructor(new Class[]{Double.class});
        } else if (numberOfParams == ONE_DOUBLE+TURTLE){
            return command.getConstructor(new Class[]{Turtle.class, Double.class});
        } else if (numberOfParams == TWO_DOUBLE){
            return command.getConstructor(new Class[]{Double.class, Double.class});
        } else if (numberOfParams == TWO_DOUBLE+TURTLE){
            return command.getConstructor(new Class[]{Turtle.class, Double.class, Double.class});
        } else if (numberOfParams == ZERO_DOUBLE){
            return command.getConstructor(new Class[]{});
        } else { //if (numberOfParams == TURTLE)
            return command.getConstructor(new Class[]{Turtle.class});
        }
    }

    private Command makeCommand(String name, double numberOfParams, Constructor constructor, Class myClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Command myCommand = new Not(1.0);
        if(numberOfParams == ONE_DOUBLE) {
            myCommand = (Command) constructor.newInstance(argumentStack.pop());
        } else if(numberOfParams == TWO_DOUBLE) {
            myCommand = (Command) constructor.newInstance(argumentStack.pop(), argumentStack.pop());
        } else if(numberOfParams == ONE_DOUBLE+TURTLE) {
            myCommand = (Command) constructor.newInstance(turtle, argumentStack.pop());
        } else if(numberOfParams == TWO_DOUBLE+TURTLE) {
            myCommand = (Command) constructor.newInstance(turtle, argumentStack.pop(), argumentStack.pop());
        } else if(numberOfParams == ZERO_DOUBLE) {
            myCommand = (Command) constructor.newInstance();
        } else if(numberOfParams == TURTLE) {
            myCommand = (Command) constructor.newInstance(turtle);
        }
        return myCommand;
    }

    private void executeCommandList(List<Command> l){
        Collections.reverse(l);
        for(Command c : l){
            System.out.println(c.execute());
            if(c instanceof ClearScreen){
                myView.updatePenStatus(0);
                myView.update(turtle.getX(),turtle.getY(), turtle.getHeading());
                myView.clear();
                myView.updatePenStatus(1);
            } else if(c instanceof HideTurtle || c instanceof ShowTurtle){
                myView.updateTurtleView(c.getResult());
            } else if(c instanceof PenDown || c instanceof PenUp){
                myView.updatePenStatus(c.getResult());
            } else {
                myView.update(turtle.getX(), turtle.getY(), turtle.getHeading());
           }
        }
        myView.playAnimation();//FIXME added by alex, makes the commands play in order (series) rather than on top of each other (parallel)
    }

    private void printCommandList(List<Command> l){  //wont need this in final submission
        Collections.reverse(l);
        for(Command c : l) {
            System.out.println(c.getResult());
            System.out.println(
                    "DURING:: x: " + turtle.getX() + " y: " + turtle.getY() + " heading: " + turtle
                            .getHeading());
        }
    }

    // utility function that reads given file and returns its entire contents as a single string
    /*private String readFileToString (String inputSource) {
        try {
            // this one line is dense, hard to read, and throws exceptions so better to wrap in method
            return new String(Files.readAllBytes(Paths.get(new URI(inputSource))));
        }
        catch (URISyntaxException | IOException e) {
            // NOT ideal way to handle exception, but this is just a simple test program
            System.out.println("ERROR: Unable to read input file " + e.getMessage());
            return "";
        }
    }*/
}