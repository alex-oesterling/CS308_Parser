package slogo.view;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import slogo.controller.Controller;
import slogo.model.Parser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class Visualizer implements ViewExternalAPI{

  public static final String XML_FILEPATH = "user.dir";
  public static final Paint BACKGROUND = Color.AZURE;
  public static final int TURTLE_SCREEN_WIDTH = 500;
  public static final int TURTLE_SCREEN_HEIGHT = 500;
  public static final int TURTLE_SCREEN_STROKEWIDTH = 3;
  public static final int TURTLE_WIDTH = 50;
  public static final int TURTLE_HEIGHT = 40;
  public static final int COLORPICKER_HEIGHT = 30;
  public static final String RESOURCE = "resources.languages";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCE + ".";

  Scene myScene;
  Parser myParser;
  Controller myController;
  HelpWindow helpWindow;
  BorderPane root;
  Group view;

  Rectangle turtleArea;
  List<ImageView> turtleImages; //FIXME Map between name and turtle instead of list (number to turtle)
  ResourceBundle myResources;
  String language;

  public Visualizer (Parser parser){
    turtleImages = new ArrayList<ImageView>();
    myParser = parser;
    myController = new Controller(parser, this);
    myController.addLanguage("English"); //FIXME set in view
    myController.addLanguage("Syntax");
  }

  public Scene setupScene() {
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Buttons");
//    turtleFile = getTurtleImage(new Stage()); //FIXME: we have to pick a turtlefile before creating our scene -- I propose in the chooseTurtle method we call getTurtleImage -- so each time we create a new imageview we have to pick a file but it prevents dependencies on the order of our code
    root = createView();
    myScene = new Scene(root);
    return myScene;
  }

  private BorderPane createView(){
    BorderPane viewPane = new BorderPane();
    viewPane.setBackground(new Background(new BackgroundFill(BACKGROUND, null, null)));
    viewPane.setPadding(new Insets(10, 10, 10, 10));
    Node turtleView = createBox();
    Node commandLine = new CommandLine(myController).setupCommandLine();
    Node userInterface = createUI();

    viewPane.setMargin(commandLine, new Insets(0, 10, 0, 10));

    viewPane.setLeft(turtleView);
    viewPane.setCenter(commandLine);
    viewPane.setRight(userInterface);
    return viewPane;
  }

  private Group createBox() {
    view = new Group();
//  turtleArea = new Rectangle(XPOS_OFFSET, YPOS_OFFSET, TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
    GridPane turtlePane = new GridPane();
    turtleArea = new Rectangle(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
    turtleArea.setFill(Color.WHITE);
    turtleArea.setStroke(Color.BLACK);
    turtleArea.setStrokeWidth(TURTLE_SCREEN_STROKEWIDTH);
    turtlePane.add(turtleArea, 0, 0);
    turtlePane.setHgrow(turtleArea, Priority.ALWAYS);
    turtlePane.setVgrow(turtleArea, Priority.ALWAYS);
    view.getChildren().add(turtlePane);
    view.getChildren().add(chooseTurtle());
    return view;
  }

  private Node createUI() {
    VBox ui = new VBox();
    ui.setSpacing(10);
    ui.getChildren().add(backgroundColor());
    ui.getChildren().add(help());
    ui.getChildren().add(languageSelect());
    return ui;
  }

  private ColorPicker backgroundColor(){
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setMaxHeight(COLORPICKER_HEIGHT);
    colorPicker.setOnAction(e -> {
//      root.getChildren().remove(turtleArea);
      Color c = colorPicker.getValue();
      turtleArea.setFill(colorPicker.getValue());
//      root.getChildren().add(turtleArea);
    });
    return colorPicker;
  }

  private ImageView chooseTurtle() {
    ImageView turtleImage = new ImageView();
    try {
      BufferedImage bufferedImage = ImageIO.read(getTurtleImage(new Stage()));
      Image image = SwingFXUtils.toFXImage(bufferedImage, null);
      turtleImage.setImage(image);
      turtleImage.setFitWidth(TURTLE_WIDTH);
      turtleImage.setFitHeight(TURTLE_HEIGHT);
      turtleImage.setX(turtleArea.getX()+turtleArea.getWidth()/2-turtleImage.getBoundsInLocal().getWidth()/2);
      turtleImage.setY(turtleArea.getY()+turtleArea.getHeight()/2-turtleImage.getBoundsInLocal().getHeight()/2);
    } catch (IOException e) {
        //FIXME add errors here
    }
    turtleImages.add(turtleImage);
    return turtleImage;
  }



  private Button help(){
    Button help = new Button(myResources.getString("HelpCommand"));
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
    fileChooser.setInitialDirectory(new File(System.getProperty(XML_FILEPATH)));
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
