package slogo.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.Stack;
import slogo.controller.Controller;
import slogo.exceptions.*;
import slogo.model.command.*;

public class CommandCreator {
  private static final String LANGUAGES_PACKAGE = "resources.languages.";
  private static final String ERROR_PACKAGE = "resources.information.ErrorText";
  private static final String INFORMATION_PACKAGE = "resources.information.";
  private static final String COMMAND_PACKAGE = "slogo.model.command.";
  private static final String COMMAND = "Command";
  private static final String CONSTANT = "Constant";
  private static final String NO_MATCH = "NO MATCH";
  private static final String VARIABLE = "Variable";
  private static final String LIST_START = "ListStart";
  private static final String LIST_END = "ListEnd";
  private static final String DO = "do";
  private static final String WORK = "Work";
  private static final String WHITESPACE = "\\s+";
  private static final int ZERO = 0;

  private Controller control;
  private Stack<String> waitingCommandsStack, variablesStack;
  private Stack<Double> argumentStack;
  private Stack<Integer> doubleParametersStack, listParametersStack, variableParametersStack;
  private Stack<List<Command>> listStack;
  private Stack<Stack> commandStackHolder, argumentStackHolder, parametersStackHolder, listParametersStackHolder, variableStackHolder, variableParametersStackHolder, listStackHolder;
  private Stack<List<Command>> currentListHolder = new Stack<>();
  private Turtle turtle;
  private String currentLine;
  private Parser commandParser, parametersParser, syntaxParser, listParamsParser, doubleParamsParser, varsParamsParser;
  private List<Command> currentList, extendedList;
  private ResourceBundle errorResources;

  /**
   * The constructor for controller class, initializes the view, list of
   * symbols that is being used for parsing, map of created variables,
   * all of the stacks used, and the parsers for each different stack
   * @param language the specific language being used (aka english, chinese, etc)
   */
  public CommandCreator(Controller controller, String language) {
    control = controller;
    errorResources = ResourceBundle.getBundle(ERROR_PACKAGE);
    makeParsers(language);
    makeStacks();
  }

  /**
   * Allows the user to pick a turtle to do work on
   * @param t turtle to become the current turtle
   */
  public void setTurtle(Turtle t){
    turtle = t;
  }

  public void orientTurtle(double x, double y, double heading){
    turtle.setX(x);
    turtle.setY(y);
    turtle.setHeading(heading);
  }

  /**
   * Change to a new language of input
   * @param language input language: English, Spanish, Urdu, etc.
   */
  public void setLanguage(String language){
    commandParser = new Parser(LANGUAGES_PACKAGE);
    commandParser.addPatterns(language);
  }

  /**
   * Receives the commands to be done from the view/UI
   * @param commands the commands the user typed in
   */
  public List<Command> getCommandsOf(String commands){
    return createExtendedList(parseText(asList(commands.split(WHITESPACE))));
  }

  private void makeParsers(String language){
    setLanguage(language);

    syntaxParser = new Parser(LANGUAGES_PACKAGE);
    syntaxParser.addPatterns("Syntax");

    parametersParser = new Parser(INFORMATION_PACKAGE);
    parametersParser.addPatterns("Parameters");

    listParamsParser = new Parser(INFORMATION_PACKAGE);
    listParamsParser.addPatterns("ListParameters");

    doubleParamsParser = new Parser(INFORMATION_PACKAGE);
    doubleParamsParser.addPatterns("TurtleAndDoubleParameters");

    varsParamsParser = new Parser(INFORMATION_PACKAGE);
    varsParamsParser.addPatterns("VariablesParameters");
  }

  private void makeStacks() {
    makeNewStacks();
    listParametersStack = new Stack<>();
  }

  private void makeNewStacks(){ //INTENTIONALLY MAKING NEW STACKS RATHER THAN CLEARING
    waitingCommandsStack = new Stack<>();
    argumentStack = new Stack<>();
    doubleParametersStack = new Stack<>();
    variablesStack = new Stack<>();
    variableParametersStack = new Stack<>();
    listStack = new Stack<>();
  }

  private void makeHolderStacks() {
    commandStackHolder = new Stack<>();
    argumentStackHolder = new Stack<>();
    parametersStackHolder = new Stack<>();
    listParametersStackHolder = new Stack<>();
    variableStackHolder = new Stack<>();
    variableParametersStackHolder = new Stack<>();
    listStackHolder = new Stack<>();
    currentListHolder = new Stack<>();
  }

