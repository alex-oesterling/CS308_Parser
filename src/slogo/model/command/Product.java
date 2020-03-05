package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Product extends Command {

  /**
   * Default Product constructor, calls the super constructor
   * and sets the value to return as the product of the two;
   * performs a*b
   * @param value1 a
   * @param value2 b
   */
  public Product(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(value1*value2);
  }

}
