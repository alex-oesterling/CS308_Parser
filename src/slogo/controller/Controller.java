package slogo.controller;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.regex.Pattern;
import slogo.exceptions.InvalidCommandException;
import slogo.model.Parser;
import slogo.model.Turtle;
import slogo.model.command.Command;
import slogo.view.Visualizer;

public class Controller {
    private static final String RESOURCES_PACKAGE = "resources.languages.";
    private static final String COMMAND_PACKAGE = "slogo.model.command";
    public static final String WHITESPACE = "\\s+";

    private List<Entry<String, Pattern>> mySymbols;
    private Stack<Command> commandStack;
    Turtle turtle = new Turtle();
    Errors error = new Errors();
    Command command;
    Parser myParser;
    Visualizer myView;

    public Controller(Parser parser, Visualizer visualizer) {
        myParser = parser;
        myView = visualizer;
        mySymbols = new ArrayList<>();
    }

    /**
     * Adds the given resource file to this language's recognized types
     */
    public void addLanguage (String syntax) {
        ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PACKAGE + syntax);
        for (String key : Collections.list(resources.getKeys())) {
            String regex = resources.getString(key);
            mySymbols.add(new SimpleEntry<>(key,
                Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
        }
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
                        commandClass = Class.forName(COMMAND_PACKAGE + ".turtlecommand."+ command);
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

    /**
     * Gets parsed command from model
     * @return command
     */
    public double returnCommand(){
        return command.getResult();
    }

    /**
     * Gets x position from Turtle class
     * @return double
     */
    public double getXPosition(){
        return turtle.getX();
    }

    /**
     * Gets y position from Turtle class
     * @return double
     */
    public double getYPosition(){
        return turtle.getY();
    }

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
