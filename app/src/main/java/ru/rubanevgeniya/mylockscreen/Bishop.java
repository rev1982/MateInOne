package ru.rubanevgeniya.mylockscreen;


public class Bishop extends Figure {

  public Bishop(char color, int positionX, int positionY, String image, String type) {
    super(color, positionX, positionY, image, type);
  }

  @Override
  void findPossibleMove(Figure[][] figureOnDesk) {
    possibleMoveX.clear();
    possibleMoveY.clear();
    char enemyColor;
    if (color == 'w') {
      enemyColor = 'b';
    } else {
      enemyColor = 'w';
    }
    addPosition(figureOnDesk, 1, 1, 1, 1, enemyColor);
    addPosition(figureOnDesk, 1, 1, -1, -1, enemyColor);
    addPosition(figureOnDesk, 1, 1, -1, 1, enemyColor);
    addPosition(figureOnDesk, 1, 1, 1, -1, enemyColor);
  }

  private void addPosition(Figure[][] figureOnDesk, int kX, int kY, int signX, int signY, char color) {
    boolean isContinue = true;
    while (isContinue) {
      if ((positionY + kY * signY < 8) && (positionY + kY * signY > -1) && (positionX + kX * signX < 8)
              && (positionX + kX * signX > -1)
              && (figureOnDesk[positionX + kX * signX][positionY + kY * signY] == null)) {
        possibleMoveY.add(positionY + kY * signY);
        possibleMoveX.add(positionX + kX * signX);
      } else {
        isContinue = false;
        if ((positionY + kY * signY < 8) && (positionY + kY * signY > -1) && (positionX + kX * signX < 8)
                && (positionX + kX * signX > -1)
                && figureOnDesk[positionX + kX * signX][positionY + kY * signY].color == color
          // &&(!figureOnDesk[positionX+kX*signX][positionY+kY*signY].type.equals("king"))
                ) {
          possibleMoveY.add(positionY + kY * signY);
          possibleMoveX.add(positionX + kX * signX);
        }
      }
      kX++;
      kY++;
    }
  }
}
