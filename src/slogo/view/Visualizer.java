package slogo.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Visualizer {

    public static final int TURTLE_SCREEN_XPOS = 10;
    public static final int TURTLE_SCREEN_YPOS = 10;
    public static final int TURTLE_SCREEN_WIDTH = 500;
    public static final int TURTLE_SCREEN_HEIGHT = 500;
    public static final int TURTLE_SCREEN_STROKEWIDTH = 3;


    private Group root;
    private File turtleFile;
    private ImageView turtleImage;

    public Visualizer(){ turtleFile = getTurtleImage(new Stage()); }

    public Scene setupScene(int width, int height, javafx.scene.paint.Paint background){
        root = new Group();
        createBox();
        chooseTurtle();
        return new Scene(root, width, height, background);
    }

    private void createBox(){
        Rectangle r = new Rectangle(TURTLE_SCREEN_XPOS, TURTLE_SCREEN_YPOS, TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
        r.setFill(Color.TRANSPARENT);
        r.setStroke(Color.BLACK);
        r.setStrokeWidth(TURTLE_SCREEN_STROKEWIDTH);
        root.getChildren().add(r);
    }

    private void chooseTurtle(){
        turtleImage = new ImageView();
        try{
            BufferedImage bufferedImage = ImageIO.read(turtleFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            turtleImage.setImage(image);
            root.getChildren().add(turtleImage);
        } catch (IOException e){
            //add errors here
        }

    }

    private File getTurtleImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Turtle Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        return fileChooser.showOpenDialog(stage);
    }

}
