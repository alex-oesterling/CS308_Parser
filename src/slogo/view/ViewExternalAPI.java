package slogo.view;

import javafx.scene.paint.Color;

public interface ViewExternalAPI {
  void update(double newX, double newY, double newAngle);
  void updatePenColor(Color color);
  void updateSceneColor();
  void clear();
}
