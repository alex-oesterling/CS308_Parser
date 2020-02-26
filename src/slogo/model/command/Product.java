package slogo.model.command;

public class Product extends Command {

  /**
   * Default Product constructor, calls the super constructor
   * and sets the value to return as the product of the two;
   * performs a*b
   * @param value1 a
   * @param value2 b
   */
  public Product(Double value1, Double value2){
    super(value1*value2);
  }

}
