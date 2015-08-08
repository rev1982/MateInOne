package ru.rubanevgeniya.mylockscreen;


import android.util.Log;

import java.util.ArrayList;

public class Board {
  private String startPosition;
  protected static Figure[][] figureOnBoard = new Figure[8][8];
  protected Figure[] allFigures;
  protected ArrayList<Pos> possibleMove;
  protected boolean isAlreadyCaptured = false;
  private static String TAG = "Logs Board = ";
  private static Figure[][] tempfigureOnBoard = new Figure[8][8];
  private Figure[] tempAllFigures;
  private ArrayList<Pos> enemyPossibleMove;


  Board(String startPosition) {
    this.startPosition = startPosition;
  }

  protected void createStartPosition() {
    Log.d(TAG, "creating this startPosition " + startPosition);
    possibleMove = new ArrayList<>();
    allFigures = new Figure[(startPosition.length() - 1) / 4];
    boolean isWhite;
    int xPos;
    int yPos;
    for (int i = 0, j = 0; i < allFigures.length; j += 4, i++) {
      isWhite = startPosition.charAt(j) == 'w';
      xPos = startPosition.charAt(j + 2) - '1';
      yPos = startPosition.charAt(j + 3) - '1';
      switch (startPosition.charAt(j + 1)) {
        case 'R':
          allFigures[i] = new Rook(isWhite, xPos, yPos);
          //Log.d(TAG,"rook "+xPos+" "+yPos);
          break;
        case 'N':
          allFigures[i] = new Knight(isWhite, xPos, yPos);
          //Log.d(TAG,"knight "+xPos+" "+yPos);
          break;
        case 'B':
          allFigures[i] = new Bishop(isWhite, xPos, yPos);
          //Log.d(TAG,"bishop "+xPos+" "+yPos);
          break;
        case 'Q':
          allFigures[i] = new Queen(isWhite, xPos, yPos);
          //Log.d(TAG,"queen " + xPos+" " + yPos);
          break;
        case 'K':
          allFigures[i] = new King(isWhite, xPos, yPos);
          if (isWhite && (xPos != 4 || yPos != 0) || !isWhite && (xPos != 4 || yPos != 7)) {
            allFigures[i].wasMoved = true;
          }
          //Log.d(TAG,"king " + xPos + " " + yPos);
          break;
        case 'p':
        case 'P':
          allFigures[i] = new Pawn(isWhite, xPos, yPos);
          //Log.d(TAG,"pawn "+xPos+" "+yPos);
          break;
      }
      figureOnBoard[xPos][yPos] = allFigures[i];
    }
  }


  protected void findPossibleMove(int i) {
    possibleMove.clear();
    if (allFigures[i] != null) {
      allFigures[i].findPossibleMove(figureOnBoard);
      possibleMove = allFigures[i].possibleMove;
      if (allFigures[i].type == Figure.Type.king) {
        int j = 0;
        while (j < possibleMove.size()) {
          if (!isMoveSafety(i, possibleMove.get(j).x, possibleMove.get(j).y, allFigures[i].isWhite)) {
            possibleMove.remove(j);
          } else {
            j++;
          }
        }
      }
    }
  }

  protected void boardAndFigureAfterMove(int itemIndex, int possCoorIndex) {
    Log.d(TAG, "itemIndex = " + itemIndex + " possCoorIndex = " + possCoorIndex + " possibleMove.size() = " + possibleMove.size());
    figureOnBoard[allFigures[itemIndex].posX][allFigures[itemIndex].posY] = null;//make square free
    if ((allFigures[itemIndex].type == Figure.Type.king || allFigures[itemIndex].type == Figure.Type.rook)
            && !allFigures[itemIndex].wasMoved) {
      allFigures[itemIndex].wasMoved = true;
    }
    if (!isAlreadyCaptured && figureOnBoard[possibleMove.get(possCoorIndex).x][possibleMove.get(possCoorIndex).y] != null) {
      Log.d(TAG, "item to capture " + possibleMove.get(possCoorIndex).x + " " + possibleMove.get(possCoorIndex).y);
      for (int i = 0; i < allFigures.length; i++) {
        if ((allFigures[i] != null) && (allFigures[i].posX == possibleMove.get(possCoorIndex).x) &&
                (allFigures[i].posY == possibleMove.get(possCoorIndex).y)) {
          allFigures[i] = null;
          Log.d(TAG, "found item to del from the board, i = " + i);
          break;
        }
      }
      figureOnBoard[possibleMove.get(possCoorIndex).x][possibleMove.get(possCoorIndex).y] = null;//capture enemy
      isAlreadyCaptured = true;
    }
    allFigures[itemIndex].posX = possibleMove.get(possCoorIndex).x;
    allFigures[itemIndex].posY = possibleMove.get(possCoorIndex).y;
    figureOnBoard[allFigures[itemIndex].posX][allFigures[itemIndex].posY] = allFigures[itemIndex];//occupy new square
  }

