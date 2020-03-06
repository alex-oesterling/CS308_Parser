package slogo.model.command;

import java.util.List;
import slogo.model.Turtle;

public class MakeVariable extends Command {

  private List<Command> commands;

  /**
   * MakeVariable constructor for creating a command that holds a double
   * assumes one double is coming in, and we only care about that
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   */
  public MakeVariable(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(doubleList.get(FIRST_INDEX));
  }
}
