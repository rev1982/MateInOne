package ru.rubanevgeniya.mylockscreen;



public class King extends  Figure {
    public King(char color, int positionX, int positionY, String image, String type) {
        super(color, positionX, positionY, image, type);
    }
    private boolean isShortCastlingPossible = true;
    private boolean isLongCastlingPossible = true;


    @Override
    void findPossibleMove(Figure[][] figureOnDesk) {
        possibleMoveX.clear();
        possibleMoveY.clear();
        //System.out.println("finding possible move for king");
        if(color == 'w'){
            addPosition(figureOnDesk,'b');
            if(isShortCastlingPossible) {
                addShortCastling(figureOnDesk,'w', 'b');
            }
            if(isLongCastlingPossible){
                addLongCastling(figureOnDesk,'w','b');
            }
        } else {
            addPosition(figureOnDesk,'w');
            if(isShortCastlingPossible) {
                addShortCastling(figureOnDesk,'b', 'w');
            }
            if(isLongCastlingPossible){
                addLongCastling(figureOnDesk,'b','w');
            }
        }
    }

    void  addShortCastling(Figure[][] figureOnDesk, char color, char enemyColor){
        int yCoordinate;
        if(color == 'w'){
            yCoordinate = 0;
        } else {
            yCoordinate = 7;
        }

        if((isShortCastlingPossible) && (!wasMoved) //&& (this.color == color)
                && figureOnDesk[7][yCoordinate] != null
                && (figureOnDesk[7][yCoordinate].type.equals("rook"))
                && (figureOnDesk[7][yCoordinate].color == color) && (!figureOnDesk[7][yCoordinate].wasMoved)
                && (figureOnDesk[6][yCoordinate] == null) && (figureOnDesk[5][yCoordinate] == null)){

            int[] kingCellsY = new int[]{yCoordinate,yCoordinate,yCoordinate};
            int[] kingCellsX = new int[]{4,5,6};
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if (!isShortCastlingPossible){
                        break;
                    }else {
                        Figure figure = figureOnDesk[i][j];
                        if (figure != null && figure.color == enemyColor) {
                            figure.findPossibleMove(figureOnDesk);
                            if (figure.possibleMoveX.size() > 0) {
                                for (int k = 0; k < figure.possibleMoveX.size(); k++) {
                                    int xMove = figure.possibleMoveX.get(k);
                                    int yMove = figure.possibleMoveY.get(k);
                                    for (int m = 0; m < kingCellsX.length; m++) {
                                        if (xMove == kingCellsX[m] && yMove == kingCellsY[m]) {
                                            isShortCastlingPossible = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            possibleMoveX.add(6);
            possibleMoveY.add(yCoordinate);
        } else {
            isShortCastlingPossible = false;
        }
    }

    void  addLongCastling(Figure[][] figureOnDesk, char color, char enemyColor){
        int yCoordinate;
        if(color == 'w'){
            yCoordinate = 0;
        } else {
            yCoordinate = 7;
        }
        if((isLongCastlingPossible) && (!wasMoved) //&& (this.color == color)
                && figureOnDesk[0][yCoordinate] != null
                && (figureOnDesk[0][yCoordinate].type.equals("rook"))
                && (figureOnDesk[0][yCoordinate].color == color) && (!figureOnDesk[0][yCoordinate].wasMoved)
                && (figureOnDesk[1][yCoordinate] == null)
                && (figureOnDesk[2][yCoordinate] == null)
                && (figureOnDesk[3][yCoordinate] == null)){

            int[] kingCellsY = new int[]{yCoordinate,yCoordinate,yCoordinate};
            int[] kingCellsX = new int[]{2,3,4};
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if (!isLongCastlingPossible){
                        break;
                    }else {
                        Figure figure = figureOnDesk[i][j];
                        if (figure != null && figure.color == enemyColor) {
                            figure.findPossibleMove(figureOnDesk);
                            if (figure.possibleMoveX.size() > 0) {
                                for (int k = 0; k < figure.possibleMoveX.size(); k++) {
                                    int xMove = figure.possibleMoveX.get(k);
                                    int yMove = figure.possibleMoveY.get(k);
                                    for (int m = 0; m < kingCellsX.length; m++) {
                                        if (xMove == kingCellsX[m] && yMove == kingCellsY[m]) {
                                            isLongCastlingPossible = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            possibleMoveX.add(2);
            possibleMoveY.add(yCoordinate);
        } else {
            isLongCastlingPossible = false;
        }
    }


    void addPosition(Figure[][] figureOnDesk, char color){
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if((positionY+i > -1) && (positionY+i < 8) && (positionX+j > -1) && (positionX+j < 8)
                        && (figureOnDesk[positionX+j][positionY+i] == null
                        || ( figureOnDesk[positionX+j][positionY+i].color == color //&&
                        //(!figureOnDesk[positionX+j][positionY+i].type.equals("king"))
                ))){
                    possibleMoveY.add(positionY+i);
                    possibleMoveX.add(positionX+j);
                }
            }
        }
    }
}
