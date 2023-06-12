import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// represents examples of worlds, cells and tests.
class ExamplesFloodIt {
  // cells 
  Cell lightBlue;
  Cell lightPurple;
  Cell lightGreen;
  Cell lightYellow;
  Cell bluishGreen;
  Cell Purple;
  Cell lightOrange;
  Cell lightRed;
  //cell colors (list) 
  ArrayList<String> listofColors;
  // cells for testing (list) 
  ArrayList<Cell> testBoard;
  //  floodit world ex 
  FloodItWorld flooditWorld;
  FloodItWorld floodItWorld2;
  //cells (list)
  ArrayList<Cell> exBoard;

  //initializing
  void initWorld() {

    // valid colors

    listofColors = new ArrayList<String>();
    listofColors.add("light blue");
    listofColors.add("light purple");
    listofColors.add("light green");
    listofColors.add("light red");
    listofColors.add("purple");
    listofColors.add("light yellow");
    listofColors.add("light orange");
    listofColors.add("bluish green");

    //setting the scene

    lightBlue = new Cell(0, 0, "light blue", true,
        null, null, null, null);
    lightPurple = new Cell(1, 0, "light purple", false,
        lightBlue, null, null, null);
    lightGreen = new Cell(0, 1, "light green", false,
        null, lightBlue, null, null);
    lightYellow = new Cell(1, 1, "light yellow", false,
        lightGreen, lightPurple, null, null);
    bluishGreen = new Cell(0, 2, "bluish green", false, 
        null, lightGreen, null, null);
    Purple = new Cell(1, 2, "purple", false,
        bluishGreen, lightYellow, null, null);
    lightOrange = new Cell(0, 3, "light orange", false,
        null, bluishGreen, null, null);
    lightRed = new Cell(1, 3, "light red", false,
        lightOrange, Purple, null, null);

    //︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲v

    lightBlue.right = lightPurple;
    lightGreen.right = lightYellow;
    bluishGreen.right = Purple;
    lightOrange.right = lightRed;
    lightBlue.bottom = lightGreen;
    lightPurple.bottom = lightYellow;
    lightGreen.bottom = bluishGreen;
    lightYellow.bottom = Purple;
    bluishGreen.bottom = lightOrange;
    Purple.bottom = lightRed;


    // initColors pretty much 

    ArrayList<Cell> testBoard = new ArrayList<Cell>();
    testBoard.add(lightBlue);
    testBoard.add(lightGreen);
    testBoard.add(bluishGreen);
    testBoard.add(lightOrange);
    testBoard.add(lightPurple);
    testBoard.add(lightYellow);
    testBoard.add(Purple);
    testBoard.add(lightRed);
    // new worlds for testing
    flooditWorld = new FloodItWorld();
    floodItWorld2 = new FloodItWorld();

    flooditWorld.makeCells(FloodItWorld.testBoardSize);

    // start board of cells
    exBoard = flooditWorld.board;
    floodItWorld2.board = new ArrayList<Cell>();
    //light blue 
    floodItWorld2.board.add(lightBlue);
    //light green 
    floodItWorld2.board.add(lightGreen);
    //light purple 
    floodItWorld2.board.add(lightPurple);
    //light yellow 
    floodItWorld2.board.add(lightYellow);
  }

  // tests image method
  //converting cells to images
  void testImage(Tester t) {
    initWorld();
    //
    t.checkExpect(this.lightBlue.image(), 
        new RectangleImage(20, 20, OutlineMode.SOLID, new Color(215, 237, 250)));
    t.checkExpect(this.lightPurple.image(), 
        new RectangleImage(20, 20, OutlineMode.SOLID, new Color(243, 243, 252)));
    t.checkExpect(this.lightGreen.image(), 
        new RectangleImage(20, 20, OutlineMode.SOLID, new Color(217, 244, 224)));
    t.checkExpect(this.lightYellow.image(), 
        new RectangleImage(20, 20, OutlineMode.SOLID, new Color(250, 244, 237)));
    t.checkExpect(this.bluishGreen.image(), 
        new RectangleImage(20, 20, OutlineMode.SOLID, new Color(230, 252, 252)));
    t.checkExpect(this.Purple.image(), 
        new RectangleImage(20, 20, OutlineMode.SOLID, new Color(233, 225, 249)));
    t.checkExpect(this.lightOrange.image(), 
        new RectangleImage(20, 20, OutlineMode.SOLID, new Color(246, 223, 218)));
    t.checkExpect(this.lightRed.image(), 
        new RectangleImage(20, 20, OutlineMode.SOLID, new Color(232, 208, 215)));
  }

