package slogo.view.turtles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import slogo.controller.Controller;

/**
 * This class creates an instance of a turtle and stores all the relevant turtle information. In this way, we are able to
 * add multiple instances of turtles and update the turtle information that is stored.
 */
public class TurtleView{
    public static final String XML_FILEPATH = "user.dir";
    public static final int TURTLE_WIDTH = 50;
    public static final int TURTLE_HEIGHT = 40;
    public static final int TURTLE_SCREEN_WIDTH = 500;
    public static final int TURTLE_SCREEN_HEIGHT = 500;
    public static final int PATH_STROKE_WIDTH = 3;
    private static final String ERROR_DIALOG = "Please Choose Another File";

    private Group myPaths;
    private Group myTurtles;
    private boolean penStatus;
    private ImageView myImage;
    private Color myPenColor;
    private double currentX;
    private double currentY;
    private SequentialTransition st;
    private double heading;
    private double prevX;
    private double prevY;
    private double prevHeading;
    private double pathStrokeWidth;
    private String turtleName;
    private SimpleObjectProperty<ObservableList<String>> myTurtle;
    private boolean stopped;
    private TurtleAnimator turtleAnimator;
    private Controller myController;

    /**
     * Creates an instance of all the important variables that need to be referenced from other methods in this class.
     * @param turtles - a group of all the turtles from the visualizer
     * @param paths - a group of the paths of the turtles
     * @param name - the name of the turtle in which this turtleview instance is being created for
     */
    public TurtleView(Group turtles, Group paths, String name, Controller controller){
        penStatus = true;
        myPaths = paths;
        turtleName = name;
        myTurtles = turtles;
        myImage = createTurtle();
        myPenColor = Color.BLACK;
        myTurtle = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        pathStrokeWidth = PATH_STROKE_WIDTH;
        st = new SequentialTransition();
        currentX = myImage.getTranslateX() + myImage.getBoundsInLocal().getWidth()/2;
        currentY = myImage.getTranslateY() + myImage.getBoundsInLocal().getHeight()/2;
        heading = 0;
        prevX = currentX;
        prevY = currentY;
        prevHeading = heading;
        stopped = true;
        turtleAnimator = new TurtleAnimator(this, myImage, myPaths);
        myController = controller;
    }

    private ImageView createTurtle(){
        String string = "resources/turtles/turtle1.png";
        ImageView turtleImage = new ImageView(string);
        turtleImage.setFitWidth(TURTLE_WIDTH);
        turtleImage.setFitHeight(TURTLE_HEIGHT);
        turtleImage.setTranslateX(TURTLE_SCREEN_WIDTH / 2 - turtleImage.getBoundsInLocal().getWidth() / 2);
        turtleImage.setTranslateY(TURTLE_SCREEN_HEIGHT / 2 - turtleImage.getBoundsInLocal().getHeight() / 2);
        myTurtles.getChildren().add(turtleImage);
        return turtleImage;
    }

