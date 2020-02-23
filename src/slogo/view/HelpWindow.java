package slogo.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class HelpWindow {

    public final String TITLE = "Command Reference Page";
    public static final Paint BACKGROUND = Color.AZURE;
    public static final String RESOURCE = "resources.languages";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCE + ".";
    public static final int SIZE_WIDTH = 500;
    public static final int SIZE_HEIGHT = 500;
    public static final int GRID_VGAP = 5;
    public static final int GRID_HGAP = 70;

    Scene myScene;
    Group root;
    GridPane grid;
    ResourceBundle myResources;

    public HelpWindow(String language){
        Stage stage = new Stage();
        createGrid(language);
        stage.setScene(setScene());
        stage.setTitle(TITLE);
        stage.show();
    }

    private Scene setScene(){
        root = new Group();
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(SIZE_WIDTH, SIZE_HEIGHT);
        s1.setContent(grid);
        root.getChildren().add(s1);
        return myScene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
    }

    public GridPane createGrid(String language){
        grid = new GridPane();
        grid.setVgap(GRID_VGAP);
        grid.setHgap(GRID_HGAP);
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        int count = 0;
        Enumeration e = myResources.getKeys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            Label keyLabel = new Label(key);
            Label command = new Label(myResources.getString(key));
            GridPane.setConstraints(keyLabel, 0, count);
            GridPane.setConstraints(command, 1, count);
            grid.getChildren().add(keyLabel);
            grid.getChildren().add(command);
            count++;
        }
        return grid;
    }
}