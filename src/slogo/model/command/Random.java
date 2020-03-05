package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Random extends Command {

  /**
   * Random contructor, calls super constructor and sets
   * value to return as a random number greater than zero and strictly
   * less than max
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   */
  public Random(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(Math.random()*doubleList.get(FIRST_INDEX));
  }
}
