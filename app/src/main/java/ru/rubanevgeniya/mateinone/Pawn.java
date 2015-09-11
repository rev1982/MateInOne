package ru.rubanevgeniya.mateinone;


public class Pawn extends Figure {
  public Pawn(boolean isWhite, int posX, int posY) {
    super(isWhite, posX, posY, "o", Type.pawn);
  }

  @Override
  void findPossibleMove(Figure[][] figureOnBoard) {
    possibleMove.clear();
    if (isWhite) {
      addPosition(figureOnBoard, 1, 1, 1);
      addPosition(figureOnBoard, -1, 1, 1);
      if (posY == 1) {
        addPosition(figureOnBoard, 0, 2, 1);
      }
    } else {
      addPosition(figureOnBoard, 1, 1, -1);
      addPosition(figureOnBoard, -1, 1, -1);
      if (posY == 6) {
        addPosition(figureOnBoard, 0, 2, -1);
      }
    }
  }

  private void addPosition(Figure[][] figureOnBoard, int kX, int kY, int signY) {
    Pos pos;
    int x = posX + kX;
    int y = posY + kY * signY;
    if ((y < 8) && (y > -1)) {
      if (kY == 2) {
        if ((figureOnBoard[posX][y] == null)
                && (figureOnBoard[posX][posY + (kY - 1) * signY] == null)) {
          pos = new Pos(posX, y);
          possibleMove.add(pos);
        }
      } else {
        if (figureOnBoard[posX][y] == null) {
          pos = new Pos(posX, y);
          possibleMove.add(pos);
        }
      }
      if ((kX != 0) && (x < 8) && (x > -1)
              && figureOnBoard[x][y] != null
              && figureOnBoard[x][y].isWhite == !isWhite
              ) {
        pos = new Pos(x, y);
        possibleMove.add(pos);
      }
    }
  }
}
