package slogo.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    }
    try {
      myDoc = builder.parse(myFile);
    } catch (SAXException | IOException e) {
    }
    myDoc.getDocumentElement().normalize();
  }

  private void readFile(){
    readTurtles();
    myStage.setScene(myVisualizer.setupScene());
    readPreferences();
    readCommandHistory();
    readUserVariables();
    readUserCommands();
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

    if(turtleNode.getNodeType() == Node.ELEMENT_NODE){
      Element turtleElement = (Element) turtleNode;
      addTurtles(turtleElement);
    }
  }

  private void addTurtles(Element turtlesElement) {
    NodeList turtleList = turtlesElement.getElementsByTagName("Turtle");
    for(int i = 0; i < turtleList.getLength(); i++){
      Node turtle = turtleList.item(i);
      if(turtle.getNodeType() == Node.ELEMENT_NODE){
        Element turtleElement = (Element) turtle;
        myVisualizer.addTurtle(turtleElement.getAttribute("name"),
            Double.parseDouble(turtleElement.getAttribute("xpos")),
            Double.parseDouble(turtleElement.getAttribute("ypos")),
            Integer.parseInt(turtleElement.getAttribute("heading"))
            );
      }
    }
  }

  private void readCommandHistory(){
    NodeList commands = myDoc.getElementsByTagName("CommandHistory");
    Node commandNode = commands.item(0);

    if(commandNode.getNodeType() == Node.ELEMENT_NODE){
      Element commandElement = (Element) commandNode;
      populateCommands(commandElement);
    }
  }

  private void populateCommands(Element commandElement) {
    NodeList commandHistory = commandElement.getElementsByTagName("Command");

    for(int i = 0; i<commandHistory.getLength(); i++){
      Node cmd = commandHistory.item(i);

      if(cmd.getNodeType() == Node.ELEMENT_NODE){
        Element cmdElement = (Element) cmd;
        myVisualizer.getTerminal().addHistory(cmdElement.getAttribute("syntax"));
      }
    }

    NodeList commandFiles = commandElement.getElementsByTagName("File");
    Node file = commandFiles.item(0);
    if(file.getNodeType() == Node.ELEMENT_NODE){
      Element fileElement = (Element) file;
      try {
        myVisualizer.getTerminal().addHistory(Files.readString(new File(TXT_FILEPATH + fileElement.getAttribute("filename")).toPath()));
      } catch (IOException e) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText("Command history text file does not exist");
        errorAlert.setContentText("Please specify a valid text file in your XML");
        errorAlert.showAndWait();
      }
    }
  }

  private void readUserVariables(){
    NodeList userVars = myDoc.getElementsByTagName("UserVariables");
    Node varNode = userVars.item(0);

    if(varNode.getNodeType() == Node.ELEMENT_NODE){
      Element varElement = (Element) varNode;
      populateUserVariables(varElement);
    }
  }

  private void populateUserVariables(Element varElement) {
    NodeList variableList = varElement.getElementsByTagName("Variable");

    for(int i = 0; i < variableList.getLength(); i++){
      Node var = variableList.item(i);

      if(var.getNodeType() == Node.ELEMENT_NODE){
        Element variable = (Element) var;
        myVisualizer.addVariable(variable.getAttribute("name"), variable.getAttribute("value"));
      }
    }
  }

  private void readUserCommands(){
    NodeList userCmds = myDoc.getElementsByTagName("UserCommands");
    Node cmdNode = userCmds.item(0);

    if(cmdNode.getNodeType() == Node.ELEMENT_NODE){
      Element cmdElement = (Element) cmdNode;
      populateUserCommands(cmdElement);
    }
  }

  private void populateUserCommands(Element cmdElement) {
    NodeList commandList = cmdElement.getElementsByTagName("Command");

    for(int i = 0; i < commandList.getLength(); i++){
      Node cmd = commandList.item(i);

      if(cmd.getNodeType() == Node.ELEMENT_NODE){
        Element variable = (Element) cmd;
        myVisualizer.addCommand(variable.getAttribute("name"), variable.getAttribute("syntax"));
      }
    }
  }
}
