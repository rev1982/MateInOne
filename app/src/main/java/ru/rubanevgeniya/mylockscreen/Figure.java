package ru.rubanevgeniya.mylockscreen;


import java.util.ArrayList;

public abstract class Figure {
  enum Type {pawn, king, bishop}; // TODO: finish this enum
  protected String image;
  protected int positionX; // TODO: change to Pos pos; or posX, posY;
  protected int positionY;
  protected char color;
  protected String type; // TODO: change String to a new enum Type and do not compare types with Strings.equal()

  // TODO: introduce a class Pos {int x; int y;} and replace all parallel use of X and Y with one Pos
  // TODO: this protected ArrayList<Pos> possibleMoves;
  protected ArrayList<Integer> possibleMoveX;
  protected ArrayList<Integer> possibleMoveY;
  protected boolean wasMoved = false;

  public Figure(char color, int positionX, int positionY, String image, String type) {
    this.color = color;
    this.positionX = positionX;
    this.positionY = positionY;
    this.image = image;
    this.type = type;
    possibleMoveX = new ArrayList<>();
    possibleMoveY = new ArrayList<>();
  }

  void findPossibleMove(Figure[][] figureOnDesk) {

  }

}
