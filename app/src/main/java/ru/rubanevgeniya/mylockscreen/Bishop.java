package ru.rubanevgeniya.mylockscreen;


public class Bishop extends Figure {

  public Bishop(char color, int positionX, int positionY, String image, String type) {
    super(color, positionX, positionY, image, type);
  }

  @Override
  void findPossibleMove(Figure[][] figureOnDesk) { // TODO: change 'desk' to 'board'
    possibleMoveX.clear();
    possibleMoveY.clear();
    char enemyColor;
    if (color == 'w') { // TODO: change to: char enemyColor = color == 'w' ? 'b' : 'w';
      enemyColor = 'b';
    } else {
      enemyColor = 'w';
    }
    // TODO: all the code above can be moved to the base class's method findPossibleMove here just call super.findPossibleMove(figureOnDesk)
    addPosition(figureOnDesk, 1, 1, 1, 1, enemyColor);
    addPosition(figureOnDesk, 1, 1, -1, -1, enemyColor);
    addPosition(figureOnDesk, 1, 1, -1, 1, enemyColor);
    addPosition(figureOnDesk, 1, 1, 1, -1, enemyColor);
  }

  private void addPosition(Figure[][] figureOnDesk, int kX, int kY, int signX, int signY, char color) {
    // TODO: make kX and kY local, they always =1: int kX = 1; int kY = 1;
    boolean isContinue = true;
    // TODO: change while to for(boolean isContinue = true; isContinue; kX += signX, kY += signY)
    while (isContinue) {
      // TODO: common expression: int y = positionY + kY * signY;
      // TODO: common expression: int x = positionX + kX * signX
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
