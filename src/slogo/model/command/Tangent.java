package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Tangent extends Command {

  /**
   * Default Tangent constructor, calls super constructor
   * and sets the value to return as the tangent of the parameter
   * performs tan(a)
   * @param a value to take the tangent of
   */
  public Tangent(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(Math.tan(doubleList.get(FIRST_INDEX)));
  }
}
