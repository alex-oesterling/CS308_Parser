package slogo.view;

import java.util.List;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
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
        turtleImage.setTranslateX(TURTLE_SCREEN_WIDTH/2-turtleImage.getBoundsInLocal().getWidth()/2);
        turtleImage.setTranslateY(TURTLE_SCREEN_HEIGHT/2-turtleImage.getBoundsInLocal().getHeight()/2);
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
            turtleImage.setTranslateX(TURTLE_SCREEN_WIDTH/2-turtleImage.getBoundsInLocal().getWidth()/2);
            turtleImage.setTranslateY(TURTLE_SCREEN_HEIGHT/2-turtleImage.getBoundsInLocal().getHeight()/2);
            myTurtles.getChildren().remove(myImage);
            myImage = turtleImage;
            myTurtles.getChildren().add(myImage);
        } catch (IOException e) {
            //FIXME add errors here
        }
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
        double oldX = myImage.getTranslateX()+myImage.getLayoutBounds().getWidth()/2;
        double oldY = myImage.getTranslateY()+myImage.getLayoutBounds().getHeight()/2;
        if(newX != oldX || newY != oldY) {
            Path path = new Path();
            if(penStatus){
                path.setOpacity(0.5);
            } else {
                path.setOpacity(0.0);
            }
            path.setStroke(myPenColor);
            myPaths.getChildren().add(path);
            path.getElements().add(new MoveTo(oldX, oldY));
            path.getElements().add(new LineTo(newX, newY));
            PathTransition pt = new PathTransition(Duration.millis(2000), path, myImage);
            pt.setPath(path);
            pt.play();
        }

        PauseTransition pauser = new PauseTransition();
        pauser.setDuration(Duration.millis(1000));
        pauser.play();

        RotateTransition rt = new RotateTransition(Duration.millis(2000), myImage);
        rt.setToAngle(orientation);

        rt.play();
    }

    public void updatePen(Color color){
        myPenColor = color;
    }
}
