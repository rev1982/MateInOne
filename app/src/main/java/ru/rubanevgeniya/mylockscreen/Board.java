package ru.rubanevgeniya.mylockscreen;


import android.util.Log;

import java.util.ArrayList;

public class Board {
  private String startPosition;
  protected static Figure[][] figureOnDesk = new Figure[8][8];
  protected Figure[] allFigures;
  protected ArrayList<Pos> possibleMove;
  protected boolean isAlreadyCaptured = false;
  private static String TAG = "Logs Board = ";
  private static Figure[][] tempFigureOnDesk = new Figure[8][8];
  private Figure[] tempAllFigures;
  private ArrayList<Pos> enemyPossibleMove;


  Board(String startPosition) {
    this.startPosition = startPosition;
  }

  protected void createStartPosition() {
    Log.d(TAG, "creating this startPosition " + startPosition);
    possibleMove = new ArrayList<>();
    allFigures = new Figure[(startPosition.length() - 1) / 4];
    int j = 0;
    boolean isWhite;
    int xPos;
    int yPos;
    for (int i = 0; i < allFigures.length; i++) {
      isWhite = startPosition.charAt(j) == 'w';
      xPos = startPosition.charAt(j + 2) - 1;
      yPos = startPosition.charAt(j + 3) - 1;
      switch (startPosition.charAt(j + 1)) {
        case 'R':
          allFigures[i] = new Rook(isWhite, xPos, yPos, "t", Figure.Type.rook);
          //Log.d(TAG,"rook "+xPos+" "+yPos);
          break;
        case 'N':
          allFigures[i] = new Knight(isWhite, xPos, yPos, "m", Figure.Type.knight);
          //Log.d(TAG,"knight "+xPos+" "+yPos);
          break;
        case 'B':
          allFigures[i] = new Bishop(isWhite, xPos, yPos, "v", Figure.Type.bishop);
          //Log.d(TAG,"bishop "+xPos+" "+yPos);
          break;
        case 'Q':
          allFigures[i] = new Queen(isWhite, xPos, yPos, "w", Figure.Type.queen);
          //Log.d(TAG,"queen " + xPos+" " + yPos);
          break;
        case 'K':
          allFigures[i] = new King(isWhite, xPos, yPos, "l", Figure.Type.king);
          //Log.d(TAG,"king " + xPos + " " + yPos);
          break;
        case 'p':
          allFigures[i] = new Pawn(isWhite, xPos, yPos, "o", Figure.Type.pawn);
          //Log.d(TAG,"pawn "+xPos+" "+yPos);
          break;
        case 'P':
          allFigures[i] = new Pawn(isWhite, xPos, yPos, "o", Figure.Type.pawn);
          //Log.d(TAG,"pawn "+xPos+" "+yPos);
          break;
      }
      // TODO: this if is not needed, just make the following change: startPosition.charAt(ZZZ) - 1 => startPosition.charAt(ZZZ) - '1'
      // TODO: ... in the code above
      if (startPosition.charAt(j + 2) - 1 >= 48) {
        if (allFigures[i] != null) {
          allFigures[i].posX = allFigures[i].posX - 48;
          allFigures[i].posY = allFigures[i].posY - 48;
        }
        figureOnDesk[startPosition.charAt(j + 2) - 1 - 48][startPosition.charAt(j + 3) - 1 - 48] = allFigures[i];
      } else {
        figureOnDesk[startPosition.charAt(j + 2) - 1][startPosition.charAt(j + 3) - 1] = allFigures[i];
      }
      j = j + 4; // TODO: move it to for(..;..; j += 4, i++)
    }
  }

  protected void findPossibleMove(int i) {
    possibleMove.clear();
    if (allFigures[i] != null) {
      allFigures[i].findPossibleMove(figureOnDesk);
      possibleMove = allFigures[i].possibleMove;
//            for (int j = 0; j < possibleMoveY.size(); j++){
//                Log.d(TAG,"poss move for "+ allFigures[i].type + "  x = "+ possibleMoveX.get(j)+ " y = "+possibleMoveX.get(j));
//            }
    }
  }


