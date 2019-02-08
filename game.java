import java.util.ArrayList;
import java.util.Random;
import javalib.impworld.*;
import javalib.worldimages.*;

class Game extends World {
  ArrayList<ArrayList<Cell>> cells;
  int height = 16;
  int width = 30;
  int mines = 99;
  int screenWidth = 900;
  int screenHeight = 600;
  Random rand = new Random();
  
  //general constructor
  Game() {
    this.cells = new ArrayList<ArrayList<Cell>>();
    this.initialize();
  }
  
  //specified constructor
  Game(int width, int height, int mines) {
    this.width = width;
    this.height = height;
    this.mines = mines;
    this.cells = new ArrayList<ArrayList<Cell>>();
    this.initialize();
  }
  
  Game(int width, int height, int mines, int seed) {
    this.width = width;
    this.height = height;
    this.mines = mines;
    this.cells = new ArrayList<ArrayList<Cell>>();
    this.rand = new Random(seed);
    this.initialize();
  }
  
  /*TEMPLATE:
   * FIELDS:
   * this.height - int
   * this.width - int
   * this.mines - int
   * this.screenWidth - int
   * this.screenHeight - int
   * this.rand - Random
   * 
   * METHODS:
   * this.initialize() - void
   * this.addMines() - void
   * this.makeScene() - WorldScene
   */
  
  //EFFECT:
  //initializes this game's cells table and bomb assignment
  void initialize() {
    for (int i = 0; i < this.height; i++) {
      ArrayList<Cell> column = new ArrayList<Cell>();
      for (int j = 0; j < this.width; j++) {
        column.add(new Cell());
        if (j != 0) {
          column.get(j).addNeighbor(column.get(j - 1));
          column.get(j - 1).addNeighbor(column.get(j));
        }
        if (i != 0) {
          column.get(j).addNeighbor(this.cells.get(i - 1).get(j));
          this.cells.get(i - 1).get(j).addNeighbor(column.get(j));
          
          if (j != this.width - 1) {
            column.get(j).addNeighbor(this.cells.get(i - 1).get(j + 1));
            this.cells.get(i - 1).get(j + 1).addNeighbor(column.get(j));
          }
          
        }
        if (i != 0 && j != 0) {
          column.get(j).addNeighbor(this.cells.get(i - 1).get(j - 1));
          this.cells.get(i - 1).get(j - 1).addNeighbor(column.get(j));
        }     
      }
      this.cells.add(column);
    }
    this.addMines();
  }
  
  //EFFECT:
  //determines which cells to be bombs and validates those cell's hasBomb boolean field
  void addMines() {
    ArrayList<Integer> list = new ArrayList<Integer>();
    ArrayList<Integer> result = new ArrayList<Integer>();
    
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        list.add((i * width) + j);
      }
    }
    
    for (int i = 0; i < this.mines; i++) {
      int r = this.rand.nextInt(list.size());
      result.add(list.get(r));
      list.remove(r);
    }
    
    for (int i : result) {
      int row = i % this.height;
      int column = i / this.height;
      this.cells.get(row).get(column).addBomb();
    }
  }

  @Override
  //renders the board display
  public WorldScene makeScene() {
    WorldImage img = new EmptyImage();    
    for (int i = 0; i < height; i++) {
      WorldImage column = new EmptyImage();
      for (int j = 0; j < width; j++) {
        Cell c = this.cells.get(i).get(j);
        if (j == 0) {
          column = c.draw();
        }
        else {
          column = new BesideImage(column, c.draw());
        }
      }
      if (i == 0) {
        img = column;
      }
      else {
        img = new AboveImage(img, column);
      }
    }
    WorldScene scene = new WorldScene(this.screenWidth, this.screenHeight);
    img = new VisiblePinholeImage(img);
    scene.placeImageXY(img, this.screenWidth / 2, this.screenHeight / 2);
    return scene;
  }
}

