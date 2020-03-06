package slogo.view;

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
    public static final double PATH_OPACITY = .75;
    public static final double PATH_NO_OPACITY = 0.0;
    private static final String ERROR_DIALOG = "Please Choose Another File";

    private Group myPaths;
    private Queue<Path> pathHistory;
    private List<Path> backupPathHistory;
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
    private int animationDuration;
    private int totalDuration;
    private Queue<Transition> transitionQueue;
    private Queue<Transition> backupTransitionQueue;
    private boolean stopped;

    /**
     * Creates an instance of all the important variables that need to be referenced from other methods in this class.
     * @param turtles - a group of all the turtles from the visualizer
     * @param paths - a group of the paths of the turtles
     * @param name - the name of the turtle in which this turtleview instance is being created for
     */
    public TurtleView(Group turtles, Group paths, String name){
        penStatus = true;
        myPaths = paths;
        pathHistory = new LinkedList<>();
        backupPathHistory = new LinkedList<>();
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
        totalDuration = 500;
        animationDuration = totalDuration;
        transitionQueue = new LinkedList<>();
        backupTransitionQueue = new LinkedList<>();
        stopped = true;
    }

    private ImageView createTurtle(){
        String string = "resources/turtles/turtle1.png";
        ImageView turtleImage = new ImageView(string);
        turtleImage.setFitWidth(TURTLE_WIDTH);
        turtleImage.setFitHeight(TURTLE_HEIGHT);
        st = new SequentialTransition();
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

    /**
     * Updates the turtle's position, is called in the controller and updates the position whenever a corresponding command
     * is typed in.
     * @param newX - new x position
     * @param newY - new y position
     * @param orientation - new orientation
     */
    public void update(double newX, double newY, double orientation){
        if(transitionQueue.isEmpty()){
            prevX = currentX;
            prevY = currentY;
            prevHeading = heading;
        }
        newY = -newY;
        newX += TURTLE_SCREEN_WIDTH/2;
        newY += TURTLE_SCREEN_HEIGHT/2;
        double oldX = currentX;
        double oldY = currentY;
        double oldHeading = heading;
        currentX = newX;
        currentY = newY;
        heading = orientation;
        if(newX != oldX || newY != oldY) {
            Path path = new Path();
            if(penStatus){
                path.setOpacity(PATH_OPACITY);
                path.setStrokeWidth(pathStrokeWidth);
            } else {
                path.setOpacity(PATH_NO_OPACITY);
            }
            path.setStroke(myPenColor);
            path.getElements().add(new MoveTo(oldX, oldY));
            path.getElements().add(new LineTo(newX, newY));
            PathTransition pt = new PathTransition(Duration.millis(animationDuration), path, myImage);
            pt.setPath(path);
            transitionQueue.add(pt);
            pathHistory.add(path);
        }
        if(orientation != oldHeading) {
            RotateTransition rt = new RotateTransition(Duration.millis(animationDuration),
                myImage);
            rt.setToAngle(orientation);
            transitionQueue.add(rt);
            pathHistory.add(new Path());
        }
    }

    /**
     * Once the turtle's position is updated, the animation is played in order to see the turtle move.
     */
    public void playAnimation(){
        backupTransitionQueue = new LinkedList<>(transitionQueue);
        backupPathHistory = new LinkedList<>(pathHistory);
        animateRecurse();
    }

    private void animateRecurse() {
        if(animationDuration == 0) {
            while(!transitionQueue.isEmpty()){
                st.getChildren().add(transitionQueue.remove());
                myPaths.getChildren().add(pathHistory.remove());
                st.play();
            }
        } if(!transitionQueue.isEmpty()) {
            stopped = false;
            st = new SequentialTransition(transitionQueue.remove());
            st.setOnFinished(e -> {
                animateRecurse();
                myPaths.getChildren().add(pathHistory.remove());
            });
            st.play();
        } else {
            stopped = true;
            st = new SequentialTransition();
            set(myImage.getTranslateX()-TURTLE_SCREEN_WIDTH/2+myImage.getBoundsInLocal().getWidth()/2,
                TURTLE_SCREEN_HEIGHT/2-myImage.getTranslateY()-myImage.getBoundsInLocal().getHeight()/2,
                myImage.getRotate());
//            if(!myPaths.getChildren().contains(pathHistory)) {
//                myPaths.getChildren().add(pathHistory);
//            }
        }
    }

//    /**
//     * This method is called when the reset button is clicked in the UserInterface. It resets the turtle's position on the screen.
//     */
//    public void resetTurtle(){
//        myImage.setTranslateX(TURTLE_SCREEN_WIDTH / 2 - myImage.getBoundsInLocal().getWidth() / 2);
//        myImage.setTranslateY(TURTLE_SCREEN_HEIGHT / 2 - myImage.getBoundsInLocal().getHeight() / 2);
//        myImage.setRotate(0);
//        heading = 0;
//        currentY = TURTLE_SCREEN_HEIGHT/2;
//        currentX = TURTLE_SCREEN_WIDTH/2;
//    }

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
        newY = -newY;
        newX += TURTLE_SCREEN_WIDTH/2;
        newY += TURTLE_SCREEN_HEIGHT/2;

        currentX = newX;
        currentY = newY;
        heading = newHeading;

        myImage.setTranslateX(newX-myImage.getBoundsInLocal().getWidth()/2);
        myImage.setTranslateY(newY-myImage.getBoundsInLocal().getHeight()/2);
        myImage.setRotate(newHeading);
    }

    /**
     * Creates a designated size for the commands.
     * @param size
     */
    public void setCommandSize(int size){
        if(size == 0){
            return;
        }
        animationDuration = totalDuration / size;
    }

    /**
     * Retrieves all the turtles data and uses it for the XML file.
     * @return
     */
    public String[] getData(){
        double coordinateX = currentX - TURTLE_SCREEN_WIDTH/2;
        double coordinateY = TURTLE_SCREEN_HEIGHT/2 - currentY;
        return new String[]{turtleName, "" + coordinateX, "" + coordinateY, "" + heading};
    }

    public void pause(){
        st.pause();
    }

    public void play(){
        if(stopped){
            animateRecurse();
        } else {
            st.play();
        }
    }

    public void step(){
        if(!stopped || !transitionQueue.isEmpty()) {
            if(stopped) {
                st = new SequentialTransition(transitionQueue.remove());
            }
            st.setOnFinished(e-> {
                myPaths.getChildren().add(pathHistory.remove());
                stopped = true;
            });
            st.play();
        }
    }

    public void resetAnimation(){
        rewindPosition();
        transitionQueue = new LinkedList<>(backupTransitionQueue);
        pathHistory = new LinkedList<>(backupPathHistory);
    }

    public void undo(){
        rewindPosition();
        transitionQueue = new LinkedList<>();
        myPaths.getChildren().removeAll(backupPathHistory);
        pathHistory = new LinkedList<>();
    }

    private void rewindPosition() {
        stopped=true;
        set(prevX-TURTLE_SCREEN_WIDTH/2, TURTLE_SCREEN_HEIGHT/2-prevY, prevHeading);
        st = new SequentialTransition();
        myPaths.getChildren().removeAll(backupPathHistory);
    }

    public void setSpeed(int value){
        totalDuration = value;
    }
}
