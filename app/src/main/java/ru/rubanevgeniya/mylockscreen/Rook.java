package ru.rubanevgeniya.mylockscreen;

/**
 * Created by rey on 3/23/2015.
 */
public class Rook extends Figure {
  private boolean isContinue = true;
  private Pos pos;

  public Rook(boolean isWhite, int posX, int posY, String image, Type type) {
    super(isWhite, posX, posY, image, type);
  }

  @Override
  void findPossibleMove(Figure[][] figureOnBoard) {
    possibleMove.clear();
    boolean isEnemyWhite = !isWhite;
    addPosition(figureOnBoard, 1, 0, 1, 0, isEnemyWhite);
    addPosition(figureOnBoard, 0, 1, 0, 1, isEnemyWhite);
    addPosition(figureOnBoard, 1, 0, -1, 0, isEnemyWhite);
    addPosition(figureOnBoard, 0, 1, 0, -1, isEnemyWhite);
  }

  private void addPosition(Figure[][] figureOnBoard, int kX, int kY, int signX, int signY, boolean isEnemyWhite) {
    isContinue = true;
    while (isContinue) {
      if ((posY + kY * signY < 8) && (posY + kY * signY > -1) && (posX + kX * signX < 8)
              && (posX + kX * signX > -1)
              && (figureOnBoard[posX + kX * signX][posY + kY * signY] == null)) {
        pos = new Pos(posX + kX * signX, posY + kY * signY);
        possibleMove.add(pos);
      } else {
        isContinue = false;
        if ((posY + kY * signY < 8) && (posY + kY * signY > -1) && (posX + kX * signX < 8)
                && (posX + kX * signX > -1)
                && figureOnBoard[posX + kX * signX][posY + kY * signY] != null
                && figureOnBoard[posX + kX * signX][posY + kY * signY].isWhite == isEnemyWhite
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
