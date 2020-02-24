package slogo.view;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
  public static final int VIEWPANE_PADDING = 10;
  public static final int VIEWPANE_MARGIN = 0;
  public static final int VBOX_SPACING = 10;
  public static final String RESOURCE = "resources.languages";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCE + ".";

  Scene myScene;
  Parser myParser;
  Controller myController;
  HelpWindow helpWindow;
  VBox variables;
  VBox commands;
  Group view;
  Line pen;
  VBox group;
  BorderPane viewPane;
  Rectangle turtleArea;
  File turtleFile;
  List<ImageView> turtleImages; //FIXME Map between name and turtle instead of list (number to turtle)
  ResourceBundle myResources;
  String language;

  public Visualizer (Parser parser){
    turtleImages = new ArrayList<>();
    myParser = parser;
    myController = new Controller(parser, this);
    myController.addLanguage("English"); //FIXME set in view
    myController.addLanguage("Syntax");
  }

  public Scene setupScene() {
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Buttons");
    turtleFile = getTurtleImage(new Stage()); // do we want to select a new file for each new turtle or do we want to use the same turtlefile for all turtles?
    myScene = new Scene(createView());
    return myScene;
  }

  private Pane createView(){
    viewPane = new BorderPane();
    viewPane.setBackground(new Background(new BackgroundFill(BACKGROUND, null, null)));
    viewPane.setPadding(new Insets(VIEWPANE_PADDING, VIEWPANE_PADDING, VIEWPANE_PADDING, VIEWPANE_PADDING));
    Node turtleView = showUserDefined();
    Node commandLine = new CommandLine(myController).setupCommandLine();
    Node userInterface = createUI();
    viewPane.setMargin(commandLine, new Insets(VIEWPANE_MARGIN, VIEWPANE_PADDING, VIEWPANE_MARGIN, VIEWPANE_PADDING));
    viewPane.setLeft(turtleView);
    viewPane.setCenter(commandLine);
    viewPane.setRight(userInterface);
    return viewPane;
  }

  private Group createBox() {
    view = new Group();
    GridPane turtlePane = new GridPane();
    turtleArea = new Rectangle(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
    turtleArea.setFill(Color.WHITE);
    turtleArea.setStroke(Color.BLACK);
    turtleArea.setStrokeWidth(TURTLE_SCREEN_STROKEWIDTH);
    turtlePane.add(turtleArea, 0, 0);
    turtlePane.setHgrow(turtleArea, Priority.ALWAYS);
    turtlePane.setVgrow(turtleArea, Priority.ALWAYS);
    view.getChildren().addAll(turtlePane, chooseTurtle());
    return view;
  }

  private VBox showUserDefined(){
    group = new VBox();
    group.setSpacing(VBOX_SPACING);
    group.getChildren().add(createBox());

    BorderPane userDefined = new BorderPane();
    variables = new VBox();
    ScrollPane userVariables = new ScrollPane();
    userVariables.setContent(variables);
    userVariables.setPrefSize(turtleArea.getWidth() / 2 ,turtleArea.getHeight() / 4 );

    commands = new VBox();
    ScrollPane userCommands = new ScrollPane();
    userCommands.setContent(commands);
    userCommands.setPrefSize(turtleArea.getWidth() / 2 ,turtleArea.getHeight() / 4);

    userDefined.setLeft(userVariables);
    userDefined.setRight(userCommands);

    Label variablesLabel = new Label(myResources.getString("Variables"));
    Label commandsLabel = new Label(myResources.getString("Commands"));
    GridPane grid = new GridPane();
    grid.setHgap(turtleArea.getWidth()/3);
    GridPane.setConstraints(variablesLabel, 0, 0);
    GridPane.setConstraints(commandsLabel, 1, 0);
    grid.getChildren().addAll(variablesLabel, commandsLabel);

    group.getChildren().addAll(grid, userDefined);
    return group;
  }

  private Node createUI() {
    VBox ui = new VBox();
    Label background = new Label(myResources.getString("BackgroundColor"));
    Label pen = new Label(myResources.getString("PenColor"));
    Label chooseLanguage = new Label(myResources.getString("ChooseLanguage"));
    ui.setSpacing(VBOX_SPACING);
    ui.getChildren().addAll(background, backgroundColor(), pen, penColor(), chooseLanguage, languageSelect(), help());
    return ui;
  }

  private ColorPicker backgroundColor(){
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setMaxHeight(COLORPICKER_HEIGHT);
    colorPicker.setOnAction(e -> turtleArea.setFill(colorPicker.getValue()));
    return colorPicker;
  }

  private ColorPicker penColor(){
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setMaxHeight(COLORPICKER_HEIGHT);
    colorPicker.setOnAction(e -> pen.setFill(colorPicker.getValue()));
    return colorPicker;
  }

  private ImageView chooseTurtle() {
    ImageView turtleImage = new ImageView();
    try {
      BufferedImage bufferedImage = ImageIO.read(turtleFile);
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
    help.setOnAction(e-> helpWindow = new HelpWindow(language));
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
