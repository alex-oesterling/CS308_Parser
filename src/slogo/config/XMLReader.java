package slogo.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import slogo.view.Visualizer;
import slogo.view.paths.LinePathElement;
import slogo.view.paths.MoveToElement;

public class XMLReader {
  private static final String TXT_FILEPATH = "data/templates/";
  private File myFile;
  private Document myDoc;
  private Visualizer myVisualizer;
  private Stage myStage;

  public XMLReader(File file, Stage stage){
    if(file == null){
      return;
    }
    myFile = file;
    myStage = stage;
    myVisualizer = new Visualizer(stage);
    setupDocument();
    readFile();
  }

  private void setupDocument() {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = null;
    try {
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      displayAndLogError("Failed to build document parser", e);
    }
    try {
      myDoc = builder.parse(myFile);
    } catch (SAXException | IOException e) {
      displayAndLogError("Failed to read document correctly", e);
    }
    myDoc.getDocumentElement().normalize();
  }

  private void readFile(){
    myStage.setScene(myVisualizer.setupScene());
    readTurtles();
    readPreferences();
    readCommandHistory();
    readUserVariables();
    readUserCommands();
    readColorPalette();
    readPaths();
  }

  private String getElementValue(Element element, String node){
    return element.getElementsByTagName(node).item(0).getTextContent();
  }

  private void readPreferences(){
    NodeList preferences = myDoc.getElementsByTagName("Preferences");
    Node prefNode = preferences.item(0);

    if(prefNode.getNodeType() == Node.ELEMENT_NODE){
      Element prefElement = (Element) prefNode;
      getLanguage(prefElement);
      getBackground(prefElement);
    }
  }

  private void getLanguage(Element prefElement) {
    myVisualizer.setLanguage(getElementValue(prefElement, "Language"));
  }

  private void getBackground(Element prefElement) {
    myVisualizer.setBackgroundColor(getElementValue(prefElement, "Background"));
  }

  private void readTurtles() {
    NodeList turtles = myDoc.getElementsByTagName("Turtles");
    Node turtleNode = turtles.item(0);

    if(turtleNode != null && turtleNode.getNodeType() == Node.ELEMENT_NODE){
      Element turtleElement = (Element) turtleNode;
      addTurtles();
    }
  }

  private void addTurtles() {
    NodeList turtleList = myDoc.getElementsByTagName("Turtle");
    for(int i = 0; i < turtleList.getLength(); i++){
      Node turtle = turtleList.item(i);
      if(turtle.getNodeType() == Node.ELEMENT_NODE){
        Element turtleElement = (Element) turtle;
        myVisualizer.addTurtle(turtleElement.getAttribute("name"),
            Double.parseDouble(turtleElement.getAttribute("xpos")),
            Double.parseDouble(turtleElement.getAttribute("ypos")),
            Double.parseDouble(turtleElement.getAttribute("heading"))
            );
      }
    }
  }

  private void readCommandHistory(){
    NodeList commands = myDoc.getElementsByTagName("CommandHistory");
    Node commandNode = commands.item(0);

    if(commandNode != null && commandNode.getNodeType() == Node.ELEMENT_NODE){
      Element commandElement = (Element) commandNode;
      populateCommands();
    }
  }

  private void populateCommands() {
    NodeList commandHistory = myDoc.getElementsByTagName("Command");

    for(int i = 0; i<commandHistory.getLength(); i++){
      Node cmd = commandHistory.item(i);

      if(cmd.getNodeType() == Node.ELEMENT_NODE){
        Element cmdElement = (Element) cmd;
        myVisualizer.getTerminal().addHistory(cmdElement.getAttribute("syntax"));
      }
    }

    NodeList commandFiles = myDoc.getElementsByTagName("File");
    Node file = commandFiles.item(0);
    if (file != null && file.getNodeType() == Node.ELEMENT_NODE) {
      Element fileElement = (Element) file;
      try {
        myVisualizer.getTerminal().addHistory(Files
            .readString(new File(TXT_FILEPATH + fileElement.getAttribute("filename")).toPath()));
      } catch (IOException e) {
        displayAndLogError("Command history text file failed to load", e);
      }
    }
  }