  protected int boardAndFiguresAfterCastling(int startKingX, int finishKingX, int
          startKingY) {
    if (Math.abs(startKingX - finishKingX) > 1) {
      int xRookStart;
      int xRookFinish;
      if (startKingX - finishKingX == 2) {
        xRookFinish = 3;
        xRookStart = 0;
      } else {
        xRookFinish = 5;
        xRookStart = 7;
      }
      for (int i = 0; i < allFigures.length; i++) {
        if ((allFigures[i] != null) && (allFigures[i].posX == xRookStart)
                && (allFigures[i].posY == startKingY)
                && (allFigures[i].type == Figure.Type.rook)) {
          figureOnBoard[allFigures[i].posX][allFigures[i].posY] = null;
          allFigures[i].posX = xRookFinish;
          allFigures[i].wasMoved = true;
          figureOnBoard[allFigures[i].posX][allFigures[i].posY] = allFigures[i];
          return i;
        }
      }
    }
    return -1;
  }

  protected void boardAndFiguresAfterPawnBecomesAnotherItem(ChessView chessView, Figure.Type newItemType, int index) {
    Log.d(TAG, "start changing, new item =" + newItemType);
    boolean isWhite = allFigures[index].isWhite;
    int x = allFigures[index].posX;
    int y = allFigures[index].posY;
    if (newItemType != null) {
      switch (newItemType) {
        case rook:
          allFigures[index] = new Rook(isWhite, x, y);
          break;
        case knight:
          allFigures[index] = new Knight(isWhite, x, y);
          break;
        case bishop:
          allFigures[index] = new Bishop(isWhite, x, y);
          break;
        case queen:
          allFigures[index] = new Queen(isWhite, x, y);
          break;
      }
      chessView.isNewBoardReady = true;
    }
  }

