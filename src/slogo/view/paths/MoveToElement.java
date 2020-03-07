package slogo.view.paths;

import javafx.scene.shape.MoveTo;

public class MoveToElement extends MoveTo {
  public MoveToElement(double x, double y) {
    super(x, y);
  }
  @Override
  public String toString(){
    return (this.getX() + " " + this.getY());
  }

}
