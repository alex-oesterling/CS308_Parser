package slogo.view.graphics;

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

/**
 * This class creates a new window with a help menu. In this menu, each possible command that the user can type is listed
 * in alphabetic order, along with what they need to type in order to execute the command. This help window changes when the
 * language of SLogo changes too.
 */
public class HelpWindow {
    private static final String TITLE = "Command Reference Page";
    private static final Paint BACKGROUND = Color.AZURE;
    private static final String RESOURCE = "resources.languages";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCE + ".";
    private static final int SIZE_WIDTH = 500;
    private static final int SIZE_HEIGHT = 500;
    private static final int GRID_VGAP = 5;
    private static final int GRID_HGAP = 70;

    GridPane grid;
    ResourceBundle myResources;

    /**
     * In order to change the language, this constructor takes in the current language of the simulation. It then creates
     * a new stage.
     * @param language
     */
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

    private void createGrid(String language){
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
