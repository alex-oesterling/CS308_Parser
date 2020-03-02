package slogo.model.command;

public class GreaterThan extends Command {

  /**
   * GreaterThan constructor for checking a>b
   * Only works for ints, Integers, doubles, Doubles, floats, and Floats
   * @param value1 a
   * @param value2 b
   */
  public GreaterThan(Double value2, Double value1){
    super(value1>value2);
  }

}
