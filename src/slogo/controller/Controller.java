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
import java.util.regex.Pattern;

import slogo.model.Parser;
import slogo.model.Turtle;
import slogo.model.command.*;
import slogo.model.command.booleancommand.*;

import slogo.model.command.turtlecommand.Forward;
import slogo.view.Visualizer;

public class Controller {
    private static final String LANGUAGES_PACKAGE = "resources.languages.";
    private static final String INFORMATION_PACKAGE = "resources.information.";
    public static final String WHITESPACE = "\\s+";
    private static final int ONE_DOUBLE = 1;
    private static final int TWO_DOUBLE = 2;
    private static final int ZERO_DOUBLE = 0;
    private static final double TURTLE = -0.1;

    private List<Entry<String, Pattern>> mySymbols;
    private Stack<String> commandStack;
    private Stack<Double> argumentStack, parametersStack;
    private Map<String, String> userCreatedCommands;
    private Turtle turtle = new Turtle();
    private Errors error = new Errors();
    private String myCommands;
    private Visualizer myView;
    private Parser commandParser, parametersParser, syntaxParser;
    private ResourceBundle languagesBundle;

    public Controller(Visualizer visualizer, String language) {
        myView = visualizer;
        mySymbols = new ArrayList<>();
        commandStack = new Stack<>();
        argumentStack = new Stack<>();
        parametersStack = new Stack<>();
        commandParser = new Parser(LANGUAGES_PACKAGE);
        commandParser.addPatterns(language);
        syntaxParser = new Parser(LANGUAGES_PACKAGE);
        syntaxParser.addPatterns("Syntax");
        parametersParser = new Parser(INFORMATION_PACKAGE);
        parametersParser.addPatterns("Parameters");
    }

    /**
     * Adds the given resource file to this language's recognized types
     */
    public void addLanguage (String language) {
        commandParser.addPatterns(language);
        languagesBundle = ResourceBundle.getBundle(LANGUAGES_PACKAGE+language);
    }

    /**
     * get a new String of commands
     */
    public void sendCommands(String commands){
        myCommands = commands;
        parseText(syntaxParser, commandParser, parametersParser, asList(myCommands.split(WHITESPACE)));
    }

    private List<String> asList(String[] array){
        List<String> list = new ArrayList<>();
        for(String s : array){
            list.add(s);
        }
        return list;
    }



//
//    //get command from the view and give to model, need a reference to the command class
//    public void runCommand(String text){
//        List<List<String>> commands = new ArrayList<List<String>>();
//        List<String> lines = Arrays.asList(text.split("\n"));
//        for(String line : lines){
//            commands.add(Arrays.asList(line.split(WHITESPACE)));
//        }
//        for(List<String> line : commands){
//            for(String command : line) {
//                if (command.trim().length() > 0) {
//                    System.out.println(getSymbol(command));
//                    Class commandClass = null;
//                    try {
//                        commandClass = Class.forName(INFORMATION_PACKAGE + ".turtlecommand."+ command);
//                        commandStack.push((Command) (commandClass.getConstructor().newInstance()));
//                    } catch (ClassNotFoundException | //FIXME generic error handling by alex. could make better possible but low priority
//                        InstantiationException |
//                        InvocationTargetException |
//                        NoSuchMethodException |
//                        IllegalAccessException e) {
//                        throw new InvalidCommandException(e);
//                    }
//                }
//            }
//        }
////        myParser.readCommandFromString(command);
//    }

    // utility function that reads given file and returns its entire contents as a single string
    private String readFileToString (String inputSource) {
        try {
            // this one line is dense, hard to read, and throws exceptions so better to wrap in method
            return new String(Files.readAllBytes(Paths.get(new URI(inputSource))));
        }
        catch (URISyntaxException | IOException e) {
            // NOT ideal way to handle exception, but this is just a simple test program
            System.out.println("ERROR: Unable to read input file " + e.getMessage());
            return "";
        }
    }

    // given some text, prints results of parsing it using the given language
    private List<Command> parseText (Parser syntax, Parser lang, Parser params, List<String> lines) {
        List<Command> commandList = new ArrayList<>();
        for (String line : lines) {
            if (line.trim().length() > 0) {
                //TODO make command objects
                String commandSyntax = syntax.getSymbol(line);
                if(commandSyntax.equals("Command")){
                    String commandName = lang.getSymbol(line);
                    if(!commandName.equals("NO MATCH")){
                        commandStack.push(commandName); //add string to stack
                        String commandParams = params.getSymbol(line);
                        double paramsNeeded  = getParamsNeeded(commandParams);
                        parametersStack.push(paramsNeeded);
                        if (checkArgumentStack()){
                            double numberOfParams = parametersStack.pop();
                            String name = commandStack.pop();
                            Command newCommand = getCommand(name, numberOfParams);
                        }
                    } else {
                        //FIXME error
                    }
                } else if (commandSyntax.equals("Constant")){
                    argumentStack.push(Double.parseDouble(lang.getSymbol(line)));
                }
            }
        }
        System.out.println();
        return commandList;
    }

    private Command getCommand(String name, double numberOfParams){
        try{
            Class command = Class.forName(name);
            Constructor constructor = getCommandConstructor(command, numberOfParams);
            Command myCommand = makeCommand(name, numberOfParams, constructor, command);
            return myCommand;
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            //FIXME !!!!!!!!!!!!
        } catch (NoSuchMethodException e){
            e.printStackTrace();
            //FIXME part 2
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            //FIXME
        } catch (InstantiationException e) {
            e.printStackTrace();
            //FIXME
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            //FIXME
        }
        return new Not(1.0); //FIXME !!!!
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
        //FIXME it shouldn't be this mofo

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

    private boolean checkArgumentStack(){
        return argumentStack.size() >= parametersStack.peek();
    }

    private int getParamsNeeded(String commandParams){
        int numberOfParams = ZERO_DOUBLE;
        if (commandParams.contains("OneDouble")){
            numberOfParams = ONE_DOUBLE;
        }
        else if (commandParams.contains("TwoDouble")){
            numberOfParams = TWO_DOUBLE;
        }
        else if (commandParams.contains("Turtle")){
            numberOfParams += TURTLE;
        }
        return numberOfParams;
    }

    private Command makeCommandObject(String name, String params){
        return new Not(0.0);
    }

    /**
     * Gets parsed command from model
     * @return command
     */
    public double returnCommand(){
        //FIXME return a list of turtle movements?
        return 0.0;// myCommand.getResult();
    }

    /**
     * Takes an error from the model
     * @param e
     */
    //FIXME commented out by Alex. when creating the error class its constructor creates a new error and there is a stack overflow
    public void setError(Errors e){
        this.error = e;
    }

}
