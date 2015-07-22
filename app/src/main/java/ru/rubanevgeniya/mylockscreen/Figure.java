package ru.rubanevgeniya.mylockscreen;


import java.util.ArrayList;

public abstract class Figure {
    protected String image;
    protected int positionX;
    protected int positionY;
    protected char color;
    protected String type;
    protected ArrayList<Integer> possibleMoveX;
    protected ArrayList<Integer> possibleMoveY;
    protected boolean wasMoved = false;
    boolean isContinue = true;

    public Figure(char color, int positionX, int positionY, String image,String type){
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;
        this.image = image;
        this.type = type;
        possibleMoveX = new ArrayList<>();
        possibleMoveY = new ArrayList<>();
    }

    void findPossibleMove(Figure[][] figureOnDesk){

    }

}
