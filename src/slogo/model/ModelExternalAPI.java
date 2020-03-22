package slogo.model;

import java.util.List;
import slogo.model.command.Command;

/**
 * API  for the external portion of  the model with all of the external public methods
 * @author Tyler Meier and Dana Mulligan
 */
public interface ModelExternalAPI {
  void setTurtle(Turtle t);
  void setLanguage(String language);
  void orientTurtle(double x, double y, double heading);
  List<Command> getCommandsOf(String commands);
}
