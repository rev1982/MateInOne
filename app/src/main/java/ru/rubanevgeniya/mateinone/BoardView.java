package ru.rubanevgeniya.mateinone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class BoardView extends View {

  protected static ColorFilter colorFilter;
  protected Paint paint = new Paint();
  protected Paint paintWithColorFilter = new Paint();
  protected static float squareSize;
  protected static float sideSize;
  protected static int DELTA;
  protected static int buttonsSize;
  protected Context context;
  protected Rect rect = new Rect();
  protected Rect rectBitmap = new Rect();
  protected static Square[][] squareArray = new Square[8][8];
  protected static Rect rect1 = new Rect();
  protected static Paint iconPaint = new Paint();
  protected static int iconHeight = 0;
  protected static int iconWidth = 0;
  protected static ColorMatrix colorMatrix;
  protected static int colorLight;
  protected static int colorDark;
  protected boolean isDrawBoardFirst;
  protected boolean isDrawUnlockFirst;
  protected static Bitmap bitmap;
  protected static int textSize1;
  protected static int textSize2;
  protected static int textSize3;
  protected static int iconWidth1;
  protected static int iconWidth2;
  protected static int iconWidth3;
  protected static int iconHeight1;
  protected static int iconHeight2;
  protected static int iconHeight3;
  protected int color1;
  protected int color2;
  protected int color3;
  protected int color4;
  protected int color6;
  protected static float longSide;
  protected LockScreenApp lockScreenApp;

  static {
    float[] cmData = new float[]{
            0.3f, 0.59f, 0.11f, 0, 0,
            0.3f, 0.59f, 0.11f, 0, 0,
            0.3f, 0.59f, 0.11f, 0, 0,
            0, 0, 0, 1, 0,};
    colorMatrix = new ColorMatrix(cmData);
    colorFilter = new ColorMatrixColorFilter(colorMatrix);
  }

  public BoardView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    isDrawBoardFirst = true;
    isDrawUnlockFirst = true;
    colorLight = getResources().getColor(R.color.mybg3);
    colorDark = getResources().getColor(R.color.mybg7);
    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.button7);
    iconPaint.setTypeface(LockScreenApp.typeface);
    iconPaint.setColor(getResources().getColor(R.color.mybg9));
    color1 = getResources().getColor(R.color.mybg1);
    color2 = getResources().getColor(R.color.mybg2);
    color3 = getResources().getColor(R.color.mybg3);
    color4 = getResources().getColor(R.color.mybg4);
    color6 = getResources().getColor(R.color.mybg6);
    lockScreenApp = (LockScreenApp) getContext();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    drawBoard(canvas);
    drawUnlock(canvas);
  }

  private void drawUnlock(Canvas canvas) {
    float xDelta = (sideSize - 5 * buttonsSize) / 6;
    float xUnlockLeft = xDelta;
    float yUnlockTop = sideSize + sideSize / 4;
    float xSettingLeft = xUnlockLeft + xDelta + buttonsSize;
    float ySettingTop = yUnlockTop;
    float xInfoLeft = xSettingLeft + buttonsSize + xDelta;

    paint.setColor(color2);
    canvas.drawRect(0, sideSize, sideSize, canvas.getHeight(), paint);
    paint.setColor(color6);
    canvas.drawRect(0, sideSize, sideSize, sideSize + sideSize / 10, paint);
    paint.setColor(color4);
    canvas.drawRect(0, sideSize + sideSize / 10, sideSize, sideSize + sideSize / 8 + sideSize / 20 + sideSize / 50 + sideSize / 40, paint);
    paint.setColor(color2);
    canvas.drawRect(0, 0, sideSize / DELTA / 2, sideSize, paint);
    canvas.drawRect(sideSize / DELTA / 2, 0, sideSize, sideSize / DELTA / 2, paint);
    canvas.drawRect(sideSize - sideSize / DELTA / 2, sideSize / DELTA / 2, sideSize, sideSize, paint);
    canvas.drawRect(0, sideSize - sideSize / DELTA / 2, sideSize, sideSize, paint);

    printBitmapWithColorFilter(canvas, bitmap,
            xSettingLeft, ySettingTop, xSettingLeft + buttonsSize, ySettingTop + buttonsSize);
    printBitmapWithColorFilter(canvas, bitmap,
            xInfoLeft, ySettingTop, xInfoLeft + buttonsSize, ySettingTop + buttonsSize);
    iconPaint.setTextSize(textSize1);
    canvas.drawText("\uF013", xSettingLeft + (buttonsSize - iconWidth1) / 2, ySettingTop + buttonsSize / 2.2f + iconHeight1 / 2, iconPaint);
    iconPaint.setTextSize(textSize2);
    canvas.drawText("\uF129", xInfoLeft + (buttonsSize - iconWidth2) / 2, ySettingTop + buttonsSize / 2 + iconHeight2 / 2, iconPaint);
    if (lockScreenApp.isLockScreen) {
      printBitmapWithColorFilter(canvas, bitmap,
              xUnlockLeft, yUnlockTop, xUnlockLeft + buttonsSize, yUnlockTop + buttonsSize);
      iconPaint.setTextSize(textSize3);
      canvas.drawText("\uF13E", xUnlockLeft + (buttonsSize - iconWidth3) / 2, yUnlockTop + buttonsSize / 2 + iconHeight3 / 2, iconPaint);
    }
  }

  protected void printBitmapWithColorFilter(Canvas canvas, Bitmap bitmap, float left, float top, float right, float bottom) {
    rectBitmap.set((int) left, (int) (top), (int) right, (int) bottom);
    rect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
    paintWithColorFilter.setColorFilter(colorFilter);
    canvas.drawBitmap(bitmap, rect, rectBitmap, paintWithColorFilter);
  }

  protected void drawBoard(Canvas canvas) {
    if (isDrawBoardFirst) {
      buttonsSize = (int) (sideSize / 6.8f);
      DELTA = LockScreenApp.DELTA;
      squareSize = (sideSize - sideSize / DELTA) / 8;
      textSize1 = findFontSizeForIcon("\uF013");
      iconWidth1 = iconWidth;
      iconHeight1 = iconHeight;
      textSize2 = findFontSizeForIcon("\uF129");
      iconWidth2 = iconWidth;
      iconHeight2 = iconHeight;
      textSize3 = findFontSizeForIcon("\uF13E");
      iconWidth3 = iconWidth;
      iconHeight3 = iconHeight;
      isDrawBoardFirst = false;
    }
    drawCells(colorDark, colorLight, canvas);
  }

  protected void drawCells(int colorDark, int colorLight, Canvas canvas) {
    Square square;
    for (int i = 1; i < 9; i++) {
      paint.setColor((i) % 2 == 0 ? colorDark : colorLight);
      for (int j = 1; j < 9; j++) {
        float left = sideSize / DELTA / 2 + squareSize * (i - 1);
        float top = sideSize / DELTA / 2 + squareSize * (j - 1);
        float right = sideSize / DELTA / 2 + squareSize * i;
        float bottom = sideSize / DELTA / 2 + squareSize * j;
        square = new Square(paint.getColor(), i - 1, 8 - j, left, top, right, bottom);
        squareArray[i - 1][8 - j] = square;
        canvas.drawRect(left, top, right, bottom, paint);
        paint.setColor((i + j) % 2 == 0 ? colorDark : colorLight);
      }
    }
  }

  protected static int findFontSizeForIcon(String icon) {
    int iconSize = 4 * (int) (sideSize / 36);
    findIconSize(iconSize, icon);
    do {
      iconSize = iconSize - 1;
      findIconSize(iconSize, icon);
    } while (Math.max(iconHeight, iconWidth) > 0.4 * buttonsSize);
    return iconSize;
  }

  protected static void findIconSize(int iconSize, String icon) {
    iconPaint.setTextSize(iconSize);
    iconPaint.getTextBounds(icon, 0, 1, rect1);
    iconHeight = rect1.bottom - rect1.top;
    iconWidth = rect1.right - rect1.left;
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    sideSize = Math.min(right - left, bottom - top);
    longSide = Math.max(right - left, bottom - top);
  }
}
