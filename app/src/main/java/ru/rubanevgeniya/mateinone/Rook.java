package ru.rubanevgeniya.mateinone;


public class Rook extends Figure {

  public Rook(boolean isWhite, int posX, int posY) {
    super(isWhite, posX, posY, "t", Type.rook);
  }

  @Override
  void findPossibleMove(Figure[][] figureOnBoard) {
    possibleMove.clear();
    addPosition(figureOnBoard, 1, 0, 1, 0);
    addPosition(figureOnBoard, 0, 1, 0, 1);
    addPosition(figureOnBoard, 1, 0, -1, 0);
    addPosition(figureOnBoard, 0, 1, 0, -1);
  }

  private void addPosition(Figure[][] figureOnBoard, int kX, int kY, int signX, int signY) {
    boolean isContinue = true;
    Pos pos;
    while (isContinue) {
      int x = posX + kX * signX;
      int y = posY + kY * signY;
      if ((y < 8) && (y > -1) && (x < 8) && (x > -1)) {
        if (figureOnBoard[x][y] == null) {
          pos = new Pos(x, y);
          possibleMove.add(pos);
        } else {
          isContinue = false;
          if (figureOnBoard[x][y].isWhite == !isWhite
                  ) {
            pos = new Pos(x, y);
            possibleMove.add(pos);
          }
        }
        kX++;
        kY++;
      } else {
        isContinue = false;
      }
    }
  }
}
