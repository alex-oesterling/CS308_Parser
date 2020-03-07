package slogo.model;

import java.util.List;

import slogo.view.Visualizer;

public class Turtle{

  public static final int DEFAULT_STARTING_X = 0;
  public static final int DEFAULT_STARTING_Y = 0;
  private static final double DEFAULT_HEADING = 0;
  private static final int DEFAULT_PEN_INDEX = 0;
  private static final int DEFAULT_BG_INDEX = 7;
  private static final int DEFAULT_SHAPE_INDEX = 0;
  private static final int DEFAULT_SIZE_PIXELS = 3;
  private static final int POSITIVE = 1;
  private static final int NEGATIVE = -1;
  private static final int QUAD2_BEGINS = 90;
  private static final int QUAD3_BEGINS = 180;
  private static final int QUAD4_BEGINS = 270;
  private static final int QUAD4_ENDS = 360;
  private static final double VISIBLE = 1;
  private static final double INVISIBLE = 0;
  private static final double DRAWING = 1.0;
  private static final double NOT_DRAWING = 0.0;
  //Fixme take out importing of the visualizer class
  private static final double VIEW_BOUNDS_X = Visualizer.TURTLE_SCREEN_WIDTH;
  private static final double VIEW_BOUNDS_Y = Visualizer.TURTLE_SCREEN_HEIGHT;
  private static final List<String> DEFAULT_TURTLE_NAMES = List.of("Alex", "Dana", "Hannah", "Robert", "Tyler");
  private double xPosition;
  private double yPosition;
  private double homeX;
  private double homeY;
  private double heading; //define heading as the degrees clockwise from North
  private double turtleIsDrawing;
  private double turtleIsVisible;
  private double penIndex;
  private double bgIndex;
  private double shapeIndex;
  private double sizePixels;
  private double Id;
  private Double turtleCount;
  private String name;

  public Turtle(String turtleName, double startingXPosition, double startingYPosition, double startingHeading, Double IdOfTurtle){
    Id = IdOfTurtle;
    turtleCount = IdOfTurtle;
    initializeBasicThings(startingXPosition, startingYPosition, startingHeading);
    name = turtleName;
  }

  /**
   * Turtle constructor to create turtle at a specific point and specific pen color
   * @param startingXPosition value to set xPosition to
   * @param startingYPosition value to set yPosition to
   * @param startingHeading value to set heading to; degrees clockwise from east
   */
  public Turtle(double startingXPosition, double startingYPosition, double startingHeading, Double IdOfTurtle){
    Id = IdOfTurtle;
    turtleCount = IdOfTurtle;
    initializeBasicThings(startingXPosition, startingYPosition, startingHeading);
    name = DEFAULT_TURTLE_NAMES.get((int) Math.floor(Math.random()*(DEFAULT_TURTLE_NAMES.size())));
  }

  /**
   * Turtle constructor to create a turtle at the default origin
   * but with a specific name
   * @param turtleName string to set name to
   */
  public Turtle(String turtleName, Double IdOfTurtle){
    this(DEFAULT_STARTING_X, DEFAULT_STARTING_Y, DEFAULT_HEADING, IdOfTurtle);
    name = turtleName;
  }

  /**
   * Default Turtle constructor
   */
  public Turtle(Double IdOfTurtle){
    this(DEFAULT_STARTING_X, DEFAULT_STARTING_Y, DEFAULT_HEADING, IdOfTurtle);
  }

  /**
   * Initializes all of the basic things needed for the turtle and its view
   * @param startingXPosition the x pos of where the turtle starts
   * @param startingYPosition the y pos of where the turtle starts
   * @param startingHeading the direction/degrees the turtle starts out facing
   */
  public void initializeBasicThings(double startingXPosition, double startingYPosition, double startingHeading){
    setX(startingXPosition);
    homeX = xPosition;
    setY(startingYPosition);
    homeY = yPosition;
    heading = startingHeading;
    turtleIsDrawing = DRAWING;
    turtleIsVisible = VISIBLE;
    penIndex = DEFAULT_PEN_INDEX;
    bgIndex = DEFAULT_BG_INDEX;
    shapeIndex = DEFAULT_SHAPE_INDEX;
    sizePixels = DEFAULT_SIZE_PIXELS;
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
    setX(xPosition + (xSign * distance * Math.sin(theta)));
    setY(yPosition + (ySign * distance * Math.cos(theta)));
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

  private double makeHeadingValid(double headingToChange) {
    return headingToChange;
  }

  /**
   * Put the turtle back where it started
   */
  public double goHome(){
    setHeading(DEFAULT_HEADING);
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
    setX(xPos);
    setY(yPos);
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
   * Gets the ID of the turtle
   * @return the ID of the turtle
   */
  public double getId(){
    return Id;
  }

  /**
   * get the turtle's name
   * @return name of turtle
   */
  public String getName(){
    return name;
  }

  /**
   * Get's the turtle count
   * @return amount of turtles created thus far
   */
  public Double getTurtleCount(){
    return turtleCount;
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
   * Gets the current pen color
   * @return the index of the color
   */
  public double getPenColor(){
    return penIndex;
  }

  /**
   * Gets the current shape of the turtle
   * @return the index of the shape
   */
  public double getShape(){
    return shapeIndex;
  }

  /**
   * Set the pen as up or down
   * @param penStatus true if penDown (drawing), false if penUp (not drawing)
   */
  public void setDrawing(double penStatus){
    if(penStatus!=0.0){
      turtleIsDrawing = DRAWING;
    } else {
      turtleIsDrawing = NOT_DRAWING;
    }
  }

  /**
   * Set the visibility of the turtle
   * @param visibility true if the turtle can be seen, false otherwise
   */
  public void setVisibility(double visibility){
    if(visibility!=0.0){
      turtleIsVisible = VISIBLE;
    } else {
      turtleIsVisible = INVISIBLE;
    }
  }

  /**
   * set the turtle's name to something new
   * @param newName name to be changed to
   */
  public void setName(String newName){
    name = newName;
  }

  /**
   * Set the x position
   * @param newXPosition value to set xPosition to
   */
  public void setX(double newXPosition){
    xPosition = newXPosition;
    if(xPosition < -VIEW_BOUNDS_X/2){
      xPosition = -VIEW_BOUNDS_X/2;
    } else if (xPosition > VIEW_BOUNDS_X/2){
      xPosition = VIEW_BOUNDS_X/2;
    }
  }

  /**
   * Set the y position
   * @param newYPosition value to set yPosition to
   */
  public void setY(double newYPosition){
    yPosition = newYPosition;
    if(yPosition < -VIEW_BOUNDS_Y/2){
      yPosition = -VIEW_BOUNDS_Y/2;
    } else if (yPosition > VIEW_BOUNDS_Y/2){
      yPosition = VIEW_BOUNDS_Y/2;
    }
  }

  /**
   * Set the color of the pen
   * @param index the index to change to
   */
  public void setPenColor(Double index){
    penIndex = index;
  }

  /**
   * Set the shape (image) of the turtle
   * @param index the index to change to
   */
  public void setShape(Double index){
    shapeIndex = index;
  }

  /**
   * Set the background color of the window
   * @param index the index of the color to change to
   */
  public void setBackground(Double index){
    bgIndex = index;
  }

  /**
   * Set the pen size in pixels
   * @param pixels the size to set the pen to
   */
  public void setPenSize(Double pixels){
    sizePixels = pixels;
  }

}