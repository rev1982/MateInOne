package ru.rubanevgeniya.mylockscreen;

/**
 * Created by rey on 3/23/2015.
 */
public class Knight extends  Figure {
    public Knight(char color, int positionX, int positionY, String image, String type) {
        super(color, positionX, positionY, image, type);
    }
    @Override
    void findPossibleMove(Figure[][] figureOnDesk){
        possibleMoveX.clear();
        possibleMoveY.clear();
        //System.out.println("finding possible move for knight");
        char enemyColor;
        if(color == 'w'){
            enemyColor = 'b';
        } else {
            enemyColor = 'w';
        }
        addPosition(figureOnDesk,positionX-2, positionY-1, enemyColor);
        addPosition(figureOnDesk,positionX-2, positionY+1, enemyColor);
        addPosition(figureOnDesk,positionX+2, positionY-1, enemyColor);
        addPosition(figureOnDesk,positionX+2, positionY+1, enemyColor);
        addPosition(figureOnDesk,positionX+1, positionY+2, enemyColor);
        addPosition(figureOnDesk,positionX-1, positionY+2, enemyColor);
        addPosition(figureOnDesk,positionX-1, positionY-2, enemyColor);
        addPosition(figureOnDesk,positionX+1, positionY-2, enemyColor);
    }

    private  void addPosition(Figure[][] figureOnDesk,int x, int y, char color){
        if((y > -1) && (x > -1) && (y < 8) && (x < 8) && (figureOnDesk[x][y] == null
                || ( figureOnDesk[x][y].color == color //&&
                //(!figureOnDesk[x][y].type.equals("king"))
        ))){
            possibleMoveY.add(y);
            possibleMoveX.add(x);
        }
    }

}
