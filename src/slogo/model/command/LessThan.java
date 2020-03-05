package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class LessThan extends Command {

  /**
   * LessThan constructor for checking a<b
   * Only works for ints and doubles
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   */
  public LessThan(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(doubleList.get(FIRST_INDEX)<doubleList.get(SECOND_INDEX));
  }

}
