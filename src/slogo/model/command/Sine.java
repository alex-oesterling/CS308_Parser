package slogo.model.command;

public class Sine extends Command {

  /**
   * Default Sine constructor, calls super constuctor
   * and sets the value to return as the sine of the parameter;
   * performs sin(a)
   * @param a value to take the sine of
   */
  public Sine(Double a){
    super(Math.sin(a));
  }
}
