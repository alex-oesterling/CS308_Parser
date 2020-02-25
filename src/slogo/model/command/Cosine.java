package slogo.model.command.mathcommand;

import slogo.model.command.Command;

public class Cosine extends Command {

  /**
   * Default Cosine constructor, calls super constructor
   * sets value to return as the cosine of the parameter
   *
   * @param a
   */
  public Cosine(double a){
    super(Math.cos(a));
  }
}
