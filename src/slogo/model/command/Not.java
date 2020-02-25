package slogo.model.command.booleancommand;

import slogo.model.command.Command;

public class Not extends Command {

  /**
   * Not constructor, flip the value of whatever given;
   * call super constructor, and update result
   * @param a
   */
  public Not(double a){
    super(!(a!=0.0));
  }

  public static void main(String[] args) {
    double a = 3.0;
    double b = 0;
    Not c = new Not(a);
    System.out.println("!"+a+" : "+c.getResult());
    c = new Not(b);
    System.out.println("!"+b+" : "+c.getResult());
  }

}
