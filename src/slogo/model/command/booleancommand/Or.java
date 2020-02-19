package slogo.model.command.booleancommand;

public class Or extends BooleanCommand {

  /**
   * Or constructors, checks for value of a||b,
   * call super constructor and update result
   * @param bool1 a
   * @param bool2 b
   */
  public Or(boolean bool1, boolean bool2){
    super();
    super.changeBooleanResultToDouble(bool1||bool2);
  }
}
