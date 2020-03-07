package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class SetPalette extends Command {

  private static final String UPDATE = "setColorPalette";
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
    blue = doubleList.get(FIRST_INDEX);
    green = doubleList.get(SECOND_INDEX);
    red = doubleList.get(THIRD_INDEX);
    index = doubleList.get(FOURTH_INDEX);
  }

  /**
   * Returns the index number for the color in the palette to be changed
   * @return the index number
   */
  @Override
  public Double getResult() {
    return index;
  }

  /**
   * Returns the method variable name and the hex value to be returned
   * @return the method variable name and the hex value
   */
  @Override
  public String getViewInteractionString() {
    return UPDATE + " " + String.format("#%02x%02x%02x", red, green, blue);
  }

  public static void main(String[] args) {
    System.out.println(String.format("#%02x%02x%02x", 50, 50, 50));
    String happy = String.format("#%02x%02x%02x", 50, 50, 50);
    System.out.println(happy);
  }
}
