package ru.rubanevgeniya.mylockscreen;


import android.util.Log;

import java.util.ArrayList;

public class Desk {
    private String startPosition;
    protected static Figure[][] figureOnDesk = new Figure[8][8];
    protected Figure[] allFigures;
    protected ArrayList<Integer> possibleMoveX;
    protected ArrayList<Integer> possibleMoveY;
    protected boolean isAlreadyCaptured = false;
    private static String TAG = "Log = ";



    Desk(String startPosition){
        this.startPosition = startPosition;
    }

    protected void createStartPosition(){
        Log.d(TAG,"creating this startPosition "+startPosition);
        possibleMoveX = new ArrayList<>();
        possibleMoveY = new ArrayList<>();
        allFigures = new  Figure[(startPosition.length()-1)/4];
        int j = 0;
        for (int i = 0 ; i < (startPosition.length()-1)/4; i++){

            if(startPosition.charAt(j) == 'b') {
                switch (startPosition.charAt(j + 1)) {
                    case 'R':
                        allFigures[i] = new Rook(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "t", "rook");
                        //System.out.println("rook "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                    case 'N':
                        allFigures[i] = new Knight(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "m", "knight");
                        //System.out.println("knight "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                    case 'B':
                        allFigures[i] = new Bishop(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "v", "bishop");
                        //System.out.println("bishop "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                    case 'Q':
                        allFigures[i] = new Queen(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "w", "queen");
                        //System.out.println("queen "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                    case 'K':
                        allFigures[i] = new King(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "l", "king");
                        //System.out.println("king "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                    case 'p':
                        allFigures[i] = new Pawn(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "o", "pawn");
                        //System.out.println("pawn "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                    case 'P':
                        allFigures[i] = new Pawn(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "o", "pawn");
                        //System.out.println("pawn "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                }
            } else {
                switch (startPosition.charAt(j + 1)) {
                    case 'R':
                        allFigures[i] = new Rook(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "t", "rook");
                        //System.out.println("rook "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                    case 'N':
                        allFigures[i] = new Knight(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "m", "knight");
                        //System.out.println("knight "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                    case 'B':
                        allFigures[i] = new Bishop(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "v", "bishop");
                        //System.out.println("bishop "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                    case 'Q':
                        allFigures[i] = new Queen(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "w", "queen");
                        //System.out.println("queen "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                    case 'K':
                        allFigures[i] = new King(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "l", "king");
                        //System.out.println("king "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                    case 'p':
                        allFigures[i] = new Pawn(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "o", "pawn");
                        //System.out.println("pawn "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                    case 'P':
                        allFigures[i] = new Pawn(startPosition.charAt(j), startPosition.charAt(j + 2) - 1, startPosition.charAt(j + 3) - 1, "o", "pawn");
                        //System.out.println("pawn "+(startPosition.charAt(j+2)-1)+" "+(startPosition.charAt(j+3)-1));
                        break;
                }
            }

            if(allFigures[i].positionX>=48) {
                allFigures[i].positionX = allFigures[i].positionX - 48;//??
                allFigures[i].positionY = allFigures[i].positionY - 48;//??
            }
            figureOnDesk[startPosition.charAt(j+2)-1-48][startPosition.charAt(j+3)-1-48]= allFigures[i];//????
            j = j + 4;
        }
    }

    protected void findPossibleMove(int i){
        possibleMoveX.clear();
        possibleMoveY.clear();
        if(allFigures[i] != null) {
                allFigures[i].findPossibleMove(figureOnDesk);
                possibleMoveX=allFigures[i].possibleMoveX;
                possibleMoveY=allFigures[i].possibleMoveY;
//            for (int j = 0; j < possibleMoveY.size(); j++){
//                Log.d(TAG,"poss move for "+ allFigures[i].type + "  x = "+ possibleMoveX.get(j)+ " y = "+possibleMoveX.get(j));
//            }
        }
    }


    protected void  deskAndFigureAfterMove(int currentItemIndex, int possibleCoordinateIndex){
        Log.d(TAG,"currentItemIndex = "+currentItemIndex+ " possibleCoordinateIndex = "+possibleCoordinateIndex+" possibleMoveX.size() = "+possibleMoveX.size());

        figureOnDesk[allFigures[currentItemIndex].positionX][allFigures[currentItemIndex].positionY]=null;//make square free
        if((allFigures[currentItemIndex].type.equals("king") || allFigures[currentItemIndex].type.equals("rook"))
                && !allFigures[currentItemIndex].wasMoved ){
            allFigures[currentItemIndex].wasMoved = true;
        }
        if(!isAlreadyCaptured && figureOnDesk[possibleMoveX.get(possibleCoordinateIndex)][possibleMoveY.get(possibleCoordinateIndex)] != null) {
            Log.d(TAG,"item to capture " + possibleMoveX.get(possibleCoordinateIndex) + " " + possibleMoveY.get(possibleCoordinateIndex));
            for (int i = 0; i < allFigures.length; i++) {
                if ((allFigures[i] != null) && (allFigures[i].positionX == possibleMoveX.get(possibleCoordinateIndex)) &&
                        (allFigures[i].positionY == possibleMoveY.get(possibleCoordinateIndex))) {
                    allFigures[i] = null;
                    Log.d(TAG,"found item to del from the desk, i = " + i);
                    break;
                }
            }
            figureOnDesk[possibleMoveX.get(possibleCoordinateIndex)][possibleMoveY.get(possibleCoordinateIndex)] = null;//capture enemy
            isAlreadyCaptured = true;
        }

            allFigures[currentItemIndex].positionX = possibleMoveX.get(possibleCoordinateIndex);
            allFigures[currentItemIndex].positionY = possibleMoveY.get(possibleCoordinateIndex);
            figureOnDesk[allFigures[currentItemIndex].positionX][allFigures[currentItemIndex].positionY] = allFigures[currentItemIndex];//occupy new square

    }


    protected int deskAndFiguresAfterCastling(int startKingX, int finishKingX, int
                                     startKingY){
                if( Math.abs(startKingX - finishKingX) > 1){
            int xRookStart;
            int xRookFinish;
            if(startKingX - finishKingX == 2){
                xRookFinish = 3;
                xRookStart = 0;
            } else {
                xRookFinish = 5;
                xRookStart = 7;
            }
            for (int i = 0; i < allFigures.length; i++){
                if((allFigures[i]!=null)&&(allFigures[i].positionX==xRookStart)
                        &&(allFigures[i].positionY==startKingY)
                        &&(allFigures[i].type.equals("rook"))){
                    figureOnDesk[allFigures[i].positionX][allFigures[i].positionY]=null;
                    allFigures[i].positionX = xRookFinish;
                    figureOnDesk[allFigures[i].positionX][allFigures[i].positionY]=allFigures[i];
                    return i;
                }
            }
        }
        return -1;
    }

    protected void deskAndFiguresAfterPawnBecomesAnotherItem(ChessView chessView,String newItem, int index){
        Log.d(TAG,"start changing, new item =" +newItem);
        char color = allFigures[index].color;
        int x = allFigures[index].positionX;
        int y = allFigures[index].positionY;
        if(newItem!=null) {
            if (newItem.equals("queen")) {
                allFigures[index] = new Queen(color, x, y, "\u265B ", newItem);
            }
            if (newItem.equals("rook")) {
                allFigures[index] = new Rook(color, x, y, "\u265C ", newItem);
            }
            if (newItem.equals("bishop")) {
                allFigures[index] = new Bishop(color, x, y, "\u265D ", newItem);
            }
            if (newItem.equals("knight")) {
                allFigures[index] = new Knight(color, x, y, "\u265E ", newItem);
            }
            chessView.isNewDeskReady = true;
        }
    }

    protected String currentChessPositionAfterMove(){
        String currentPositionAfterMove = "";
        String itemType="";
        Figure figure;
        int x;
        int y;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                figure = figureOnDesk[i][j];
                if (figure != null){
                    if(figure.type.equals("knight")){
                        itemType = "N";
                    } else {
                        itemType = figure.type.substring(0,1).toUpperCase();
                    }
                    x = figure.positionX + 1;
                    y = figure.positionY + 1;
                    currentPositionAfterMove = currentPositionAfterMove + figure.color+itemType+x+y;
                }
            }
        }
        return  currentPositionAfterMove;
    }









    private static Figure[][] tempFigureOnDesk = new Figure[8][8];
    private Figure[] tempAllFigures;
    private ArrayList<Integer> enemyPossibleMoveX;
    private ArrayList<Integer> enemyPossibleMoveY;


    protected int findKingIndex(){
        int kingIndex = -1;
        char kingColor;
        if (startPosition.charAt(startPosition.length() - 1) == 'w') {
            kingColor = 'b';
        } else {
            kingColor = 'w';
        }
        for (int i = 0; i < allFigures.length; i ++){
            if (allFigures[i].type.equals("king") && allFigures[i].color == kingColor){
                kingIndex = i;
                break;
            }
        }
        return  kingIndex;
    }

    protected  int[] findSafetyMove(int ind, char color){
        Log.d(TAG,"start findSafetyMove for "+ allFigures[ind].type + "color = "+color);
        int xOld = allFigures[ind].positionX;
        int yOld = allFigures[ind].positionY;
        int[] safetyMove = null;
        findPossibleMove(ind);
        ArrayList<Integer> kingPossibleMoveX = possibleMoveX;
        ArrayList<Integer> kingPossibleMoveY = possibleMoveY;
        int xi;
        int yi;
        boolean isSafetyMove = false;
        for (int j = 0; j < kingPossibleMoveX.size(); j ++){
            xi = kingPossibleMoveX.get(j);
            yi = kingPossibleMoveY.get(j);
            //Log.d(TAG,"possible move j = "+j+ " x = "+xi+" y = "+yi);
            createTempAllFiguresAndFigureOnDesk();
            deskAndFigureAfterMoveTemp(tempFigureOnDesk, tempAllFigures, ind, xi, yi);
            for (int i = 0; i <tempAllFigures.length; i ++){
                if (i == 0){
                    isSafetyMove = true;
                }
                if (tempAllFigures[i] != null && tempAllFigures[i].color != color && isSafetyMove){
                    findPossibleMoveTemp(i);
                    //Log.d(TAG,"enemyPossibleMoveX.size() = " + enemyPossibleMoveX.size() + " enemy i = "+ i);
                    for (int k = 0 ; k < enemyPossibleMoveX.size(); k++){
                        if (enemyPossibleMoveX.get(k) == xi && enemyPossibleMoveY.get(k) == yi ){
                            isSafetyMove = false;
                            k = enemyPossibleMoveX.size();
                            //break;
                        }
                    }
                }
                if (!isSafetyMove){
                    i = tempAllFigures.length;
                    //break;
                }
            }
            if (isSafetyMove){
                Log.d(TAG,"found safety move  x = "+xi+" y = "+yi +" for "+allFigures[ind].type +
                        "  old position is x = " + allFigures[ind].positionX +" y ="+ allFigures[ind].positionY);
                safetyMove = new int[2];
                safetyMove[0] = xi;
                safetyMove[1] = yi;
                j = kingPossibleMoveX.size();
                //break;
            } else {
                Log.d(TAG,"found no safety move   for "+allFigures[ind].type +
                        "  old position is x = " + allFigures[ind].positionX +" y ="+ allFigures[ind].positionY);
            }
        }
        return  safetyMove;
    }

    protected  int[] findArbitrarySafetyMove(char color){ //for this color
        int[] safetyMove;
        int[] safetyMoveWithInd = null;
        for (int i = 0; i < allFigures.length; i ++){
            if (allFigures[i] != null && allFigures[i].color == color ){
                safetyMove = findSafetyMove(i,color);
                if (safetyMove != null){
                    safetyMoveWithInd = new int[3];
                    safetyMoveWithInd[0] = safetyMove[0];
                    safetyMoveWithInd[1] = safetyMove[1];
                    safetyMoveWithInd[2] = i;
                    i = allFigures.length;
                    //break;
                }
            }
        }
        return  safetyMoveWithInd;
    }

    private void  deskAndFigureAfterMoveTemp(Figure[][] tempFigureOnDesk, Figure[] tempAllFigures, int currentItemIndex, int xNew, int yNew){

        tempFigureOnDesk[tempAllFigures[currentItemIndex].positionX][tempAllFigures[currentItemIndex].positionY]=null;//make square free
        if((tempAllFigures[currentItemIndex].type.equals("king") || tempAllFigures[currentItemIndex].type.equals("rook"))
                && !tempAllFigures[currentItemIndex].wasMoved ){
            tempAllFigures[currentItemIndex].wasMoved = true;
        }
        if(!isAlreadyCaptured && tempFigureOnDesk[xNew][yNew] != null) {
            //Log.d(TAG,"item to capture temp" + xNew + " " + yNew);
            for (int i = 0; i < tempAllFigures.length; i++) {
                if ((tempAllFigures[i] != null) && (tempAllFigures[i].positionX == xNew) &&
                        (tempAllFigures[i].positionY == yNew)) {
                    tempAllFigures[i] = null;
                    //Log.d(TAG,"found item to del from the desk temp, i = " + i);
                    break;
                }
            }
            tempFigureOnDesk[xNew][yNew] = null;//capture enemy
            isAlreadyCaptured = true;
        }

        tempAllFigures[currentItemIndex].positionX = xNew;
        tempAllFigures[currentItemIndex].positionY = yNew;
        tempFigureOnDesk[tempAllFigures[currentItemIndex].positionX][tempAllFigures[currentItemIndex].positionY] = tempAllFigures[currentItemIndex];//occupy new square
        isAlreadyCaptured = false;
    }


    private void findPossibleMoveTemp(int i){
        if(tempAllFigures[i] != null) {
            tempAllFigures[i].findPossibleMove(tempFigureOnDesk);
            enemyPossibleMoveX=tempAllFigures[i].possibleMoveX;
            enemyPossibleMoveY=tempAllFigures[i].possibleMoveY;
        }
//        for (int j = 0; j < enemyPossibleMoveY.size(); j++){
//            Log.d(TAG, "poss move for enemy " + tempAllFigures[i].type + "  x = " + enemyPossibleMoveX.get(j) + " y = " + enemyPossibleMoveY.get(j));
//        }
    }

    protected  boolean isCheck(int ind,  char color){
        Log.d(TAG,"isCheck start");
        int xi = allFigures[ind].positionX;
        int yi = allFigures[ind].positionY;
        boolean isCheck = false;
            for (int i = 0; i < allFigures.length; i ++){
                if (allFigures[i] != null && allFigures[i].color != color && !isCheck){
                    findPossibleMove(i);
                    for (int k = 0 ; k < possibleMoveX.size(); k++){
                        if (possibleMoveX.get(k) == xi && possibleMoveY.get(k) == yi ){
                            isCheck = true;
                            k = possibleMoveX.size();
                            //break;
                        }
                    }
                }
                if (isCheck){
                    //break;
                    i = allFigures.length;
                }
            }
        Log.d(TAG,"is check = "+ isCheck);
        return  isCheck;
    }

    protected void createTempAllFiguresAndFigureOnDesk() {
        Log.d(TAG, "creating temp allFigures ");
        tempAllFigures = new Figure[allFigures.length];
        for (int i = 0; i < allFigures.length; i++) {
            if (allFigures[i] == null) {
                tempAllFigures[i] = null;
            } else {
                switch (allFigures[i].type) {
                    case "rook":
                        tempAllFigures[i] = new Rook(allFigures[i].color, allFigures[i].positionX, allFigures[i].positionY, allFigures[i].image, allFigures[i].type);
                        break;
                    case "knight":
                        tempAllFigures[i] = new Knight(allFigures[i].color, allFigures[i].positionX, allFigures[i].positionY, allFigures[i].image, allFigures[i].type);
                        break;
                    case "bishop":
                        tempAllFigures[i] = new Bishop(allFigures[i].color, allFigures[i].positionX, allFigures[i].positionY, allFigures[i].image, allFigures[i].type);
                        break;
                    case "queen":
                        tempAllFigures[i] = new Queen(allFigures[i].color, allFigures[i].positionX, allFigures[i].positionY, allFigures[i].image, allFigures[i].type);
                        break;
                    case "king":
                        tempAllFigures[i] = new King(allFigures[i].color, allFigures[i].positionX, allFigures[i].positionY, allFigures[i].image, allFigures[i].type);
                        break;
                    case "pawn":
                        tempAllFigures[i] = new Pawn(allFigures[i].color, allFigures[i].positionX, allFigures[i].positionY, allFigures[i].image, allFigures[i].type);
                        break;
                }
                if(tempAllFigures[i].positionX>=48) {
                    tempAllFigures[i].positionX = tempAllFigures[i].positionX - 48;//??
                    tempAllFigures[i].positionY = tempAllFigures[i].positionY - 48;//??
                }
                tempFigureOnDesk[tempAllFigures[i].positionX][tempAllFigures[i].positionY]= tempAllFigures[i];//????
            }
        }
        }

}




