package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class SetPalette extends Command {

  private Double index, red, green, blue;

  /**
   * Sets the color in the palette to a desired color given the r g b values
   *
   * @param turtleList  the list of turtles being brought in to use this command (if needed)
   * @param doubleList  the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList  the list of strings being used for this command (if needed)
   */
  public SetPalette(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super();
    index = doubleList.get(FIRST_INDEX);
    red = doubleList.get(SECOND_INDEX);
    green = doubleList.get(THIRD_INDEX);
    blue = doubleList.get(FOURTH_INDEX);
  }
}
