package slogo.view;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UserInterface {
  public static final int VBOX_SPACING = 10;
  public static final int COLORPICKER_HEIGHT = 30;
  public static final int LISTVIEW_WIDTH = 100;
  public static final int LISTVIEW_HEIGHT  = 250;
  public static final String RESOURCES = "resources";
  public static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
  public static final String DEFAULT_COLOR_RESOURCE_PACKAGE = FORMAT_PACKAGE + ".Colors";
  public static final int HBOX_SPACING = 10;



  private Styler styler;
  private ResourceBundle myResources;
  private Visualizer myVisualizer;
  private PenProperties penProperties;
  private ColorPalette colorPalette;
  private ShapePalette shapePalette;
  private ColorPicker backgroundColorPicker;
  private ComboBox<String> turtleBox;
  private ListView<String> myList;

  public UserInterface(Visualizer visualizer, ResourceBundle resources){
    styler = new Styler();
    myResources = resources;
    myVisualizer = visualizer;
    shapePalette = new ShapePalette();
    myList = new ListView<>();
  }

  public Node createTotalUI(){
    HBox hbox = new HBox();
    hbox.setSpacing(HBOX_SPACING);
    hbox.getChildren().addAll(createTurtleUI(), createSettingsUI());
    return hbox;
  }

  private Node createTurtleUI() {
    VBox ui = new VBox();
    ui.setSpacing(VBOX_SPACING);
    ui.getChildren().addAll(styler.createButton(myResources.getString("AddTurtle"), e-> myVisualizer.addTurtle()),
        makeTurtleSelector(),
        styler.createButton(myResources.getString("ChooseTurtle"), e-> myVisualizer.getCurrentTurtle().chooseTurtle(myVisualizer.getCurrentTurtle().getTurtleImage(new Stage()))),
        styler.createButton(myResources.getString("ResetCommand"), e-> myVisualizer.reset()),
        styler.createButton(myResources.getString("MoveTurtle"), e-> new MoveTurtle(myVisualizer)),
        addTurtleInfo());
    return ui;
  }

  private Node createSettingsUI() {
    VBox ui = new VBox();
    ui.setSpacing(VBOX_SPACING);
    ui.getChildren().addAll(styler.createLabel(myResources.getString("BackgroundColor")),
        backgroundColor(),
        styler.createLabel(myResources.getString("ChooseLanguage")),
        languageSelect(),
        styler.createButton(myResources.getString("PenProperties"), myVisualizer.createPenProperties()),
        styler.createButton(myResources.getString("ColorPalette"), myVisualizer.showColorPalette()),
        styler.createButton(myResources.getString("ShapePalette"), myVisualizer.showShapePalette()),
        styler.createButton(myResources.getString("HelpCommand"), e-> new HelpWindow(myVisualizer.getLanguage())));
    return ui;
  }

  private ColorPicker backgroundColor(){
    backgroundColorPicker = new ColorPicker();
    backgroundColorPicker.setMaxHeight(COLORPICKER_HEIGHT);
    backgroundColorPicker.setValue(myVisualizer.getBackground());
    backgroundColorPicker.setOnAction(e -> {
      myVisualizer.getUserDefined().setFill(backgroundColorPicker.getValue());
    });
    return backgroundColorPicker;
  }

  private HBox addTurtleInfo(){
    HBox hbox = new HBox();
    VBox vbox = new VBox();
    vbox.setSpacing(VBOX_SPACING);
    vbox.getChildren().addAll(styler.createLabel(myResources.getString("ID")),
        styler.createLabel(myResources.getString("XCord")),
        styler.createLabel(myResources.getString("YCord")),
        styler.createLabel(myResources.getString("Angle")),
        styler.createLabel(myResources.getString("PenColor")),
        styler.createLabel(myResources.getString("PenWidth")),
        styler.createLabel(myResources.getString("PenDownLabel")));
    myList.setPrefSize(LISTVIEW_WIDTH, LISTVIEW_HEIGHT);
    hbox.getChildren().addAll(vbox, myList);
    return hbox;
  }

  private ComboBox<String> makeTurtleSelector(){
    turtleBox = new ComboBox();
    turtleBox.setPromptText("Pick Turtle");
    turtleBox.valueProperty().addListener((o, old, neww) ->{
      myVisualizer.setTurtle(neww);
      turtleBox.getSelectionModel().select(neww);
    });
    turtleBox.itemsProperty().bind(myVisualizer.getTurtlesProperty());
    turtleBox.getSelectionModel().selectFirst();
    return turtleBox;
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
    comboBox.setValue(myResources.getString(myVisualizer.getLanguage()));
    comboBox.setOnAction(event -> {
      for(String key : myResources.keySet()){
        if(comboBox.getValue().toString().equals(myResources.getObject(key))){
          myVisualizer.setLanguage(key);
        }
      }
    });
    return comboBox;
  }

  public void updatePen(TurtleView currentTurtle, double value){
    if(colorPalette!=null){
      currentTurtle.updatePen(Color.web(colorPalette.getColorMapValue(value)));
    }
  }

  public void setBackgroundPicker(String hexColor){
    backgroundColorPicker.setValue(Color.web(hexColor));
  }

  public ListView<String> getList(){return myList;}
}
