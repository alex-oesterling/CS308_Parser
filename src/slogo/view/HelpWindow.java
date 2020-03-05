package slogo.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class HelpWindow {

    public static final String TITLE = "Command Reference Page";
    public static final Paint BACKGROUND = Color.AZURE;
    public static final String RESOURCE = "resources.languages";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCE + ".";
    public static final int SIZE_WIDTH = 500;
    public static final int SIZE_HEIGHT = 500;
    public static final int GRID_VGAP = 5;
    public static final int GRID_HGAP = 70;

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
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(SIZE_WIDTH, SIZE_HEIGHT);
        s1.setContent(grid);
        return new Scene(s1, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
    }

    public void createGrid(String language){
        grid = new GridPane();
        grid.setVgap(GRID_VGAP);
        grid.setHgap(GRID_HGAP);
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        Enumeration e = myResources.getKeys();
        TreeMap<String, String> treeMap = new TreeMap<>();
        while (e.hasMoreElements()) {
            String keyStr = (String) e.nextElement();
            treeMap.put(keyStr, myResources.getString(keyStr));
        }
        int count = 0;
        for (String key : treeMap.keySet()) {
            Label keyLabel = new Label(key);
            String com = myResources.getString(key);
            Label command = new Label(removeSlash(com));
            GridPane.setConstraints(keyLabel, 0, count);
            GridPane.setConstraints(command, 1, count);
            grid.getChildren().addAll(keyLabel, command);
            count++;
        }
    }

    private String removeSlash(String string){
        for(int i = 0; i < string.length() -1; i++){
            if((string.charAt(i) == '\\') && (string.charAt(i+1) == '?')){
                StringBuilder sb = new StringBuilder(string);
                sb.delete(i, i+1);
                string = sb.toString();
            }
        }
        return string;
    }
}
