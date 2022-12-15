// Team 10 Workshop 15
package snakeladder.game;

import ch.aplu.jgamegrid.*;
import java.awt.Point;

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



  // new attributes/methods -----------------------------------------------
  private boolean isLowest = false;  // whether rolled lowest possible number
  private boolean isBack = false;    // whether should move back
  public boolean isBack() {
    return isBack;
  }
  public void setBack(boolean back) {
    isBack = back;
  }

  private boolean isMovedOnCon = false;
  public boolean isMovedOnCon() {return isMovedOnCon;}
  public void setIsMovedOnCon(boolean movedOnCon) {isMovedOnCon = movedOnCon;}

  private ToggleStrategy toggleStrategy = new RealToggleStrategy();

  private InformationCount information;
  public InformationCount getInformation() {
    return this.information;
  }

  public GamePane getGamePane() {
    return gamePane;
  }

  // new things end------------------------------------------------------------





  Puppet(GamePane gp, NavigationPane np, String puppetImage, String puppetName)
  {
    super(puppetImage);
    this.gamePane = gp;
    this.navigationPane = np;
    this.puppetName = puppetName;
    this.information = new InformationCount(puppetName);
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

    if (nbSteps > 0){
      information.rolled(nbSteps);
    }

    // check whether rolled the lowest number
    if (nbSteps == navigationPane.getNumberOfDice()){isLowest = true;}
    setActEnabled(true);
  }

  void resetToStartingPoint() {
    cellIndex = 0;
    setLocation(gamePane.startLocation);
    setActEnabled(true);
  }


  int getCellIndex() {
    return cellIndex;
  }


  private void moveToNextCell(int nbSteps)
  {
    if (nbSteps > 0){
      cellIndex++;
    }
    else if (nbSteps < 0){
      cellIndex--;
    }
    setLocation(GamePane.cellToLocation(cellIndex));
  }





  public void act() {
    if ((cellIndex / 10) % 2 == 0) {
      if (isHorzMirror())
        setHorzMirror(false);
    }
    else {
      if (!isHorzMirror())
        setHorzMirror(true);
    }


    // Animation: Move on connection
    if (currentCon != null && nbSteps == 0 && !(isLowest && currentCon.cellEnd - currentCon.cellStart < 0)) {
      int x = gamePane.x(y, currentCon);
      setPixelLocation(new Point(x, y));
      y += dy;

      // Check end of connection
      if ((dy > 0 && (y - gamePane.toPoint(currentCon.locEnd).y) > 0)
        || (dy < 0 && (y - gamePane.toPoint(currentCon.locEnd).y) < 0)) {
        gamePane.setSimulationPeriod(100);
        setActEnabled(false);
        setLocation(currentCon.locEnd);
        cellIndex = currentCon.cellEnd;
        setLocationOffset(new Point(0, 0));
        currentCon = null;
        navigationPane.prepareRoll(cellIndex);
        navigationPane.toggleButton(false);
        isMovedOnCon = true;  // if go through connection, disable move back until process the normal movement
      }
      return;
    }
    // Normal movement
    if (nbSteps != 0) {
      moveToNextCell(nbSteps);

      if (cellIndex == 100) {  // Game over
        setActEnabled(false);
        navigationPane.prepareRoll(cellIndex);
        return;
      }

      // update number of move steps
      if (nbSteps > 0){nbSteps--;}
      if (nbSteps < 0){nbSteps++;}

      if (nbSteps == 0) {
        // Check if on connection start
        if ((currentCon = gamePane.getConnectionAt(getLocation())) != null && !(isLowest && currentCon.cellEnd - currentCon.cellStart < 0)) {
          gamePane.setSimulationPeriod(50);
          y = gamePane.toPoint(currentCon.locStart).y;
          if (currentCon.locEnd.y > currentCon.locStart.y)
            dy = gamePane.animationStep;
          else
            dy = -gamePane.animationStep;
          if (currentCon instanceof Snake) {
            navigationPane.showStatus("Digesting...");
            navigationPane.playSound(GGSound.MMM);
          }
          else {
            navigationPane.showStatus("Climbing...");
            navigationPane.playSound(GGSound.BOING);
          }

          // Update the information when step on connection
          if (currentCon.cellEnd - currentCon.cellStart > 0){
            information.updateTravelUp();
          }
          else{
            information.updateTravelDown();
          }
        }  // end of Check if on connection start
        else {
          isMovedOnCon = false;  // if it's normal movement, set to false, then its able to move back
          navigationPane.toggleButton(false);
          setActEnabled(false);
          navigationPane.prepareRoll(cellIndex);
        }


        // whether apply toggle strategy, but reset toggle button first
        if (isAuto){
          navigationPane.toggleButton(false);
          boolean toggle = toggleStrategy.checkWhetherToggle(navigationPane, gamePane);
          if (toggle) {
            navigationPane.toggleButton(true);
          }
          else{
            navigationPane.toggleButton(false);
          }
        }   // end of toggle strategy

      }
    }  // end of normal movement
  }


}
