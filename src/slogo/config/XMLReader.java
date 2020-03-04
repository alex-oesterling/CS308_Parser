package slogo.config;

import java.io.File;
import java.io.IOException;
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
  private File myFile;
  private Document myDoc;
  private Visualizer myVisualizer;

  public XMLReader(File file, Stage stage){
    myFile = file;
    setupDocument();
    readFile();
    myVisualizer = new Visualizer(stage);
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
    myVisualizer.setBackgroundColor();
  }
}
