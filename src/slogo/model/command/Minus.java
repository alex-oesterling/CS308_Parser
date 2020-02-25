package slogo.model.command;

public class Minus extends Command {

  /**
   * Default Minus constructor, calls super constructor
   * sets the value to return as -a
   * @param value a
   */
  public Minus(Double value){
    super(-1*value);
  }
}
