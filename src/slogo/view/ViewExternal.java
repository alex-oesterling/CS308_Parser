package slogo.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

public class ViewExternal implements ViewExternalAPI {
  private Visualizer myVisualizer;

  public ViewExternal(Visualizer view){
    myVisualizer = view;
  }
  @Override
  public void update(double newX, double newY, double orientation){
    myVisualizer.getTurtleList().get(0).update(newX, newY, orientation);
  }

  @Override
  public void updatePenColor(Color color) {
    myVisualizer.getTurtleList().get(0).updatePen(color);
  }

  @Override
  public void updateSceneColor() {

  }

  @Override
  public void clear() {
    myVisualizer.clear();
  }

  @Override
  public void updateTurtleView(double value) {
    myVisualizer.getTurtleList().get(0).updateTurtleView(value);
  }

  @Override
  public void updatePenStatus(double value) {
    myVisualizer.getTurtleList().get(0).updatePenStatus(value);
  }
}
