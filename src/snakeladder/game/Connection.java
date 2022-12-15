// Team 10 Workshop 15
package snakeladder.game;

import ch.aplu.jgamegrid.Location;

public abstract class Connection
{
  Location locStart;
  Location locEnd;
  int cellStart;
  int cellEnd;

  Connection(int cellStart, int cellEnd)
  {
    this.cellStart = cellStart;
    this.cellEnd = cellEnd;
    locStart = GamePane.cellToLocation(cellStart);
    locEnd = GamePane.cellToLocation(cellEnd);
  }

  String imagePath;

  public Location getLocStart() {
    return locStart;
  }

  public Location getLocEnd() {
    return locEnd;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public double xLocationPercent(int locationCell) {
    return (double) locationCell / GamePane.NUMBER_HORIZONTAL_CELLS;
  }
  public double yLocationPercent(int locationCell) {
    return (double) locationCell / GamePane.NUMBER_VERTICAL_CELLS;
  }


  // new methods
  public void reverseConnection(){
    // used to reverse the connection
    Location locTemp = locStart;
    locStart = locEnd;
    locEnd = locTemp;

    int cellTemp = cellStart;
    cellStart = cellEnd;
    cellEnd = cellTemp;
  }

  public int getCellStart() {
    return cellStart;
  }

  public int getCellEnd() {
    return cellEnd;
  }
}


