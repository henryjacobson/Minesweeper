import java.util.ArrayList;
import tester.*;
import java.awt.Color;
import javalib.worldimages.*;



class ExamplesMinesweeper {
  Cell c1 = new Cell();
  Cell c2 = new Cell();
  Cell c3 = new Cell();
  ArrayList<Cell> listC2 = new ArrayList<Cell>();
  ArrayList<Cell> listC2C3 = new ArrayList<Cell>();
  
  void initConditions() {
    c1 = new Cell();
    c2 = new Cell();
    c3 = new Cell();
    listC2 = new ArrayList<Cell>();
    listC2.add(c2);
    listC2C3 = new ArrayList<Cell>();
    listC2C3.add(c2);
    listC2C3.add(c3);
  }
  
  
  void testWorld(Tester t) {
    
    //new Game().bigBang(900, 600, 0.5);
    
    new Game(30, 16, 99).bigBang(900, 600, 0.5);
    
    //new Game(4, 4, 7, 10).bigBang(900, 600, 0.5);
  }
  
  
  
  void testAddNeighbor(Tester t) {
    initConditions();
    c1.addNeighbor(c2);
    t.checkExpect(c1.adjacent.contains(c2), true);
    c1.addNeighbor(c2);
    c1.adjacent.remove(c2);
    t.checkExpect(c1.adjacent.contains(c2), false);
    c1.addNeighbor(c2);
    c1.addNeighbor(c3);
    t.checkExpect(c1.adjacent.contains(c2), true);
    t.checkExpect(c1.adjacent.contains(c3), true);
  }
  
  void testCountAdjBombs(Tester t) {
    t.checkExpect(new Game(4, 4, 7, 10).cells.get(0).get(0).countAdjBombs(), 0);
    t.checkExpect(new Game(4, 4, 7, 10).cells.get(1).get(0).countAdjBombs(), 1);
    t.checkExpect(new Game(4, 4, 7, 10).cells.get(0).get(1).countAdjBombs(), 3);
    t.checkExpect(new Game(4, 4, 7, 10).cells.get(1).get(1).countAdjBombs(), 4);
    t.checkExpect(new Game(4, 4, 7, 10).cells.get(1).get(2).countAdjBombs(), 4);
  }
  
  void testDraw(Tester t) {
    WorldImage result = new RectangleImage(25, 25, "solid", Color.GRAY);
    result = new OverlayImage(new RectangleImage(25,25, "outline", Color.BLACK), result);
 
    t.checkExpect(c1.draw(), result);
    
    c1.hasBomb = true;
    
    result = new OverlayImage(new CircleImage(10, "solid", Color.RED), result);
    t.checkExpect(c1.draw(), result);
  }
}

