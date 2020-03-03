package slogo.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.security.Key;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ColorPalette {

    public static final Paint BACKGROUND = Color.AZURE;
    public static final String RESOURCE = "resources.formats";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCE + ".Colors";
    public final String TITLE = "Color Palette";
    public static final int SIZE_WIDTH = 150;
    public static final int SIZE_HEIGHT = 500;
    public static final int RECTANGLE_WIDTH = 50;
    public static final int RECTANGLE_HEIGHT = 25;
    public static final int HBOX_SPACING = 10;
    public static final int VBOX_SPACING = 10;

    private Group root;
    private Scene myScene;
    private VBox vbox;
    private ResourceBundle myResources;
    private TreeMap<Double, String> treeMap;

    public ColorPalette(){
        Stage stage = new Stage();
        stage.setScene(setScene());
        stage.setTitle(TITLE);
        stage.show();
    }

    private Scene setScene(){
        root = new Group();
        createGrid();
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(SIZE_WIDTH, SIZE_HEIGHT);
        s1.setContent(vbox);
        root.getChildren().add(s1);
        return myScene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
    }

    private void createGrid() {
        vbox = new VBox();
        vbox.setSpacing(VBOX_SPACING);
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
        Enumeration e = myResources.getKeys();
        treeMap = new TreeMap<>();
        while (e.hasMoreElements()) {
            String keyStr = (String) e.nextElement();
            treeMap.put(Double.valueOf(keyStr), myResources.getString(keyStr));
        }
        for (Double key : treeMap.keySet()) {
            HBox hbox = new HBox();
            hbox.setSpacing(HBOX_SPACING);
            Rectangle r = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT, Color.web(treeMap.get(key)));
            Label colorVal = new Label(Double.toString(key));
            hbox.getChildren().addAll(r, colorVal);
            vbox.getChildren().add(hbox);
        }
    }

    public TreeMap<Double, String> getColorMap(){return treeMap;}
}
