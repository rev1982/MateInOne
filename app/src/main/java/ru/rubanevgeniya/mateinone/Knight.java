package ru.rubanevgeniya.mateinone;

public class Knight extends Figure {
  public Knight(boolean isWhite, int posX, int posY) {
    super(isWhite, posX, posY, "m", Type.knight);
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
            || (figureOnBoard[x][y].isWhite == !isWhite
    ))) {
      pos = new Pos(x, y);
      possibleMove.add(pos);
    }
  }
}
