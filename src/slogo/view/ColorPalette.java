package slogo.view;

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

public class ColorPalette {

    public static final Paint BACKGROUND = Color.AZURE;
    public static final String TITLE = "Color Palette";
    public static final int SIZE_WIDTH = 150;
    public static final int SIZE_HEIGHT = 500;
    public static final int RECTANGLE_WIDTH = 50;
    public static final int RECTANGLE_HEIGHT = 25;
    public static final int HBOX_SPACING = 10;
    public static final int VBOX_SPACING = 10;

    private Map<Double, String> treeMap;

    public ColorPalette(Map<Double, String> colorMap){
        this.treeMap = colorMap;
    }

    public void showPalette(){
        Stage stage = new Stage();
        stage.setScene(setScene());
        stage.setTitle(TITLE);
        stage.show();
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

    public String getColorMapValue(double value){return treeMap.get(value);}
}
