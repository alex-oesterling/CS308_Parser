package slogo.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;
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
  //private static final String MAKE_VARIABLE = "MakeVariable";
  private static final String LIST_START = "ListStart";
  private static final String LIST_END = "ListEnd";
  private static final String DO = "do";
  private static final String WORK = "Work";
  private static final String WHITESPACE = "\\s+";
  private static final int ZERO = 0;
  private static boolean IS_VARIABLE = false;

  private Controller control;
  private Stack<String> commandStack;
  private Stack<Double> argumentStack;
  private Stack<Integer> doubleParametersStack, listParametersStack;
  private Stack<List<Command>> listStack;
  private Stack<Stack> commandStackHolder, argumentStackHolder, parametersStackHolder, listParametersStackHolder, listStackHolder;
  private Stack<List<Command>> currentListHolder = new Stack<>();
  private Turtle turtle;
  private Parser commandParser, parametersParser, syntaxParser, listParamsParser, doubleParamsParser;
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
    return createExtendedList(parseText(syntaxParser, commandParser, parametersParser, asList(commands.split(WHITESPACE))));
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
  }

  private void makeStacks() {
    makeNewStacks();
    listParametersStack = new Stack<>(); //todo figure out why we separated this one out
  }

  private void makeNewStacks(){ //INTENTIONALLY MAKING NEW STACKS RATHER THAN CLEARING
    commandStack = new Stack<>();
    argumentStack = new Stack<>();
    doubleParametersStack = new Stack<>();
    listStack = new Stack<>();
  }

  private void makeHolderStacks() {
    commandStackHolder = new Stack<>();
    argumentStackHolder = new Stack<>();
    parametersStackHolder = new Stack<>();
    listParametersStackHolder = new Stack<>();
    listStackHolder = new Stack<>();
    currentListHolder = new Stack<>();
  }

  private void holdStacks(){
    commandStackHolder.push(commandStack);
    argumentStackHolder.push(argumentStack);
    parametersStackHolder.push(doubleParametersStack);
    listParametersStackHolder.push(listParametersStack);
    listStackHolder.push(listStack);
    makeNewStacks();
    listStack =  listStackHolder.pop();
    currentListHolder.push(currentList);
  }

  private void stopHoldingStacks(){
    commandStack = commandStackHolder.pop();
    argumentStack = argumentStackHolder.pop();
    doubleParametersStack = parametersStackHolder.pop();
    listParametersStack = listParametersStackHolder.pop();
    currentList = currentListHolder.pop();
  }

  private List<String> asList(String[] array){
    List<String> list = new ArrayList<>();
    for(String s : array){ list.add(s); }
    return list;
  }

  private List<Command> parseText (Parser syntax, Parser lang, Parser params, List<String> lines) {
    currentList = new ArrayList<>();
    ListIterator<String> iterator = lines.listIterator();

    if (syntax.getSymbol(lines.get(ZERO)).equals(CONSTANT)){ // eg they type in 50 fd
      throw new InvalidConstantException(new Throwable(), errorResources.getString("StartWithConstant"));
    }
    makeHolderStacks();
    while(iterator.hasNext() && !IS_VARIABLE) {
      String line = iterator.next();
      if (line.trim().length() > 0) {
        String commandSyntax = syntax.getSymbol(line); //get what sort of thing it is
        if(commandSyntax.equals(COMMAND)){
          doCommandWork(params, lang, syntax, currentList, line, commandSyntax, lines);
        } else if (commandSyntax.equals(CONSTANT)){
          doConstantWork(line, currentList);
        } else if (commandSyntax.equals(VARIABLE)){
          if(control.hasUserCreatedCommandVariables(line)){
            doCommandVariable(line, syntax, lang, params, currentList);
          } else if (control.hasUserCreatedConstantVariables(line)){
            doConstantVariable(line, currentList);
          } else {
            throw new InvalidVariableException(new Throwable(), errorResources.getString("FakeVariable"));
          }
        } else if (commandSyntax.equals(LIST_START)){
          doListStartWork();
        } else if (commandSyntax.equals(LIST_END)){
          doListEndWork();
        }
      }
    }
    IS_VARIABLE = false;
    while(!commandStack.isEmpty()){
      tryToMakeCommands(currentList);
    }
    return createExtendedList(currentList);
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
    tryToMakeCommands(currentList);
  }

  private void doCommandWork(Parser params, Parser lang, Parser syntax, List<Command> commandList, String line, String commandSyntax, List<String> lines){
    String commandName = lang.getSymbol(line); //get the string name, such as "Forward" or "And"
    if (commandName.equals(NO_MATCH)){
      throw new InvalidCommandException(new Throwable(), commandSyntax, line);
    }
//    else if (commandName.equals(MAKE_VARIABLE)){
//      dealWithMakingVariables(lines, line, syntax);}
    else {
      validCommand(params, commandName, commandList);
    }
  }

