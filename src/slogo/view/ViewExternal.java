package slogo.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

public class ViewExternal implements ViewExternalAPI {
  private List<TurtleView> turtleList;

  public ViewExternal(List<TurtleView> turtles){
    turtleList = turtles;
  }
  @Override
  public void update(double newX, double newY, double orientation){
    turtleList.get(0).update(newX, newY, orientation);
  }

  @Override
  public void updatePenColor(Color color) {
    turtleList.get(0).updatePen(color);
  }

  @Override
  public void updateSceneColor() {

  }

  @Override
  public void clear() {

  }
}
