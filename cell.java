import java.util.ArrayList;
import java.awt.Color;
import javalib.worldimages.*;

class Cell {
  ArrayList<Cell> adjacent;
  boolean hasBomb = false;
  boolean revealed = false;
  
  //constructor
  Cell() {
    this.adjacent = new ArrayList<Cell>();
  }
  
  /*Template:
   * FIELDS:
   * this.adjacent - ArrayList<Cell>
   * this.hasBomb - boolean
   * this.revealed - boolean
   * 
   * METHODS:
   * this.addNeighbor(Cell n) - void
   * this.countAdjBombs() - int
   * this.addBomb() - void
   * this.draw() - WorldImage
   */
  
  //EFFECT:
  //adds the inputed cell to this.adjacent if not already in the array
  void addNeighbor(Cell n) {
    if (!this.adjacent.contains(n)) {
      this.adjacent.add(n);
    }
  }
  
  //returns the amount of adjacent cells containing a bomb
  int countAdjBombs() {
    int cnt = 0;
    
    for (Cell n : this.adjacent) {
      if (n.hasBomb) {
        cnt++;
      }
    }
    return cnt;
  }
  
  //EFFECT:
  //denotes a cell as having a bomb
  void addBomb() {
    this.hasBomb = true;
  }
  
  //renders this as a single outlined cell
  //(bomb display commented out below)
  WorldImage draw() {
    WorldImage result = new RectangleImage(25, 25, "solid", Color.GRAY);
    result = new OverlayImage(new RectangleImage(25,25, "outline", Color.BLACK), result);
    
    
    if (this.hasBomb) {
      result = new OverlayImage(new CircleImage(10, "solid", Color.RED), result);
    }
    /*
    */
    
    return result;
  }
}
