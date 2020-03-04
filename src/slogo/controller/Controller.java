package slogo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import slogo.exceptions.InvalidCommandException;
import slogo.exceptions.InvalidTurtleException;
import slogo.model.Parser;
import slogo.model.Turtle;
import slogo.model.command.*;
import slogo.view.ViewExternal;
import slogo.fun.RomanNumerals;

public class Controller {
    private static final String LANGUAGES_PACKAGE = "resources.languages.";
    private static final String INFORMATION_PACKAGE = "resources.information.";
    private static final String COMMAND_PACKAGE = "slogo.model.command.";
    private static final String COMMAND = "Command";
    private static final String CONSTANT = "Constant";
    private static final String NO_MATCH = "NO MATCH";
    private static final String VARIABLE = "Variable";
    private static final String MAKE_VARIABLE = "MakeVariable";
    private static final String WHITESPACE = "\\s+";
    private static final int ZERO = 0;
    private static final int ONE_DOUBLE_PARAM_VALUE = 1;
    private static final int TWO_DOUBLE_PARAM_VALUE = 2;
    private static final Integer SECOND_GEN = 2;
    private static final double ZERO_DOUBLE_PARAM_VALUE = 0;
    private static final double TURTLE_PARAM_VALUE = -0.5;
    private static boolean IS_FIRST_CONSTANT = false;
    private static boolean IS_VARIABLE = false;

    private List<Entry<String, Pattern>> mySymbols;
    private Stack<String> commandStack;
    private Stack<Double> argumentStack, doubleAndTurtleParametersStack, listParametersStack;
    private Stack<List<Command>> listStack;
    private Map<String, List> userCreatedCommandVariables;
    private Map<String, String> userCreatedConstantVariables;
    private Map<String, Turtle> turtleMap;
    private Map<String, Integer> nameCount;
    private Turtle turtle;
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
        makeMaps();
        makeStacks();

        commandParser = new Parser(LANGUAGES_PACKAGE);
        commandParser.addPatterns(language);

        syntaxParser = new Parser(LANGUAGES_PACKAGE);
        syntaxParser.addPatterns("Syntax");

