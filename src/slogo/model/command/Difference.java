package slogo.model.command;

public class Difference extends Command {

  /**
   * Default constructor that calls the super constructor
   * and sets the value to return as the difference between the two provided values;
   * performs a-b
   * @param minuend a
   * @param subtrahend b
   */
  public Difference(Double minuend, Double subtrahend){
    super(minuend-subtrahend);
  }
}
