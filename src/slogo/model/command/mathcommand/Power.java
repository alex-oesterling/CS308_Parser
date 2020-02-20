package slogo.model.command.mathcommand;

public class Power extends MathCommand{
  public Power(double base, double exponent){
    super();
    super.sendMathResultUp(Math.pow(base, exponent));
  }
}
