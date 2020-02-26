package slogo.view;

import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.util.Duration;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TurtleView{
    public static final String XML_FILEPATH = "user.dir";
    public static final int TURTLE_WIDTH = 50;
    public static final int TURTLE_HEIGHT = 40;
    public static final int TURTLE_SCREEN_WIDTH = 500;
    public static final int TURTLE_SCREEN_HEIGHT = 500;
    public static final int PATH_STROKE_WIDTH = 3;
    public static final int PATH_TRANSITION_DURATION = 2000;
    public static final int ROTATE_TRANSITION_DURATION = 2000;
    public static final int PAUSE_TRANSITION_DURATION = 1000;
    public static final int TURTLE_RESET_ANGLE = 0;
    public static final double PATH_OPACITY = 0.75;
    public static final double PATH_NO_OPACITY = 0.0;
    private static final String ERROR_DIALOG = "Please Choose Another File";

    private Group myPaths;
    private Group myTurtles;
    private boolean penStatus;
    private ImageView myImage;
    private Color myPenColor;

    public TurtleView(Group turtles, Group paths){
        penStatus = true;
        myPaths = paths;
        myTurtles = turtles;
        myImage = createTurtle();
        myPenColor = Color.BLACK;
    }

    private ImageView createTurtle(){
        ImageView turtleImage = new ImageView("resources/turtles/turtle1.png");
        turtleImage.setFitWidth(TURTLE_WIDTH);
        turtleImage.setFitHeight(TURTLE_HEIGHT);
        turtleImage.setTranslateX(TURTLE_SCREEN_WIDTH / 2 - turtleImage.getBoundsInLocal().getWidth() / 2);
        turtleImage.setTranslateY(TURTLE_SCREEN_HEIGHT / 2 - turtleImage.getBoundsInLocal().getHeight() / 2);
        myTurtles.getChildren().add(turtleImage);
        return turtleImage;
    }

    public void chooseTurtle() {
        ImageView turtleImage = new ImageView();
        try {
            BufferedImage bufferedImage = ImageIO.read(getTurtleImage(new Stage()));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            turtleImage.setImage(image);
            turtleImage.setFitWidth(TURTLE_WIDTH);
            turtleImage.setFitHeight(TURTLE_HEIGHT);
            turtleImage.setTranslateX(TURTLE_SCREEN_WIDTH / 2 - turtleImage.getBoundsInLocal().getWidth() / 2);
            turtleImage.setTranslateY(TURTLE_SCREEN_HEIGHT / 2 - turtleImage.getBoundsInLocal().getHeight() / 2);
            myTurtles.getChildren().remove(myImage);
            myImage = turtleImage;
            myTurtles.getChildren().add(myImage);
        } catch (IllegalArgumentException e){
            return;
        } catch (Exception e) {
            retryLoadFile("Please choose a valid image");
        }
    }

    private void retryLoadFile(String message) {
        boolean badFile;
        displayError(message);
        do {
            badFile = false;
            try {
                chooseTurtle();
            } catch (NullPointerException e){
                return;
            } catch (Exception e){
                displayError(message);
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

    private File getTurtleImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Turtle Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        fileChooser.setInitialDirectory(new File(System.getProperty(XML_FILEPATH)));
        return fileChooser.showOpenDialog(stage);
    }

    public void update(double newX, double newY, double orientation){
        newY = -newY;
        newX += TURTLE_SCREEN_WIDTH/2;
        newY += TURTLE_SCREEN_HEIGHT/2;
        double oldX = myImage.getTranslateX()+ myImage.getLayoutBounds().getWidth() / 2;
        double oldY = myImage.getTranslateY() + myImage.getLayoutBounds().getHeight() / 2;
        if(newX != oldX || newY != oldY) {
            Path path = new Path();
            if(penStatus){
                path.setOpacity(PATH_OPACITY);
                path.setStrokeWidth(PATH_STROKE_WIDTH);
            } else {
                path.setOpacity(PATH_NO_OPACITY);
            }
            path.setStroke(myPenColor);
            myPaths.getChildren().add(path);
            path.getElements().add(new MoveTo(oldX, oldY));
            path.getElements().add(new LineTo(newX, newY));
            PathTransition pt = new PathTransition(Duration.millis(PATH_TRANSITION_DURATION), path, myImage);
            pt.setPath(path);
            pt.play();
        }

        PauseTransition pauser = new PauseTransition();
        pauser.setDuration(Duration.millis(PAUSE_TRANSITION_DURATION));
        pauser.play();

        RotateTransition rt = new RotateTransition(Duration.millis(ROTATE_TRANSITION_DURATION), myImage);
        rt.setToAngle(orientation);
        rt.play();
    }

    public void resetTurtle(){
        myImage.setTranslateX(TURTLE_SCREEN_WIDTH / 2 - myImage.getBoundsInLocal().getWidth() / 2);
        myImage.setTranslateY(TURTLE_SCREEN_HEIGHT / 2 - myImage.getBoundsInLocal().getHeight() / 2);
        RotateTransition rt = new RotateTransition(Duration.millis(ROTATE_TRANSITION_DURATION), myImage);
        rt.setToAngle(TURTLE_RESET_ANGLE);
        rt.play();
    }

    public void updatePen(Color color){
        myPenColor = color;
    }
}
