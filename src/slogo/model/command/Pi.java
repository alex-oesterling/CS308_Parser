package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Pi extends Command {

  /**
   * Default constructor, calls super constructor
   * and sets the result to be returned as pi
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList the list of strings being used for this command (if needed)
   */
  public Pi(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
    super(Math.PI);
  }
}