package slogo.model.command.booleancommand;

public class Not extends BooleanCommand {

  /**
   * Not constructor, flip the value of whatever given;
   * call super constructor, and update result
   * @param a
   */
  public Not(boolean a){
    super();
    super.changeBooleanResultToDouble(!a);
  }

}
