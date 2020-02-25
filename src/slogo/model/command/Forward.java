package slogo.model.command;

import slogo.model.Turtle;

public class Forward extends Command {

  /**
   * Forward constructor, to get the value for going forward
   * Calls super and sets the forward value
   * @param body the specific turtle being used, what the forward value will be
   * @param value the value of how far it is going forward
   */
  public Forward(Turtle body, Double value) {
    super(value);
    body.move(value);
  }

  public static void main(String[] args) {
    Turtle t = new Turtle();
    Forward f = new Forward(t, 50.0);
    System.out.println("x: "+t.getX()+" y: "+t.getY()+" and H: "+t.getHeading());
  }
}
