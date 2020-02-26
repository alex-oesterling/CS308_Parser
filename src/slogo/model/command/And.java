package slogo.model.command;

public class And extends Command {

  /**
   * Constructor of 'and' logic
   * @param value1 condition 1
   * @param value2 condition 2
   */
  public And(Double value1, Double value2){
   super(value1!=0.0&&value2!=0.0); //'convert' to booleans
  }

}
