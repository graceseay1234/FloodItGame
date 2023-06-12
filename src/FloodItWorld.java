import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;


// ｡ﾟ•┈୨♡୧┈•ﾟ｡  Flood It    
class FloodItWorld extends World {
  int boardSize = 20;
  //  num of colors in game; cannot exceed this num
  int colorsUsed = 8;
  //  all cells
  ArrayList<Cell> board;
  static final int testBoardSize = 6;
  int limit;
  // clicks starts at 0
  int clicks = 0;
  // time starts at 0
  int time = 0; 

  FloodItWorld(int boardsz, int colors) {
    boardSize = boardsz;
    colorsUsed = colors;
    makeCells(boardSize);
    if (boardSize > 12) {
      limit = boardSize + colorsUsed + 20;
    }
    else if (boardSize < 4) {
      limit = boardSize + colorsUsed - 2;
    }
    else {
      limit = boardSize + colorsUsed - 1;
    }
  }


  //★ for testing

  FloodItWorld() {
    boardSize = 2;
    colorsUsed = 3;
    limit = 3;
  }

  //︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲

  // generates random colored cells; 
  // the amount depends on the size of the board 
  void makeCells(int boardsz) {
    board = new ArrayList<Cell>();
    for (int i = 0; i < boardsz; i++) {
      for (int j = 0; j < boardsz; j++) {
        if (i == 0 && j == 0) {
          board.add(new Cell(0, 0, true, this.colorsUsed));
        }
        else {
          board.add(new Cell(i, j, false, this.colorsUsed)); 
        }
      }
    }

    //  T, B, L, R modification

    for (int i = 0; i < board.size(); i++) {
      Cell modifyThis = board.get(i);
      if (board.get(i).x == 0) { 
        modifyThis.left = null;
      }
      //  left
      else {
        modifyThis.left = board.get(i - boardsz);
      }
      if (board.get(i).x == boardsz - 1) {
        modifyThis.right = null;
      }
      //  right
      else {
        modifyThis.right = board.get(i + boardsz);
      }
      if (board.get(i).y == 0) {
        modifyThis.top = null;
      } 
      //  top
      else {
        modifyThis.top = board.get(i - 1);
      }
      if (board.get(i).y == boardsz - 1) {
        modifyThis.bottom = null;
      } 
      //  bottom
      else {
        modifyThis.bottom = board.get(i + 1);
      }
    }
  }


  //   determines which cell in the board
  //    was clicked on and returns that cell.
  public Cell clickedCell(Posn pos) {
    Cell cell = null;
    for (Cell c: board) {
      if ((c.x <= ((pos.x - 71) / 20)) && (((pos.x - 71) / 20) <= c.x )
          && (c.y <= ((pos.y - 71) / 20)) && (((pos.y - 71) / 20) <= c.y )) {
        cell = c;
      }
    }
    return cell;
  }

  //  changes  first cell to the color 
  //  of the cell that has been clicked
  public void updateOnClick(Cell cell) {
    if (cell != null) {
      Cell changeState = board.get(0);
      changeState.color = cell.color;
      board.set(0, changeState);
    }
  }

  //  alters world state depending
  //  on which cell is clicked

  public void onMouseClick(Posn pos) {
    if ((pos.x < 70 || pos.x > (boardSize * 20 + 70))
        || (pos.y < 70 || pos.y > (boardSize * 20 + 70))) {
    } else {
      this.updateOnClick(this.clickedCell(pos)); 
      clicks++;
    }
  }


  //  makes the world scene 
  //   that is to be displayed to the player.
  public WorldScene makeScene() {
    // size and background 
    WorldScene finalScene = new WorldScene(1200, 800);

    //   RULES
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

    //  WATER
    finalScene.placeImageXY(new FromFileImage("output-onlinepngtools-3.png"), 820, 150);
    //ﾟ "FLOOD IT" TITLE
    finalScene.placeImageXY(new FromFileImage("d8ca33e33c53b884f1509626a6393bb7.png"), 820, 150);
    //  MOVES MADE
    finalScene.placeImageXY((new TextImage(Integer.toString(clicks, 200) 
        + " /" + Integer.toString(limit, 200) , Color.PINK)), 
        boardSize * 13, boardSize * 25);
    //  TIME
    finalScene.placeImageXY(new TextImage("Time: " + time / 10 + "s", 20,
        FontStyle.REGULAR, Color.PINK), boardSize * 13, boardSize * 27);
    //  if player loses, overlay "YOU LOSE" on sand png
    if (clicks >= limit
        && (!allFlooded())) {
      finalScene.placeImageXY(new FromFileImage("sand_PNG24.png"),  
          500, 600);
      finalScene.placeImageXY(new FromFileImage("6ad7085ce08a743b1d74baf6833de11a.png"),  
          820, 600);
    }
    //   if player wins, overlay "YOU WIN"
    else if (clicks <= limit
        && allFlooded()) {
      finalScene.placeImageXY(new FromFileImage("7af9c61b4cc3ab1829a91ef060dea022.png"),  
          820, 600);
    }
    for (Cell c: board) {
      finalScene.placeImageXY(c.image(), 80 + 20 * c.x, 80 + 20 * c.y);
    }
    return finalScene;
  }

  // changes all the flooded cells in the board to have the color
  // of the first cell in the board which already has been changed to have 
  // the color of the cell that was clicked on.⋆⑅˚₊
  public void updateWorldState() {
    //  progress of flow 
    Cell floodProgress = this.board.get(0);
    //  direction of flow
    String floodingTo = floodProgress.color;
    for (int i = 0; i < board.size(); i++) {
      Cell cell = board.get(i);
      if (cell.flooded) {
        // ♡ sets color
        cell.setColor(floodingTo);
        cell.update(floodingTo);
      }
      //produces
      makeScene();
    }
  }

  //  checks if all the cells in the board are flooded
  boolean allFlooded() {
    boolean result = true;
    for (Cell cell: board) {
      result = result && cell.flooded;
    }
    return result;
  }

  // changes the state of the world at each tick
  public void onTick() {
    time++;
    updateWorldState();
  }

  // game is reset when player presses r 
  // scrambles order and colors
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      this.board = new ArrayList<Cell>();
      //  ♡ starts player back over w/ 0 clicks
      clicks = 0;
      makeCells(boardSize);
    }
  }

  //  alters what is given 
  // to bigBang depending on what the user inputs 
  public void startGame(int gridSize, int numberOfColors) {
    // ♡ cannot exceed 8
    if (numberOfColors > 8) {
      throw new IllegalArgumentException("✧You may not use more than 8 colors!✧");
    }
    boardSize = gridSize;
    colorsUsed = numberOfColors;
    FloodItWorld world = new FloodItWorld(gridSize, numberOfColors);
    world.bigBang(1200, 800, 0.1);
  }
}