package ru.rubanevgeniya.mateinone;


public class King extends Figure {
  public King(boolean isWhite, int posX, int posY) {
    super(isWhite, posX, posY, "l", Type.king);
  }

  private boolean isShortCastlingPossible = true;
  private boolean isLongCastlingPossible = true;
  private Pos pos;
  private static final int[] whiteKingCellsY = new int[]{0, 0, 0};
  private static final int[] blackKingCellsY = new int[]{7, 7, 7};
  private static final int[] kingCellsShortX = new int[]{4, 5, 6};
  private static final int[] kingCellsLongX = new int[]{2, 3, 4};

  @Override
  void findPossibleMove(Figure[][] figureOnBoard) {
    possibleMove.clear();
    addPosition(figureOnBoard);
    if (isShortCastlingPossible) {
      isShortCastlingPossible = addCastling(figureOnBoard, false);
    }
    if (isLongCastlingPossible) {
      isShortCastlingPossible = addCastling(figureOnBoard, true);
    }
  }

  @SuppressWarnings("ConstantConditions")
  boolean addCastling(Figure[][] figureOnBoard, boolean isLong) {
    int yCoordinate = isWhite ? 0 : 7;
    boolean isCastlingPossible = isLong ? isLongCastlingPossible : isShortCastlingPossible;
    int x = isLong ? 0 : 7;

    if (!(isCastlingPossible && !wasMoved
            && (isWhite && posX == 4 && posY == 0 || !isWhite && posX == 4 && posY == 7)
            && figureOnBoard[x][yCoordinate] != null
            && figureOnBoard[x][yCoordinate].type == Type.rook
            && figureOnBoard[x][yCoordinate].isWhite == isWhite
            && !figureOnBoard[x][yCoordinate].wasMoved
            && ((isLong && figureOnBoard[1][yCoordinate] == null
            && figureOnBoard[2][yCoordinate] == null
            && figureOnBoard[3][yCoordinate] == null)
            || (!isLong && figureOnBoard[6][yCoordinate] == null
            && figureOnBoard[5][yCoordinate] == null)))) {
      return false;
    }
    int xPos = isLong ? 2 : 6;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Figure figure = figureOnBoard[i][j];
        if (figure != null && figure.isWhite == !isWhite) {
          figure.findPossibleMove(figureOnBoard);
          for (int k = 0; k < figure.possibleMove.size(); k++) {
            int xMove = figure.possibleMove.get(k).x;
            int yMove = figure.possibleMove.get(k).y;
            for (int m = 0; m < kingCellsLongX.length; m++) {
              if (((isLong && xMove == kingCellsLongX[m]) ||
                      (!isLong && xMove == kingCellsShortX[m])) && (
                      (isWhite && yMove == whiteKingCellsY[m])
                              || (!isWhite && yMove == blackKingCellsY[m]))) {
                return false;
              }
            }
          }
        }
      }
    }
    pos = new Pos(xPos, yCoordinate);
    possibleMove.add(pos);
    return true;
  }

  void addPosition(Figure[][] figureOnBoard) {
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        if ((posY + i > -1) && (posY + i < 8) && (posX + j > -1) && (posX + j < 8)
                && (figureOnBoard[posX + j][posY + i] == null
                || (figureOnBoard[posX + j][posY + i].isWhite == !isWhite))) {
          pos = new Pos(posX + j, posY + i);
          possibleMove.add(pos);
        }
      }
    }
  }
}
