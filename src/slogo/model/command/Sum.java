package slogo.model.command.mathcommand;

import slogo.model.command.Command;

public class Sum extends Command {

  /**
   * Default constructor for Sum, calls the super constructor
   * and sets the value to return as the sum of the two parameters
   * performs a+b
   * @param addend1 a
   * @param addend2 b
   */
  public Sum(Double addend1, Double addend2){
    super(addend1+addend2);
    System.out.println("sum "+addend1+" "+addend2+" = "+(addend1+addend2));
  }
}