  private void holdStacks(){
    commandStackHolder.push(waitingCommandsStack);
    argumentStackHolder.push(argumentStack);
    parametersStackHolder.push(doubleParametersStack);
    listParametersStackHolder.push(listParametersStack);
    variableStackHolder.push(variablesStack);
    variableParametersStackHolder.push(variableParametersStack);
    listStackHolder.push(listStack);
    makeNewStacks();
    listStack =  listStackHolder.pop();
    currentListHolder.push(currentList);
  }

  private void stopHoldingStacks(){
    waitingCommandsStack = commandStackHolder.pop();
    argumentStack = argumentStackHolder.pop();
    doubleParametersStack = parametersStackHolder.pop();
    listParametersStack = listParametersStackHolder.pop();
    variablesStack = variableStackHolder.pop();
    variableParametersStack = variableParametersStackHolder.pop();
    currentList = currentListHolder.pop();
  }

  private List<String> asList(String[] array){
    List<String> list = new ArrayList<>();
    for(String s : array){ list.add(s); }
    return list;
  }

  private List<Command> parseText(List<String> lines) {
    currentList = new ArrayList<>();
    ListIterator<String> iterator = lines.listIterator();

    if (syntaxParser.getSymbol(lines.get(ZERO)).equals(CONSTANT)){ // eg they type in 50 fd
      throw new InvalidConstantException(new Throwable(), errorResources.getString("StartWithConstant"));
    }

    makeHolderStacks();

    while(iterator.hasNext()) {
      currentLine = iterator.next();
      if (currentLine.trim().length() > ZERO) {
        String commandSyntax = syntaxParser.getSymbol(currentLine); //get what sort of thing it is

        if(commandSyntax.equals(COMMAND)){
          doCommandWork();
        } else if (commandSyntax.equals(CONSTANT)){
          doConstantWork();
        } else if (commandSyntax.equals(VARIABLE)){
          doVariableWork();
        } else if (commandSyntax.equals(LIST_START)){
          doListStartWork();
        } else if (commandSyntax.equals(LIST_END)){
          doListEndWork();
        }


        tryToMakeCommands(currentList);
      }
    }
    while(!waitingCommandsStack.isEmpty()){
      tryToMakeCommands(currentList);
    }
    return createExtendedList(currentList);
  }

  private void doVariableWork(){
    if(control.validCommandVariable(currentLine)){
      List<Command> varCommand = control.getUserCreatedCommandVariables(currentLine);
      currentList.addAll(varCommand);
    } else if (control.validConstantVariable(currentLine)){
      argumentStack.push(control.getUserCreatedConstantVariables(currentLine));
    }
    variablesStack.push(currentLine);
  }

  private String doWorkString(String type){
    return DO+type+WORK;
  }

  private void doListStartWork(){
    holdStacks();
    currentList = new ArrayList<>();
  }

  private void doListEndWork(){
    if(!currentList.isEmpty()) {
      listStack.push(currentList);
    }
    stopHoldingStacks();
  }

  private void doCommandWork(){
    String commandName = commandParser.getSymbol(currentLine); //get the string name, such as "Forward" or "And"
    if (commandName.equals(NO_MATCH)) {
      throw new InvalidCommandException(new Throwable(), syntaxParser.getSymbol(currentLine), currentLine);
    } else {
      validCommand(commandName);
    }
  }

  private void validCommand(String commandName) {
    waitingCommandsStack.push(commandName); //add string to stack
    pushParamsNeeded(parametersParser.getSymbol(commandName)); //get Parameters string, and convert that string to a double
  }

  private void doConstantWork(){
    argumentStack.push(Double.parseDouble(currentLine));
  }

  /**
   * puts the number of parameters of each type on each respective parameters stack
   * @param commandType type of command
   */
  private void pushParamsNeeded(String commandType){
    String listParamString = listParamsParser.getSymbol(commandType);
    String doubleParamsString = doubleParamsParser.getSymbol(commandType);
    String varsParamsString = varsParamsParser.getSymbol(commandType);

    if(listParamString.equals(NO_MATCH) || doubleParamsString.equals(NO_MATCH) || varsParamsString.equals(NO_MATCH)){
      throw new InvalidPropertyException(new Throwable(), errorResources.getString("InvalidPropertiesFile"));
    }

    listParametersStack.push(Integer.parseInt(listParamString));
    doubleParametersStack.push(Integer.parseInt(doubleParamsString));
    variableParametersStack.push(Integer.parseInt(varsParamsString));
  }