  protected void deskAndFigureAfterMove(int itemIndex, int possCoorIndex) {
    Log.d(TAG, "itemIndex = " + itemIndex + " possCoorIndex = " + possCoorIndex + " possibleMove.size() = " + possibleMove.size());
    figureOnDesk[allFigures[itemIndex].posX][allFigures[itemIndex].posY] = null;//make square free
    if ((allFigures[itemIndex].type == Figure.Type.king || allFigures[itemIndex].type == Figure.Type.rook)
            && !allFigures[itemIndex].wasMoved) {
      allFigures[itemIndex].wasMoved = true;
    }
    if (!isAlreadyCaptured && figureOnDesk[possibleMove.get(possCoorIndex).x][possibleMove.get(possCoorIndex).y] != null) {
      Log.d(TAG, "item to capture " + possibleMove.get(possCoorIndex).x + " " + possibleMove.get(possCoorIndex).y);
      for (int i = 0; i < allFigures.length; i++) {
        if ((allFigures[i] != null) && (allFigures[i].posX == possibleMove.get(possCoorIndex).x) &&
                (allFigures[i].posY == possibleMove.get(possCoorIndex).y)) {
          allFigures[i] = null;
          Log.d(TAG, "found item to del from the desk, i = " + i);
          break;
        }
      }
      figureOnDesk[possibleMove.get(possCoorIndex).x][possibleMove.get(possCoorIndex).y] = null;//capture enemy
      isAlreadyCaptured = true;
    }

    allFigures[itemIndex].posX = possibleMove.get(possCoorIndex).x;
    allFigures[itemIndex].posY = possibleMove.get(possCoorIndex).y;
    figureOnDesk[allFigures[itemIndex].posX][allFigures[itemIndex].posY] = allFigures[itemIndex];//occupy new square

  }