//  private void dealWithMakingVariables(List<String> lines, String line, Parser syntax){
//    IS_VARIABLE = true;
//    List<String> copyLines = new CopyOnWriteArrayList(lines);
//    copyLines.remove(line);
//    String variable = copyLines.get(ZERO);
//    String firstCommand = copyLines.get(1);
//    String type = syntax.getSymbol(firstCommand);
//    copyLines.remove(variable);
//    if (copyLines.size() > 1 || (copyLines.size() == 1 && type.equals("Command"))){
//      control.addUserCreatedCommand(variable, copyLines);
//    } else if (copyLines.size() == 1 && type.equals("Constant")){
//      control.addUserCreatedVariable(variable, firstCommand);
//    }
//  }

  private List<Command> validCommand(Parser params, String commandName, List<Command> commandList) {
    commandStack.push(commandName); //add string to stack
    pushParamsNeeded(params.getSymbol(commandName)); //get Parameters string, and convert that string to a double
    return tryToMakeCommands(commandList);
  }

  //fixme add in controller
  private void doCommandVariable(String line, Parser syntax, Parser lang, Parser params, List<Command> commandList){
    List<String> variableDoesWhat = control.getUserCreatedCommandVariables(line)/*userCreatedCommandVariables.get(line)*/;

    for (String s : variableDoesWhat){
      String comSyntax = syntax.getSymbol(s);
      if (comSyntax.equals(COMMAND)){
        doCommandWork(params, lang, syntax, commandList, s, comSyntax, variableDoesWhat);
      } else if (comSyntax.equals(CONSTANT)){
        doConstantWork(s, commandList);
      }
    }
  }

  //fixme add in controller
  private void doConstantVariable(String line, List commandList){
    doConstantWork(control.getUserCreatedConstantVariables(line)/*userCreatedConstantVariables.get(line)*/, commandList);
  }

  private void doConstantWork(String constant, List<Command> commandList){
    argumentStack.push(Double.parseDouble(constant));
    tryToMakeCommands(commandList);
  }

  private void pushParamsNeeded(String commandParams){
    String listParamString = listParamsParser.getSymbol(commandParams);
    if(listParamString.equals(NO_MATCH)){
      throw new InvalidPropertyException(new Throwable(), errorResources.getString("InvalidPropertiesFile"));
    } else {
      listParametersStack.push(Integer.parseInt(listParamString));
    }

    String doubleParamsString = doubleParamsParser.getSymbol(commandParams);
    if(doubleParamsString.equals(NO_MATCH)){
      throw new InvalidPropertyException(new Throwable(), errorResources.getString("InvalidPropertiesFile"));
    } else {
      doubleParametersStack.push(Integer.parseInt(doubleParamsString)); //add that value to the params stack
    }
  }

  private List<Command> tryToMakeCommands(List<Command> commandList){
    if(checkArgumentStack() && checkListStack()){
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
    Command newCommand = getCommand(commandStack.pop());
    if(!commandStack.isEmpty()){
      argumentStack.push(newCommand.getResult());
      tryToMakeCommands(commands); //slightly recursive :D
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
    return command.getConstructor(List.class, List.class, List.class);
  }

  private Command makeCommand(Constructor constructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
    List<Turtle> turtleListToGive = new ArrayList<>();
    turtleListToGive.add(turtle/*activeTurtles*/); //fixme

    return (Command) constructor.newInstance(turtleListToGive, doubleListToGive(), commandListToGive());
  }

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

  private List<Command> createExtendedList(List<Command> l){
    extendedList = new ArrayList<>();
    for(Command c : l){
      extendedList.addAll(c.getCommandList());
    }
    return extendedList;
  }
}