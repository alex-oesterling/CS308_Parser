package slogo.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ResourceBundle;

import slogo.exceptions.*;
import slogo.model.ModelExternal;
import slogo.model.Turtle;
import slogo.model.command.*;
import slogo.view.ViewExternal;
import slogo.fun.RomanNumerals;

public class Controller {

  private static final String ERROR_PACKAGE = "resources.information.ErrorText";
  private static final int ZERO = 0;
  private static final Integer SECOND_GEN = 2;
  private static final double STARTING_ID = 0;

  private ModelExternal modelExternal;
  private Map<String, List> userCreatedCommandVariables;
  private Map<String, String> userCreatedConstantVariables;
  private Map<String, Turtle> nameToTurtle;
  private Map<Turtle, Double> turtleId;
  private Map<String, Integer> nameCount;
  private Turtle turtle;
  private ViewExternal myView;
  private double idOfTurtle;
  private Command currentCommand;
  private ResourceBundle errorResources;

  /**
   * The constructor for controller class, initializes the view, list of symbols that is being used
   * for parsing, map of created variables, all of the stacks used, and the parsers for each
   * different stack
   *
   * @param visualizer the view of the program
   * @param language   the specific language being used (aka english, chinese, etc)
   */
  public Controller(ViewExternal visualizer, String language) {
    modelExternal = new ModelExternal(this, language);
    errorResources = ResourceBundle.getBundle(ERROR_PACKAGE);
    myView = visualizer;
    idOfTurtle = STARTING_ID;

    makeMaps();
  }

  private void makeMaps() {
    userCreatedCommandVariables = new HashMap<>();
    userCreatedConstantVariables = new HashMap<>();
    nameToTurtle = new HashMap<>();
    nameCount = new HashMap<>();
    turtleId = new HashMap<>();
  }

  /**
   * Add a new turtle to the map of turtles, change the current turtle to this newly created turtle;
   * turtle with a default "name" that is its hashcode
   */
  public void addTurtle() {
    idOfTurtle++;
    turtle = new Turtle(idOfTurtle, myView.getArenaWidth(), myView.getArenaHeight());
    if (nameCount.containsKey(turtle.getName())) {
      Integer generation = nameCount.get(turtle.getName());
      nameCount.put(turtle.getName(), nameCount.get(turtle.getName()) + 1);
      RomanNumerals rn = new RomanNumerals();
      turtle.setName(turtle.getName() + " " + rn.intToNumeral(generation));
    }
    nameCount.putIfAbsent(turtle.getName(), SECOND_GEN);
    nameToTurtle.putIfAbsent(turtle.getName(), turtle);
    modelExternal.setTurtle(turtle);
  }

  /**
   * Adds a new turtle to the screen with the given parameters
   *
   * @param name            new name of the turtle
   * @param startingX       the x position of where the turtle will start
   * @param startingY       the y position of where the turtle will start
   * @param startingHeading where the turtle will be facing
   */
  public void addTurtle(String name, double startingX, double startingY, double startingHeading) {
    idOfTurtle++;
    Turtle t = new Turtle(name, startingX, startingY, startingHeading, idOfTurtle, myView.getArenaWidth(), myView.getArenaHeight());
    if (nameToTurtle.containsKey(t.getName())) {
      throw new InvalidTurtleException("Turtle already exists",
          new Throwable()); //shouldn't ever get to this
    }
    nameToTurtle.putIfAbsent(t.getName(), t);
    turtle = t;
    modelExternal.setTurtle(t);
  }

  /**
   * get the name of the current turtle
   *
   * @return turtle's name
   */
  public String getTurtleName() {
    return turtle.getName();
  }

  /**
   * Sets the turtle to specific position
   * @param xPos x pos to set to
   * @param yPos y pos to set to
   * @param heading degrees to be facing
   */
  public void orientTurtle(double xPos, double yPos, double heading){
    modelExternal.orientTurtle(xPos, yPos, heading);
  }

  /**
   * Allows a command variable to be updated in the UI
   *
   * @param key      the old command value
   * @param newValue what it will be changed to
   */
  public void updateCommandVariable(String key, String newValue) {
    if (userCreatedConstantVariables.containsKey(key)) {
      userCreatedConstantVariables.put(key, newValue);
    }
  }

  /**
   * Add a user created variable to the map of user created variables
   *
   * @param key   variable name
   * @param value variable value
   */
  public void addUserVariable(String key, String value) {
    userCreatedConstantVariables.putIfAbsent(key, value);
  }

  /**
   * add a user created command to the map of user created commands
   *
   * @param key    variable name
   * @param syntax variable commands
   */
  public void addUserCommand(String key, String syntax) {
    List<String> commandList = Arrays.asList(syntax.split(" "));
    userCreatedCommandVariables.putIfAbsent(key, commandList);
  }

