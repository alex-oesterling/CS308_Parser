package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class NaturalLog extends Command {

  /**
   * NaturalLog constructor that returns the natural log of a
   * performs ln(a)
   * @param a
   */
  public NaturalLog(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(Math.log(a));
  }
}