        parametersParser = new Parser(INFORMATION_PACKAGE);
        parametersParser.addPatterns("Parameters");
    }

    private void makeMaps() {
        userCreatedCommandVariables = new HashMap<>();
        userCreatedConstantVariables = new HashMap<>();
        turtleMap = new HashMap<>();
        nameCount = new HashMap<>();
    }

    private void makeStacks() { //TODO remove duplicate with resetStacks()
        commandStack = new Stack<>();
        argumentStack = new Stack<>();
        doubleAndTurtleParametersStack = new Stack<>();
        listParametersStack = new Stack<>();
        listStack = new Stack<>();
    }

    /**
     * Add a new turtle to the map of turtles, change the current turtle to
     * this newly created turtle; turtle with a default "name" that is its hashcode
     */
    public void addTurtle(){
        Turtle t = new Turtle();
        if(nameCount.containsKey(t.getName())){
            Integer generation = nameCount.get(t.getName());
            nameCount.put(t.getName(), nameCount.get(t.getName())+1);
            RomanNumerals rn = new RomanNumerals();
            t.setName(t.getName() + " " + rn.intToNumeral(generation));
        }
        nameCount.putIfAbsent(t.getName(), SECOND_GEN);
        if(turtleMap.containsKey(t.getName())){
            throw new InvalidTurtleException("Turtle already exists", new Throwable()); //shouldn't ever get to this
        }
        turtleMap.putIfAbsent(t.getName(), t);
        turtle = t;
    }

    public void addTurtle(String name, double startingX, double startingY, int startingHeading){
        Turtle t = new Turtle(name, startingX, startingY, startingHeading);
        if(turtleMap.containsKey(t.getName())){
            throw new InvalidTurtleException("Turtle already exists", new Throwable()); //shouldn't ever get to this
        }
        turtleMap.putIfAbsent(t.getName(), t);
        turtle = t;
    }

    /**
     * get the name of the current turtle
     * @return turtle's name
     */
    public String getTurtleName(){
        return turtle.getName();
    }

    public void updateCommandVariable(String key, String newValue){
        if(userCreatedConstantVariables.containsKey(key)){
            userCreatedConstantVariables.put(key, newValue);
        }
    }

    public void addUserVariable(String key, String value){
        userCreatedConstantVariables.putIfAbsent(key, value);
    }

    public void addUserCommand(String key, String syntax){
        List<String> commandList = Arrays.asList(syntax.split(" "));
        userCreatedCommandVariables.putIfAbsent(key, commandList);
    }

    /**
     * Allows the user to pick a turtle to do work on
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
    public void reset(){ //TODO rename to resetAll?
        turtleMap = new HashMap<>();
        nameCount = new HashMap<>();
        addTurtle();
        resetStacks();
    }

    private void resetStacks(){ //INTENTIONALLY MAKING NEW STACKS RATHER THAN CLEARING
        argumentStack = new Stack<>();
        commandStack = new Stack<>();
        doubleAndTurtleParametersStack = new Stack<>();
        listStack = new Stack<>();
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
        List<Command> currentList = commandList;
        ListIterator<String> iterator = lines.listIterator();
        String isFirstConstant = lines.get(ZERO);

        if (syntax.getSymbol(isFirstConstant).equals(CONSTANT)){
            System.out.println("Sorry but you can't start your command with a constant");
            throw new InvalidCommandException(new Throwable(), syntax.getSymbol(isFirstConstant), isFirstConstant);
            //FIXME Need to add an exception (better one) here
        }
        Stack<String> commandStackHolder = new Stack<>();
        Stack<Double> argumentStackHolder = new Stack<>();
        Stack<Double> parametersStackHolder = new Stack<>();
        Stack<Double> listParametersStackHolder = new Stack<>();
        Stack<List<Command>> listStackHolder;
        while(iterator.hasNext() && !IS_VARIABLE) {
            String line = iterator.next();
            if (line.trim().length() > 0) {
                String commandSyntax = syntax.getSymbol(line); //get what sort of thing it is
                if(commandSyntax.equals(COMMAND)){
                    doCommandWork(params, lang, syntax, currentList, line, commandSyntax, lines);
                } else if (commandSyntax.equals(CONSTANT)){
                    doConstantWork(line, currentList);
                } else if (commandSyntax.equals(VARIABLE)){
                    if(userCreatedCommandVariables.containsKey(line)){
                        doCommandVariable(line, syntax, lang, params, currentList);
                    }
                    else if (userCreatedConstantVariables.containsKey(line)){
                        doConstantVariable(line, currentList);
                    }
                    else{
                        System.out.println("This variable does not exist yet");
                        throw new InvalidCommandException(new Throwable(), commandSyntax, line);
                        //FIXME Need to add an exception (better one) here
                    }
                } else if (commandSyntax.equals("ListStart")){
                    List<Command> tempList = new ArrayList<>();
                    commandStackHolder = commandStack;
                    argumentStackHolder = argumentStack;
                    parametersStackHolder = doubleAndTurtleParametersStack;
                    listStackHolder = listStack;
                    listParametersStackHolder = listParametersStack;
                    resetStacks();
                    listStack = listStackHolder;
                    currentList = tempList;
                } else if (commandSyntax.equals("ListEnd")){
                    listStack.push(currentList);
                    commandStack = commandStackHolder;
                    argumentStack = argumentStackHolder;
                    doubleAndTurtleParametersStack = parametersStackHolder;
                    listParametersStack = listParametersStackHolder;
                    currentList = commandList;
                }
            }
        }
        IS_VARIABLE = false;
        while(commandStack.size()>0 ){
            tryToMakeCommands(commandList);
        }
        commandList = fillCommands(commandList);
        executeCommandList(commandList);
        return commandList;
    }

    private void doCommandWork(Parser params, Parser lang, Parser syntax, List<Command> commandList, String line, String commandSyntax, List<String> lines){
        String commandName = lang.getSymbol(line); //get the string name, such as "Forward" or "And"
        if (commandName.equals(NO_MATCH)){
            throw new InvalidCommandException(new Throwable(), commandSyntax, line);
        }
        else if (commandName.equals("MakeVariable")){
            dealWithMakingVariables(lines, line, syntax);
        } else {
            validCommand(params, commandName, commandList);
        }
    }

    private void dealWithMakingVariables(List<String> lines, String line, Parser syntax){
        IS_VARIABLE = true;
        List<String> copyLines = new CopyOnWriteArrayList(lines);
        copyLines.remove(line);
        String variable = copyLines.get(0);
        String firstCommand = copyLines.get(1);
        String type = syntax.getSymbol(firstCommand);
        copyLines.remove(variable);
        if (copyLines.size() > 1 || (copyLines.size() == 1 && type.equals("Command"))){
            if (!userCreatedConstantVariables.containsKey(variable)){
                userCreatedCommandVariables.put(variable, copyLines);                           //Added by Alex to interface with view
                String commandSyntax = String.join(" ", copyLines.toArray(new String[0]));
                myView.addCommand(variable, commandSyntax);
            }
            else { System.out.println("Variable already defined as a constant variable"); } //FIXME make an exception thrown
        }
        else if (copyLines.size() == 1 && type.equals("Constant")){
            if (!userCreatedCommandVariables.containsKey(variable)){
                userCreatedConstantVariables.put(variable, firstCommand);                       //Added by Alex to interface with view
                myView.addVariable(variable, firstCommand);
            }
            else { System.out.println("Variable already defined as a command variable"); } //FIXME make an exception thrown
        }
    }

    private List<Command> validCommand(Parser params, String commandName, List<Command> commandList) {
        commandStack.push(commandName); //add string to stack
        String commandParams = params.getSymbol(commandName); //get Parameters string, such as "OneDouble" or "TurtleOneDouble"

        double paramsNeeded  = getParamsNeeded(commandParams); //convert that string to a double
        doubleAndTurtleParametersStack.push(paramsNeeded); //add that value to the params stack

        return tryToMakeCommands(commandList);
    }

    private void doCommandVariable(String line, Parser syntax, Parser lang, Parser params, List<Command> commandList){
        List<String> variableDoesWhat = userCreatedCommandVariables.get(line);
        for (String s : variableDoesWhat){
            String comSyntax = syntax.getSymbol(s);
            if (comSyntax.equals(COMMAND)){ doCommandWork(params, lang, syntax, commandList, s, comSyntax, variableDoesWhat); }
            else if (comSyntax.equals(CONSTANT)){ doConstantWork(s, commandList); }
        }
    }

    private void doConstantVariable(String line, List commandList){
        String constant = userCreatedConstantVariables.get(line);
        doConstantWork(constant, commandList);
    }

    private void doConstantWork(String line, List<Command> commandList){
        Double argumentValue = Double.parseDouble(line);
        argumentStack.push(argumentValue);
        tryToMakeCommands(commandList);
    }

    private double getParamsNeeded(String commandParams){ //TODO make another properties file that has OneDouble=1.0; TwoDouble=2.0;
        double numberOfParams = ZERO_DOUBLE_PARAM_VALUE;
        double listParam = ZERO_DOUBLE_PARAM_VALUE;
        if (commandParams.contains("OneDouble")){
            numberOfParams = ONE_DOUBLE_PARAM_VALUE;
        } else if (commandParams.contains("TwoDouble")){
            numberOfParams = TWO_DOUBLE_PARAM_VALUE;
        }
        if (commandParams.contains("Turtle")){
            numberOfParams += TURTLE_PARAM_VALUE;
        }
        if(commandParams.contains("OneList")) {
            listParam = ONE_DOUBLE_PARAM_VALUE;
        } else if (commandParams.contains("TwoList")) {
            listParam = TWO_DOUBLE_PARAM_VALUE;
        }
        listParametersStack.push(listParam);
        return numberOfParams;
    }

    private List<Command> tryToMakeCommands(List<Command> commandList){
        if(checkArgumentStack()&&checkListStack()){
            commandList.add(weHaveEnoughArgumentsToMakeACommand(commandList));
        }
        return commandList;
    }

    private boolean checkArgumentStack(){
        return !doubleAndTurtleParametersStack.isEmpty() && argumentStack.size() >= doubleAndTurtleParametersStack.peek();
    }

    private boolean checkListStack(){
        return !listParametersStack.isEmpty() && (listStack.size() >= listParametersStack.peek());
    }

    private Command weHaveEnoughArgumentsToMakeACommand(List<Command> commands){
        double numberOfParams = doubleAndTurtleParametersStack.pop(); //to be used in creating the command
        String name = commandStack.pop();
        Command newCommand = getCommand(name, numberOfParams);
        if(commandStack.size()!=0){
            argumentStack.push(newCommand.getResult());
            tryToMakeCommands(commands); //slightly recursive :D
        }
        return newCommand;
    }

    private Command getCommand(String commandName, double numberOfParams){
        try{
            Class commandClass = Class.forName(COMMAND_PACKAGE+commandName);
            Constructor commandConstructor = getCommandConstructor(commandClass, numberOfParams);
            return makeCommand(commandName, numberOfParams, commandConstructor);
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
        if(numberOfParams == ONE_DOUBLE_PARAM_VALUE && listParametersStack.peek() == 0){
            return command.getConstructor(Double.class);
        } else if (!listParametersStack.isEmpty() && (numberOfParams == ONE_DOUBLE_PARAM_VALUE + TURTLE_PARAM_VALUE && listParametersStack.peek() == ZERO_DOUBLE_PARAM_VALUE)){
            return command.getConstructor(Turtle.class, Double.class);
        } else if (!listParametersStack.isEmpty() && (numberOfParams == TWO_DOUBLE_PARAM_VALUE)){
            return command.getConstructor(Double.class, Double.class);
        } else if (!listParametersStack.isEmpty() && (numberOfParams == TWO_DOUBLE_PARAM_VALUE + TURTLE_PARAM_VALUE && listParametersStack.peek() == ZERO_DOUBLE_PARAM_VALUE)){
            return command.getConstructor(Turtle.class, Double.class, Double.class);
        } else if (!listParametersStack.isEmpty() && (numberOfParams == ZERO_DOUBLE_PARAM_VALUE && listParametersStack.peek() == ZERO_DOUBLE_PARAM_VALUE)) {
            return command.getConstructor();
        } else if (!listParametersStack.isEmpty() && (numberOfParams == ONE_DOUBLE_PARAM_VALUE && listParametersStack.peek() == ONE_DOUBLE_PARAM_VALUE)) {
            return command.getConstructor(Double.class, List.class);
        } else if (!listParametersStack.isEmpty() && listParametersStack.peek() == TWO_DOUBLE_PARAM_VALUE) {
            return command.getConstructor(List.class, List.class);
        } else {
            return command.getConstructor(Turtle.class);
        }
    }

    private Command makeCommand(String name, double numberOfParams, Constructor constructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Command myCommand = new Not(1.0);
        if(listParametersStack.isEmpty()){
            //fixme throw exception?
        }
        if(numberOfParams == ONE_DOUBLE_PARAM_VALUE && listParametersStack.peek() == 0) {
            myCommand = (Command) constructor.newInstance(argumentStack.pop());
        } else if(numberOfParams == TWO_DOUBLE_PARAM_VALUE&& listParametersStack.peek() == 0) {
            myCommand = (Command) constructor.newInstance(argumentStack.pop(), argumentStack.pop());
        } else if(numberOfParams == ONE_DOUBLE_PARAM_VALUE + TURTLE_PARAM_VALUE && listParametersStack.peek() == 0) {
            myCommand = (Command) constructor.newInstance(turtle, argumentStack.pop());
        } else if(numberOfParams == TWO_DOUBLE_PARAM_VALUE + TURTLE_PARAM_VALUE && listParametersStack.peek() == 0) {
            myCommand = (Command) constructor.newInstance(turtle, argumentStack.pop(), argumentStack.pop());
        } else if(numberOfParams == ZERO_DOUBLE_PARAM_VALUE && listParametersStack.peek() == 0) {
            myCommand = (Command) constructor.newInstance();
        } else if(numberOfParams == TURTLE_PARAM_VALUE && listParametersStack.peek() == 0) {
            myCommand = (Command) constructor.newInstance(turtle);
        } else if (numberOfParams == ONE_DOUBLE_PARAM_VALUE && listParametersStack.peek() == ONE_DOUBLE_PARAM_VALUE) { //fixme remove magic num
            myCommand = (Command) constructor.newInstance(argumentStack.pop(), listStack.pop());
        } else if (listParametersStack.peek() == TWO_DOUBLE_PARAM_VALUE) { //fixme remove magic num
            myCommand = (Command) constructor.newInstance(listStack.pop(), listStack.pop());
        }
        listParametersStack.pop();
        return myCommand;
    }

    private List<Command> fillCommands(List<Command> l){
        List<Command> extendedList = new ArrayList<>();
        for(Command c : l){
            extendedList.addAll(c.getCommandList());
        }
        return extendedList;
    }

    private void executeCommandList(List<Command> l){
        for(Command c : l){
            System.out.println(c);
            System.out.println(c.execute());
            System.out.println(
                "DURING:: x: " + turtle.getX() + " y: " + turtle.getY() + " heading: " + turtle
                    .getHeading());
            if(c instanceof ClearScreen){
                myView.updatePenStatus(0);
                myView.update(turtle.getX(),turtle.getY(), turtle.getHeading());
                myView.clear();
                myView.updatePenStatus(1);
            } else if(c instanceof HideTurtle || c instanceof ShowTurtle){ //FIXME get rid of instance of. maybe commands can have a string that returns a view method to call and then we use reflection for making the method with one parameter (getResult())...
                myView.updateTurtleView(c.getResult());
            } else if(c instanceof PenDown || c instanceof PenUp){
                myView.updatePenStatus(c.getResult());
            } else if(c instanceof SetBackground){
                myView.updateBackgroundColor(c.getResult());
            } else if(c instanceof SetPenColor){
                myView.updateCommandPenColor(c.getResult());
            } else if(c instanceof SetShape){
                myView.updateShape(c.getResult());
            } else if(c instanceof SetPenSize){
                myView.updatePenSize(c.getResult());
            }
            else {
                myView.update(turtle.getX(), turtle.getY(), turtle.getHeading());
           }
        }
        resetStacks();
        myView.playAnimation();
    }
}