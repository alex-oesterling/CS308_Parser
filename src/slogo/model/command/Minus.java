package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Minus extends Command {

  /**
   * Default Minus constructor, calls super constructor
   * sets the value to return as -a
   * @param value a
   */
  public Minus(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(-1*value);
  }
}
