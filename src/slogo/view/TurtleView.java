package slogo.view;

import java.io.IOException;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class TurtleView{
    public static final String XML_FILEPATH = "user.dir";
    public static final int TURTLE_WIDTH = 50;
    public static final int TURTLE_HEIGHT = 40;
    public static final int TURTLE_SCREEN_WIDTH = 500;
    public static final int TURTLE_SCREEN_HEIGHT = 500;
    public static final int PATH_STROKE_WIDTH = 3;
    public static final int TURTLE_RESET_ANGLE = 0;
    public static final double PATH_OPACITY = .75;
    public static final double PATH_NO_OPACITY = 0.0;
    private static final String ERROR_DIALOG = "Please Choose Another File";
    public static final int TOTAL_DURATION = 500;

    private Group myPaths;
    private Group myTurtles;
    private boolean penStatus;
    private ImageView myImage;
    private Color myPenColor;
    private double currentX;
    private double currentY;
    private SequentialTransition st;
    private double heading;
    private double pathStrokeWidth;
    private String turtleName;
    private SimpleObjectProperty<ObservableList<String>> myTurtle;
    private int animationDuration;

    public TurtleView(Group turtles, Group paths, String name){
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
        animationDuration = TOTAL_DURATION;
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

    public File getTurtleImage(Stage stage) {
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
            st.getChildren().add(pt);
            myPaths.getChildren().add(path);
        }
        if(orientation != oldHeading) {
            RotateTransition rt = new RotateTransition(Duration.millis(animationDuration),
                myImage);
            rt.setToAngle(orientation);
            st.getChildren().add(rt);
        }
        //System.out.println(turtleStats());
    }

    public void playAnimation(){
        st.play();
        st = new SequentialTransition();
    }

    public void resetTurtle(){
        myImage.setTranslateX(TURTLE_SCREEN_WIDTH / 2 - myImage.getBoundsInLocal().getWidth() / 2);
        myImage.setTranslateY(TURTLE_SCREEN_HEIGHT / 2 - myImage.getBoundsInLocal().getHeight() / 2);
        myImage.setRotate(0);
        heading = 0;
        currentY = TURTLE_SCREEN_HEIGHT/2;
        currentX = TURTLE_SCREEN_WIDTH/2;
    }

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

    public void updatePen(Color color){
        myPenColor = color;
    }

    public void changePenStatus(Boolean status){penStatus = status;}

    public boolean getPenStatus(){return penStatus;}

    public void changePenWidth(double penWidth) {pathStrokeWidth = penWidth;}

    public void updateTurtleView(double value){
        myImage.setVisible(value != 0.0);
    }

    public void setPenSize(double value){
        pathStrokeWidth = value;
    }

    public void setShape(ImageView turtle){
//        turtle.setTranslateX(currentX - turtle.getBoundsInLocal().getWidth() / 2);
//        turtle.setTranslateY(currentY - turtle.getBoundsInLocal().getHeight() / 2);
        myTurtles.getChildren().remove(myImage);
        myImage = turtle;
        myTurtles.getChildren().add(myImage);
        set(currentX-TURTLE_SCREEN_WIDTH/2, TURTLE_SCREEN_HEIGHT/2-currentY, heading);
    }

    public void updatePenStatus(double value){
        penStatus = (value != 0.0);
    }

    public Color getColor(){return myPenColor;}

    public void setOpacity(double newValue){
        myImage.setOpacity(newValue);
    }

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

    public void setCommandSize(int size){
        if(size == 0){return;}
        animationDuration = TOTAL_DURATION /size;
    }

    public String[] getData(){
        double coordinateX = currentX - TURTLE_SCREEN_WIDTH/2;
        double coordinateY = TURTLE_SCREEN_HEIGHT/2 - currentY;
        return new String[]{turtleName, "" + coordinateX, "" + coordinateY, "" + heading};
    }
}
