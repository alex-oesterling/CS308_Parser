package slogo.model.command.mathcommand;

import slogo.model.command.Command;

public class Random extends Command {

  /**
   * Random contructor, calls super constructor and sets
   * value to return as a random number greater than zero and strictly
   * less than max
   * @param max maximum random value
   */
  public Random(double max){
    super(Math.random()*max);
  }
}
