package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Power extends Command {

  /**
   * Power Constructor, passes up result to super
   * performs a^b
   * @param base a
   * @param exponent b
   */
  public Power(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(Math.pow(base, exponent));
  }
}
