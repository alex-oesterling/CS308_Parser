package slogo.model.command;

public class LessThan extends Command {

  /**
   * LessThan constructor for checking a<b
   * Only works for ints and doubles
   * @param value1 a
   * @param value2 b
   */
  public LessThan(Double value2, Double value1){
    super(value1<value2);
  }

}