  // tests initColors method
  // does the list contain the valid colors?

  void testInitColors(Tester t) {
    this.listofColors = null;
    t.checkExpect(listofColors, null);
    initWorld();
    t.checkExpect(listofColors.contains("light blue"), true);
    t.checkExpect(listofColors.contains("light purple"), true);
    t.checkExpect(listofColors.contains("light green"), true);
    t.checkExpect(listofColors.contains("purple"), true);
    t.checkExpect(listofColors.contains("light red"), true);
    t.checkExpect(listofColors.contains("light yellow"), true);
    t.checkExpect(listofColors.contains("light orange"), true);
    t.checkExpect(listofColors.contains("bluish green"), true);
  }

  // tests setColor method.
  // are the colors correctly associated w their 
  // string counterparts?
  // is this easily overrided?

  void testSetColor(Tester t) {
    initWorld();
    //initial color associations
    t.checkExpect(lightBlue.color, "light blue");
    t.checkExpect(lightPurple.color, "light purple");
    t.checkExpect(lightGreen.color, "light green");
    //set
    lightGreen.setColor("light orange");
    lightBlue.setColor("light purple");
    lightPurple.setColor("light blue");
    //changed
    t.checkExpect(lightBlue.color, "light purple");
    t.checkExpect(lightPurple.color, "light blue");
    t.checkExpect(lightGreen.color, "light orange");
  }

  // tests update method
  void testUpdate(Tester t) {
    initWorld();
    t.checkExpect(lightBlue.right.flooded, false);
    t.checkExpect(lightBlue.bottom.flooded, false);
    lightBlue.update("light blue");
    t.checkExpect(lightBlue.right.flooded, false);
    t.checkExpect(lightBlue.bottom.flooded, false);
    lightBlue.color = "light purple";
    lightBlue.update("light purple");
    t.checkExpect(lightBlue.right.flooded, true);
    t.checkExpect(lightBlue.bottom.flooded, false);
    lightBlue.update("light green");
    t.checkExpect(lightBlue.right.flooded, true);
    t.checkExpect(lightBlue.bottom.flooded, true);
  }

  // tests makeCells method
  // make sure the flood motion isnt all at once
  // but rather trickles down
  void testmakeCells(Tester t) {
    initWorld();
    // ☆ is first cell flooded? ☆
    t.checkExpect(exBoard.get(0).flooded, true);
    // ☆ do the cells have colors and coordinates? ☆
    for (int i = 0; i < flooditWorld.board.size(); i++) {
      Cell randcell = flooditWorld.board.get(i);
      int boardSize = 6; 
      t.checkRange(randcell.y, 0, boardSize);
      t.checkRange(randcell.x, 0, boardSize);
      // ☆ is the color of the cell in the list? ☆
      t.checkExpect(listofColors.contains(randcell.color), true);
      if (randcell.x == 0) {
        t.checkExpect(randcell.left, null);
      }
      else { //L ♡
        t.checkExpect(randcell.left, flooditWorld.board.get(i - boardSize));
      }
      if (randcell.x == (boardSize - 1)) {
        t.checkExpect(randcell.right, null);
      }
      else { //R ♡
        t.checkExpect(randcell.right, flooditWorld.board.get(i + boardSize));
      }
      if (randcell.y == 0) {
        t.checkExpect(randcell.top, null);
      }
      else { //T ♡
        t.checkExpect(randcell.top, flooditWorld.board.get(i - 1));
      }
      if (randcell.y == (boardSize - 1)) {
        t.checkExpect(randcell.bottom, null);
      }
      else { //B ♡
        t.checkExpect(randcell.bottom, flooditWorld.board.get(i + 1));
      }
    }
    // ✧˖*°࿐ ensures all of the other cells arent flooded
    // when they dont need to be
    for (int i = 1; i < exBoard.size(); i++) {
      t.checkExpect(exBoard.get(i).flooded, false);
    }
  }


