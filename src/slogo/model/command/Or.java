package slogo.model.command;

public class Or extends Command {

  /**
   * Or constructors, checks for value of a||b,
   * call super constructor and update result
   * @param value1 a
   * @param value2 b
   */
  public Or(double value1, Double value2){
    super(value1!=0.0||value2!=0.0);
  }

}
