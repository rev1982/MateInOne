package ru.rubanevgeniya.mylockscreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class BoardView extends View {

  private ColorFilter colorFilter;
  private Paint paint = new Paint();
  private Paint paintWithColorFilter = new Paint();
  private float squareSize;
  private static float sideSize;
  private int DELTA;
  private int buttonsSize;
  protected Context context;
  private Rect rect = new Rect();
  private Rect rectBitmap = new Rect();
  protected static Square[][] squareArray = new Square[8][8];
  private Rect rect1 = new Rect();
  private Paint iconPaint = new Paint();
  private int iconHeight = 0;
  private int iconWidth = 0;

  public BoardView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    float[] cmData = new float[]{
            0.3f, 0.59f, 0.11f, 0, 0,
            0.3f, 0.59f, 0.11f, 0, 0,
            0.3f, 0.59f, 0.11f, 0, 0,
            0, 0, 0, 1, 0,};
    ColorMatrix colorMatrix = new ColorMatrix(cmData);
    colorFilter = new ColorMatrixColorFilter(colorMatrix);
  }


  @Override
  protected void onDraw(Canvas canvas) {
    printBoard(canvas);
    printUnlock(canvas);
  }


  private void printUnlock(Canvas canvas) {

    paint.setColor(getResources().getColor(R.color.mybg2));
    canvas.drawRect(0, sideSize, sideSize, canvas.getHeight(), paint);
    paint.setColor(getResources().getColor(R.color.mybg6));
    canvas.drawRect(0, sideSize, sideSize, sideSize + sideSize / 10, paint);

    paint.setColor(getResources().getColor(R.color.mybg4));
    canvas.drawRect(0, sideSize + sideSize / 10, sideSize, sideSize + sideSize / 8 + sideSize / 20 + sideSize / 50 + sideSize / 40, paint);

    paint.setColor(getResources().getColor(R.color.mybg2));
    canvas.drawRect(0, 0, sideSize / DELTA / 2, sideSize, paint);
    canvas.drawRect(sideSize / DELTA / 2, 0, sideSize, sideSize / DELTA / 2, paint);
    canvas.drawRect(sideSize - sideSize / DELTA / 2, sideSize / DELTA / 2, sideSize, sideSize, paint);
    canvas.drawRect(0, sideSize - sideSize / DELTA / 2, sideSize, sideSize, paint);

    float xUnlockLeft = sideSize / 25;
    float yUnlockTop = sideSize + sideSize / 4;
    printBitmapWithColorFilter(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.button7),
            xUnlockLeft, yUnlockTop, xUnlockLeft + buttonsSize, yUnlockTop + buttonsSize);

    float xInfoLeft = xUnlockLeft + buttonsSize + buttonsSize / 7;
    printBitmapWithColorFilter(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.button7),
            xInfoLeft, yUnlockTop, xInfoLeft + buttonsSize, yUnlockTop + buttonsSize);

    Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/fontawesome.woff");
    iconPaint.setTypeface(typeface);
    iconPaint.setColor(getResources().getColor(R.color.mybg9));
    iconPaint.setTextSize(findFontSizeForIcon("\uF13E"));
    canvas.drawText("\uF13E", xUnlockLeft + (buttonsSize - iconWidth) / 2, yUnlockTop + buttonsSize / 2 + iconHeight / 2, iconPaint);
    iconPaint.setTextSize(findFontSizeForIcon("\uF129"));
    canvas.drawText("\uF129", xInfoLeft + (buttonsSize - iconWidth) / 2, yUnlockTop + buttonsSize / 2 + iconHeight / 2, iconPaint);
  }

  private void printBitmapWithColorFilter(Canvas canvas, Bitmap bitmap, float left, float top, float right, float bottom) {
    rectBitmap.top = (int) (top);
    rectBitmap.left = (int) left;
    rectBitmap.right = (int) right;
    rectBitmap.bottom = (int) bottom;

    rect.top = 0;
    rect.left = 0;
    rect.right = bitmap.getWidth();
    rect.bottom = bitmap.getHeight();

    paintWithColorFilter.setColorFilter(colorFilter);
    canvas.drawBitmap(bitmap, rect, rectBitmap, paintWithColorFilter);

  }


  private void printBoard(Canvas canvas) {

    sideSize = Math.min(canvas.getWidth(), canvas.getHeight());
    buttonsSize = (int) (sideSize / 6.8f);//7
    DELTA = (int) (sideSize / (67.5));
    squareSize = (sideSize - sideSize / DELTA) / 8;
    int colorLight = getResources().getColor(R.color.mybg3);
    int colorDark = getResources().getColor(R.color.mybg7);
    printCells(colorDark, colorLight, canvas);

  }

  private void printCells(int colorDark, int colorLight, Canvas canvas) {
    Square square;

    for (int i = 1; i < 9; i++) {
      if ((i) % 2 == 0) {
        paint.setColor(colorDark);
      } else {
        paint.setColor(colorLight);
      }
      for (int j = 1; j < 9; j++) {

        float left = sideSize / DELTA / 2 + squareSize * (i - 1);
        float top = sideSize / DELTA / 2 + squareSize * (j - 1);
        float right = sideSize / DELTA / 2 + squareSize * i;
        float bottom = sideSize / DELTA / 2 + squareSize * j;

        rectBitmap.top = (int) top;
        rectBitmap.left = (int) left;
        rectBitmap.bottom = (int) bottom;
        rectBitmap.right = (int) right;

        square = new Square(paint.getColor(), i - 1, 8 - j, left, top, right, bottom);
        squareArray[i - 1][8 - j] = square;
        canvas.drawRect(left, top, right, bottom, paint);

        if ((i + j) % 2 == 0) {
          paint.setColor(colorDark);
        } else {
          paint.setColor(colorLight);
        }
      }
    }
  }

  private int findFontSizeForIcon(String icon) {
    int iconSize = 4 * (int) (sideSize / 36);
    findIconSize(iconSize, icon);
    if (Math.max(iconHeight, iconWidth) > 0.4 * buttonsSize) {
      do {
        iconSize = iconSize - 1;
        findIconSize(iconSize, icon);
      } while (Math.max(iconHeight, iconWidth) > 0.4 * buttonsSize);
    }
    return iconSize;
  }

  private void findIconSize(int iconSize, String icon) {
    iconPaint.setTextSize(iconSize);
    iconPaint.getTextBounds(icon, 0, 1, rect1);
    iconHeight = rect1.bottom - rect1.top;
    iconWidth = rect1.right - rect1.left;
  }


}
