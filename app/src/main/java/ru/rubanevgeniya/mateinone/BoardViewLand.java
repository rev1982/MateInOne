package ru.rubanevgeniya.mateinone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class BoardViewLand extends BoardView {

  public BoardViewLand(Context context, AttributeSet attrs) {
    super(context, attrs);
    isDrawBoardFirst = true;
    colorLight = getResources().getColor(R.color.mybg3);
    colorDark = getResources().getColor(R.color.mybg7);
    this.context = context;
    Typeface typeface = LockScreenApp.typeface;
    iconPaint.setTypeface(typeface);
    iconPaint.setColor(getResources().getColor(R.color.mybg9));
  }

  @Override
  protected void onDraw(Canvas canvas) {
    drawBoard(canvas);
    drawUnlock(canvas);
  }

  private void drawUnlock(Canvas canvas) {
    paint.setColor(color1);
    canvas.drawRect(sideSize, 0, canvas.getWidth(), sideSize, paint);
    paint.setColor(color4);
    canvas.drawRect(0, 0, sideSize / DELTA / 2, sideSize, paint);
    canvas.drawRect(sideSize / DELTA / 2, 0, sideSize, sideSize / DELTA / 2, paint);
    canvas.drawRect(sideSize - sideSize / DELTA / 2, sideSize / DELTA / 2, sideSize, sideSize, paint);
    canvas.drawRect(0, sideSize - sideSize / DELTA / 2, sideSize, sideSize, paint);
    paint.setColor(color6);
    canvas.drawRect(sideSize, 0, canvas.getWidth(), sideSize / 3.7f - squareSize, paint);
    paint.setColor(color3);
    float y = sideSize / 3 + sideSize / 20;
    canvas.drawRect(sideSize, sideSize / 3.7f - squareSize, canvas.getWidth(), y, paint);
    paint.setColor(color2);
    canvas.drawRect(sideSize, y, canvas.getWidth(), sideSize, paint);

    float delta = (longSide - sideSize - 4 * buttonsSize) / 5;
    float xSettingLeft = sideSize + delta;
    float ySettingTop = sideSize / 2 + sideSize / 4;
    printBitmapWithColorFilter(canvas, bitmap,
            xSettingLeft, ySettingTop, xSettingLeft + buttonsSize, ySettingTop + buttonsSize);
    float xInfoLeft = xSettingLeft + buttonsSize + delta;
    float xUnlockLeft = xInfoLeft + buttonsSize + delta;
    printBitmapWithColorFilter(canvas, bitmap,
            xInfoLeft, ySettingTop, xInfoLeft + buttonsSize, ySettingTop + buttonsSize);
    iconPaint.setTextSize(textSize1);
    canvas.drawText("\uF013", xSettingLeft + (buttonsSize - iconWidth1) / 2, ySettingTop + buttonsSize / 2.2f + iconHeight1 / 2, iconPaint);
    iconPaint.setTextSize(textSize2);
    canvas.drawText("\uF129", xInfoLeft + (buttonsSize - iconWidth2) / 2, ySettingTop + buttonsSize / 2 + iconHeight2 / 2, iconPaint);
    if (lockScreenApp.isLockScreen) {
      printBitmapWithColorFilter(canvas, bitmap,
              xUnlockLeft, ySettingTop, xUnlockLeft + buttonsSize, ySettingTop + buttonsSize);
      canvas.drawText("\uF13E", xUnlockLeft + (buttonsSize - iconWidth3) / 2, ySettingTop + buttonsSize / 2 + iconHeight3 / 2, iconPaint);
    }
  }
}