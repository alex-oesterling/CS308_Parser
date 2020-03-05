package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Or extends Command {

  /**
   * Or constructors, checks for value of a||b,
   * call super constructor and update result
   * @param value1 a
   * @param value2 b
   */
  public Or(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(value1!=0.0||value2!=0.0);
  }

}
