package slogo.view;

import javafx.scene.paint.Color;

public class ViewExternal implements ViewExternalAPI {
  private Visualizer myVisualizer;

  public ViewExternal(Visualizer view){
    myVisualizer = view;
  }
  @Override
  public void update(double newX, double newY, double orientation){
    myVisualizer.getCurrentTurtle().update(newX, newY, orientation);
  }

  @Override
  public void updatePenColor(Color color) {
    myVisualizer.getCurrentTurtle().updatePen(color);
  }

  @Override
  public void updateCommandPenColor(double value){
    myVisualizer.colorPalettePenColor(value);
  }

  @Override
  public void updateBackgroundColor(double value){
    myVisualizer.setBackgroundColor(value);
  }

  @

  @Override
  public void updateSceneColor() {

  }

  @Override
  public void clear() {
    myVisualizer.clear();
  }

  @Override
  public void updateTurtleView(double value) {
    myVisualizer.getCurrentTurtle().updateTurtleView(value);
  }

  @Override
  public void updatePenStatus(double value) {
    myVisualizer.getCurrentTurtle().updatePenStatus(value);
  }

  @Override
  public void playAnimation(){
    myVisualizer.getCurrentTurtle().playAnimation();
  }

  @Override
  public void addCommand(String commandSyntax) {
    myVisualizer.addCommand(commandSyntax);
  }

  @Override
  public void addVariable(String newVariable, double newValue) {
    myVisualizer.addVariable(newVariable, newValue);
  }

}
