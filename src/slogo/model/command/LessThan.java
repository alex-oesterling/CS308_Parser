package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class LessThan extends Command {

  /**
   * LessThan constructor for checking a<b
   * Only works for ints and doubles
   * @param value1 a
   * @param value2 b
   */
  public LessThan(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(value1<value2);
  }

}
