package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Not extends Command {

  /**
   * Not constructor, flip the value of whatever given;
   * call super constructor, and update result
   * @param a
   */
  public Not(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(!(a!=0.0));
  }

}
