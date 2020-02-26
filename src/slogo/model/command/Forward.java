package slogo.model.command;

import slogo.model.Turtle;

public class Forward extends Command {

  private Turtle t;
  private Double distance;

  /**
   * Forward constructor, to get the value for going forward
   * Calls super
   * @param body the specific turtle being used, what the forward value will be
   * @param value the value of how far it is going forward
   */
  public Forward(Turtle body, Double value) {
    super(value);
    t = body;
    distance = value;
  }

  /**
   * Allows the turtle to move forward
   * @return distance travelled
   */
  @Override
  public Double execute(){
    t.move(distance);
    return distance;
  }

  /**
   * Get the distance to be returned
   * @return distance
   */
  @Override
  public Double getResult() {
    return distance;
  }

  public static void main(String[] args) {
    Turtle t = new Turtle();
    t.setHeading(315.0);
    Forward f = new Forward(t, 50.0);
    System.out.println("x: " + t.getX() + " y: " + t.getY() + " heading: " + t.getHeading());
    System.out.println(f.getResult());
    System.out.println(f.execute());
    System.out.println("after:: x: " + t.getX() + " y: " + t.getY() + " heading: " + t.getHeading());
  }
}

