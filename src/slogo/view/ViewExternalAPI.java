package slogo.view;

import javafx.scene.paint.Color;

public interface ViewExternalAPI {
  void update(double newX, double newY, double newAngle);
  void updatePenColor(Color color);
  void updateCommandPenColor(double value);
  void updateBackgroundColor(double value);
  void updatePenSize(double value);
  void updateSceneColor();
  void clear();
  void updateTurtleView(double value);
  void updatePenStatus(double value);
  void playAnimation();
  void addCommand(String commandSyntax);
  void addVariable(String newVariable, double newValue);
//  void setOpacity();

}
