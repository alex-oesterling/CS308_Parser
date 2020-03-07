package slogo.view.graphics;

import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.Map;

/**
 * This class creates a color palette. The color palette is created in a new window and consists of color rectangles and
 * corresponding doubles as their index. The user is able to use these indices as a means for changing the pen color and
 * background color. Additionally, the user can change the colors in the color palette.
 */
public class ColorPalette {
    private static final Paint BACKGROUND = Color.AZURE;
    private static final String TITLE = "Color Palette";
    private static final int SIZE_WIDTH = 150;
    private static final int SIZE_HEIGHT = 500;
    private static final int RECTANGLE_WIDTH = 50;
    private static final int RECTANGLE_HEIGHT = 25;
    private static final int HBOX_SPACING = 10;
    private static final int VBOX_SPACING = 10;
    private static final String RESOURCES = "resources";
    private static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
    private static final String DEFAULT_COLOR_RESOURCE_PACKAGE = FORMAT_PACKAGE + ".Colors";


    private Map<Double, String> treeMap;
    private Stage stage;

    /**
     * This public class creates a scene in which the color palette is then displayed. It takes in a map with doubles as
     * the keys and strings as the values. In this way, the backend can update the map.
     */
    public ColorPalette(){
        treeMap = createColorMap();
        stage = new Stage();
    }

    /**
     * Only called when the color palette needs to be shown. In this way, without the color palette showing, the user can
     * still access the colors and the indices.
     */
    public void showPalette(){
        stage.setScene(setScene());
        stage.setTitle(TITLE);
        stage.show();
    }

    /**
     * Updates an indicated entry in the colormap to a new specified value based on 3 RGB inputs
     * @param index - the entry to be overwritten
     * @param red - the value of the red portion of the color
     * @param green - the value of the green portion of the color
     * @param blue - the value of the blue portion of the color
     */
    public void updateColorMap(double index, double red, double green, double blue){
        String hex = String.format("#%02x%02x%02x", (int)red, (int)green, (int)blue);
        treeMap.put(index, hex);
        stage.setScene(setScene());
    }

    private Map<Double, String> createColorMap(){
        ResourceBundle myColorResources = ResourceBundle.getBundle(DEFAULT_COLOR_RESOURCE_PACKAGE);
        Enumeration e = myColorResources.getKeys();
        TreeMap<Double, String> treeMap = new TreeMap<>();
        while (e.hasMoreElements()) {
            String keyStr = (String) e.nextElement();
            treeMap.put(Double.valueOf(keyStr), myColorResources.getString(keyStr));
        }
        return treeMap;
    }

    private Scene setScene(){
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(SIZE_WIDTH, SIZE_HEIGHT);
        s1.setContent(createGrid());
        return new Scene(s1, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
    }

    private VBox createGrid() {
        VBox vbox = new VBox();
        vbox.setSpacing(VBOX_SPACING);
        for (Double key : treeMap.keySet()) {
            HBox hbox = new HBox();
            hbox.setSpacing(HBOX_SPACING);
            Rectangle r = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT, Color.web(treeMap.get(key)));
            Label colorVal = new Label(Double.toString(key));
            hbox.getChildren().addAll(r, colorVal);
            vbox.getChildren().add(hbox);
        }
        return vbox;
    }

    /**
     * This getter method allows the controller to then access the visualizer which in turn changes the color
     * of either the background or the pen color.
     * @param value - the chosen value by the user
     * @return hex string which is then converted into a color in ViewExternal
     */
    public String getColorMapValue(double value){return treeMap.get(value);}
}
