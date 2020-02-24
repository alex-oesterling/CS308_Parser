package slogo.model;

public class Turtle{

  private static final int DEFAULT_STARTING_X = 0;
  private static final int DEFAULT_STARTING_Y = 0;
  private static final int DEFAULT_HEADING = 90;
  private static final int QUAD1_BEGINS = 0;
  private static final int QUAD2_BEGINS = 90;
  private static final int QUAD3_BEGINS = 180;
  private static final int QUAD4_BEGINS = 270;
  private static final int QUAD4_ENDS = 360;
  private static final String DEFAULT_PEN_COLOR = "BLACK";

  private double xPosition;
  private double yPosition;
  private double homeX;
  private double homeY;
  //define heading as the degrees clockwise from North
  private double heading;
  private double turtleIsDrawing;
  private String penColorName;
  private double turtleIsVisible;

  /**
   * Turtle constructor to create turtle at a specific point
   * @param startingXPosition value to set xPosition to
   * @param startingYPosition value to set yPosition to
   * @param startingHeading value to set heading to; degrees clockwise from east
   * @param penColor string to set penColorName to
   */
  public Turtle(double startingXPosition, double startingYPosition, int startingHeading, String penColor){
    //TODO check that parameters are valid
    xPosition = startingXPosition;
    homeX = xPosition;
    yPosition = startingYPosition;
    homeY = yPosition;
    heading = startingHeading;
    penColorName = penColor;
    turtleIsDrawing = 1;
    turtleIsVisible = 1;
  }

  /**
   * Turtle constructor to create turtle at a specific point
   * @param startingXPosition value to set xPosition to
   * @param startingYPosition value to set yPosition to
   * @param startingHeading value to set heading to; degrees clockwise from east
   */
  public Turtle(double startingXPosition, double startingYPosition, int startingHeading){
    this(startingXPosition,startingYPosition, startingHeading, DEFAULT_PEN_COLOR); //values will be checked for validness in this() constructor
  }

  /**
   * Turtle constructor to create a turtle at the default origin
   * but with a specific pen color
   * @param penColor string to set penColorName to
   */
  public Turtle(String penColor){
    this(DEFAULT_STARTING_X, DEFAULT_STARTING_Y, DEFAULT_HEADING, penColor); //penColor will be checked for validness in this() constructor
  }

  /**
   * default Turtle constructor
   */
  public Turtle(){
    this(DEFAULT_STARTING_X,DEFAULT_STARTING_Y, DEFAULT_HEADING, DEFAULT_PEN_COLOR);
  }

  /**
   * set the x position
   * @param newXPosition value to set xPosition to
   */
  public void setX(double newXPosition){
    //TODO check that newXPosition is valid
    xPosition = newXPosition;
  }

  /**
   * get the x position
   * @return xPosition
   */
  public double getX(){
    return xPosition;
  }

  /**
   * set the y position
   * @param newYPosition value to set yPosition to
   */
  public void setY(double newYPosition){
    //TODO check that newYPosition is valid
    yPosition = newYPosition;
  }
  /**
   * get the y position
   * @return yPos
   */
  public double getY(){
    return yPosition;
  }

  /**
   * return the unique id of the turtle
   * @return the object's hashcode
   */
  public int getId(){
    return this.hashCode();
  }

  /**
   * set a new Pen color
   * @param newColor
   */
  public void setPenColor(String newColor){
    //TODO check that newColor is valid
    penColorName = newColor.toUpperCase();
  }

  /**
   * getter for String of the pen color name
   * @return
   */
  public String getPenColor(){
    return penColorName;
  }

  /**
   * Mover method that updates the position of the turtle
   * based on the current heading (which quadrant the turtle points to)
   * and the incoming distance d
   * @param distance incoming distance
   */
  public void move(double distance){
    double theta = heading;
    if(heading>QUAD2_BEGINS && heading<QUAD3_BEGINS){
      theta = QUAD3_BEGINS - heading;
    } else if(heading>QUAD3_BEGINS && heading<QUAD4_BEGINS){
      theta = heading - QUAD3_BEGINS;
    } else if(heading>QUAD4_BEGINS){
      theta = QUAD4_ENDS - heading;
    }
    xPosition += distance * Math.sin(theta);
    yPosition += distance * Math.cos(theta);
  }

  /**
   * Change the heading of the turtle, and make sure
   * that it's within 0 and 360 degrees
   * @param deltaTheta value to change by
   * @return deltaTheta change in heading
   */
  public double turn(double deltaTheta){
    heading+=deltaTheta;
    makeHeadingValid();
    return deltaTheta;
  }

  /**
   * Set heading to a given value, and make it valid
   * (between 0 and 360)
   * @param theta new heading
   * @return difference between the two headings
   */
  public double setHeading(double theta){
    double oldHeading = heading;
    heading = theta;
    makeHeadingValid();
    return Math.min(Math.abs(oldHeading-heading), QUAD4_ENDS-oldHeading+heading);
  }

  private void makeHeadingValid() {
    heading %= QUAD4_ENDS; //make it a value between -360 and 360
    if(heading < QUAD1_BEGINS){
      heading += QUAD4_ENDS; //make it a value between 0 and 360
    }
  }

  /**
   * put the turtle back where it started
   */
  public double goHome(){
    return moveToPosition(homeX, homeY);
  }

  /**
   * Set the turtle's position to a specified location,
   * and return the distance it travelled
   * @param newXPos new X position
   * @param newYPos new Y position
   * @return distance travelled by turtle
   */
  public double moveToPosition(double newXPos, double newYPos){
    double deltaX = xPosition - newXPos;
    double deltaY = yPosition - newYPos;

    xPosition = newXPos;
    yPosition = newYPos;

    return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
  }

  /**
   * Set the pen as up or down
   * @param penStatus true if penDown (drawing), false if penUp (not drawing)
   */
  public double setDrawing(double penStatus){
    turtleIsDrawing = penStatus;
    return penStatus;
  }

  /**
   * Return the drawing capabilities of the turtle
   * @return pen status
   */
  public double getDrawingStatus(){
    return turtleIsDrawing;
  }

  /**
   * Set the visibility of the turtle
   * @param visibility true if the turtle can be seen, false otherwise
   */
  public double setVisibility(double visibility){
    turtleIsVisible = visibility;
    return visibility;
  }

  /**
   * Tell the view if the turtle should be shown or not
   * @return turtleIsVisible
   */
  public double isTurtleVisible(){
    return turtleIsVisible;
  }

  /**
   * Return the heading of the current turtle in degrees (as a double)
   * @return  heading, in degrees
   */
  public double getHeading(){ return heading; }

  /**
   * Turns the turtle to face the point (x,y)
   * @param xPos the x position to set the turtle to
   * @param yPos the y position to set the turtle to
   * @return the degrees turned
   */
  public double pointTowards(double xPos, double yPos){
    return setHeading();
  }
}


