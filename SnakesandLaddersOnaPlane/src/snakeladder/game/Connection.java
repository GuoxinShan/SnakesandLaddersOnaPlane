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

  public void switchRoles(){
    //switch roles of snakes and ladders
    int defaultStart = this.cellStart;
    int defaultEnd = this.cellEnd;
    this.cellEnd = defaultStart;
    this.cellStart = defaultEnd;
    this.locStart = GamePane.cellToLocation(cellStart);
    this.locEnd = GamePane.cellToLocation(cellEnd);
  }

  public boolean isUp(){
    if(cellStart<cellEnd){
      return true;}
    return false;
  }

}
