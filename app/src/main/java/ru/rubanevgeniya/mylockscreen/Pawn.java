package ru.rubanevgeniya.mylockscreen;


public class Pawn extends Figure {
  public Pawn(boolean isWhite, int posX, int posY, String image, Type type) {
    super(isWhite, posX, posY, image, type);
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
    if (kY == 2) {
      if ((posY + kY * signY < 8) && (posY + kY * signY > -1)
              && (figureOnBoard[posX][posY + kY * signY] == null)
              && (figureOnBoard[posX][posY + (kY - 1) * signY] == null)) {
        pos = new Pos(posX, posY + kY * signY);
        possibleMove.add(pos);
      }
    } else {
      if ((posY + kY * signY < 8) && (posY + kY * signY > -1)
              && (figureOnBoard[posX][posY + kY * signY] == null)) {
        pos = new Pos(posX, posY + kY * signY);
        possibleMove.add(pos);
      }
    }
    if ((kX != 0) && (posY + kY * signY < 8) && (posY + kY * signY > -1) && (posX + kX < 8)
            && (posX + kX > -1)
            && figureOnBoard[posX + kX][posY + kY * signY] != null
            && figureOnBoard[posX + kX][posY + kY * signY].isWhite == !isWhite
      //&&(!figureOnBoard[posX+kX][posY+kY*signY].type.equals("king"))
            ) {
      pos = new Pos(posX + kX, posY + kY * signY);
      possibleMove.add(pos);
    }
  }
}
