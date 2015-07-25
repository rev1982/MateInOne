package ru.rubanevgeniya.mylockscreen;


public class Bishop extends Figure {

  public Bishop(boolean isWhite, int posX, int posY, String image, Type type) {
    super(isWhite, posX, posY, image, type);
  }

  @Override
  void findPossibleMove(Figure[][] figureOnBoard) {
    possibleMove.clear();
    addPosition(figureOnBoard, 1, 1);
    addPosition(figureOnBoard, -1, -1);
    addPosition(figureOnBoard,  -1, 1);
    addPosition(figureOnBoard,  1, -1);
    addPosition(figureOnBoard, 1, -1);
  }

  private void addPosition(Figure[][] figureOnBoard, int signX, int signY) {
    Pos pos;
    int kX = 1;
    int kY = 1;
    boolean isContinue = true;
    while (isContinue ) {
      if ((posY + kY * signY < 8) && (posY + kY * signY > -1) && (posX + kX * signX < 8) && (posX + kX * signX > -1)
              && (figureOnBoard[posX + kX * signX][posY + kY * signY] == null)) {
        pos = new Pos(posX + kX * signX, posY + kY * signY);
        possibleMove.add(pos);
      } else {
        isContinue = false;
        if ((posY + kY * signY < 8) && (posY + kY * signY > -1) && (posX + kX * signX < 8) && (posX + kX * signX > -1)
                && figureOnBoard[posX + kX * signX][posY + kY * signY] != null
        && figureOnBoard[posX + kX * signX][posY + kY * signY].isWhite == !isWhite
          // &&(!figureOnBoard[positionX+kX*signX][positionY+kY*signY].type.equals("king"))
                ) {
          pos = new Pos(posX + kX * signX, posY + kY * signY);
          possibleMove.add(pos);
        }
      }
      kX++;
      kY++;
    }

//    for(boolean isContinue = true; isContinue; kX += signX, kY += signY) {
//      if ((y < 8) && (y > -1) && (x < 8) && (x > -1)
//              && (figureOnBoard[x][y] == null)) {
//        pos = new Pos(x, y);
//        possibleMove.add(pos);
//      } else {
//        isContinue = false;
//        if ((y < 8) && (y > -1) && (x < 8) && (x > -1)
//                && figureOnBoard[posX + kX * signX][posY + kY * signY].isWhite == !isWhite
//          // &&(!figureOnBoard[positionX+kX*signX][positionY+kY*signY].type.equals("king"))
//                ) {
//          pos = new Pos(x, y);
//          possibleMove.add(pos);
//        }
//      }
//    }


  }
}
