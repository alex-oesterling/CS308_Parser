package slogo.model.command;

public class Power extends Command {

  /**
   * Power Constructor, passes up result to super
   * performs a^b
   * @param base a
   * @param exponent b
   */
  public Power(Double base, Double exponent){
    super(Math.pow(base, exponent));
  }
}
