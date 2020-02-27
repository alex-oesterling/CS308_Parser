package slogo.model;

public class Turtle{

  public static final int DEFAULT_STARTING_X = 0;
  public static final int DEFAULT_STARTING_Y = 0;
  private static final int DEFAULT_HEADING = 0;
  private static final int POSITIVE = 1;
  private static final int NEGATIVE = -1;
  private static final int QUAD1_BEGINS = 0;
  private static final int QUAD2_BEGINS = 90;
  private static final int QUAD3_BEGINS = 180;
  private static final int QUAD4_BEGINS = 270;
  private static final int QUAD4_ENDS = 360;
  private static final double VISIBLE = 1;
  private static final double INVISIBLE = 0;
  private static final double DRAWING = 1.0;
  private static final double NOT_DRAWING = 0.0;
  private static final String DEFAULT_PEN_COLOR = "BLACK";

  private double xPosition;
  private double yPosition;
  private double homeX;
  private double homeY;
  private double heading;           //define heading as the degrees clockwise from North
  private double turtleIsDrawing;
  private double turtleIsVisible;
  private String penColorName;

  /**
   * Turtle constructor to create turtle at a specific point and specific pen color
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
   * Turtle constructor to create turtle at a specific point with no specific pen color
   * @param startingXPosition value to set xPosition to
   * @param startingYPosition value to set yPosition to
   * @param startingHeading value to set heading to; degrees clockwise from east
   */
  public Turtle(double startingXPosition, double startingYPosition, int startingHeading){
    //values will be checked for validness in this() constructor
    this(startingXPosition,startingYPosition, startingHeading, DEFAULT_PEN_COLOR);
  }

  /**
   * Turtle constructor to create a turtle at the default origin
   * but with a specific pen color
   * @param penColor string to set penColorName to
   */
  public Turtle(String penColor){
    //penColor will be checked for validness in this() constructor
    this(DEFAULT_STARTING_X, DEFAULT_STARTING_Y, DEFAULT_HEADING, penColor);
  }

  /**
   * Default Turtle constructor
   */
  public Turtle(){
    this(DEFAULT_STARTING_X, DEFAULT_STARTING_Y, DEFAULT_HEADING, DEFAULT_PEN_COLOR);
  }

  /**
   * Mover method that updates the position of the turtle
   * based on the current heading (which quadrant the turtle points to)
   * and the incoming distance d
   * @param distance incoming distance
   */
  public void move(double distance){
    double theta = heading;
    int xSign = POSITIVE;
    int ySign = POSITIVE;

    if(heading>QUAD2_BEGINS && heading<QUAD3_BEGINS){
      theta = QUAD3_BEGINS - heading;
      ySign = NEGATIVE;
    }
    else if(heading>QUAD3_BEGINS && heading<QUAD4_BEGINS){
      theta = heading - QUAD3_BEGINS;
      xSign = NEGATIVE;
      ySign = NEGATIVE;
    }
    else if(heading>QUAD4_BEGINS){
      theta = QUAD4_ENDS - heading;
      xSign = NEGATIVE;
    }

    theta = convertToRadians(theta);
    xPosition += xSign * distance * Math.sin(theta);
    yPosition += ySign * distance * Math.cos(theta);
  }

  private double convertToRadians(double theta){
    return theta*Math.PI/QUAD3_BEGINS;
  }

  /**
   * Change the heading of the turtle, and make sure
   * that it's within 0 and 360 degrees
   * @param deltaTheta value to change by
   * @return deltaTheta change in heading
   */
  public double turn(double deltaTheta){
    heading+=deltaTheta;
    heading = makeHeadingValid(heading);
    return deltaTheta;
  }
  /**
   * Turns the turtle to face the point (x,y)
   * @param xPos the x position to set the turtle to
   * @param yPos the y position to set the turtle to
   * @return the degrees turned
   */
  public void pointTowards(double xPos, double yPos){
    setHeading(headingTowards(xPos,yPos));
  }

  /**
   * Calculate the angle from North (in degrees) needed to connect
   * a line from the origin to the point
   * @param xPos x value of the point
   * @param yPos y value of the point
   * @return a value between 0 and 360 degrees of the angle CW from north to the point
   */
  public double headingTowards(double xPos, double yPos){
    double theta = Math.atan(xPos/yPos);                         //value within quadrant; good for quadrants I and III
    if((xPos < 0) != (yPos < 0)){ theta = Math.atan(yPos/xPos);} //value within quadrants II and IV

    theta = convertToDegrees(theta);

    if(xPos > 0 && yPos < 0){ theta += QUAD2_BEGINS; }           //in quadrant ii
    else if (xPos < 0 && yPos < 0){ theta += QUAD3_BEGINS; }     //in quadrant iii
    else if(xPos < 0 && yPos > 0){ theta += QUAD4_BEGINS; }      //in quadrant iv
    System.out.println(theta);
    return makeHeadingValid(theta);
  }

