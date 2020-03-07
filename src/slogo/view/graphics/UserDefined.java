package slogo.view.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import slogo.view.Styler;

/**
 * This class creates many components of the visualizer. These components include all of the turtleview, the command history,
 * and the variable history.
 */
public class UserDefined {
    private static final int TURTLE_SCREEN_WIDTH = 500;
    private static final int TURTLE_SCREEN_HEIGHT = 500;
    private static final int TURTLE_SCREEN_STROKEWIDTH = 3;
    private static final int VBOX_SPACING = 10;
    private static final int VIEWPANE_PADDING = 10;

    private Rectangle turtleArea;
    private Color backgroundColor;
    private Group turtlePaths;
    private Group turtles;
    private VBox commandHistory;
    private VBox varHistory;
    private Styler styler;
    private List<Path> pathList;

    /**
     * Initializes all the groups and other elements.
     * @param resources - a resources bundle in order to create all the labels
     */
    public UserDefined(ResourceBundle resources){
        turtlePaths = new Group();
        turtles = new Group();
        backgroundColor = Color.WHITE;
        styler = new Styler(resources);
        pathList = new ArrayList<>();
    }

    /**
     * Using a vbox, creates the turtle view and adds the command history as well as the variable history.
     * @return - vbox of the turtle area
     */
    public VBox showUserDefined(){
        VBox group = new VBox();
        group.setSpacing(VBOX_SPACING);
        group.getChildren().add(createBox());

        BorderPane userDefined = new BorderPane();

        commandHistory = new VBox();
        varHistory = new VBox();
        Node varScroll = makeHistory(varHistory);
        Node commandScroll = makeHistory(commandHistory);

        commandHistory.setPrefWidth(turtleArea.getWidth()/2-VIEWPANE_PADDING*2);
        varHistory.setPrefWidth(turtleArea.getWidth()/2-VIEWPANE_PADDING*2);

        Label varLabel = styler.createLabel("Variables");
        VBox variables = new VBox();
        variables.getChildren().addAll(varLabel, varScroll);
        variables.setVgrow(varScroll, Priority.ALWAYS);

        Label cmdLabel = styler.createLabel("Commands");
        VBox commands = new VBox();
        commands.getChildren().addAll(cmdLabel, commandScroll);
        commands.setVgrow(commandScroll, Priority.ALWAYS);

        userDefined.setLeft(commands);
        userDefined.setRight(variables);

        group.getChildren().addAll(userDefined);
        group.setVgrow(userDefined, Priority.ALWAYS);
        return group;
    }

    private Group createBox() {
        turtleArea = new Rectangle(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
        turtleArea.setFill(backgroundColor);
        turtleArea.setStroke(Color.BLACK);
        turtleArea.setStrokeWidth(TURTLE_SCREEN_STROKEWIDTH);
        Group view = new Group();
        view.getChildren().addAll(turtleArea, turtlePaths, turtles);
        return view;
    }

    private Node makeHistory(VBox history) {
        ScrollPane userCommands = new ScrollPane();
        userCommands.setContent(history);
        userCommands.setPrefSize(TURTLE_SCREEN_WIDTH/2,TURTLE_SCREEN_HEIGHT/4);
        history.heightProperty().addListener((obs, old, newValue) -> userCommands.setVvalue((Double)newValue));
        return userCommands;
    }

    /**
     * sets the fill color of the turtle area whether changes in a color picker or by a command.
     * @param color
     */
    public void setFill(Color color){
        turtleArea.setFill(color);
        backgroundColor = color;
    }

    /**
     * Gets the fill of the turtle area in an effort to bind colorpickers with the color of the turtle area.
     * @return
     */
    public Color getFill(){
        return backgroundColor;
    }

    /**
     * Adds a new command to the history of user defined commands when designated by the user.
     * @param newCommand
     */
    public void addCommand(Node newCommand){
        commandHistory.getChildren().add(newCommand);
    }

    /**
     * Adds a new variable to the history of user defined variables when designated by the user.
     * @param newVariable
     */
    public void addVariable(Node newVariable){
        varHistory.getChildren().add(newVariable);
    }

    /**
     * Gets the group of turtle paths which are then passed into the turtleview.
     * @return group of turtle paths
     */
    public Group getTurtlePaths(){
        return turtlePaths;
    }

    /**
     * Gets the group of turtles which are then passed into the turtle view.
     * @return group of turtles
     */
    public Group getTurtles(){
        return turtles;
    }

    /**
     * @return - A list of Path objects for writing into the XML.
     */
    public List<Path> getPathList(){return pathList;}
}
