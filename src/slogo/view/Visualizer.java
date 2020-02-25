package slogo.view;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.paint.Paint;
import javafx.util.Duration;
import slogo.controller.Controller;
import slogo.model.Parser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import slogo.model.command.booleancommand.And;

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
  BorderPane root;
  Group view;
  VBox variables;
  VBox commands;
  Line pen;
  VBox group;
  BorderPane viewPane;
  Rectangle turtleArea;
  List<ImageView> turtleImages; //FIXME Map between name and turtle instead of list (number to turtle)
  ResourceBundle myResources;
  String language;
  private boolean penStatus = true;
  private Group turtlePaths;

  public Visualizer (Parser parser){
    turtleImages = new ArrayList<>();
    myParser = parser;
    myController = new Controller(new And(0, 0), this); //FIXME  just made a random command
    myController.addLanguage("English");
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
    turtlePaths = new Group();
    turtleArea = new Rectangle(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
    turtleArea.setFill(Color.WHITE);
    turtleArea.setStroke(Color.BLACK);
    turtleArea.setStrokeWidth(TURTLE_SCREEN_STROKEWIDTH);
    view.getChildren().addAll(turtleArea, chooseTurtle(), turtlePaths);
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
    variables.heightProperty().addListener((obs, old, newValue) -> userVariables.setVvalue((Double)newValue));

    commands = new VBox();
    ScrollPane userCommands = new ScrollPane();
    userCommands.setContent(commands);
    userCommands.setPrefSize(turtleArea.getWidth() / 2 ,turtleArea.getHeight() / 4);
    commands.heightProperty().addListener((obs, old, newValue) -> userCommands.setVvalue((Double)newValue));
    //fixme duplicated code to create boxes for saved commands -- extract method?
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
    group.setVgrow(userDefined, Priority.ALWAYS);
    return group;
  }

  private Node createUI() {
    VBox ui = new VBox();
    Label background = new Label(myResources.getString("BackgroundColor"));
    Label pen = new Label(myResources.getString("PenColor"));
    Label chooseLanguage = new Label(myResources.getString("ChooseLanguage"));
    ui.setSpacing(VBOX_SPACING);
    ui.getChildren().addAll(background, backgroundColor(), pen, penColor(), chooseLanguage, languageSelect(), help(), testUpdate());
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
      BufferedImage bufferedImage = ImageIO.read(getTurtleImage(new Stage()));
      Image image = SwingFXUtils.toFXImage(bufferedImage, null);
      turtleImage.setImage(image);
      turtleImage.setFitWidth(TURTLE_WIDTH);
      turtleImage.setFitHeight(TURTLE_HEIGHT);
//      turtleImage.setX(turtleArea.getX()+turtleArea.getWidth()/2-turtleImage.getBoundsInLocal().getWidth()/2);
//      turtleImage.setY(turtleArea.getY()+turtleArea.getHeight()/2-turtleImage.getBoundsInLocal().getHeight()/2);
      turtleImage.setTranslateY(turtleArea.getX()+turtleArea.getWidth()/2-turtleImage.getBoundsInLocal().getWidth()/2);
      turtleImage.setTranslateX(turtleArea.getY()+turtleArea.getHeight()/2-turtleImage.getBoundsInLocal().getHeight()/2);
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

  private Button testUpdate(){
    Button test = new Button("Test");
    test.setOnAction(e->update(200, 200, 90));
    return test;
  }

  @Override
  public void update(double newX, double newY, double orientation){
    ImageView turtleimage = turtleImages.get(0);
    double oldX = turtleimage.getTranslateX();
    double oldY = turtleimage.getTranslateY();
    System.out.println(oldX);
    System.out.println(oldY);
    Path path = new Path();
    turtlePaths.getChildren().add(path);
    path.getElements().add(new MoveTo(oldX, oldY));
    path.getElements().add(new LineTo(newX, newY));
    System.out.println(turtleimage.getTranslateX());
    System.out.println(turtleimage.getTranslateY());
    PathTransition pt = new PathTransition(Duration.millis(2000), path, turtleimage);
    pt.play();
//    System.out.println(turtleimage.getLayoutX()+newX-oldX);
//    System.out.println(turtleimage.getLayoutY()+newY-oldY);
    if(penStatus){
      path.setOpacity(0.5);
    } else {
      path.setOpacity(0.0);
    }

    RotateTransition rt = new RotateTransition(Duration.millis(2000), turtleimage);
    rt.setToAngle(orientation);

    rt.play();
  }

  @Override
  public void updatePenColor() {

  }

  @Override
  public void updateSceneColor() {

  }

  @Override
  public void clear() {

  }

  @Override
  public void update() {

  }
}
