package slogo.model;

public class Turtle {

  private static final int DEFAULT_STARTING_X = 0;
  private static final int DEFAULT_STARTING_Y = 0;
  private static final String DEFAULT_PEN_COLOR = "BLACK";

  private double xPosition;
  private double yPosition;
  private String penColorName;

  /**
   * Turtle constructor to create turtle at a specific point
   * @param startingXPosition value to set xPosition to
   * @param startingYPosition value to set yPosition to
   * @param penColor string to set penColorName to
   */
  public Turtle(double startingXPosition, double startingYPosition, String penColor){
    //TODO check that parameters are valid
    xPosition = startingXPosition;
    yPosition = startingYPosition;
    penColorName = penColor;
  }

  /**
   * Turtle constructor to create turtle at a specific point
   * @param startingXPosition value to set xPosition to
   * @param startingYPosition value to set yPosition to
   */
  public Turtle(double startingXPosition, double startingYPosition){
    this(startingXPosition,startingYPosition, DEFAULT_PEN_COLOR); //values will be checked for validness in this() constructor
  }

  /**
   * Turtle constructor to create a turtle at the default origin
   * but with a specific pen color
   * @param penColor string to set penColorName to
   */
  public Turtle(String penColor){
    this(DEFAULT_STARTING_X, DEFAULT_STARTING_Y, penColor); //penColor will be checked for validness in this() constructor
  }

  /**
   * default Turtle constructor
   */
  public Turtle(){
    this(DEFAULT_STARTING_X,DEFAULT_STARTING_Y, DEFAULT_PEN_COLOR);
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
}
