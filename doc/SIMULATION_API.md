Dana Mulligan (dmm107)
Hannah Taubenfeld (hbt3)
Tyler Meier (tkm22)
Alex Oesterling (axo)

Examination of Cell Society (simulation_team22) project:
* any method that is used purely within a class should be private
* any method inteded to be accessed by a different class should be public

```java
package cellmodel
public class Simulation { 
    public Simulation(Board board, int numPossStates)
    public void resetKeyFrame(int fps)
        // should be private
    public void step() 
        // should be private
    public void endSim()
       // should be private
    public void saveCurrent() 
        //external 
}
 
package cellmodel.errorhandling
public class SaveException extends RuntimeException { 
    public SaveException(String errorMessage)
    //external; constructor
}
 
package cellmodel.errorhandling
public class XMLException extends RuntimeException { 
    public XMLException(String property)
    //external; constructor
    public XMLException(String property1, String property2) 
    //external; constructor
}
 
package cellmodel.errorhandling
public class ErrorPopup { 
    public ErrorPopup(Exception e)
    //external; constructor
}
 
package cellmodel.celltype
public class Cell{ 
  public Cell(int initState, int row, int col) 
  //internal; constructor
  public void changeStateAndView(int state) 
  //internal
  public int getTurnsSinceStateChanges() 
  //internal
  public void setTurnsSinceStateChange(int val)
  //internal
  public int getState() 
  //internal
  public int getRowNumber()
  //internal
  public int getColNumber()
  //internal
  public void addNeighbor(Cell neighbor) 
  //internal
  public void removeNeighbor(Cell neighbor)
  //internal
  public List<Cell> getNeighbors() 
  //internal
  public void setNeighbors(List<Cell> neighbors)
  //internal
  public List<Cell> getNeighborsWithState(int state) 
  //internal
  public int numNeighborsOfSameState() 
  //internal
  public int numNeighborsWithGivenState(int state) 
  //internal
  public void setState(int state) 
  //internal
  public int getMoves() 
  //internal
  public void setMoves(int moves) 
  //internal
}
 
package cellmodel
public class Configuration { 
    public Configuration(String inputfileName) throws XMLException 
    //internal; constructor
    public Simulation getInitSim()throws XMLException
    //external
}
 
package cellmodel.boardtype
public class SquareBoard extends Board { 
  public SquareBoard(int numCols, int numRows, Rules rules)
  //internal; constructor
  protected void addNeighborsToCells(Cell[][] cells) 
  //internal
}
 
package cellmodel.boardtype
public class TriangleBoard extends Board { 
  public TriangleBoard(int numCols, int numRows, Rules rules)
  //internal; constructor
  protected void addNeighborsToCells(Cell[][] cells) 
  //internal
}
 
package cellmodel.boardtype
public abstract class Board{ 
  public Board(int numCols, int numRows, Rules rules) 
  // internal; constructor
  public void updateBoard()
  // internal
  public void updateCell(int state, int row, int col)
  // should be private, but it would require how the board is initialized
  public void randomizeCellState(int row, int col)
  // external
  public int getNumRows()
  // external
  public int getNumCols()
  // external
  public List getStates()
  // external
  public Map<String, String> getRulesParameters() 
  // external
  public int getState(int row, int col)
  // external
  public Map getStateHistory()
  // external
  public String getRulesClass() 
  // external
  public int getNumPossibleStates()
  // external 
  protected void removeUnwantedNeighbors(Cell[][] cells) 
  //
  abstract protected void addNeighborsToCells(Cell[][] cells)
  protected String getMyNeighborhood()
  protected static ResourceBundle getStyleResourceBundle()
}
 
package cellmodel
public class Main { 
    //internal; constructor
}
 
package cellmodel.rules
public class PredatorOrPrey extends Rules { 
  public PredatorOrPrey(HashMap<String, String> setupParameters)
  //internal; constructor
  public void changeState(Cell cell, Cell cloneCell) 
  // internal
  public boolean areCornersNeighbors()
}
 
package cellmodel.rules
public class GameOfLife extends Rules { 
  public GameOfLife(HashMap<String, String> setupParameters)
  //internal; constructor
  public void changeState(Cell cell, Cell cloneCell) 
  //internal
  public boolean areCornersNeighbors()
  //internal
}
 
package cellmodel.rules
public class RockPaperScissors extends Rules { 
  public RockPaperScissors(HashMap<String, String> setupParameters) 
  //internal; constructor
  public void changeState(Cell cell, Cell cloneCell) 
  // internal
  public boolean areCornersNeighbors() 
  //internal
}
 
package cellmodel.rules
abstract public class Rules { 
  public Rules(HashMap<String, String> setupParameters)
  //internal; constructor
  abstract public void changeState(Cell cell, Cell cloneCell)
  //internal 
  abstract public boolean areCornersNeighbors()
  //internal
  public int getNumberOfPossibleStates()
  //internal 
  public HashMap<String, String> getParameters()
  // internal
  protected int getRandomIndex(List<Cell> cellList) 
  // internal
}
 
package cellmodel.rules
public class Percolation extends Rules { 
  public Percolation(HashMap<String, String> setupParameters)
  //internal; constructor
  public void changeState(Cell cell, Cell cloneCell) 
  //internal
  public boolean areCornersNeighbors()
  //internal
}
 
package cellmodel.rules
public class ForagingAnts extends Rules { 
  public ForagingAnts(HashMap<String, String> setupParameters)
  //internal; constructor
  public void changeState(Cell cell, Cell cloneCell) 
  // internal
  public boolean areCornersNeighbors() 
  // internal
}
 
package cellmodel.rules
public class Segregation extends Rules { 
  public Segregation(HashMap<String, String> setupParameters)
    //internal; constructor
  public void changeState(Cell cell, Cell cloneCell) 
  // internal
  public boolean areCornersNeighbors()
  // internal
}
 
package cellmodel.rules
public class Fire extends Rules { 
    public Fire(HashMap<String, String> setupParameters)
      //internal; constructor
    public void changeState(Cell cell, Cell cloneCell) 
    // internal
    public boolean areCornersNeighbors()
    // internal
}
 
package display
public class UserInterface extends Application { 
    public void start(Stage primaryStage) 
}
 
package display.visualizer
public class SquareVisualizer extends Visualizer { 
  public SquareVisualizer(String rulesClass, int numPossibleStates) 
  protected void moveToNextRow()
  //internal within the visualizer package
  protected void moveOver()
  //internal within the visualizer package
  protected void resetVariables()
  //internal within the visualizer package
  protected Double[] getCorners()
  //internal within the visualizer package
  
}
 
package display.visualizer
public class HistoryGraph extends Application { 
  public HistoryGraph()
  // external; constructor
  public void start(Stage stage) 
  // external
  public void beginGraph(int numberOfStates)
  // external
  public void updateGraph(Map counts, int cycle)
  // external
}
 
package display.visualizer
public abstract class Visualizer extends Application { 
    public Visualizer(String rulesClass, int totalNumStates) 
    public void start(Stage primaryStage)
    // external
    public void closeWindow()
    // external
    public void displayBoard(Board board)
    // external
    protected Polygon cellView(Color color)
    //internal within the visualizer package
    abstract protected void moveToNextRow()
    //internal within the visualizer package
    abstract protected void moveOver()
    //internal within the visualizer package
    abstract protected Double[] getCorners()
    // internal within the visualizer package
    abstract protected void resetVariables()
    //internal
    protected void incrementXPos(double dif)
    //internal within the visualizer package
    protected void resetXPos()
    //internal within the visualizer package
    protected double getXPos()
    //internal within the visualizer package
    protected void resetYPos()
    //internal within the visualizer package
    protected void incrementYPos(double dif)
    //internal within the visualizer package
    protected double getYPos()
    //internal within the visualizer package
    protected void resetRow()
    //internal within the visualizer package
    protected void incrementRow()
    //internal within the visualizer package
    protected int getRow()
    // should be private and moved to TriangleVisualizer
    protected void resetCol()
    //internal within the visualizer package
    protected void incrementCol()
    //internal within the visualizer package
    protected int getCol()
    // should be private and moved to TriangleVisualizer
    protected double getWidth()
    //internal within the visualizer package
    protected double getHeight()
    //internal within the visualizer package
    //so many protected methods because the ones in triangle and square vis call them
}
 
package display.visualizer
public class TriangleVisualizer extends Visualizer { 
  public TriangleVisualizer(String rulesClass, int numPossibleStates) 
  //external; constructor
  protected void moveToNextRow()
  //internal within the visualizer package
  protected void moveOver()
  //internal within the visualizer package
  protected Double[] getCorners()
  //internal within the visualizer package
  protected void resetVariables()
  //internal within the visualizer package
}
```

## API Redesign: Redesigned within the comments

### Internal
Abstract classes:
* Rules
* Board
* Visualizer
* All of the rule sets, internal because multiple can be created and nothing else is accessing them except for the model

### External
* view needs to be able to know what "state" something is in, gets it from model
* model needs to know if a cell is clicked & change the state of that cell