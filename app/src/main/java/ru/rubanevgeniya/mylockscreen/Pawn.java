package ru.rubanevgeniya.mylockscreen;


public class Pawn extends Figure {
    public Pawn(char color, int positionX, int positionY, String image, String type) {
        super(color, positionX, positionY, image, type);
    }
    @Override
    void findPossibleMove(Figure[][] figureOnDesk){
        possibleMoveX.clear();
        possibleMoveY.clear();
        if(color=='w') {
            addPosition(figureOnDesk,1, 1, 1,'b');
            addPosition(figureOnDesk,-1, 1, 1,'b');
            if (positionY == 1) {
                addPosition(figureOnDesk,0, 2, 1,'b');
            }
        }
        if(color=='b') {
            addPosition(figureOnDesk,1,1,-1,'w');
            addPosition(figureOnDesk,-1, 1, -1,'w');
            if (positionY == 6) {
                addPosition(figureOnDesk,0, 2, -1,'w');
            }
        }
    }

    private void addPosition(Figure[][] figureOnDesk,int kX, int kY, int signY, char color){
        if((positionY+kY*signY < 8) && (positionY+kY*signY > -1)
                    &&(figureOnDesk[positionX][positionY+kY*signY] == null)){
                possibleMoveY.add(positionY+kY*signY);
                possibleMoveX.add(positionX);
            }
        if((kX!=0) && (positionY+kY*signY < 8) && (positionY+kY*signY > -1) && (positionX+kX < 8)
                        && (positionX+kX > -1)
                && figureOnDesk[positionX+kX][positionY+kY*signY]!= null
                        && figureOnDesk[positionX+kX][positionY+kY*signY].color == color
                        //&&(!figureOnDesk[positionX+kX][positionY+kY*signY].type.equals("king"))
                ){
                    possibleMoveY.add(positionY+kY*signY);
                    possibleMoveX.add(positionX+kX);
                }
            }
}
