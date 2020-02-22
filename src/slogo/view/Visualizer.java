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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import slogo.controller.Controller;
import slogo.model.Parser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Visualizer implements ViewExternalAPI{

  public static final int SIZE_WIDTH = 1000;
  public static final int SIZE_HEIGHT = 800;
  public static final Paint BACKGROUND = Color.AZURE;
  public static final int TURTLE_SCREEN_XPOS = 10;
  public static final int TURTLE_SCREEN_YPOS = 10;
  public static final int TURTLE_SCREEN_WIDTH = 500;
  public static final int TURTLE_SCREEN_HEIGHT = 500;
  public static final int TURTLE_SCREEN_STROKEWIDTH = 3;

  Scene myScene;
  Parser myParser;
  Controller myController;
  Group root;
  TextField textBox;
  Rectangle r;
  File turtleFile;
  ImageView turtleImage;

  public Visualizer (Parser parser){
    myParser = parser;
    myController = new Controller(parser, this);
  }


  public Scene setupScene(){
    root = new Group();
    turtleFile = getTurtleImage(new Stage());
    root.getChildren().addAll(setupCommandLine(), createBox(), chooseTurtle());
    createBox();
    myScene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
    return myScene;
  }

  private Node setupCommandLine(){
    HBox commandLine = new HBox();

    textBox = new TextField();
    textBox.setPromptText("Enter command:");
    commandLine.getChildren().add(textBox);

    //FIXME language labels/properties
    Button run = new Button("Run");
    run.setOnAction(e->submitCommand());
    commandLine.getChildren().add(run);

    return commandLine;
  }

    private Rectangle createBox() {
        r = new Rectangle(TURTLE_SCREEN_XPOS, TURTLE_SCREEN_YPOS, TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
        r.setFill(Color.TRANSPARENT);
        r.setStroke(Color.BLACK);
        r.setStrokeWidth(TURTLE_SCREEN_STROKEWIDTH);
        return r;
    }

    private ImageView chooseTurtle() {
      turtleImage = new ImageView();
      try {
          BufferedImage bufferedImage = ImageIO.read(turtleFile);
          Image image = SwingFXUtils.toFXImage(bufferedImage, null);
          turtleImage.setImage(image);
      } catch (IOException e) {
          //add errors here
      }
      return turtleImage;
    }

  private void submitCommand() {
    if((textBox.getText() != null) && !textBox.getText().isEmpty()){
      myController.setCommand(textBox.getText());
    }
  }

  private File getTurtleImage(Stage stage) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Choose Turtle Image");
      fileChooser.getExtensionFilters().addAll(
              new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
      return fileChooser.showOpenDialog(stage);
    }

  @Override
  public void updateXPos() {

  }

  @Override
  public void updateYPos() {

  }

  @Override
  public void updateOrientation() {

  }

  @Override
  public void updatePenColor() {

  }

  @Override
  public void updateSceneColor() {

  }
}
