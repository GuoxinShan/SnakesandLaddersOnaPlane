package snakeladder.game;

import ch.aplu.jgamegrid.*;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Puppet extends Actor
{
  private GamePane gamePane;
  private NavigationPane navigationPane;
  private int cellIndex = 0;
  private int nbSteps;
  private Connection currentCon = null;
  private int y;
  private int dy;
  private boolean isAuto;
  private String puppetName;

  //user rolling statistics
  public Map<Integer, Integer> userRollingStatistics = new HashMap<Integer,Integer>();
  public Map<String,Integer> connectionStatistics = new HashMap<String,Integer>();

  Puppet(GamePane gp, NavigationPane np, String puppetImage)
  {
    super(puppetImage);
    this.gamePane = gp;
    this.navigationPane = np;
    initializeStatistics();
  }
  public void setGamePane(GamePane gp){
    this.gamePane = gp;
  }

  public boolean isAuto() {
    return isAuto;
  }

  public void setAuto(boolean auto) {
    isAuto = auto;
  }

  public String getPuppetName() {
    return puppetName;
  }

  public void setPuppetName(String puppetName) {
    this.puppetName = puppetName;
  }

  void go(int nbSteps)
  {
    if (cellIndex == 100)  // after game over
    {
      cellIndex = 0;
      setLocation(gamePane.startLocation);
    }
    this.nbSteps = nbSteps;
    if(nbSteps >0){
      updateUserRollingStatistics(nbSteps);
    }
    setActEnabled(true);
  }
  void resetToStartingPoint() {
    cellIndex = 0;
    setLocation(gamePane.startLocation);
    setActEnabled(true);
  }

  public int getCellIndex() {
    return cellIndex;
  }

  //initialize user rolling statistics
  private void initializeStatistics() {
    for (int i = 1; i <= 12; i++) {
      userRollingStatistics.put(i, 0);
    }
    connectionStatistics.put("up", 0);
    connectionStatistics.put("down", 0);
  }

  private void updateConnectionStatistics(boolean isUp) {
    if (isUp) {
      connectionStatistics.put("up", connectionStatistics.get("up") + 1);
    } else {
      connectionStatistics.put("down", connectionStatistics.get("down") + 1);
    }
    
  }

  private void updateUserRollingStatistics(int diceValue) {
    int count = userRollingStatistics.get(diceValue);
    userRollingStatistics.put(diceValue, count + 1);
    
  }
  
  //move puppet backforward one cell
  private void moveBack(){
    int tens = cellIndex / 10;
    int ones = cellIndex - tens * 10;
    if (tens % 2 == 0)     // Cells starting left 01, 21, .. 81
    {
      if (ones == 1 && cellIndex > 0)
        setLocation(new Location(getX(), getY()+1 ));
      else
        setLocation(new Location(getX()-1, getY()));
    }
    else     // Cells starting left 20, 40, .. 100
    {
      if (ones == 1)
        setLocation(new Location(getX(), getY()+1));
      else
        setLocation(new Location(getX()+1, getY() ));
    }
    cellIndex--;
  }

  private void moveToNextCell()
  {
    int tens = cellIndex / 10;
    int ones = cellIndex - tens * 10;
    if (tens % 2 == 0)     // Cells starting left 01, 21, .. 81
    {
      if (ones == 0 && cellIndex > 0)
        setLocation(new Location(getX(), getY() - 1));
      else
        setLocation(new Location(getX() + 1, getY()));
    }
    else     // Cells starting left 20, 40, .. 100
    {
      if (ones == 0)
        setLocation(new Location(getX(), getY() - 1));
      else
        setLocation(new Location(getX() - 1, getY()));
    }
    cellIndex++;
  }

  private void startAtConnection(){
    if (nbSteps == 0)
    {
      // Check if on connection start
      if ((currentCon = gamePane.getConnectionAt(getLocation())) != null)
      {
        gamePane.setSimulationPeriod(50);
        y = gamePane.toPoint(currentCon.locStart).y;
        if (currentCon.locEnd.y > currentCon.locStart.y)
          dy = gamePane.animationStep;
        else
          dy = -gamePane.animationStep;
        if (currentCon instanceof Snake)
        {
          navigationPane.showStatus("Digesting...");
          navigationPane.playSound(GGSound.MMM);
          updateConnectionStatistics(false);
        }
        else
        {
          navigationPane.showStatus("Climbing...");
          navigationPane.playSound(GGSound.BOING);
          updateConnectionStatistics(true);
        }
      }
      else
      {
        setActEnabled(false);
        navigationPane.prepareRoll(cellIndex);
      }
    }
  }

  public void act()
  {
    if ((cellIndex / 10) % 2 == 0)
    {
      if (isHorzMirror())
        setHorzMirror(false);
    }
    else
    {
      if (!isHorzMirror())
        setHorzMirror(true);
    }

    // Animation: Move on connection
    if (currentCon != null)
    {
      int x = gamePane.x(y, currentCon);
      setPixelLocation(new Point(x, y));
      y += dy;

      // Check end of connection
      if ((dy > 0 && (y - gamePane.toPoint(currentCon.locEnd).y) > 0)
        || (dy < 0 && (y - gamePane.toPoint(currentCon.locEnd).y) < 0))
      {
        gamePane.setSimulationPeriod(100);
        setActEnabled(false);
        setLocation(currentCon.locEnd);
        cellIndex = currentCon.cellEnd;
        setLocationOffset(new Point(0, 0));
        currentCon = null;
        navigationPane.prepareRoll(cellIndex);
        
      }
      return;
    }

    if(nbSteps == -1){
      moveBack();
      nbSteps++;
      startAtConnection();
    }
    
    // Normal movement
    if (nbSteps > 0)
    {
      moveToNextCell();

      if (cellIndex == 100)  // Game over
      {
        setActEnabled(false);
        navigationPane.prepareRoll(cellIndex);
        return;
      }

      nbSteps--;
      startAtConnection();
    }
  }

}
