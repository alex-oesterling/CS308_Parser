package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Minus extends Command {

  /**
   * Default Minus constructor, calls super constructor
   * sets the value to return as -a
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   */
  public Minus(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(-1*doubleList.get(FIRST_INDEX));
  }
}