  // tests clickedCell method
  void testclickedCell(Tester t) {
    initWorld();
    t.checkExpect(flooditWorld.clickedCell(new Posn(70, 70)), exBoard.get(0));
    t.checkExpect(flooditWorld.clickedCell(new Posn(140, 140)), exBoard.get(21));
    t.checkExpect(flooditWorld.clickedCell(new Posn(170, 170)), exBoard.get(28));
    t.checkExpect(flooditWorld.clickedCell(new Posn(180, 180)), exBoard.get(35));
  }

  // render. place cell on background and adds
  // hypothetical endings
  void testMakeScene(Tester t) {
    initWorld();
    WorldScene finalScene = new WorldScene(1200, 800);

    // ✧･ﾟ: *✧･ﾟ rules 
    finalScene.placeImageXY(new TextImage("Rules:", 20,
        FontStyle.BOLD, Color.PINK), 800, 255);
    finalScene.placeImageXY(new TextImage("Starting Point: Top-Left", 15,
        FontStyle.REGULAR, Color.PINK), 800, 300);
    finalScene.placeImageXY(new TextImage("1.) Change the color of the cell by clicking", 15,
        FontStyle.REGULAR, Color.PINK), 800, 350);
    finalScene.placeImageXY(new TextImage("on one of it's neighbors", 15,
        FontStyle.REGULAR, Color.PINK), 800, 365);
    finalScene.placeImageXY(new TextImage("2.) Try to fill the board with one color", 15,
        FontStyle.REGULAR, Color.PINK), 800, 405);
    finalScene.placeImageXY(new TextImage("within the given amount of moves", 15,
        FontStyle.ITALIC, Color.PINK), 800, 420);

    //   water png

    finalScene.placeImageXY(new FromFileImage("output-onlinepngtools-3.png"), 820, 150);

    //  title "flood it" png

    finalScene.placeImageXY(new FromFileImage("d8ca33e33c53b884f1509626a6393bb7.png"), 820, 150);

    //   colors 

    finalScene.placeImageXY(lightBlue.image(), 80, 80);
    finalScene.placeImageXY(lightGreen.image(), 80, 100);
    finalScene.placeImageXY(lightPurple.image(), 100, 80);
    finalScene.placeImageXY(lightYellow.image(), 100, 100);

    //   moves made 

    finalScene.placeImageXY((new TextImage(Integer.toString(floodItWorld2.clicks, 200) 
        + " /" + Integer.toString(floodItWorld2.limit, 200) , Color.PINK)), 
        floodItWorld2.boardSize * 13, floodItWorld2.boardSize * 25);

    //  time count 

    finalScene.placeImageXY(new TextImage("Time: " + floodItWorld2.time / 10 + "s", 20,
        FontStyle.REGULAR, Color.PINK), floodItWorld2.boardSize * 13, floodItWorld2.boardSize * 27);



    t.checkExpect(floodItWorld2.makeScene(), finalScene);
  }

  // tests updateWorldState method
  void testUpdateWorldState(Tester t) {
    initWorld();
    t.checkExpect(floodItWorld2.board.get(0), lightBlue);
    floodItWorld2.board.get(1).flooded = true;
    floodItWorld2.board.get(2).flooded = true;
    floodItWorld2.updateWorldState();
    t.checkExpect(floodItWorld2.board.get(1).color, "light blue");
    t.checkExpect(floodItWorld2.board.get(2).color, "light blue");
  }

