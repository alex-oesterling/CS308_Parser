package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Random extends Command {

  /**
   * Random contructor, calls super constructor and sets
   * value to return as a random number greater than zero and strictly
   * less than max
   * @param max maximum random value
   */
  public Random(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(Math.random()*max);
  }
}