    /**
     * When a button is clicked in UserInterface to choose a turtle image, a file chooser appears
     * @param imageFile - the selected file from the file chooser
     */
    public void chooseTurtle(File imageFile) {
        ImageView turtleImage = new ImageView();
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            turtleImage.setImage(image);
            turtleImage.setFitWidth(TURTLE_WIDTH);
            turtleImage.setFitHeight(TURTLE_HEIGHT);
            myTurtles.getChildren().remove(myImage);
            myImage = turtleImage;
            myTurtles.getChildren().add(myImage);
            set(currentX-TURTLE_SCREEN_WIDTH/2, TURTLE_SCREEN_HEIGHT/2-currentY, heading);
        } catch (IllegalArgumentException e){
            return;
        } catch (IOException e) {
            retryLoadFile(e.getMessage());
        }
    }

    private void retryLoadFile(String message) {
        boolean badFile;
        displayError(message);
        do {
            badFile = false;
            try {
                chooseTurtle(getTurtleImage(new Stage()));
            } catch (NullPointerException e){
                return;
            } catch (Exception e){
                displayError(e.getMessage());
                badFile = true;
            }
        } while (badFile);
    }

    private void displayError(String message) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText(message);
        errorAlert.setContentText(ERROR_DIALOG);
        errorAlert.showAndWait();
    }

    /**
     * Creates a file chooser to choose a new image from the user's files.
     * @param stage
     * @return - the file
     */
    public File getTurtleImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Turtle Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        fileChooser.setInitialDirectory(new File(System.getProperty(XML_FILEPATH)));
        return fileChooser.showOpenDialog(stage);
    }

    public void update(double newX, double newY, double orientation){turtleAnimator.update(newX, newY, orientation);}

    public void playAnimation(){turtleAnimator.playAnimation();}


    /**
     * This method creates an object property which is then binded with the turtle's information and displayed on the screen.
     * In order to do this, all the necessary info is added to an observable list.
     * @return SimpleObjectProperty of an observable list of strings.
     */
    public SimpleObjectProperty<ObservableList<String>> turtleStats(){
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(turtleName,
                Double.toString(currentX),
                Double.toString(currentY),
                Double.toString(heading),
                String.valueOf(myPenColor),
                Double.toString(pathStrokeWidth),
                Boolean.toString(penStatus));
        myTurtle.setValue(observableList);
        return myTurtle;
    }

    /**
     * Called by the visualizer to update the pen color when either the colorpicker changes its color or the user writes
     * a command which changes the color.
     * @param color
     */
    public void updatePen(Color color){
        myPenColor = color;
    }

    /**
     * Called by the visualizer to update the pen status when it is changed dynamically in the pen properties window.
     * @param status - boolean passed in
     */
    public void changePenStatus(Boolean status){penStatus = status;}

    /**
     * Called by the visualizer to update the pen width when it is changed dynamically in the pen properties window.
     * @param penWidth
     */
    public void changePenWidth(double penWidth) {
        try {
            pathStrokeWidth = penWidth;
        } catch (NumberFormatException e){
            return;
        }
    }

    /**
     * Called by controller and then visualizer which reads in a command that determines if the turtle is visible or not.
     * The controller then returns a double which is passed to this method.
     * @param value - double value determining if the turtle is visible or not
     */
    public void updateTurtleView(double value){
        myImage.setVisible(value != 0.0);
    }

    /**
     * Called by the controller and then the visualizer which reads in a double value from the user commands and then sets the
     * pen width.
     * @param value
     */
    public void setPenSize(double value){
        pathStrokeWidth = value;
    }

    /**
     * Works in conjunction with the shape palette in which the user specifies the value of the shape they want and then
     * the image is passed into this method and set to be the turtle.
     * @param turtle
     */
    public void setShape(ImageView turtle){
        myTurtles.getChildren().remove(myImage);
        myImage = turtle;
        myTurtles.getChildren().add(myImage);
        set(currentX-TURTLE_SCREEN_WIDTH/2, TURTLE_SCREEN_HEIGHT/2-currentY, heading);
    }

    /**
     * Another pen status update method, however, this one is defined by the user in the command box. The model then returns
     * a value which distinguishes the pen to either be up or down.
     * @param value
     */
    public void updatePenStatus(double value){
        penStatus = (value != 0.0);
    }

    /**
     *
     * @return Color - the current pen color
     */
    public Color getColor(){return myPenColor;}

    public double getLineWidth(){return pathStrokeWidth;}

    public boolean penDown(){return penStatus;}

    /**
     * Sets the opacity of the turtle. This is called in order to display some turtles to be active while others to be inactive.
     * @param newValue - the new turtle opacity
     */
    public void setOpacity(double newValue){
        myImage.setOpacity(newValue);
    }

    /**
     * Refactored code in order to set the turtles position when a new turtle image is used
     * @param newX - new x position
     * @param newY - new y position
     * @param newHeading - new orientation
     */
    public void set(double newX, double newY, double newHeading){
        updateCurrent(newX, newY, newHeading);
        System.out.println(newX + " " + newY + " " + newHeading);

        myImage.setTranslateX(currentX-myImage.getBoundsInLocal().getWidth()/2);
        myImage.setTranslateY(currentY-myImage.getBoundsInLocal().getHeight()/2);
        myImage.setRotate(heading);

        myController.setTurtle(newX, newY, newHeading);
    }

    public void setCommandSize(int size){turtleAnimator.setCommandSize(size);}

    /**
     * Retrieves all the turtles data and uses it for the XML file.
     * @return
     */
    public Double[] getData(){
        double coordinateX = currentX - TURTLE_SCREEN_WIDTH/2;
        double coordinateY = TURTLE_SCREEN_HEIGHT/2 - currentY;
        return new Double[]{coordinateX, coordinateY, heading};
    }

    public String getName(){
        return turtleName;
    }

    public void pause(){turtleAnimator.pause();}

    public void play(){turtleAnimator.play();}

    public void step(){turtleAnimator.step();}

    public void resetAnimation(){turtleAnimator.resetAnimation();}

    public void undo(){ turtleAnimator.undo(); }

    public void setSpeed(int value){turtleAnimator.setSpeed(value);}

    public void updateHistory(){
        prevX = currentX;
        prevY = currentY;
        prevHeading = heading;
    }

    public void updateCurrent(double newX, double newY, double orientation){
        newY = -newY;
        newX += TURTLE_SCREEN_WIDTH/2;
        newY += TURTLE_SCREEN_HEIGHT/2;
        currentX = newX;
        currentY = newY;
        heading = orientation;
    }

    public void rewindCoords(){
        System.out.println(prevX + "" + prevY);
        myImage.setTranslateX(prevX-myImage.getBoundsInLocal().getWidth()/2);
        myImage.setTranslateY(prevY-myImage.getBoundsInLocal().getHeight()/2);
        myImage.setRotate(prevHeading);
    }

    public void undoAnimation(){
        set(prevX-TURTLE_SCREEN_WIDTH/2, TURTLE_SCREEN_HEIGHT/2-prevY, prevHeading);
    }
}