  protected String currentChessPositionAfterMove() {
    StringBuilder builder = new StringBuilder("");
    Figure figure;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        figure = figureOnBoard[i][j];
        if (figure != null) {
          builder.append(figure.isWhite ? 'w' : 'b');
          builder.append(figure.type.toChar());
          builder.append(figure.posX + 1);
          builder.append(figure.posY + 1);
        }
      }
    }
    return builder.toString();
  }

  protected int findKingIndex() {
    boolean isEnemyKingWhite = startPosition.charAt(startPosition.length() - 1) == 'b';
    for (int i = 0; i < allFigures.length; i++) {
      if (allFigures[i] != null && allFigures[i].type == Figure.Type.king && allFigures[i].isWhite == isEnemyKingWhite) {
        return i;
      }
    }
    return -1;
  }

  protected boolean isCheckTemp(int ind, boolean isWhite) {
    Log.d(TAG, "isCheckTemp start");
    int xi = tempAllFigures[ind].posX;
    int yi = tempAllFigures[ind].posY;
    for (int i = 0; i < tempAllFigures.length; i++) {
      if (tempAllFigures[i] != null && tempAllFigures[i].isWhite != isWhite) {
        findPossibleMoveTemp(i);
        for (int k = 0; k < enemyPossibleMove.size(); k++) {
          if (enemyPossibleMove.get(k).x == xi && enemyPossibleMove.get(k).y == yi) {
            Log.d(TAG, "is check = " + true);
            return true;
          }
        }
      }
    }
    Log.d(TAG, "is checkTemp = " + false);
    return false;
  }

  protected int[] findMoveAfterCheck(int kingInd, boolean isWhite) {
    Log.d(TAG, "start findMoveAfterCheck for isWhite = " + isWhite);
    boolean isCapturing;
    for (int i = 0; i < allFigures.length; i++) {
      if (allFigures[i] != null && allFigures[i].isWhite == isWhite) {
        findPossibleMove(i);
        for (int j = 0; j < possibleMove.size(); j++) {
          createTempAllFiguresOnBoard();
          isCapturing = boardAndFigureAfterMoveTemp(tempfigureOnBoard, tempAllFigures, i,
                  possibleMove.get(j).x, possibleMove.get(j).y);
          if (!isCheckTemp(kingInd, isWhite)) {
            int[] safetyMove = new int[4];
            safetyMove[0] = possibleMove.get(j).x;
            safetyMove[1] = possibleMove.get(j).y;
            safetyMove[2] = isCapturing ? 1 : 0;
            safetyMove[3] = i;
            return safetyMove;
          }
        }
      }
    }
    return null;
  }

  protected int[] findSafetyMove(int ind, boolean isWhite) {
    Log.d(TAG, "start findSafetyMove for " + allFigures[ind].type + "isWhite = " + isWhite);
    findPossibleMove(ind);
    ArrayList<Pos> kingPossibleMove = possibleMove;
    int xi;
    int yi;
    boolean isSafetyMove = false;
    for (int j = 0; j < kingPossibleMove.size(); j++) {
      xi = kingPossibleMove.get(j).x;
      yi = kingPossibleMove.get(j).y;
      createTempAllFiguresOnBoard();
      boolean isCapturing = boardAndFigureAfterMoveTemp(tempfigureOnBoard, tempAllFigures, ind, xi, yi);
      for (int i = 0; i < tempAllFigures.length; i++) {
        if (i == 0) {
          isSafetyMove = true;
        }
        if (!isSafetyMove) {
          break;
        } else if (tempAllFigures[i] != null && tempAllFigures[i].isWhite != isWhite) {
          findPossibleMoveTemp(i);
          for (int k = 0; k < enemyPossibleMove.size(); k++) {
            if (enemyPossibleMove.get(k).x == xi && enemyPossibleMove.get(k).y == yi) {
              isSafetyMove = false;
              break;
            }
          }
        }
      }
      if (isSafetyMove) {
        Log.d(TAG, "found safety move  x = " + xi + " y = " + yi + " for " + allFigures[ind].type +
                "  old position is x = " + allFigures[ind].posX + " y =" + allFigures[ind].posY);
        int[] safetyMove = new int[3];
        safetyMove[0] = xi;
        safetyMove[1] = yi;
        safetyMove[2] = isCapturing ? 1 : 0;
        return safetyMove;
      }
    }
    Log.d(TAG, "found NO safety move    for " + allFigures[ind].type +
            "  old position is x = " + allFigures[ind].posX + " y =" + allFigures[ind].posY);
    return null;
  }

  protected boolean isMoveSafety(int ind, int xi, int yi, boolean isWhite) {
    createTempAllFiguresOnBoard();
    boardAndFigureAfterMoveTemp(tempfigureOnBoard, tempAllFigures, ind, xi, yi);
    for (int i = 0; i < tempAllFigures.length; i++) {
      if (tempAllFigures[i] != null && tempAllFigures[i].isWhite != isWhite) {
        findPossibleMoveTemp(i);
        for (int k = 0; k < enemyPossibleMove.size(); k++) {
          if (enemyPossibleMove.get(k).x == xi && enemyPossibleMove.get(k).y == yi) {
            return false;
          }
        }
      }
    }
    return true;
  }

  protected int[] findArbitrarySafetyMove(boolean isWhite) { //for this color
    int[] safetyMove;
    for (int i = 0; i < allFigures.length; i++) {
      if (allFigures[i] != null && allFigures[i].isWhite == isWhite) {
        safetyMove = findSafetyMove(i, isWhite);
        if (safetyMove != null) {
          int[] safetyMoveWithInd = new int[4];
          safetyMoveWithInd[0] = safetyMove[0];
          safetyMoveWithInd[1] = safetyMove[1];
          safetyMoveWithInd[2] = safetyMove[2];
          safetyMoveWithInd[3] = i;
          return safetyMoveWithInd;
        }
      }
    }
    return null;
  }

  protected int[] findArbitraryPossibleMove(boolean isWhite) { //for this color
    for (int i = 0; i < allFigures.length; i++) {
      if (allFigures[i] != null && allFigures[i].isWhite == isWhite) {
        findPossibleMove(i);
        if (possibleMove != null && possibleMove.size() > 0) {
          int[] possMoveWithInd = new int[4];
          possMoveWithInd[0] = possibleMove.get(0).x;
          possMoveWithInd[1] = possibleMove.get(0).y;
          possMoveWithInd[2] = (figureOnBoard[possibleMove.get(0).x][possibleMove.get(0).y] != null ? 1 : 0);
          possMoveWithInd[3] = i;
          return possMoveWithInd;
        }
      }
    }
    return null;
  }

  private boolean boardAndFigureAfterMoveTemp(Figure[][] tempfigureOnBoard, Figure[] tempAllFigures,
                                              int itemIndex, int xNew, int yNew) {
    boolean isCapturing = false;
    tempfigureOnBoard[tempAllFigures[itemIndex].posX][tempAllFigures[itemIndex].posY] = null;//make square free
    if ((tempAllFigures[itemIndex].type == Figure.Type.king || tempAllFigures[itemIndex].type == Figure.Type.rook)
            && !tempAllFigures[itemIndex].wasMoved) {
      tempAllFigures[itemIndex].wasMoved = true;
    }
    if (!isAlreadyCaptured && tempfigureOnBoard[xNew][yNew] != null) {
      //Log.d(TAG,"item to capture temp" + xNew + " " + yNew);
      for (int i = 0; i < tempAllFigures.length; i++) {
        if ((tempAllFigures[i] != null) && (tempAllFigures[i].posX == xNew) &&
                (tempAllFigures[i].posY == yNew)) {
          tempAllFigures[i] = null;
          //Log.d(TAG,"found item to del from the board temp, i = " + i);
          break;
        }
      }
      tempfigureOnBoard[xNew][yNew] = null;//capture enemy
      isAlreadyCaptured = true;
      isCapturing = true;
    }
    tempAllFigures[itemIndex].posX = xNew;
    tempAllFigures[itemIndex].posY = yNew;
    tempfigureOnBoard[tempAllFigures[itemIndex].posX][tempAllFigures[itemIndex].posY] = tempAllFigures[itemIndex];//occupy new square
    isAlreadyCaptured = false;
    return isCapturing;
  }

  private void findPossibleMoveTemp(int i) {
    if (tempAllFigures[i] != null) {
      tempAllFigures[i].findPossibleMove(tempfigureOnBoard);
      enemyPossibleMove = tempAllFigures[i].possibleMove;

//      if (tempAllFigures[i].type == Figure.Type.king){
//        int j = 0;
//        while (j < enemyPossibleMove.size()) {
//          if (!isMoveSafety(i, enemyPossibleMove.get(j).x, enemyPossibleMove.get(j).y, tempAllFigures[i].isWhite)){
//            enemyPossibleMove.remove(j);
//          } else {
//            j++;
//          }
//        };
//      }

    }
  }

  protected boolean isCheck(int ind, boolean isWhite) {
    Log.d(TAG, "isCheck start");
    int xi = allFigures[ind].posX;
    int yi = allFigures[ind].posY;
    for (int i = 0; i < allFigures.length; i++) {
      if (allFigures[i] != null && allFigures[i].isWhite != isWhite) {
        findPossibleMove(i);
        for (int k = 0; k < possibleMove.size(); k++) {
          if (possibleMove.get(k).x == xi && possibleMove.get(k).y == yi) {
            Log.d(TAG, "is check = " + true);
            return true;
          }
        }
      }
    }
    Log.d(TAG, "is check = " + false);
    return false;
  }

  protected void createTempAllFiguresOnBoard() {
    Log.d(TAG, "creating temp allFigures ");
    tempfigureOnBoard = new Figure[8][8];//??
    tempAllFigures = new Figure[allFigures.length];//???
    for (int i = 0; i < allFigures.length; i++) {
      if (allFigures[i] == null) {
        tempAllFigures[i] = null;
      } else {
        switch (allFigures[i].type) {
          case rook:
            tempAllFigures[i] = new Rook(allFigures[i].isWhite, allFigures[i].posX, allFigures[i].posY);
            tempAllFigures[i].wasMoved = allFigures[i].wasMoved;
            break;
          case knight:
            tempAllFigures[i] = new Knight(allFigures[i].isWhite, allFigures[i].posX, allFigures[i].posY);
            break;
          case bishop:
            tempAllFigures[i] = new Bishop(allFigures[i].isWhite, allFigures[i].posX, allFigures[i].posY);
            break;
          case queen:
            tempAllFigures[i] = new Queen(allFigures[i].isWhite, allFigures[i].posX, allFigures[i].posY);
            break;
          case king:
            tempAllFigures[i] = new King(allFigures[i].isWhite, allFigures[i].posX, allFigures[i].posY);
            tempAllFigures[i].wasMoved = allFigures[i].wasMoved;
            break;
          case pawn:
            tempAllFigures[i] = new Pawn(allFigures[i].isWhite, allFigures[i].posX, allFigures[i].posY);
            break;
        }
        tempfigureOnBoard[tempAllFigures[i].posX][tempAllFigures[i].posY] = tempAllFigures[i];
      }
    }
  }
}




