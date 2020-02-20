package slogo.model;

/**
 * Author: Alex Oesterling
 * The API for the external portion of the Model.
 * Feel free to add any public methods here which will be used by the visualizer (so probably will be a collaborative
 * effort between all team members)
 */
public interface ModelExternalAPI {
  void readCommandFromString(String command);
}
