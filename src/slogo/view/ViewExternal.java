package slogo.view;

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
  public void updateCommandPenColor(double value){
    myVisualizer.setPenColor(value);
  }

  @Override
  public void updateBackgroundColor(double value){
    myVisualizer.setBackgroundColorFromPalette(value);
  }

  @Override
  public void updatePenSize(double value){
    myVisualizer.setPenSize(value);
  }

  @Override
  public void updateShape(double value){
    myVisualizer.setShape(value);
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
  public void updateStatus(){
    myVisualizer.getCurrentTurtle().playAnimation();
    myVisualizer.getCurrentTurtle().turtleStats();
  }

  @Override
  public void addCommand(String commandSyntax, String syntax) {
    myVisualizer.addCommand(commandSyntax, syntax);
  }

  @Override
  public void addVariable(String newVariable, String newValue) {
    myVisualizer.addVariable(newVariable, newValue);
  }

  @Override
  public void setCommandSize(int size){
    myVisualizer.getCurrentTurtle().setCommandSize(size);
  }

  @Override
  public void setColorPallete(double id, double red, double green, double blue) {

  }

  @Override
  public int getArenaWidth() {return myVisualizer.getArenaWidth(); }

  @Override
  public int getArenaHeight() {return myVisualizer.getArenaHeight(); }
}
