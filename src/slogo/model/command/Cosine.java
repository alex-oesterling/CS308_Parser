package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Cosine extends Command {

  /**
   * Default Cosine constructor, calls super constructor
   * sets value to return as the cosine of the parameter
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList the list of strings being used for this command (if needed)
   */
  public Cosine(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
    super(Math.cos(doubleList.get(FIRST_INDEX)));
  }
}
