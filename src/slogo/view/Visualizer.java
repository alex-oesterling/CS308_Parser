package slogo.view;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import slogo.controller.Controller;
import slogo.model.Parser;

import javax.imageio.ImageIO;
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
  public static final int COLORPICKER_HEIGHT = 30;
  public static final int MENUBUTTON_HEIGHT = 30;
  public static final int RECTANGLE_INDEX = 2;
  public static final String RESOURCE = "resources.languages";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCE + ".";

  Scene myScene;
  Parser myParser;
  Controller myController;
  HelpWindow helpWindow;
  Group root;

  javafx.scene.control.TextArea textBox;

  Rectangle r;
  File turtleFile;
  ImageView turtleImage;
  ResourceBundle myResources;
  String language;

  public Visualizer (Parser parser){
    myParser = parser;
    myController = new Controller(parser, this);
    myController.addLanguage("English"); //FIXME set in view
    myController.addLanguage("Syntax");
  }

  public Scene setupScene(){
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Buttons");
    root = new Group();
    turtleFile = getTurtleImage(new Stage());

    root.getChildren().addAll(setupCommandLine(), createBox(), chooseTurtle(), backgroundColor(), languageSelect(), help());
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

    Button clear = new Button(myResources.getString("ClearCommand"));
    clear.setOnAction(e->{
      textBox.clear();
    });
    commandLine.getChildren().add(clear);

    commandLine.setLayoutX(XPOS_OFFSET);
    commandLine.setLayoutY(2 * YPOS_OFFSET + TURTLE_SCREEN_HEIGHT);
    return commandLine;
  }

  private void submitCommand() {
    if((textBox.getText() != null) && !textBox.getText().isEmpty()){
      myController.runCommand(textBox.getText());
      textBox.clear();
    }
  }

  private Rectangle createBox() {
    r = new Rectangle(XPOS_OFFSET, YPOS_OFFSET, TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
    r.setFill(Color.WHITE);
    r.setStroke(Color.BLACK);
    r.setStrokeWidth(TURTLE_SCREEN_STROKEWIDTH);
    return r;
  }

  private ColorPicker backgroundColor(){
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setLayoutX( XPOS_OFFSET);
    colorPicker.setLayoutY(3 * YPOS_OFFSET + TURTLE_SCREEN_HEIGHT + TEXTBOX_HEIGHT);
    colorPicker.setMaxHeight(COLORPICKER_HEIGHT);
    colorPicker.setOnAction(e -> {
      root.getChildren().remove(r);
      Color c = colorPicker.getValue();
      r.setFill(colorPicker.getValue());
      root.getChildren().add(RECTANGLE_INDEX,r);
    });
    return colorPicker;
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



  private Button help(){
    Button help = new Button(myResources.getString("HelpCommand"));
    help.setLayoutX(XPOS_OFFSET);
    help.setLayoutY( 5 * YPOS_OFFSET + TURTLE_SCREEN_HEIGHT + TEXTBOX_HEIGHT + COLORPICKER_HEIGHT + MENUBUTTON_HEIGHT);
    help.setOnAction(e-> {
      helpWindow = new HelpWindow(language);
    });
    return help;
  }

  private ComboBox languageSelect(){
    String languages[] = { myResources.getString("English"),
            myResources.getString("Chinese"),
            myResources.getString("French"),
            myResources.getString("German"),
            myResources.getString("Italian"),
            myResources.getString("Portuguese"),
            myResources.getString("Spanish"),
            myResources.getString("Russian"),
            myResources.getString("Urdu")
    };
    ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(languages));
    comboBox.setValue(myResources.getString("English"));
    language = comboBox.getValue().toString();
    comboBox.setLayoutX(XPOS_OFFSET);
    comboBox.setLayoutY(4 * YPOS_OFFSET + TURTLE_SCREEN_HEIGHT + TEXTBOX_HEIGHT + COLORPICKER_HEIGHT);
    comboBox.setOnAction(event -> {
      //TODO: pass in value of combobox to some method to change the language
      language = comboBox.getValue().toString();
    });
    return comboBox;
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