  protected int deskAndFiguresAfterCastling(int startKingX, int finishKingX, int
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
          figureOnDesk[allFigures[i].posX][allFigures[i].posY] = null;
          allFigures[i].posX = xRookFinish;
          figureOnDesk[allFigures[i].posX][allFigures[i].posY] = allFigures[i];
          return i;
        }
      }
    }
    return -1;
  }

  protected void deskAndFiguresAfterPawnBecomesAnotherItem(ChessView chessView, Figure.Type newItemType, int index) {
    Log.d(TAG, "start changing, new item =" + newItemType);
    boolean isWhite = allFigures[index].isWhite;
    int x = allFigures[index].posX;
    int y = allFigures[index].posY;
    if (newItemType != null) {
      switch (newItemType) {
        case rook:
          allFigures[index] = new Rook(isWhite, x, y, "\u265C ", newItemType);
          break;
        case knight:
          allFigures[index] = new Knight(isWhite, x, y, "\u265E ", newItemType);
          break;
        case bishop:
          allFigures[index] = new Bishop(isWhite, x, y, "\u265D ", newItemType);
          break;
        case queen:
          allFigures[index] = new Queen(isWhite, x, y, "\u265B ", newItemType);
          break;
      }
      chessView.isNewDeskReady = true;
    }
  }

  protected String currentChessPositionAfterMove() {
    StringBuilder builder = new StringBuilder("");
    char itemType = ' ';
    Figure figure;
    char itemColor;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        figure = figureOnDesk[i][j];
        if (figure != null) {
          itemColor = figure.isWhite ? 'w' : 'b';
          switch (figure.type) {
            case rook:
              itemType = 'R';
              break;
            case knight:
              itemType = 'N';
              break;
            case bishop:
              itemType = 'B';
              break;
            case queen:
              itemType = 'Q';
              break;
            case king:
              itemType = 'K';
              break;
            case pawn:
              itemType = 'P';
              break;
          }
          builder.append(itemColor);
          builder.append(itemType);
          builder.append(figure.posX + 1);
          builder.append(figure.posY + 1);
        }
      }
    }
    return builder.toString();
  }

  protected int findKingIndex() {
    int kingIndex = -1;
    boolean isEnemyKingWhite = startPosition.charAt(startPosition.length() - 1) == 'b';
    for (int i = 0; i < allFigures.length; i++) {
      if (allFigures[i] != null && allFigures[i].type == Figure.Type.king && allFigures[i].isWhite == isEnemyKingWhite) {
        kingIndex = i;
        break;
      }
    }
    return kingIndex;
  }

  protected int[] findSafetyMove(int ind, boolean isWhite) {
    Log.d(TAG, "start findSafetyMove for " + allFigures[ind].type + "isWhite = " + isWhite);
    int[] safetyMove = null;
    findPossibleMove(ind);
    ArrayList<Pos> kingPossibleMove = possibleMove;
    int xi;
    int yi;
    boolean isSafetyMove = false;
    for (int j = 0; j < kingPossibleMove.size(); j++) {
      xi = kingPossibleMove.get(j).x;
      yi = kingPossibleMove.get(j).y;
      createTempAllFiguresAndFigureOnDesk();
      deskAndFigureAfterMoveTemp(tempFigureOnDesk, tempAllFigures, ind, xi, yi);
      for (int i = 0; i < tempAllFigures.length; i++) {
        if (i == 0) {
          isSafetyMove = true;
        }
        if (tempAllFigures[i] != null && tempAllFigures[i].isWhite != isWhite && isSafetyMove) {
          findPossibleMoveTemp(i);
          for (int k = 0; k < enemyPossibleMove.size(); k++) {
            if (enemyPossibleMove.get(k).x == xi && enemyPossibleMove.get(k).y == yi) {
              isSafetyMove = false;
              k = enemyPossibleMove.size();
            }
          }
        }
        if (!isSafetyMove) {
          i = tempAllFigures.length;
        }
      }
      if (isSafetyMove) {
        Log.d(TAG, "found safety move  x = " + xi + " y = " + yi + " for " + allFigures[ind].type +
                "  old position is x = " + allFigures[ind].posX + " y =" + allFigures[ind].posY);
        safetyMove = new int[2];
        safetyMove[0] = xi;
        safetyMove[1] = yi;
        j = kingPossibleMove.size();
      } else {
        Log.d(TAG, "found no safety move   for " + allFigures[ind].type +
                "  old position is x = " + allFigures[ind].posX + " y =" + allFigures[ind].posY);
      }
    }
    return safetyMove;
  }

  protected int[] findArbitrarySafetyMove(boolean isWhite) { //for this color
    int[] safetyMove;
    int[] safetyMoveWithInd = null;
    for (int i = 0; i < allFigures.length; i++) {
      if (allFigures[i] != null && allFigures[i].isWhite == isWhite) {
        safetyMove = findSafetyMove(i, isWhite);
        if (safetyMove != null) {
          safetyMoveWithInd = new int[3];
          safetyMoveWithInd[0] = safetyMove[0];
          safetyMoveWithInd[1] = safetyMove[1];
          safetyMoveWithInd[2] = i;
          i = allFigures.length;
        }
      }
    }
    return safetyMoveWithInd;
  }

  private void deskAndFigureAfterMoveTemp(Figure[][] tempFigureOnDesk, Figure[] tempAllFigures,
                                          int itemIndex, int xNew, int yNew) {

    tempFigureOnDesk[tempAllFigures[itemIndex].posX][tempAllFigures[itemIndex].posY] = null;//make square free
    if ((tempAllFigures[itemIndex].type == Figure.Type.king || tempAllFigures[itemIndex].type == Figure.Type.rook)
            && !tempAllFigures[itemIndex].wasMoved) {
      tempAllFigures[itemIndex].wasMoved = true;
    }
    if (!isAlreadyCaptured && tempFigureOnDesk[xNew][yNew] != null) {
      //Log.d(TAG,"item to capture temp" + xNew + " " + yNew);
      for (int i = 0; i < tempAllFigures.length; i++) {
        if ((tempAllFigures[i] != null) && (tempAllFigures[i].posX == xNew) &&
                (tempAllFigures[i].posY == yNew)) {
          tempAllFigures[i] = null;
          //Log.d(TAG,"found item to del from the desk temp, i = " + i);
          break;
        }
      }
      tempFigureOnDesk[xNew][yNew] = null;//capture enemy
      isAlreadyCaptured = true;
    }
    tempAllFigures[itemIndex].posX = xNew;
    tempAllFigures[itemIndex].posY = yNew;
    tempFigureOnDesk[tempAllFigures[itemIndex].posX][tempAllFigures[itemIndex].posY] = tempAllFigures[itemIndex];//occupy new square
    isAlreadyCaptured = false;
  }


  private void findPossibleMoveTemp(int i) {
    if (tempAllFigures[i] != null) {
      tempAllFigures[i].findPossibleMove(tempFigureOnDesk);
      enemyPossibleMove = tempAllFigures[i].possibleMove;
    }
  }

  protected boolean isCheck(int ind, boolean isWhite) {
    Log.d(TAG, "isCheck start");
    int xi = allFigures[ind].posX;
    int yi = allFigures[ind].posY;
    boolean isCheck = false;
    for (int i = 0; i < allFigures.length; i++) {
      if (allFigures[i] != null && allFigures[i].isWhite != isWhite && !isCheck) {
        findPossibleMove(i);
        for (int k = 0; k < possibleMove.size(); k++) {
          if (possibleMove.get(k).x == xi && possibleMove.get(k).y == yi) {
            isCheck = true;
            k = possibleMove.size();
          }
        }
      }
      if (isCheck) {
        i = allFigures.length;
      }
    }
    Log.d(TAG, "is check = " + isCheck);
    return isCheck;
  }

  protected void createTempAllFiguresAndFigureOnDesk() {
    Log.d(TAG, "creating temp allFigures ");
    tempAllFigures = new Figure[allFigures.length];
    for (int i = 0; i < allFigures.length; i++) {
      if (allFigures[i] == null) {
        tempAllFigures[i] = null;
      } else {
        switch (allFigures[i].type) {
          case rook:
            tempAllFigures[i] = new Rook(allFigures[i].isWhite, allFigures[i].posX, allFigures[i].posY, allFigures[i].image, allFigures[i].type);
            break;
          case knight:
            tempAllFigures[i] = new Knight(allFigures[i].isWhite, allFigures[i].posX, allFigures[i].posY, allFigures[i].image, allFigures[i].type);
            break;
          case bishop:
            tempAllFigures[i] = new Bishop(allFigures[i].isWhite, allFigures[i].posX, allFigures[i].posY, allFigures[i].image, allFigures[i].type);
            break;
          case queen:
            tempAllFigures[i] = new Queen(allFigures[i].isWhite, allFigures[i].posX, allFigures[i].posY, allFigures[i].image, allFigures[i].type);
            break;
          case king:
            tempAllFigures[i] = new King(allFigures[i].isWhite, allFigures[i].posX, allFigures[i].posY, allFigures[i].image, allFigures[i].type);
            break;
          case pawn:
            tempAllFigures[i] = new Pawn(allFigures[i].isWhite, allFigures[i].posX, allFigures[i].posY, allFigures[i].image, allFigures[i].type);
            break;
        }
        if (tempAllFigures[i].posX >= 48) {
          tempAllFigures[i].posX = tempAllFigures[i].posX - 48;
          tempAllFigures[i].posY = tempAllFigures[i].posY - 48;
        }
        tempFigureOnDesk[tempAllFigures[i].posX][tempAllFigures[i].posY] = tempAllFigures[i];
      }
    }
  }

}




