package ru.rubanevgeniya.mylockscreen;


import android.util.Log;

public class Bishop extends Figure {

  public Bishop(boolean isWhite, int posX, int posY) {
    super(isWhite, posX, posY, "v", Type.bishop);
  }

  @Override
  void findPossibleMove(Figure[][] figureOnBoard) {
    possibleMove.clear();
    addPosition(figureOnBoard, 1, 1);
    addPosition(figureOnBoard, -1, -1);
    addPosition(figureOnBoard, -1, 1);
    addPosition(figureOnBoard, 1, -1);
    addPosition(figureOnBoard, 1, -1);
  }

  private void addPosition(Figure[][] figureOnBoard, int signX, int signY) {
    Pos pos;
    int kX = 1;
    int kY = 1;
    boolean isContinue = true;
    while (isContinue) {
      int x = posX + kX * signX;
      int y = posY + kY * signY;
      if (y < 8 && y > -1 && x < 8 && x > -1) {
        if (figureOnBoard[x][y] == null) {
          pos = new Pos(x, y);
          possibleMove.add(pos);
        } else {
          isContinue = false;
          if (figureOnBoard[x][y] != null && figureOnBoard[x][y].isWhite == !isWhite
            // &&(!figureOnBoard[positionX+kX*signX][positionY+kY*signY].type.equals("king"))
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
