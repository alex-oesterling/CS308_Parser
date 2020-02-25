package slogo.view;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
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
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import slogo.controller.Controller;
import slogo.model.Parser;
import java.util.ResourceBundle;
import slogo.model.command.booleancommand.And;

public class Visualizer implements ViewExternalAPI{

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

  private Scene myScene;
  private Parser myParser;
  private Controller myController;
  private HelpWindow helpWindow;
  private BorderPane root;
  private Group view;
  private VBox variables;
  private VBox commands;
  private Line pen;
  private VBox group;
  private BorderPane viewPane;
  private Rectangle turtleArea;
  private List<ImageView> turtleImages; //FIXME Map between name and turtle instead of list (number to turtle)
  private ResourceBundle myResources;
  private String language;
  private boolean penStatus = true;
  private Group turtlePaths;
  private Turtle turtle;

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
    turtle = new Turtle();
    turtleImages.add(0, turtle.createTurtle());
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

    turtlePaths.getChildren().add(turtleImages.get(0));
    view.getChildren().addAll(turtleArea, turtlePaths);

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
    Button chooseTurtle = new Button(myResources.getString("ChooseTurtle"));
    chooseTurtle.setOnAction(e-> {
      turtlePaths.getChildren().remove(turtleImages.get(0));
      turtleImages.add(0, turtle.chooseTurtle());
      turtlePaths.getChildren().add(turtleImages.get(0));
            }
    );
    ui.setSpacing(VBOX_SPACING);
    ui.getChildren().addAll(background, backgroundColor(), pen, penColor(), chooseLanguage, languageSelect(), chooseTurtle,
            help(), testUpdate());
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

  private Button testUpdate(){
    Button test = new Button("Test");
    test.setOnAction(e->update(200, 200, 90));
    return test;
  }

  @Override
  public void update(double newX, double newY, double orientation){
    ImageView turtleimage = turtleImages.get(0);

    double oldX = turtleimage.getTranslateX()+turtleimage.getBoundsInParent().getWidth()/2;
    double oldY = turtleimage.getTranslateY()+turtleimage.getBoundsInParent().getHeight()/2;
    if(newX != oldX || newY != oldY) {
      Path path = new Path();
      if(penStatus){
        path.setOpacity(0.5);
      } else {
        path.setOpacity(0.0);
      }
      turtlePaths.getChildren().add(path);
      path.getElements().add(new MoveTo(oldX, oldY));
      path.getElements().add(new LineTo(newX, newY));
      PathTransition pt = new PathTransition(Duration.millis(2000), path, turtleimage);
      pt.setPath(path);
      pt.play();
    }

    PauseTransition pauser = new PauseTransition();
    pauser.setDuration(Duration.millis(1000));
    pauser.play();

//    RotateTransition rt = new RotateTransition(Duration.millis(2000), turtleimage);
//    rt.setToAngle(orientation);
//
//    rt.play();
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
