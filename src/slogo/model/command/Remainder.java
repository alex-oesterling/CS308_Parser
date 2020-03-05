package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Remainder extends Command {

  /**
   * Default constructor for finding the remainder of a division
   * calls the super constructor, and sets the value to return
   * @param dividend
   * @param divisor
   */
  public Remainder(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(dividend%divisor);
  }
}
