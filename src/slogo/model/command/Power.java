package slogo.model.command;

public class Power extends Command {

  /**
   * Power Constructor, passes up result to super
   * performs a^b
   * @param base a
   * @param exponent b
   */
  public Power(Double exponent, Double base){
    super(Math.pow(base, exponent));
  }
}
