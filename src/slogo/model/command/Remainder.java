package slogo.model.command;

public class Remainder extends Command {

  /**
   * Default constructor for finding the remainder of a division
   * calls the super constructor, and sets the value to return
   * @param dividend
   * @param divisor
   */
  public Remainder(Double dividend, Double divisor){
    super(dividend%divisor);
  }
}
