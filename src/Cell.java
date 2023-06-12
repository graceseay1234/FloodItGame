import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;


//  ｡ﾟ•┈୨♡୧┈•ﾟ｡Represents a single square of the game area
class Cell {
  //  In logical coordinates, with the origin at the top-left corner of the
  // screen
  int x;
  int y;
  String color;
  boolean flooded;
  Posn posn;
  //  the four adjacent cells to this one
  Cell left;
  Cell right;
  Cell top;
  Cell bottom;
  //  The list of colors
  ArrayList<String> colors;

  Cell(int x, int y, boolean flooded, int colorNum) {
    this.x = x; 
    this.y = y;
    initColors();
    // ♡ randomizes color based on #
    int random = (int) (Math.random() * colorNum);
    // ♡  random color
    this.color = colors.get(random);
    this.flooded = flooded;
    // ♡  x and y
    this.posn = new Posn(this.x, this.y);
  }

  // ﾟconstructor for testing
  Cell(int x, int y, String color, boolean flooded,
      Cell left, Cell top, Cell right, Cell bottom) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = flooded;
    this.posn = new Posn(this.x, this.y);
    this.left = left;
    this.top = top; 
    this.bottom = bottom;
    this.right = right;
  }

  // ︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲︲

  // generates cell on background
  //  pastel colors
  WorldImage image() {
    if (this.color.equals("light purple")) {
      return new RectangleImage(20, 20, OutlineMode.SOLID, new Color(243, 243, 252)); 
    }
    else if (this.color.equals("light blue")) { 
      return new RectangleImage(20, 20, OutlineMode.SOLID, new Color(215, 237, 250));
    }
    else if (this.color.equals("light green")) {   
      return new RectangleImage(20, 20, OutlineMode.SOLID, new Color(217, 244, 224));  
    }
    else if (this.color.equals("light red")) { 
      return new RectangleImage(20, 20, OutlineMode.SOLID, new Color(232, 208, 215));
    }
    else if (this.color.equals("purple")) { 
      return new RectangleImage(20, 20, OutlineMode.SOLID, new Color(233, 225, 249)); 
    }
    else if (this.color.equals("light yellow")) { 
      return new RectangleImage(20, 20, OutlineMode.SOLID, new Color(250, 244, 237)); 
    }
    else if (this.color.equals("light orange")) {
      return new RectangleImage(20, 20, OutlineMode.SOLID, new Color(246, 223, 218)); 
    }
    else { //light greenish blue
      return new RectangleImage(20, 20, OutlineMode.SOLID, new Color(230, 252, 252)); 
    }
  }


  // pastel colors ✧･ﾟ: *✧･ﾟ:

  // new Color(215, 237, 250)
  // new Color(243, 243, 252)
  // new Color(217, 244, 224)
  // new Color(233, 225, 249)
  // new Color(250, 244, 237)
  // new Color(246, 223, 218)
  // new Color(230, 252, 252)
  // new Color(232, 208, 215)


  // pink colors ✧･ﾟ: *✧･ﾟ:

  // new Color(255,192,203)
  // new Color(255,228,225)
  // new Color(255,182,193)
  // new Color(255,105,180)
  // new Color(219,112,147)
  // new Color(255,20,147)
  // new Color(255,228,225)
  // new Color(253,221,230)

  //  change states of colors to switch to pink pastels

  // alters color of this cell to the input string
  void setColor(String color) {
    this.color = color;
  }

  //  modifies the colors list to include the allowed colors.
  void initColors() {
    colors = new ArrayList<String>();
    colors.add("light blue");
    colors.add("light red");
    colors.add("light orange");
    colors.add("light purple");
    colors.add("light green");
    colors.add("purple");
    colors.add("light yellow");
    colors.add("bluish green");
  }

  // alters isFlooded value of cells that have not yet
  // been flooded; makes them the color that is
  // flooding 
  void update(String rbg) {
    //left
    if (this.left != null
        && !this.left.flooded
        && this.left.color.equals(rbg)) {
      this.left.flooded = true;
    }
    //right
    if (this.right != null
        && !this.right.flooded
        && this.right.color.equals(rbg)) {
      this.right.flooded = true;
    }
    //top
    if (this.top != null
        && !this.top.flooded 
        && this.top.color.equals(rbg)) {
      this.top.flooded = true;
    }
    //bottom
    if (this.bottom != null
        && !this.bottom.flooded 
        && this.bottom.color.equals(rbg)) {
      this.bottom.flooded = true;
    }
  }
}