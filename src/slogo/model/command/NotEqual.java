package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class NotEqual extends Command {

  /**
   * NotEqual constructor using doubles, call super constructor and update result
   * @param double1 first double
   * @param double2 second double
   */
  public NotEqual(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(double1!=double2);
  }

}
