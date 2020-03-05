package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Pi extends Command {

  /**
   * Default constructor, calls super constructor
   * and sets the result to be returned as pi
   */
  public Pi(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(Math.PI);
  }
}
