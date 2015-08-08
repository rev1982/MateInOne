package ru.rubanevgeniya.mylockscreen;


public class Square {
  protected int color;
  protected int positionX;
  protected int positionY;
  protected float left;
  protected float top;
  protected float right;
  protected float bottom;

  Square(int color, int positionX, int positionY, float left, float top, float right,
         float bottom) {
    this.color = color;
    this.positionX = positionX;
    this.positionY = positionY;
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }
}
