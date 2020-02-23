package slogo.model.command.mathcommand;

public class Product extends MathCommand {

  /**
   * Default Product constructor, calls the super constructor
   * and sets the value to return as the product of the two;
   * performs a*b
   * @param value1 a
   * @param value2 b
   */
  public Product(double value1, double value2){
    super(value1*value2);
  }

}
