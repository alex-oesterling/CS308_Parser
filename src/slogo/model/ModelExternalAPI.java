package slogo.model;

import java.util.List;
import slogo.model.command.Command;

/**
 * Author: Alex Oesterling
 * The API for the external portion of the Model.
 * Feel free to add any public methods here which will be used by the visualizer (so probably will be a collaborative
 * effort between all team members)
 */
public interface ModelExternalAPI {
  void setTurtle(Turtle t);
  void setLanguage(String language);
  List<Command> getCommandsOf(String commands);
}
