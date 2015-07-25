package ru.rubanevgeniya.mylockscreen;

public class Knight extends Figure {
  public Knight(boolean isWhite, int posX, int posY, String image, Type type) {
    super(isWhite, posX, posY, image, type);
  }

  @Override
  void findPossibleMove(Figure[][] figureOnBoard) {
    possibleMove.clear();
    addPosition(figureOnBoard, posX - 2, posY - 1);
    addPosition(figureOnBoard, posX - 2, posY + 1);
    addPosition(figureOnBoard, posX + 2, posY - 1);
    addPosition(figureOnBoard, posX + 2, posY + 1);
    addPosition(figureOnBoard, posX + 1, posY + 2);
    addPosition(figureOnBoard, posX - 1, posY + 2);
    addPosition(figureOnBoard, posX - 1, posY - 2);
    addPosition(figureOnBoard, posX + 1, posY - 2);
  }

  private void addPosition(Figure[][] figureOnBoard, int x, int y) {
    Pos pos;
    if ((y > -1) && (x > -1) && (y < 8) && (x < 8)
            && (figureOnBoard[x][y] == null
            || (figureOnBoard[x][y].isWhite == !isWhite //&&
            //(!figureOnBoard[x][y].type.equals("king"))
    ))) {
      pos = new Pos(x, y);
      possibleMove.add(pos);
    }
  }

}
