package slogo.model.command.booleancommand;

import slogo.model.command.Command;

public class And extends Command {

  /**
   * constructor of and logic
   * @param value1 condition 1
   * @param value2 condition 2
   */
  public And(double value1, double value2){
   super(value1!=0.0&&value2!=0.0); //'convert' to booleans
  }

  public static void main(String[] args) {
    double a = 1.0;
    double b = 0.0;
    double c = 1.0;
    double d = -1.0;
    And e = new And(a,b);
    System.out.println(a+"&&"+b+": "+e.getResult());
    e = new And(a,c);
    System.out.println(a+"&&"+c+": "+e.getResult());
    e = new And(a,d);
    System.out.println(a+"&&"+d+": "+e.getResult());
    e = new And(d,b);
    System.out.println(d+"&&"+b+": "+e.getResult());
  }
}
