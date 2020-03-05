package slogo.view;

import javafx.scene.paint.Color;

public interface ViewExternalAPI {
  void update(double newX, double newY, double newAngle);
  void updatePenColor(Color color);
  void updateCommandPenColor(double value);
  void updateBackgroundColor(double value);
  void updatePenSize(double value);
  void updateShape(double value);
  void updateSceneColor();
  void clear();
  void updateTurtleView(double value);
  void updatePenStatus(double value);
  void updateStatus();
  void addCommand(String commandSyntax, String syntax);
  void addVariable(String newVariable, String newValue);
  void setCommandSize(int size);
//  void setOpacity();
}