  private double convertToDegrees(double theta){
    return theta*QUAD3_BEGINS/Math.PI;
  }

  /**
   * Get the delta theta between heading and a new heading
   * (between 0 and 360)
   * @param theta new heading
   * @return difference between the two headings
   */
  public double getDeltaTheta(double theta, double comparisonHeading){
    double oldHeading = heading;
    comparisonHeading = theta;
    comparisonHeading = makeHeadingValid(comparisonHeading);
    return Math.min(Math.abs(oldHeading-comparisonHeading), QUAD4_ENDS-oldHeading+comparisonHeading);
  }

  /**
   * Set the heading to a new angle
   * @param degreesCWFromNorth angle to set the heading in DEGREES
   */
  public void setHeading(double degreesCWFromNorth){
    heading = degreesCWFromNorth;
    heading = makeHeadingValid(heading);
  }

  private double makeHeadingValid(double headingToChange) { //FIXME commented out by Alex Oesterling to handle turtle rotating correctly -- Just so basic looks nice
//    headingToChange %= QUAD4_ENDS;                                      //make it a value between -360 and 360
//    if(headingToChange < QUAD1_BEGINS){ headingToChange += QUAD4_ENDS; }        //make it a value between 0 and 360
    return headingToChange;
  }

  /**
   * Put the turtle back where it started
   */
  public double goHome(){
    return moveToPosition(homeX, homeY);
  }

  /**
   * Calculate the distance from the turtle's current point to a specific point
   * @param xPos x value of the point
   * @param yPos y value of the point
   * @return distance away from point
   */
  public double distanceToPosition(double xPos, double yPos){
    double deltaX = xPosition - xPos;
    double deltaY = yPosition - yPos;

    return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
  }

  /**
   * Set the turtle's position to a specified location,
   * and return the distance it travelled
   * @param xPos new X position
   * @param yPos new Y position
   * @return distance travelled by turtle
   */
  public double moveToPosition(double xPos, double yPos){
    double distance = distanceToPosition(xPos, yPos);
    xPosition = xPos;
    yPosition = yPos;

    return distance;
  }

  /**
   * Tell the view if the turtle should be shown or not
   * @return turtleIsVisible
   */
  public double isTurtleVisible(){
    return turtleIsVisible;
  }

  /**
   * Return the drawing capabilities of the turtle
   * @return pen status
   */
  public double getDrawingStatus(){
    return turtleIsDrawing;
  }

  /**
   * Return the heading of the current turtle in degrees (as a double)
   * @return  heading, in degrees
   */
  public double getHeading(){
    return heading;
  }

  /**
   * Return the unique id of the turtle
   * @return the object's hashcode
   */
  public int getId(){
    return this.hashCode();
  }

  /**
   * Getter for String of the pen color name
   * @return
   */
  public String getPenColor(){
    return penColorName;
  }

  /**
   * Get the x position
   * @return xPosition
   */
  public double getX(){
    return xPosition;
  }

  /**
   * Get the y position
   * @return yPos
   */
  public double getY(){
    return yPosition;
  }

  /**
   * Set the pen as up or down
   * @param penStatus true if penDown (drawing), false if penUp (not drawing)
   */
  public void setDrawing(double penStatus){
    if(penStatus!=0.0){ turtleIsDrawing = DRAWING; }
    else { turtleIsDrawing = NOT_DRAWING; }
  }

  /**
   * Set a new Pen color
   * @param newColor
   */
  public void setPenColor(String newColor){
    penColorName = newColor.toUpperCase();
  }
  //TODO check that newColor is valid

  /**
   * Set the visibility of the turtle
   * @param visibility true if the turtle can be seen, false otherwise
   */
  public void setVisibility(double visibility){
    if(visibility!=0.0){ turtleIsVisible = VISIBLE; }
    else { turtleIsVisible = INVISIBLE; }
  }

  /**
   * Set the x position
   * @param newXPosition value to set xPosition to
   */
  public void setX(double newXPosition){
    xPosition = newXPosition;
  }
  //TODO check that newXPosition is valid

  /**
   * Set the y position
   * @param newYPosition value to set yPosition to
   */
  public void setY(double newYPosition){
    yPosition = newYPosition;
  }
  //TODO check that newYPosition is valid
}