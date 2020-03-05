package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Sine extends Command {

  /**
   * Default Sine constructor, calls super constuctor
   * and sets the value to return as the sine of the parameter;
   * performs sin(a)
   * @param a value to take the sine of
   */
  public Sine(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(Math.sin(a));
  }
}
