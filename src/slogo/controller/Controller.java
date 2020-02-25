package slogo.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import slogo.exceptions.InvalidCommandException;
import slogo.model.Parser;
import slogo.model.Turtle;
import slogo.model.command.*;
import slogo.model.command.booleancommand.*;

import slogo.view.Visualizer;

public class Controller {
    private static final String LANGUAGES_PACKAGE = "resources.languages.";
    private static final String INFORMATION_PACKAGE = "resources.information.";
    public static final String WHITESPACE = "\\s+";

    private List<Entry<String, Pattern>> mySymbols;
    private Stack<Command> commandStack, argumentStack;
    private Map<String, String> userCreatedCommands;
    private Turtle turtle = new Turtle();
    private Errors error = new Errors();
    private String myCommands;
    private Visualizer myView;
    private Parser inputParser, parametersParser;

    public Controller(Visualizer visualizer, String language) {
        myView = visualizer;
        mySymbols = new ArrayList<>();
        commandStack = new Stack<>();
        argumentStack = new Stack<>();
        inputParser = new Parser(LANGUAGES_PACKAGE);
        inputParser.addPatterns("Syntax");
        parametersParser = new Parser(INFORMATION_PACKAGE);
        parametersParser.addPatterns("Parameters");
    }

    /**
     * Adds the given resource file to this language's recognized types
     */
    public void addLanguage (String language) {
        inputParser.addPatterns(language);
    }

    /**
     * get a new String of commands
     */
    public void sendCommands(String commands){
        myCommands = commands;
        parseText(inputParser, asList(myCommands.split(WHITESPACE)));
    }

    private List<String> asList(String[] array){
        List<String> list = new ArrayList<>();
        for(String s : array){
            list.add(s);
        }
        return list;
    }


    /**
     * Returns language's type associated with the given text if one exists
     */
    public String getSymbol (String text) {
        final String ERROR = "NO MATCH";
        for (Entry<String, Pattern> e : mySymbols) {
            if (match(text, e.getValue())) {
                return e.getKey();
            }
        }
        // FIXME: perhaps throw an exception instead
        return ERROR;
    }

    /**
     * Returns true if the given text matches the given regular expression pattern
     */
    private boolean match (String text, Pattern regex) {
        return regex.matcher(text).matches();
    }

    //get command from the view and give to model, need a reference to the command class
    public void runCommand(String text){
        List<List<String>> commands = new ArrayList<List<String>>();
        List<String> lines = Arrays.asList(text.split("\n"));
        for(String line : lines){
            commands.add(Arrays.asList(line.split(WHITESPACE)));
        }
        for(List<String> line : commands){
            for(String command : line) {
                if (command.trim().length() > 0) {
                    System.out.println(getSymbol(command));
                    Class commandClass = null;
                    try {
                        commandClass = Class.forName(INFORMATION_PACKAGE + ".turtlecommand."+ command);
                        commandStack.push((Command) (commandClass.getConstructor().newInstance()));
                    } catch (ClassNotFoundException | //FIXME generic error handling by alex. could make better possible but low priority
                        InstantiationException |
                        InvocationTargetException |
                        NoSuchMethodException |
                        IllegalAccessException e) {
                        throw new InvalidCommandException(e);
                    }
                }
            }
        }
//        myParser.readCommandFromString(command);
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

    // given some text, prints results of parsing it using the given language
    private List<Command> parseText (Parser lang, Parser params, List<String> lines) {
        List<Command> commandList = new ArrayList<>();
        for (String line : lines) {
            if (line.trim().length() > 0) {
                //TODO make command objects
                String commandName = lang.getSymbol(line);
                String commandParams = params.getSymbol(line);
                System.out.println(String.format("%s : %s, requires %s", line, commandName, commandParams));
                Command command = makeCommandObject(commandName, commandParams);
                commandList.add(command);
            }
        }
        System.out.println();
        return commandList;
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

    /*
    private void makeCommandMap(){
        sLogoCommands = new HashMap<String, String>();
        Set<String> keys = resourceBundle.keySet();
        for(String key : keys){
            sLogoCommands.putIfAbsent(key, resourceBundle.getString(key));
        }
    }*/

    /**
     * Takes an error from the model
     * @param e
     */

    //FIXME commented out by Alex. when creating the error class its constructor creates a new error and there is a stack overflow
    public void setError(Errors e){
        this.error = e;
    }

    //do actions that buttons execute

    //for the help button, gets a list of commands
//    public List<String> getCommandsList(){
//
//        return list;
//    }


}
