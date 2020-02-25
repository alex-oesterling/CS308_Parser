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
import slogo.exceptions.InvalidCommandException;
import slogo.model.Parser;
import slogo.model.Turtle;
import slogo.model.command.*;
import slogo.model.command.booleancommand.Not;
import slogo.view.Visualizer;

public class Controller {
    private static final String LANGUAGES_PACKAGE = "resources.languages.";
    private static final String INFORMATION_PACKAGE = "resources.information.";
    public static final String WHITESPACE = "\\s+";
    private static final int ONE_DOUBLE = 1;
    private static final int TWO_DOUBLE = 2;
    private static final int ZERO_DOUBLE = 0;
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
                    System.out.println("line 107: "+commandName);
                    if(!commandName.contains("NO MATCH")){
                        commandStack.push(commandName); //add string to stack
                        String commandParams = params.getSymbol(commandName); //get Parameters string, such as "OneDouble" or "TurtleOneDouble"
                        double paramsNeeded  = getParamsNeeded(commandParams); //convert that string to a double
                        System.out.println("putting paramsNeeded("+paramsNeeded+") on the paramsStack");
                        parametersStack.push(paramsNeeded); //add that value to the params stack
                        if (checkArgumentStack()){ //we have the right number of arguments
                            weHaveEnoughArgumentsToMakeACommand();
                        }
                    } else {
                        //FIXME error
                    }
                } else if (commandSyntax.equals("Constant")){
                    Double argumentValue = Double.parseDouble(line);
                    System.out.println("putting argument ("+argumentValue+") on the argsStack");
                    argumentStack.push(argumentValue);
                    if (checkArgumentStack()){ //we have the right number of arguments
                        weHaveEnoughArgumentsToMakeACommand();
                    }
                }
            }
        }
        System.out.println();
        return commandList;
    }

    private void weHaveEnoughArgumentsToMakeACommand(){
        double numberOfParams = parametersStack.pop(); //to be used in creating the command
        System.out.println("line 116: num of params: "+numberOfParams);
        String name = commandStack.pop();
        System.out.println("Trying to make "+name+" command with "+numberOfParams+" parameters using the argument "+argumentStack.peek());
        Command newCommand = getCommand(name, numberOfParams);
    }

    private Command getCommand(String name, double numberOfParams){
        try{
            System.out.println("Name: "+name);
            Class command = Class.forName("slogo.model.command."+name);
            System.out.println("Class.forName finished");
            Constructor constructor = getCommandConstructor(command, numberOfParams); //failing here right now
            System.out.println("Constructor created");
            Command myCommand = makeCommand(name, numberOfParams, constructor, command);
            return myCommand;
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

    private Command makeCommand(String name, double numberOfParams, Constructor constructor, Class myClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Command myCommand = new Not(1.0);
        System.out.println("line162: numberOfParams");
        if(numberOfParams == ONE_DOUBLE) {
            myCommand = (Command) constructor.newInstance(argumentStack.pop());
        } else if(numberOfParams == TWO_DOUBLE) {
            myCommand = (Command) constructor.newInstance(argumentStack.pop(), argumentStack.pop());
        } else if(numberOfParams == ONE_DOUBLE+TURTLE) {
            myCommand = (Command) constructor.newInstance(turtle, argumentStack.pop());
            System.out.println("popping!! popperoo!");
        } else if(numberOfParams == TWO_DOUBLE+TURTLE) {
            myCommand = (Command) constructor.newInstance(turtle, argumentStack.pop(), argumentStack.pop());
        } else if(numberOfParams == ZERO_DOUBLE) {
            myCommand = (Command) constructor.newInstance();
        } else if(numberOfParams == TURTLE) {
            myCommand = (Command) constructor.newInstance(turtle);
        }
        argumentStack.push(myCommand.getResult());
        return myCommand;
        //FIXME it shouldn't be this mofo
    }
    //commandStack.push((Command) (commandClass.getConstructor().newInstance()));

    private Constructor getCommandConstructor(Class command, double numberOfParams) throws NoSuchMethodException {
        System.out.println("reached getCommandConstructor()");
        if(numberOfParams == ONE_DOUBLE){
            System.out.println("double");
            return command.getConstructor(new Class[]{Double.class});
        } else if (numberOfParams == ONE_DOUBLE+TURTLE){
            System.out.println("turtle, double");
            return command.getConstructor(new Class[]{Turtle.class, Double.class});
        } else if (numberOfParams == TWO_DOUBLE){
            System.out.println("double, double");
            return command.getConstructor(new Class[]{Double.class, Double.class});
        } else if (numberOfParams == TWO_DOUBLE+TURTLE){
            System.out.println("turtle, double, double");
            return command.getConstructor(new Class[]{Turtle.class, Double.class, Double.class});
        } else if (numberOfParams == ZERO_DOUBLE){
            System.out.println("you take nothing");
            return command.getConstructor(new Class[]{});
        } else { //if (numberOfParams == TURTLE)
            System.out.println("you need a turtle");
            return command.getConstructor(new Class[]{Turtle.class});
        }
    }

    private Command makeCommandObject(String name, String params){
        return new Not(0.0);
    }

    private boolean checkArgumentStack(){
        return argumentStack.size() >= parametersStack.peek();
    }

    private double getParamsNeeded(String commandParams){
        System.out.println("line 204: "+commandParams);
        double numberOfParams = ZERO_DOUBLE;
        if (commandParams.contains("OneDouble")){
            numberOfParams = ONE_DOUBLE;
            System.out.println("you have one double param: + "+numberOfParams);
        }
        else if (commandParams.contains("TwoDouble")){
            numberOfParams = TWO_DOUBLE;
            System.out.println("you have two double params: + "+numberOfParams);
        }
        if (commandParams.contains("Turtle")){
            numberOfParams += TURTLE;
            System.out.println("You have a turtle parameter: "+numberOfParams);
        }
        System.out.println("Number of params after getParamsNeeded("+commandParams+"): "+numberOfParams);
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
    //TODO DELETE ME
    public Turtle getTurtle(){
        return turtle;
    }

    public static void main(String[] args) {
        Controller c = new Controller(new Visualizer(), "English");
        c.addLanguage("English");
        System.out.println("x: "+c.getTurtle().getX()+" y: "+c.getTurtle().getY()+" h: "+c.getTurtle().getHeading());
        /*Forward f = new Forward(c.getTurtle(), 50);
       // c.getVisualizer().update(c.getTurtle().getX(), c.getTurtle().getY(), c.getTurtle().getHeading());
        Right r = new Right(c.getTurtle(), 90);
        //c.getVisualizer().update(c.getTurtle().getX(), c.getTurtle().getY(), c.getTurtle().getHeading());
        Sum s = new Sum(50, 50);
        Forward f2 = new Forward(c.getTurtle(), s.getResult());
        //c.getVisualizer().update(c.getTurtle().getX(), c.getTurtle().getY(), c.getTurtle().getHeading());
        Home h = new Home(c.getTurtle());
        //c.getVisualizer().update(c.getTurtle().getX(), c.getTurtle().getY(), c.getTurtle().getHeading());
        SetTowards st = new SetTowards(c.getTurtle(), 100, 100);
        //c.getVisualizer().update(c.getTurtle().getX(), c.getTurtle().getY(), c.getTurtle().getHeading());
        Forward f3 = new Forward(c.getTurtle(), 50);
        //c.getVisualizer().update(c.getTurtle().getX(), c.getTurtle().getY(), c.getTurtle().getHeading());
        s = new Sum(100,30);
        Left l = new Left(c.getTurtle(),45);
        SetPosition sp = new SetPosition(c.getTurtle(), s.getResult(), l.getResult());
        */
        String test = "fd 50 fd 20";
        String test2 = "+ 30 30";
        String test3 = "not 3";

        c.sendCommands(test);
        System.out.println("x: "+c.getTurtle().getX()+" y: "+c.getTurtle().getY()+" h: "+c.getTurtle().getHeading());

    }
}
