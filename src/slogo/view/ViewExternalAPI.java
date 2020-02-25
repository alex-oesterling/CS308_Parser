package slogo.view;

public interface ViewExternalAPI {
  void update(double newX, double newY, double newAngle);
  void updatePenColor();
  void updateSceneColor();
  void clear();
  void update();
}
