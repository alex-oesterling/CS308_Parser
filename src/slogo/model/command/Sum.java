package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Sum extends Command {

  /**
   * Default constructor for Sum, calls the super constructor
   * and sets the value to return as the sum of the two parameters
   * performs a+b
   * @param addend1 a
   * @param addend2 b
   */
  public Sum(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(addend1+addend2);
  }
}
