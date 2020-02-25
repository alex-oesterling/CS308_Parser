package slogo.model.command;

public class Random extends Command {

  /**
   * Random contructor, calls super constructor and sets
   * value to return as a random number greater than zero and strictly
   * less than max
   * @param max maximum random value
   */
  public Random(Double max){
    super(Math.random()*max);
  }
}