  /**
   * Allows the user to pick a turtle to do work on
   *
   * @param name turtle to become the current turtle
   */
  public void chooseTurtle(String name) {
    turtle = nameToTurtle.get(name);
    modelExternal.setTurtle(turtle);
  }

  /**
   * Change to a new language of input
   *
   * @param language input language: English, Spanish, Urdu, etc.
   */
  public void addLanguage(String language) {
    modelExternal.setLanguage(language);
  }

  /**
   * Receives the commands to be done from the view/UI
   *
   * @param commands the commands the user typed in
   */
  public void sendCommands(String commands) {
    executeCommandList(modelExternal.getCommandsOf(commands));
  }

  //todo finish comment

  /**
   * get the user created constant or variable in a line from the map
   *
   * @param line
   * @return
   */
  public String getUserCreatedConstantVariables(String line) {
    return userCreatedConstantVariables.get(line);
  }

  //todo finish comment

  /**
   * @param line
   * @return
   */
  public List<String> getUserCreatedCommandVariables(String line) {
    return userCreatedCommandVariables.get(line);
  }

  //todo finish comment

  /**
   * @param line
   * @return
   */
  public boolean hasUserCreatedCommandVariables(String line) {
    return userCreatedCommandVariables.containsKey(line);
  }

  //todo finish comment

  /**
   * @param line
   * @return
   */
  public boolean hasUserCreatedConstantVariables(String line) {
    return userCreatedConstantVariables.containsKey(line);
  }

  //todo finish comment

  /**
   * @param variable
   * @param copyLines
   */
  public void addUserCreatedCommand(String variable, List<String> copyLines) {
    if (!userCreatedConstantVariables.containsKey(variable)) {
      userCreatedCommandVariables.put(variable, copyLines);
      String commandSyntax = String.join(" ", copyLines.toArray(new String[ZERO]));
      myView.addCommand(variable, commandSyntax);
    } else {
      throw new InvalidVariableException(new Throwable(),
          errorResources.getString("AlreadyConstant"));
    }
  }

  public void addUserCreatedVariable(String variable, String firstCommand) {
    if (!userCreatedCommandVariables.containsKey(variable)) {
      userCreatedConstantVariables.put(variable, firstCommand);
      myView.addVariable(variable, firstCommand);
    } else {
      throw new InvalidVariableException(new Throwable(),
          errorResources.getString("AlreadyCommand"));
    }
  }

  private void executeCommandList(List<Command> l) {
    myView.setCommandSize(l.size());
    for (Command c : l) {
      currentCommand = c;
      System.out.println(currentCommand);
      System.out.println(currentCommand.getResult());
      currentCommand.execute();
      makeMethod(currentCommand, currentCommand.getViewInteractionString().split(" ")[ZERO]);
    }
    myView.updateStatus();
  }

  private void makeMethod(Command c, String methodName){
    try {
      Method method = Controller.class.getDeclaredMethod(methodName);
      method.invoke(Controller.this);
    } catch (NoSuchMethodException e) {
      throw new NoClassException(new Throwable(), errorResources.getString("NoMethod"));
    } catch (IllegalAccessException e) {
      //todo change this
      throw new NoClassException(new Throwable(), errorResources.getString("NoMethod"));
    } catch (InvocationTargetException e) {
      //todo change this
      throw new NoClassException(new Throwable(), errorResources.getString("NoMethod"));
    }
  }

  private void addUserConstantToMap(Command c) {
    String variableName = c.getViewInteractionString().split(" ")[1];//todo remove hard code 1
    //todo handle putting into maps
  }

  private void addUserCommandToMap(Command c){
    String variableName = c.getViewInteractionString().split(" ")[1];//todo remove hard code 1
    //todo handle putting into maps

  }

  private void update(){
    myView.update(turtle.getX(), turtle.getY(), turtle.getHeading());
  }

  private void updatePenSize(){
    myView.updatePenSize(currentCommand.getResult());
  }

  private void updateShape(){
    myView.updateShape(currentCommand.getResult());
  }

  private void updateCommandPenColor(){
    myView.updateCommandPenColor(currentCommand.getResult());
  }

  private void updateBackgroundColor() {
    myView.updateBackgroundColor(currentCommand.getResult());
  }

  private void updatePenStatus() {
    myView.updatePenStatus(currentCommand.getResult());
  }

  private void updateTurtleView(){
    myView.updateTurtleView(currentCommand.getResult());
  }

  private void clear() {
    myView.updatePenStatus(0);
    myView.update(turtle.getX(), turtle.getY(), turtle.getHeading());
    myView.clear();
    myView.updatePenStatus(1);
  }
}