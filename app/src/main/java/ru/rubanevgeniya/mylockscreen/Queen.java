package ru.rubanevgeniya.mylockscreen;


public class Queen extends Figure {

  public Queen(boolean isWhite, int posX, int posY, String image, Type type) {
    super(isWhite, posX, posY, image, type);
  }

  @Override
  void findPossibleMove(Figure[][] figureOnBoard) {
    possibleMove.clear();
    addPosition(figureOnBoard, 1, 1, 1, 1);
    addPosition(figureOnBoard, 1, 1, -1, -1);
    addPosition(figureOnBoard, 1, 1, -1, 1);
    addPosition(figureOnBoard, 1, 1, 1, -1);
    addPosition(figureOnBoard, 1, 0, 1, 0);
    addPosition(figureOnBoard, 0, 1, 0, 1);
    addPosition(figureOnBoard, 1, 0, -1, 0);
    addPosition(figureOnBoard, 0, 1, 0, -1);
  }

  private void addPosition(Figure[][] figureOnBoard, int kX, int kY, int signX, int signY) {
    Pos pos;
    boolean isContinue = true;

    while (isContinue) {
      if ((posY + kY * signY < 8) && (posY + kY * signY > -1) && (posX + kX * signX < 8) && (posX + kX * signX > -1)
              && (figureOnBoard[posX + kX * signX][posY + kY * signY] == null)) {
        pos = new Pos(posX + kX * signX, posY + kY * signY);
        possibleMove.add(pos);
      } else {
        isContinue = false;
        if ((posY + kY * signY < 8) && (posY + kY * signY > -1) && (posX + kX * signX < 8) && (posX + kX * signX > -1)
                && figureOnBoard[posX + kX * signX][posY + kY * signY] != null
                && figureOnBoard[posX + kX * signX][posY + kY * signY].isWhite == !isWhite
          //&&(!figureOnBoard[posX+kX*signX][posY+kY*signY].type.equals("king"))
                ) {
          pos = new Pos(posX + kX * signX, posY + kY * signY);
          possibleMove.add(pos);
        }
      }
      kX++;
      kY++;
    }
  }

}
