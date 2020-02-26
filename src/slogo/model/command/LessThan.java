package slogo.model.command;

public class LessThan extends Command {

  /**
   * LessThan constructor for checking a<b
   * Only works for ints and doubles
   * @param value1 a
   * @param value2 b
   */
  public LessThan(Double value1, Double value2){
    super(value1<value2);
  }

}