  private List<Command> tryToMakeCommands(List<Command> commandList){
    if(variableParametersStack.isEmpty()){
      return commandList; //we have nothing more to do
    }
    if (variableParametersStack.peek() != ZERO && (checkArgumentStack() || checkListStack())){
      commandList.add(weHaveEnoughArgumentsToMakeACommand(commandList));
    } else if (variableParametersStack.peek() == ZERO && checkArgumentStack() && checkListStack()){
      commandList.add(weHaveEnoughArgumentsToMakeACommand(commandList));
    }
    return commandList;
  }

  private boolean checkArgumentStack(){
    return !doubleParametersStack.isEmpty() && argumentStack.size() >= doubleParametersStack.peek();
  }

  private boolean checkListStack(){
    return !listParametersStack.isEmpty() && (listStack.size() >= listParametersStack.peek());
  }

  private Command weHaveEnoughArgumentsToMakeACommand(List<Command> commands){
    if(variableParametersStack.peek() != ZERO && checkArgumentStack()){
      listParametersStack.pop();
      listParametersStack.push(ZERO);
    } else if (variableParametersStack.peek() != ZERO && checkListStack()){
      doubleParametersStack.pop();
      doubleParametersStack.push(ZERO);
    }
    Command newCommand = getCommand(waitingCommandsStack.pop());
    if(!variablesStack.isEmpty()){
      listStack.push(List.of(newCommand));
      tryToMakeCommands(commands);
    } else if(!waitingCommandsStack.isEmpty()){
      argumentStack.push(newCommand.getResult());
      tryToMakeCommands(commands); //slightly recursive
    }
    return newCommand;
  }

  private Command getCommand(String commandName){
    try{
      Class commandClass = Class.forName(COMMAND_PACKAGE + commandName);
      return makeCommand(commandConstructor(commandClass));
    } catch (ClassNotFoundException e){
      throw new NoClassException(new Throwable(), errorResources.getString("NoClass"));
    } catch (NoSuchMethodException e){
      throw new NoClassException(new Throwable(), errorResources.getString("NoMethod"));
    } catch (IllegalAccessException e) {
      throw new IllegalException(new Throwable(), errorResources.getString("Illegal"));
    } catch (InstantiationException e) {
      throw new InstantException(new Throwable(), errorResources.getString("Instantiation"));
    } catch (InvocationTargetException e) {
      throw new InvocationException(new Throwable(), errorResources.getString("Invocation"));
    }
  }

  private Constructor commandConstructor(Class command) throws NoSuchMethodException {
    return command.getConstructor(List.class, List.class, List.class, List.class);
  }

  private Command makeCommand(Constructor constructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
    List<Turtle> turtleListToGive = new ArrayList<>();
    turtleListToGive.add(turtle);

    return (Command) constructor.newInstance(turtleListToGive, doubleListToGive(), commandListToGive(), stringListToGive());
  }

  //todo make the below three methods into one method
  private List<Double> doubleListToGive(){
    List<Double> doubles = new ArrayList<>();
    if(doubleParametersStack.isEmpty()){
      return doubles;
    }
    int numberOfDoublesNeeded = doubleParametersStack.pop();
    for(int k=0; k < numberOfDoublesNeeded; k++){
      doubles.add(argumentStack.pop());
    }
    return doubles;
  }

  private List<List<Command>> commandListToGive(){
    List<List<Command>> lists = new ArrayList<>();
    if(listParametersStack.isEmpty()){
      return lists;
    }
    int numberOfListsNeeded = listParametersStack.pop();
    for(int k=0; k < numberOfListsNeeded; k++){
      lists.add(listStack.pop());
    }
    return lists;
  }

  private List<String> stringListToGive(){
    List<String> strings = new ArrayList<>();
    if(variableParametersStack.isEmpty()){
      return strings;
    }
    int numberOfStringsNeeded = variableParametersStack.pop();
    for(int k=0; k < numberOfStringsNeeded; k++){
      strings.add(variablesStack.pop());
    }
    return strings;
  }

  private List<Command> createExtendedList(List<Command> l){
    extendedList = new ArrayList<>();
    for(Command c : l){
      extendedList.addAll(c.getCommandList());
    }
    return extendedList;
  }
}