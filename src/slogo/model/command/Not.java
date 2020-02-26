package slogo.model.command;

public class Not extends Command {

  /**
   * Not constructor, flip the value of whatever given;
   * call super constructor, and update result
   * @param a
   */
  public Not(Double a){
    super(!(a!=0.0));
  }

}
