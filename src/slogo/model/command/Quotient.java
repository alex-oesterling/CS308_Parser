package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Quotient extends Command {

  /**
   * Calls the super constructor, and sets the result to be returned
   * as the quotient of the two provided numbers;
   * performs a/b
   * @param dividend number to be divided; a
   * @param divisor number to divide by; b
   */
  public Quotient(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(dividend/divisor);
  }
}
