package slogo.model.command.mathcommand;

public class NaturalLog extends MathCommand {
  public NaturalLog(double a){
    super();
    super.sendMathResultUp(Math.log(a));
  }
}
