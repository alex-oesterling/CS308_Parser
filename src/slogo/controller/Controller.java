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
import slogo.view.Visualizer;

public class Controller {
    private static final String LANGUAGES_PACKAGE = "resources.languages.";
    private static final String INFORMATION_PACKAGE = "resources.information.";
    private static final String COMMAND_PACKAGE = "slogo.model.command.";
    public static final String WHITESPACE = "\\s+";
    private static final int ONE_DOUBLE = 1;
    private static final int TWO_DOUBLE = 2;
    private static final double ZERO_DOUBLE = 0;
    private static final double TURTLE = -0.5;

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

    private static final boolean RUN_DUVALL = false;

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

    private List<Command> parseText (Parser syntax, Parser lang, Parser params, List<String> lines) {
        boolean startingToRead = true;
        List<Command> commandList = new ArrayList<>();
        for (String line : lines) {
            if (line.trim().length() > 0) {
                String commandSyntax = syntax.getSymbol(line); //get what sort of thing it is
                if(commandSyntax.equals("Command")){
                    String commandName = lang.getSymbol(line); //get the string name, such as "Forward" or "And"
                    if(!commandName.contains("NO MATCH")){
                        validCommand(params, commandName, commandList);
                    } else {
                        //FIXME error
                    }
                } else if (commandSyntax.equals("Constant")){
                    Double argumentValue = Double.parseDouble(line);
                    argumentStack.push(argumentValue);
                    tryToMakeCommands(commandList);
                    //commandList.addAll(tryToMakeCommands(commandList));
                }
            }
        }
        while(commandStack.size()>0){
            tryToMakeCommands(commandList);
            //commandList.addAll(tryToMakeCommands(commandList));
        }
        printCommandList(commandList);
        return commandList;
    }

    private void printCommandList(List<Command> l){
        for(Command c : l){
            System.out.println(c);
            System.out.println(c.execute());
            System.out.println("DURING:: x: "+turtle.getX()+" y: "+turtle.getY()+" heading: "+turtle.getHeading());
            myView.update(turtle.getX(),turtle.getY(), turtle.getHeading());
        }
    }

    private List<Command> validCommand(Parser params, String commandName, List<Command> commandList) {
        commandStack.push(commandName); //add string to stack
        String commandParams = params.getSymbol(commandName); //get Parameters string, such as "OneDouble" or "TurtleOneDouble"

        double paramsNeeded;
        if(RUN_DUVALL){
            paramsNeeded = getParamsNeededDUVALL(commandParams);
        } else {
            paramsNeeded  = getParamsNeeded(commandParams); //convert that string to a double
        }

        //System.out.println("putting paramsNeeded("+paramsNeeded+") on the paramsStack");
        parametersStack.push(paramsNeeded); //add that value to the params stack
        return tryToMakeCommands(commandList);
    }

    private List<Command> tryToMakeCommands(List<Command> commandList){
        if(checkArgumentStack()){
            commandList.add(weHaveEnoughArgumentsToMakeACommand());
        }
        return commandList;
    }

    private Command weHaveEnoughArgumentsToMakeACommand(){
        double numberOfParams = parametersStack.pop(); //to be used in creating the command
        //System.out.println("line 116: num of params: "+numberOfParams);
        String name = commandStack.pop();

        Command newCommand;
        if(RUN_DUVALL) {
            newCommand = getCommandDUVALL(name, numberOfParams);
        } else {
            newCommand = getCommand(name, numberOfParams);
        }

        if(commandStack.size()!=0){
            argumentStack.push(newCommand.getResult());
        }
        return newCommand;
    }

