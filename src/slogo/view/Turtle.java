package slogo.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Turtle {

    public static final String XML_FILEPATH = "user.dir";
    public static final int TURTLE_WIDTH = 50;
    public static final int TURTLE_HEIGHT = 40;
    public static final int TURTLE_SCREEN_WIDTH = 500;
    public static final int TURTLE_SCREEN_HEIGHT = 500;

    public ImageView createTurtle(){
        ImageView turtleImage = new ImageView("resources/turtles/turtle1.png");
        turtleImage.setFitWidth(TURTLE_WIDTH);
        turtleImage.setFitHeight(TURTLE_HEIGHT);
        turtleImage.setTranslateX(TURTLE_SCREEN_WIDTH/2-turtleImage.getBoundsInLocal().getWidth()/2);
        turtleImage.setTranslateY(TURTLE_SCREEN_HEIGHT/2-turtleImage.getBoundsInLocal().getHeight()/2);
        return turtleImage;
    }

    public ImageView chooseTurtle() {
        ImageView turtleImage = new ImageView();
        try {
            BufferedImage bufferedImage = ImageIO.read(getTurtleImage(new Stage()));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            turtleImage.setImage(image);
            turtleImage.setFitWidth(TURTLE_WIDTH);
            turtleImage.setFitHeight(TURTLE_HEIGHT);
            turtleImage.setTranslateX(TURTLE_SCREEN_WIDTH/2-turtleImage.getBoundsInLocal().getWidth()/2);
            turtleImage.setTranslateY(TURTLE_SCREEN_HEIGHT/2-turtleImage.getBoundsInLocal().getHeight()/2);
        } catch (IOException e) {
            //FIXME add errors here
        }
        return turtleImage;
    }

    private File getTurtleImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Turtle Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        fileChooser.setInitialDirectory(new File(System.getProperty(XML_FILEPATH)));
        return fileChooser.showOpenDialog(stage);
    }

}
