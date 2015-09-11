package ru.rubanevgeniya.mateinone;


import java.util.ArrayList;

public abstract class Figure {
  protected enum Type {
    pawn, king, bishop, queen, knight, rook;

    public static final String STRING = "pKBQNR";

    public char toChar() {
      return STRING.charAt(this.ordinal());
    }

    public static Type fromChar(char c) {
      return c == 'P' ? pawn : Type.values()[STRING.indexOf(c)];
    }
  }

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
