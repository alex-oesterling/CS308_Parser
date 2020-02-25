package slogo.model.command;

public class Cosine extends Command {

  /**
   * Default Cosine constructor, calls super constructor
   * sets value to return as the cosine of the parameter
   *
   * @param a
   */
  public Cosine(Double a){
    super(Math.cos(a));
  }
}
