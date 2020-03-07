package slogo.view.paths;

import javafx.scene.shape.LineTo;

public class LinePathElement extends LineTo {
  public LinePathElement(double x, double y){
    super(x, y);
  }
  @Override
  public String toString(){
    return(this.getX() + " " + this.getY());
  }
}
