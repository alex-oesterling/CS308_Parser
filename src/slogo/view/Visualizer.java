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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class Visualizer implements ViewExternalAPI{

  public static final Paint BACKGROUND = Color.AZURE;
  public static final int SIZE_WIDTH = 1000;
  public static final int SIZE_HEIGHT = 800;
  public static final int XPOS_OFFSET = 10;
  public static final int YPOS_OFFSET = 10;
  public static final int TURTLE_SCREEN_WIDTH = 500;
  public static final int TURTLE_SCREEN_HEIGHT = 500;
  public static final int TURTLE_SCREEN_STROKEWIDTH = 3;
  public static final int TURTLE_WIDTH = 50;
  public static final int TURTLE_HEIGHT = 40;
  public static final int TEXTBOX_WIDTH = 500;
  public static final int TEXTBOX_HEIGHT = 100;
  public static final String RESOURCE = "resources.languages";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCE + ".";

  Scene myScene;
  Parser myParser;
  Controller myController;
  Group root;
  javafx.scene.control.TextArea textBox;
  Rectangle r;
  File turtleFile;
  ImageView turtleImage;
  ResourceBundle myResources;

  public Visualizer (Parser parser){
    myParser = parser;
    myController = new Controller(parser, this);
  }

  public Scene setupScene(){
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
    root = new Group();
    turtleFile = getTurtleImage(new Stage());
    root.getChildren().addAll(setupCommandLine(), createBox(), chooseTurtle());
    createBox();
    myScene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
    return myScene;
  }

  private Node setupCommandLine(){
    HBox commandLine = new HBox();

    textBox = new javafx.scene.control.TextArea();
    textBox.setEditable(true);
    textBox.wrapTextProperty();
    textBox.setMaxWidth(TEXTBOX_WIDTH);
    textBox.setMaxHeight(TEXTBOX_HEIGHT);
    textBox.setPromptText(myResources.getString("TextBoxFiller"));
    commandLine.getChildren().add(textBox);

    Button run = new Button(myResources.getString("RunCommand"));
    run.setOnAction(e->submitCommand());
    commandLine.getChildren().add(run);

    commandLine.setLayoutX(XPOS_OFFSET);
    commandLine.setLayoutY(2 * YPOS_OFFSET + TURTLE_SCREEN_HEIGHT);
    return commandLine;
  }

    private Rectangle createBox() {
      r = new Rectangle(XPOS_OFFSET, YPOS_OFFSET, TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
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
          turtleImage.setFitWidth(TURTLE_WIDTH);
          turtleImage.setFitHeight(TURTLE_HEIGHT);
          turtleImage.setX((XPOS_OFFSET + TURTLE_SCREEN_WIDTH) / 2 - turtleImage.getBoundsInLocal().getWidth() / 2);
          turtleImage.setY((YPOS_OFFSET + TURTLE_SCREEN_HEIGHT) / 2 - turtleImage.getBoundsInLocal().getHeight() / 2);
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