  // tests updateOnClick
  //  does the board change when the cell is clicked?
  void testUpdateOnClick(Tester t) {
    initWorld();
    floodItWorld2.board = new ArrayList<Cell>();
    floodItWorld2.board.add(lightBlue);
    t.checkExpect(floodItWorld2.board.get(0), lightBlue);
    floodItWorld2.updateOnClick(lightBlue);
    t.checkExpect(floodItWorld2.board.get(0).color, "light blue");
  }

  // tests onMouseClick method
  //  does change happen when mouse click occurs?
  void testOnMouseClick(Tester t) {
    initWorld();
    floodItWorld2.makeScene();
    ArrayList<Cell> test = floodItWorld2.board;
    t.checkExpect(test.get(0), lightBlue);
    t.checkExpect(floodItWorld2.clicks, 0);
    floodItWorld2.onMouseClick(new Posn(0, 0));
    t.checkExpect(test.get(0), lightBlue);
    t.checkExpect(floodItWorld2.clicks, 0);
    floodItWorld2.onMouseClick(new Posn(100, 100));
    t.checkExpect(test.get(0).color, "light yellow");
    t.checkExpect(floodItWorld2.clicks, 1);
    floodItWorld2.onMouseClick(new Posn(100, 100));
    t.checkExpect(floodItWorld2.clicks, 2);
  }

  // tests on key event
  //does the board reset when "r" is pressed?
  void testOnKeyEvent(Tester t) {
    initWorld();
    floodItWorld2.makeCells(FloodItWorld.testBoardSize);
    ArrayList<Cell> hypothetical = new ArrayList<Cell>();
    hypothetical = floodItWorld2.board;
    int testBdSize = FloodItWorld.testBoardSize;
    t.checkExpect(floodItWorld2.boardSize == 2, true);
    t.checkExpect(floodItWorld2.colorsUsed == 3, true);
    t.checkExpect(testBdSize == 6, true);
    t.checkExpect(floodItWorld2.board.equals(hypothetical), true);

    // "b" will not work to reset
    // board will remain the same
    floodItWorld2.onKeyEvent("b");
    t.checkExpect(floodItWorld2.boardSize == 2, true);
    t.checkExpect(floodItWorld2.colorsUsed == 3, true);
    t.checkExpect(testBdSize == 6, true);
    t.checkExpect(floodItWorld2.board.equals(hypothetical), true);

    // "c" will not work to reset
    // board will remain the same 
    floodItWorld2.onKeyEvent("c");
    t.checkExpect(floodItWorld2.boardSize == 2, true);
    t.checkExpect(floodItWorld2.colorsUsed == 3, true);
    t.checkExpect(testBdSize == 6, true);
    t.checkExpect(floodItWorld2.board.equals(hypothetical), true);

    // "r" will work
    floodItWorld2.onKeyEvent("r");
    t.checkExpect(floodItWorld2.boardSize == 2, true);
    t.checkExpect(floodItWorld2.colorsUsed == 3, true);
    t.checkExpect(testBdSize == 6, true);
    t.checkExpect(floodItWorld2.board.equals(hypothetical), false);
  }

  // tests allFlooded method
  //  are all of the cells flooded?
  void testAllFlooded(Tester t) {
    initWorld();
    t.checkExpect(floodItWorld2.allFlooded(), false);
    for (Cell c: floodItWorld2.board) {
      c.flooded = true;
    }
    t.checkExpect(floodItWorld2.allFlooded(), true);
  }


  //  ♡ ·˚ ♡♡ ·˚ ♡♡ ·˚ ♡♡ ·˚ ♡ RUN GAME ♡ ·˚ ♡♡ ·˚ ♡♡ ·˚ ♡♡ ·˚ ♡

  void testGame(Tester t) {
    FloodItWorld w = new FloodItWorld(20, 6);
    t.checkException(
        new IllegalArgumentException("✧You may not use more than 8 colors!✧"),
        w, "startGame", 20, 9);
    // ↓ change grid size and num of colors here ↓
    w.startGame(20, 6);
    t.checkExpect(w.boardSize, 20);
    t.checkExpect(w.colorsUsed, 6);
    t.checkExpect(w.clicks, 0);
    t.checkExpect(w.limit, 46);
  }
}
