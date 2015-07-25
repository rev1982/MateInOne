package ru.rubanevgeniya.mylockscreen;


import java.util.ArrayList;

public abstract class Figure {
  protected enum Type {pawn, king, bishop, queen, knight, rook}
  protected String image;
  protected int posX;
  protected int posY;
  protected boolean isWhite;
  protected Type type;
  protected ArrayList<Pos> possibleMove;
  protected boolean wasMoved = false;

  public Figure(boolean isWhite, int posX, int posY, String image, Type type) {
    this.isWhite = isWhite;
    this.posX = posX;
    this.posY = posY;
    this.image = image;
    this.type = type;
    possibleMove = new ArrayList<>();
  }

  void findPossibleMove(Figure[][] figureOnBoard) {

  }

}