    private Command getCommand(String commandName, double numberOfParams){
        try{
            //System.out.println("Name: "+name);
            Class commandClass = Class.forName(COMMAND_PACKAGE+commandName);
            //System.out.println("Class.forName finished");
            Constructor commandConstructor = getCommandConstructor(commandClass, numberOfParams); //failing here right now
            //System.out.println("Constructor created");
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
    private Command getCommandDUVALL(String commandName, double numberOfParams){
        try{
            //System.out.println("Name: "+name);
            Class commandClass = Class.forName(COMMAND_PACKAGE+commandName);
            //System.out.println("Class.forName finished");
            //Constructor commandConstructor = getCommandConstructor(commandClass, numberOfParams); //failing here right now
            //System.out.println("Constructor created");
            return makeCommandDUVALL(commandClass);
        } catch (ClassNotFoundException e){
            System.out.println("ClassNotFoundException");
            e.printStackTrace();
            //FIXME !!!!!!!!!!!!
        } /*catch (NoSuchMethodException e){
            System.out.println("NoSuchMethodException!!");
            e.printStackTrace();
            //FIXME part 2
        } /*catch (IllegalAccessException e) {
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
        }*/
        return new Not(1.0); //FIXME !!!!
    }

    private Command makeCommand(String name, double numberOfParams, Constructor constructor, Class myClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Command myCommand = new Not(1.0);
        //System.out.println("line162: numberOfParams");
        if(numberOfParams == ONE_DOUBLE) {
            myCommand = (Command) constructor.newInstance(argumentStack.pop());
        } else if(numberOfParams == TWO_DOUBLE) {
            myCommand = (Command) constructor.newInstance(argumentStack.pop(), argumentStack.pop());
        } else if(numberOfParams == ONE_DOUBLE+TURTLE) {
            myCommand = (Command) constructor.newInstance(turtle, argumentStack.pop());
            //System.out.println("popping!! popperoo!");
        } else if(numberOfParams == TWO_DOUBLE+TURTLE) {
            myCommand = (Command) constructor.newInstance(turtle, argumentStack.pop(), argumentStack.pop());
        } else if(numberOfParams == ZERO_DOUBLE) {
            myCommand = (Command) constructor.newInstance();
        } else if(numberOfParams == TURTLE) {
            myCommand = (Command) constructor.newInstance(turtle);
        }
        //argumentStack.push(myCommand.getResult());
        return myCommand;
    }

    private Command makeCommandDUVALL(Class myClass){
        try{
            Constructor<?>[] constructors = myClass.getDeclaredConstructors();
            for(Constructor<?> c : constructors){
                Class<?>[] actual = c.getParameterTypes();
                Class<?>[] expected = new Class<?>[]{Double.class, Double.class};
                if(c.getParameterTypes() == new Class[]{Turtle.class}){
                    return (Command) c.newInstance(turtle);
                } else if(c.getParameterTypes() == new Class[]{Turtle.class, Double.class}) {
                    return (Command) c.newInstance(turtle, argumentStack.pop());
                } else if(c.getParameterTypes() == new Class[]{Turtle.class, Double.class, Double.class}){
                    return (Command) c.newInstance(turtle, argumentStack.pop(), argumentStack.pop());
                } else if(c.getParameterTypes() == new Class[]{Double.class}){
                    return (Command) c.newInstance(argumentStack.pop());
                } else if(c.getParameterTypes().equals(new Class<?>[]{Double.class, Double.class})){
                    return (Command) c.newInstance(argumentStack.pop(), argumentStack.pop());
                } else if(c.getParameterTypes() == new Class[]{}){
                    return (Command) c.newInstance();
                } else if(c.getParameterTypes().equals(new Class<?>[]{java.lang.Double.class, java.lang.Double.class})){
                    return (Command) c.newInstance(argumentStack.pop(), argumentStack.pop());
                }
            }
        } catch (Exception e){
            System.out.println("duvall Exception");
            e.printStackTrace();
        }
        return new Not(1.0);
    }

    private Constructor getCommandConstructor(Class command, double numberOfParams) throws NoSuchMethodException {
        //System.out.println("reached getCommandConstructor()");
        if(numberOfParams == ONE_DOUBLE){
            //System.out.println("double");
            return command.getConstructor(new Class[]{Double.class});
        } else if (numberOfParams == ONE_DOUBLE+TURTLE){
            //System.out.println("turtle, double");
            return command.getConstructor(new Class[]{Turtle.class, Double.class});
        } else if (numberOfParams == TWO_DOUBLE){
            //System.out.println("double, double");
            return command.getConstructor(new Class[]{Double.class, Double.class});
        } else if (numberOfParams == TWO_DOUBLE+TURTLE){
            //System.out.println("turtle, double, double");
            return command.getConstructor(new Class[]{Turtle.class, Double.class, Double.class});
        } else if (numberOfParams == ZERO_DOUBLE){
            //System.out.println("you take nothing");
            return command.getConstructor(new Class[]{});
        } else { //if (numberOfParams == TURTLE)
            //System.out.println("you need a turtle");
            return command.getConstructor(new Class[]{Turtle.class});
        }
    }

    private boolean checkArgumentStack(){
        return argumentStack.size() >= parametersStack.peek();
    }

    private double getParamsNeeded(String commandParams){
        //System.out.println("line 204: "+commandParams);
        double numberOfParams = ZERO_DOUBLE;
        if (commandParams.contains("OneDouble")){
            numberOfParams = ONE_DOUBLE;
            //System.out.println("you have one double param: + "+numberOfParams);
        }
        else if (commandParams.contains("TwoDouble")){
            numberOfParams = TWO_DOUBLE;
            //System.out.println("you have two double params: + "+numberOfParams);
        }
        if (commandParams.contains("Turtle")){
            numberOfParams += TURTLE;
            //System.out.println("You have a turtle parameter: "+numberOfParams);
        }
        //System.out.println("Number of params after getParamsNeeded("+commandParams+"): "+numberOfParams);
        return numberOfParams;
    }

    private double getParamsNeededDUVALL(String commandParams){
        //System.out.println("line 204: "+commandParams);
        double numberOfParams = ZERO_DOUBLE;
        if (commandParams.contains("OneDouble")){
            numberOfParams = ONE_DOUBLE;
            //System.out.println("you have one double param: + "+numberOfParams);
        }
        else if (commandParams.contains("TwoDouble")){
            numberOfParams = TWO_DOUBLE;
            //System.out.println("you have two double params: + "+numberOfParams);
        }
        //System.out.println("Number of params after getParamsNeeded("+commandParams+"): "+numberOfParams);
        return numberOfParams;
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

    //TODO DELETE ME
    public Turtle getTurtle(){
        return turtle;
    }

    public static void main(String[] args) {
        Controller c = new Controller(new Visualizer(), "English");
        //c.addLanguage("English");
        System.out.println("BEFORE:: x: "+c.getTurtle().getX()+" y: "+c.getTurtle().getY()+" heading: "+c.getTurtle().getHeading());

        String test = "fd 50 left 90 fd 20"; //two commands that should execute on their own x:50, y:20, h:0
        String test2 = "fd + 30 30"; //two linked commands x:60, y:0, h:90
        String test3 = "fd not 0"; //two linked commands of different types x:1, y:0, h:90
        String test4 = "fd pi"; //two linked commands of different types x:3.14, y:0, h:90
        String test5 = "pi"; //no turtle involved x:0, y:0, h:90
        String test6 = "fd fd fd 30"; //final: x:1, y:90, h: 0
        String test7 = "fd sum sum sum 10 20 30 40";

        c.sendCommands(test6);
        System.out.println(" AFTER:: x: "+c.getTurtle().getX()+" y: "+c.getTurtle().getY()+" heading: "+c.getTurtle().getHeading());

    }
}