  private void displayAndLogError(String header, Exception e) {
    Alert errorAlert = new Alert(AlertType.ERROR);
    errorAlert.setHeaderText(header);
    errorAlert.setContentText(e.getMessage());
    errorAlert.showAndWait();
  }

  private void readUserVariables(){
    NodeList userVars = myDoc.getElementsByTagName("UserVariables");
    Node varNode = userVars.item(0);

    if(varNode != null && varNode.getNodeType() == Node.ELEMENT_NODE){
      Element varElement = (Element) varNode;
      populateUserVariables();
    }
  }

  private void populateUserVariables() {
    NodeList variableList = myDoc.getElementsByTagName("Variable");

    for(int i = 0; i < variableList.getLength(); i++){
      Node var = variableList.item(i);

      if(var.getNodeType() == Node.ELEMENT_NODE){
        Element variable = (Element) var;
        myVisualizer.addVariable(variable.getAttribute("name"), Double.parseDouble(variable.getAttribute("value")));
      }
    }
  }

  private void readUserCommands(){
    NodeList userCmds = myDoc.getElementsByTagName("UserCommands");
    Node cmdNode = userCmds.item(0);

    if(cmdNode != null && cmdNode.getNodeType() == Node.ELEMENT_NODE){
      Element cmdElement = (Element) cmdNode;
      populateUserCommands();
    }
  }

  private void populateUserCommands() {
    NodeList commandList = myDoc.getElementsByTagName("Command");

    for(int i = 0; i < commandList.getLength(); i++){
      Node cmd = commandList.item(i);

      if(cmd.getNodeType() == Node.ELEMENT_NODE){
        Element variable = (Element) cmd;
        myVisualizer.addCommand(variable.getAttribute("name"), variable.getAttribute("syntax"));
      }
    }
  }

  private void readColorPalette(){
    NodeList colors = myDoc.getElementsByTagName("ColorPalette");
    Node colorNode = colors.item(0);

    if(colorNode!= null && colorNode.getNodeType() == Node.ELEMENT_NODE){
      Element colorElement = (Element) colorNode;
      overwritePalette();
    }
  }

  private void overwritePalette(){
    NodeList colorList = myDoc.getElementsByTagName("Color");

    for(int i = 0; i < colorList.getLength(); i++){
      Node color = colorList.item(i);

      if(color.getNodeType() == Node.ELEMENT_NODE){
        Element paletteElement = (Element) color;
        myVisualizer.updateColorMap(Double.parseDouble(paletteElement.getAttribute("index")), Color.web(paletteElement.getAttribute("color")).toString());
      }
    }
  }

  private void readPaths(){
    NodeList paths = myDoc.getElementsByTagName("Paths");
    Node pathNode = paths.item(0);

    if(pathNode!=null && pathNode.getNodeType() == Node.ELEMENT_NODE){
      Element pathsElement = (Element) pathNode;
      drawPaths();
    }
  }

  private void drawPaths() {
    NodeList pathList = myDoc.getElementsByTagName("Path");

    for(int i = 0; i < pathList.getLength(); i++){
      Node pathNode = pathList.item(i);

      if(pathNode.getNodeType() == Node.ELEMENT_NODE){
        Element pathElement = (Element) pathNode;
        Path path = new Path();
        path.getElements().add(new MoveToElement(Double.parseDouble(pathElement.getAttribute("x0")), Double.parseDouble(pathElement.getAttribute("y0"))));
        path.getElements().add(new LinePathElement(Double.parseDouble(pathElement.getAttribute("x1")), Double.parseDouble(pathElement.getAttribute("y1"))));
        path.setStroke(Color.web(pathElement.getAttribute("color")));
        path.setStrokeWidth(Double.parseDouble(pathElement.getAttribute("stroke")));
        path.setOpacity(Double.parseDouble(pathElement.getAttribute("opacity")));
        myVisualizer.getPaths().add(path);
        myVisualizer.getTurtlePaths().getChildren().add(path);
      }
    }
  }
}
