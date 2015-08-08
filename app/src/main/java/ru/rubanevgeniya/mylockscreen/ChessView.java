package ru.rubanevgeniya.mylockscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Timer;


public class ChessView extends View {

  public static final int INVALID = -1000;
  private boolean isPortrait;
  private boolean isRightMove;
  private boolean isWrongMove;
  private boolean isItemMoving;
  private int amountOfLevelsToSolve;
  private boolean isPlayingWhite;
  private char whoseTurnToPlay;
  private Paint paintDarkCircles = new Paint();
  private Paint paintLightCircles = new Paint();
  private Paint paint4fSize = new Paint();
  private Paint paint2fSize = new Paint();
  private Paint paint1dot3fSize = new Paint();
  private Paint paint1dot5fSize = new Paint();
  private static Paint paint = new Paint();
  private Paint paintWithColorFilter = new Paint();
  private Paint fontPaint = new Paint();
  private static Paint fontPaintLettersNumbers = new Paint();
  private float squareSize;
  private float sideSize;
  private int fontSize;
  private int DELTA;
  private final String[] LETTERS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
  private String startPosition;
  private int indOfPosition;
  private int indOfPositionForResults;
  private Board board;
  private boolean isInitialChessPositionReady;
  private final static float[] coorInCellsX = new float[8];
  private final static float[] coorInCellsY = new float[8];
  private int buttonsSize;
  private Coordinate[] movingCoor;
  private int itemInd = 0;
  private ArrayList<Pos> moves = new ArrayList<>();
  private boolean drag = false;
  private float dragX = 0;
  private float dragY = 0;
  private LockScreenApp lockScreenApp;
  //private DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
  //private DateFormat dateFormat2 = new SimpleDateFormat("hh:mm");
  protected Timer timer = new Timer();
  private boolean isIlluminatingCells;
  private int numberOfPossibleResolvingAttempts;
  private int numberOfAlreadyResolved;
  private Rect clipBound = new Rect();
  private Rect rect = new Rect();
  private Rect rectBitmap = new Rect();
  private Rect rect1 = new Rect();
  private int chessHeight = 0;
  private int chessWidth = 0;
  private float xUnlockCenter;
  private float yUnlockCenter;
  private float xSettingCenter;
  private float ySettingCenter;
  private float xRefreshLeft;
  private float yRefreshTop;
  private float xNextLeft;
  private float yNextTop;
  private int numberOfAttempt;
  private int numberOfSuccessfulAttempt;
  private int startX;
  private int finishX;
  private int startY;
  private Figure currentFigure;
  protected Figure.Type chosenItem;
  protected boolean isNewItemChosen = false;
  protected boolean isNewBoardReady = false;
  private boolean isDrawingResolving;
  //protected MyTimerTask myTimerTask;
  //private float xAnswerCenter;
  //private float yAnswerCenter;
  //private float xDateCenter;
  //private float yDateCenter;
//  private float xTimeCenter;
//  private float yTimeCenter;
  private int numberOfJustSolvedLevels;
  private boolean isEverythingSolved;
  private boolean isSecondTouch;
  private int indOfPossibleMoveSecondTouch;
  private boolean isDrawingCircles;
  private String text1;
  private String text2;
  private String text3;
  private String text22;
  private String levels1;
  private String level;
  private String levels;
  private String toUnlockSolve;
  private String solved;
  private String blackToMove;
  private String whiteToMove;
  private String rusString;
  private String levels3;
  private String toUnlockSolve0;
  private static String TAG = "Log = ";
  private Paint iconPaint = new Paint();
  private int iconHeight = 0;
  private int iconWidth = 0;
  private int forwardHeight;
  private int forwardWidth;
  private int refreshHeight;
  private int refreshWidth;
  private static final String forward = "\uF061";
  private static final String refresh = "\uF021";
  float xInfoLeft;
  float yInfoTop;
  private boolean isWhiteTurnToPlay;
  private boolean isWorkingWithHandler = false;
  private String title;
  private String ok;
  private Bitmap bitmapButton;
  private int color;
  private String toUnlockTouch;
  private String touch;
  private Paint paintGreen = new Paint();
  private Paint paintRed = new Paint();
  private String fail;
  private float landXCenter;
  private String currentProblemNum;
  private static String kingStr;
  private static String queenStr;
  private static String rookStr;
  private static String bishopStr;
  private static String pawnStr;
  private static String knightStr;
  private static String captureStr;
  private static String mateStr;
  private int indOfCapturedPiece = -1;
  private  static  String stalemate;

  static {
    fontPaintLettersNumbers.setTextAlign(Paint.Align.CENTER);
    fontPaintLettersNumbers.setColor(Color.BLACK);
    paint.setAlpha(150);
  }

  public ChessView(Context context, AttributeSet attrs) {
    super(context, attrs);
    lockScreenApp = (LockScreenApp) getContext();
    isInitialChessPositionReady = false;
    Board.figureOnBoard = new Figure[8][8];
//    myTimerTask = new MyTimerTask();
//    timer.schedule(myTimerTask,
//            60000 - (Calendar.getInstance().getTimeInMillis() % 60000), 30000);
    float[] cmData = new float[]{
            0.3f, 0.59f, 0.11f, 0, 0,
            0.3f, 0.59f, 0.11f, 0, 0,
            0.3f, 0.59f, 0.11f, 0, 0,
            0, 0, 0, 1, 0,};
    ColorMatrix colorMatrix = new ColorMatrix(cmData);
    paintWithColorFilter.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    numberOfPossibleResolvingAttempts = LockScreenReceiver.loadOuterInfoInt("amountOfAttemptsToResolve3", getContext());
    isIlluminatingCells = !LockScreenReceiver.loadOuterInfoBoolean("isNoIlluminating", getContext());
    amountOfLevelsToSolve = LockScreenReceiver.loadOuterInfoInt("amountOfLevelsToSolve3", getContext());
    numberOfAlreadyResolved = lockScreenApp.loadInfoInt("alreadyResolved");
    numberOfAttempt = lockScreenApp.loadInfoInt("numberOfAttempt2");
    numberOfSuccessfulAttempt = lockScreenApp.loadInfoInt("numberOfSuccessfulAttempt");
    numberOfJustSolvedLevels = lockScreenApp.loadInfoInt("amountOfJustSolvedLevels");

    paintDarkCircles.setAlpha(100);
    paintLightCircles.setColor(getResources().getColor(R.color.mybg2));
    paintDarkCircles.setColor(getResources().getColor(R.color.mybg4));
    iconPaint.setTypeface(LockScreenApp.typeface);//Typeface.createFromAsset(getContext().getAssets(), "fonts/fontawesome.woff")
    color = getResources().getColor(R.color.mybg9);
    iconPaint.setColor(color);
    fontPaintLettersNumbers.setColor(getResources().getColor(R.color.mybg10));
    setColorStyleAlign(paint2fSize);
    setColorStyleAlign(paint4fSize);
    setColorStyleAlign(paint2fSize);
    setColorStyleAlign(paint1dot5fSize);
    setColorStyleAlign(paint1dot3fSize);
    text1 = getResources().getString(R.string.NumberOfLevels) + " " + ChessPositions.allStartPositions.size();
    text2 = getResources().getString(R.string.NumberOfSolvedLevels);
    text3 = getResources().getString(R.string.NumberOfAttempts);
    text22 = getResources().getString(R.string.andCheckmate);
    level = getResources().getString(R.string.level);
    levels = getResources().getString(R.string.levels);
    toUnlockSolve = getResources().getString(R.string.toUnlockSolve);
    solved = getResources().getString(R.string.solved);
    blackToMove = getResources().getString(R.string.blackToMove);
    whiteToMove = getResources().getString(R.string.whiteToMove);
    rusString = getResources().getString(R.string.rusString);
    levels3 = getResources().getString(R.string.levels3);
    toUnlockSolve0 = getResources().getString(R.string.toUnlockSolve0);
    fontPaint.setTextAlign(Paint.Align.CENTER);
    fontPaint.setTypeface(Typefaces.get(context, "fonts/Chess-7.TTF"));//Typeface.createFromAsset(getContext().getAssets(), "fonts/Chess-7.TTF")
    title = getResources().getString(R.string.Results);
    ok = getResources().getString(R.string.Ok);
    bitmapButton = BitmapFactory.decodeResource(getResources(), R.drawable.button7);
    toUnlockTouch = getResources().getString(R.string.toUnlockTouch);
    touch = getResources().getString(R.string.touch);
    paintGreen.setColor(getResources().getColor(R.color.green1));
    paintRed.setColor(getResources().getColor(R.color.red2));
    fail = getResources().getString(R.string.fail);
    currentProblemNum = getResources().getString(R.string.currNum);
    kingStr = getResources().getString(R.string.King);
    queenStr = getResources().getString(R.string.Queen);
    bishopStr = getResources().getString(R.string.Bishop);
    rookStr = getResources().getString(R.string.Rook);
    knightStr = getResources().getString(R.string.Knight);
    pawnStr = getResources().getString(R.string.Pawn);
    captureStr = getResources().getString(R.string.capture);
    mateStr = getResources().getString(R.string.mate);
    stalemate = getResources().getString(R.string.stalemate);

  }

  @Override
  protected void onDraw(Canvas canvas) {
    //Log.d(TAG,"onDraw +++");
    if (!isInitialChessPositionReady) {
      findStartPosition(canvas);
    } else {
      canvas.getClipBounds(clipBound);
    }
    drawLettersAndNumbers(canvas);
    //drawDateAndTime(canvas);
    drawChessPosition(canvas);
    if (isRightMove) {
      drawAfterMove(canvas, true);
    } else if (isWrongMove) {
      drawAfterMove(canvas, false);
    }
  }

  private void setColorStyleAlign(Paint paint) {
    paint.setColor(color);
    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    paint.setTextAlign(Paint.Align.CENTER);
  }

  private void findCoordinatesInCells() {
    for (int i = 0; i < 8; i++) {
      int i1 = i - 8 * (i / 8);
      if (isPlayingWhite) {
        coorInCellsX[i] = sideSize / DELTA / 2 + squareSize / 2 + squareSize * (i1);
        coorInCellsY[7 - i] = sideSize / DELTA / 2 + squareSize * (i) + squareSize / 2 + squareSize / (3.1f) + squareSize / 12;
      } else {
        coorInCellsY[i] = sideSize / DELTA / 2 + squareSize * (i) + squareSize / 2 + squareSize / (3.1f) + squareSize / 12;
        coorInCellsX[7 - i] = sideSize / DELTA / 2 + squareSize / 2 + squareSize * (i1);
      }
    }
  }

//  private void drawDateAndTime(Canvas canvas) {
//    canvas.drawText(dateFormat2.format(Calendar.getInstance().getTime()), xTimeCenter, yTimeCenter, paint4fSize);
//    canvas.drawText(dateFormat1.format(Calendar.getInstance().getTime()), xDateCenter, yDateCenter, paint1dot5fSize);
//  }

  private void drawBitmapWithColorFilter(Canvas canvas, Bitmap bitmap, float left, float top, float right, float bottom) {
    rectBitmap.set((int) left, (int) (top), (int) right, (int) bottom);
    rect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
    canvas.drawBitmap(bitmap, rect, rectBitmap, paintWithColorFilter);
  }

//  private void drawBitmap(Canvas canvas, Bitmap bitmap, float left, float top, float right, float bottom) {
//    rectBitmap.set((int) left, (int) (top), (int) right, (int) bottom);
//    rect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
//    canvas.drawBitmap(bitmap, rect, rectBitmap, paint);
//  }

  private int findFontSizeForChess() {
    int fSize = 4 * fontSize;
    findChessSize(fSize);
    do {
      fSize = fSize - 1;
      findChessSize(fSize);
    } while (Math.max(chessHeight, chessWidth) > 0.8 * squareSize);
    return fSize;
  }

  private void findChessSize(int fSize) {
    fontPaint.setTextSize(fSize);
    int height = 0;
    int width = 0;
    for (int i = 0; i < board.allFigures.length; i++) {
      fontPaint.getTextBounds(board.allFigures[i].image, 0, 1, rect1);
      if (rect1.bottom - rect1.top > height) {
        height = rect1.bottom - rect1.top;
      }
      if (rect1.right - rect1.left > width) {
        width = rect1.right - rect1.left;
      }
    }
    chessHeight = height;
    chessWidth = width;
  }

  private void drawLettersAndNumbers(Canvas canvas) {
    String num;
    for (int i = 0; i < 8; i++) {
      num = Integer.toString(i + 1);
      canvas.drawText(LETTERS[i], coorInCellsX[i], sideSize / DELTA / (2.5f), fontPaintLettersNumbers);
      canvas.drawText(LETTERS[i], coorInCellsX[i], sideSize - sideSize / DELTA / (10f), fontPaintLettersNumbers);
      canvas.drawText(num, sideSize / DELTA / 5, coorInCellsY[i] - squareSize / 3, fontPaintLettersNumbers);
      canvas.drawText(num, sideSize - sideSize / DELTA / 3.5f, coorInCellsY[i] - squareSize / 3, fontPaintLettersNumbers);
    }
  }

  private void findStartPosition(Canvas canvas) {
    sideSize = Math.min(canvas.getHeight(), canvas.getWidth());
    float longSide = Math.max(canvas.getHeight(), canvas.getWidth());
    if (canvas.getHeight() > canvas.getWidth()) {
      isPortrait = true;
    }
    buttonsSize = (int) (sideSize / 6.8f);
    DELTA = (int) (sideSize / (67.5));
    squareSize = (sideSize - sideSize / DELTA) / 8;
    fontSize = (int) (sideSize / 36);
    float xDelta;
    if (isPortrait) {
      xDelta = (sideSize - 5 * buttonsSize)/6; //buttonsSize / 7;
      xUnlockCenter = sideSize / 25 + buttonsSize / 2;
      yUnlockCenter = 5 * sideSize / 4 + buttonsSize / 2;
      xSettingCenter = xUnlockCenter + (buttonsSize + xDelta);
      ySettingCenter = yUnlockCenter;
      //xAnswerCenter = sideSize - sideSize / 4;
      //yAnswerCenter = sideSize + sideSize / 20;
//      xTimeCenter = sideSize * 0.8125f;
//      yTimeCenter = sideSize * 1.308f;
//      xDateCenter = xTimeCenter;
//      yDateCenter = yTimeCenter + sideSize * 0.075f;
      xNextLeft = xSettingCenter + buttonsSize / 2 + xDelta + (buttonsSize + xDelta);
      yNextTop = ySettingCenter - buttonsSize / 2;
      xRefreshLeft = xNextLeft + buttonsSize + xDelta;
      yRefreshTop = yNextTop;
    } else {
      landXCenter = sideSize + (longSide - sideSize) / 2;
      xDelta = (longSide - sideSize - 4 * buttonsSize) / 5;
      xSettingCenter = sideSize + xDelta + buttonsSize / 2;
      ySettingCenter = sideSize * 0.75f + buttonsSize / 2;
      xUnlockCenter = xSettingCenter +  2 * buttonsSize + 2 * xDelta ;
      yUnlockCenter = ySettingCenter;
      //xAnswerCenter = sideSize + sideSize / 4;
      //yAnswerCenter = sideSize / 20;
//      xTimeCenter = landXCenter;
//      yTimeCenter = sideSize * 0.6167f;
//      xDateCenter = xTimeCenter;
//      yDateCenter = yTimeCenter + sideSize / 12;
      xNextLeft = xSettingCenter - buttonsSize / 2;
      yNextTop = ySettingCenter - buttonsSize / 2 - (buttonsSize  + xDelta);
      xRefreshLeft = xNextLeft + buttonsSize + xDelta;
      yRefreshTop = yNextTop;
    }
    xInfoLeft = xSettingCenter + buttonsSize / 2 + xDelta;
    yInfoTop = ySettingCenter - buttonsSize / 2;


    iconPaint.setTextSize(findFontSizeForIcon(forward));
    forwardHeight = iconHeight;
    forwardWidth = iconWidth;
    refreshHeight = iconHeight;
    refreshWidth = iconWidth;
    fontPaintLettersNumbers.setTextSize(fontSize);
    paint2fSize.setTextSize(2 * fontSize);
    paint4fSize.setTextSize(3 * fontSize);
    paint1dot3fSize.setTextSize(1.3f * fontSize);
    paint1dot5fSize.setTextSize(1.5f * fontSize);

    indOfPosition = lockScreenApp.loadInfoInt("indOfPosition");
    if (indOfPosition == INVALID) {
      indOfPosition = 0;
    }
    //Log.d(TAG,"indOfPosition = " + indOfPosition);
    if (indOfPosition >= ChessPositions.allStartPositions.size()) {
      indOfPosition = 0;
      Log.d(TAG, "troubles with indOfPosition, let it be = " + 0);
    }

    //begin check if it was screen rotation after move
    String currentChessPositionAfterMove = lockScreenApp.loadInfo("currentChessPositionAfterMove");
    if (currentChessPositionAfterMove != null) {//after screen rotation
      indOfPosition = lockScreenApp.loadInfoInt("oldIndOfPosition");
      if (lockScreenApp.loadInfoBoolean("isRightMove")) {
        isRightMove = true;
      }
      if (lockScreenApp.loadInfoBoolean("isWrongMove")) {
        isWrongMove = true;
      }
      if (lockScreenApp.loadInfoBoolean("isDrawingResolving")) {
        isDrawingResolving = true;
      }
      startPosition = currentChessPositionAfterMove;
    } else {
      startPosition = ChessPositions.allStartPositions.get(indOfPosition);
    }
    //end check if it was screen rotation after move
    indOfPositionForResults = indOfPosition;

    if (startPosition.charAt(startPosition.length() - 1) == 'b') {
      isPlayingWhite = false;
      whoseTurnToPlay = 'b';
      isWhiteTurnToPlay = false;
    } else {
      isPlayingWhite = true;
      whoseTurnToPlay = 'w';
      isWhiteTurnToPlay = true;
    }
    findCoordinatesInCells();
    fontPaint.setTextSize(4 * fontSize);
  }

  private void drawChessPosition(Canvas canvas) {
    if (!isInitialChessPositionReady) {
      Coordinate coordinate;
      lockScreenApp.handler.removeCallbacksAndMessages(null);
      fontPaint.setTextSize(4 * fontSize);
      board = new Board(startPosition);
      board.createStartPosition();
      isInitialChessPositionReady = true;
      movingCoor = new Coordinate[board.allFigures.length];
      fontPaint.setTextSize(findFontSizeForChess());
      for (int i = 0; i < board.allFigures.length; i++) {
        coordinate = new Coordinate(coorInCellsX[board.allFigures[i].posX],
                coorInCellsY[board.allFigures[i].posY]);
        movingCoor[i] = coordinate;
        fontPaint.setColor(board.allFigures[i].isWhite ? Color.WHITE : Color.BLACK);
        canvas.drawText(board.allFigures[i].image, coorInCellsX[board.allFigures[i].posX],
                coorInCellsY[board.allFigures[i].posY], fontPaint);
      }
    } else {
      if (isIlluminatingCells && isDrawingCircles) { //illuminating cells by default
        drawCircles(canvas);
      }
      for (int i = 0; i < board.allFigures.length; i++) {
        if (board.allFigures[i] != null
                && i != indOfCapturedPiece
                && movingCoor[i].x <= clipBound.right
                && movingCoor[i].x >= clipBound.left
                && movingCoor[i].y >= clipBound.top
                && movingCoor[i].y <= clipBound.bottom) {
          fontPaint.setColor(board.allFigures[i].isWhite ? Color.WHITE : Color.BLACK);
          canvas.drawText(board.allFigures[i].image, movingCoor[i].x,
                  movingCoor[i].y, fontPaint);
        }
      }
    }
    drawToMoveAndCheckmate(canvas);
  }

  private void drawToMoveAndCheckmate(Canvas canvas) {
    String blackOrWhiteToMove;
    if (amountOfLevelsToSolve == INVALID) {
      amountOfLevelsToSolve = 1;
    }
    if (numberOfJustSolvedLevels == INVALID) {
      numberOfJustSolvedLevels = 0;
    }
    int leftToSolve = amountOfLevelsToSolve - numberOfJustSolvedLevels;
    if (leftToSolve == 1) {
      levels1 = level;
    } else if (leftToSolve == 0 || leftToSolve >= 5) {
      levels1 = levels3;
    } else if (leftToSolve <= 4 && leftToSolve > 1) {
      levels1 = levels;
    }
    fontPaint.setColor((whoseTurnToPlay == 'b') ? Color.BLACK : Color.WHITE);
    paint2fSize.setColor((whoseTurnToPlay == 'b') ? getResources().getColor(R.color.mybg10) : Color.WHITE);
    blackOrWhiteToMove = (whoseTurnToPlay == 'b') ? blackToMove : whiteToMove;
    String moveString = lockScreenApp.loadInfo("move");
    String enemyMoveString = lockScreenApp.loadInfo("enemyMove");
    enemyMoveString = (enemyMoveString == null) ? "" : ";  " + enemyMoveString;

    if (isPortrait) {
      if (isRightMove) {
        canvas.drawRect(0, sideSize, sideSize, sideSize + sideSize / 10, paintGreen);
        canvas.drawText(ok + "  " + moveString, sideSize / 2, sideSize * 1.0667f, paint2fSize);
      } else if (isWrongMove) {
        canvas.drawRect(0, sideSize, sideSize, sideSize + sideSize / 10, paintRed);
        canvas.drawText(fail + "  " + moveString + enemyMoveString, sideSize / 2, sideSize * 1.0667f, paint2fSize);
      } else {
        canvas.drawText("o", sideSize / 11, sideSize * 1.0917f, fontPaint); //pawn
        canvas.drawText(blackOrWhiteToMove + " " + text22, sideSize * 11 / 20, sideSize * 1.0667f, paint2fSize);
      }
      if (lockScreenApp.isLockScreen) {
        if (leftToSolve == 0) {
          canvas.drawText(toUnlockTouch + " " + touch, sideSize / 2, sideSize * 1.145f, paint1dot3fSize);
        } else {
          canvas.drawText(toUnlockSolve0 + " " + toUnlockSolve + " " + Integer.toString(leftToSolve) + " " +
                  levels1, sideSize / 2, sideSize * 1.145f, paint1dot3fSize);
        }
        canvas.drawText(rusString + " " + numberOfJustSolvedLevels + " " + solved,
                sideSize / 2, sideSize * 1.195f, paint1dot3fSize);
      }
    } else {
      if (isRightMove) {
        canvas.drawRect(sideSize, 0, canvas.getWidth(), sideSize / 3.7f - squareSize, paintGreen);
        canvas.drawText(ok + "  " + moveString, landXCenter, sideSize * 0.09f, paint2fSize);
      } else if (isWrongMove) {
        canvas.drawRect(sideSize, 0, canvas.getWidth(), sideSize / 3.7f - squareSize, paintRed);
        canvas.drawText(fail + "  " + moveString + enemyMoveString, landXCenter, sideSize * 0.09f, paint2fSize);
      } else {
        if (text22.length() > 1) {
          canvas.drawText("o", sideSize * 8 / 7, sideSize * 0.097f, fontPaint);//pawn
          canvas.drawText(blackOrWhiteToMove, landXCenter, sideSize * 0.067f, paint2fSize);
          canvas.drawText(text22, landXCenter, sideSize * 0.128f, paint2fSize);
        } else {
          canvas.drawText("o", sideSize * 8 / 7, sideSize * 0.110f, fontPaint);//pawn
          canvas.drawText(blackOrWhiteToMove, landXCenter, sideSize * 0.090f, paint2fSize);
          //canvas.drawText(text22, landXCenter, sideSize * 0.085f, paint2fSize);
        }
      }
      if (lockScreenApp.isLockScreen) {
        if (leftToSolve == 0) {
          canvas.drawText(toUnlockTouch, landXCenter, sideSize * 0.230f, paint1dot3fSize);
          canvas.drawText(touch, landXCenter, sideSize * 0.280f, paint1dot3fSize);
        } else {
          canvas.drawText(toUnlockSolve0, landXCenter, sideSize * 0.230f, paint1dot3fSize);
          canvas.drawText(toUnlockSolve + " " + Integer.toString(leftToSolve) + " " +
                  levels1, landXCenter, sideSize * 0.280f, paint1dot3fSize);
        }
        canvas.drawText(rusString + " " + numberOfJustSolvedLevels + " " + solved,
                landXCenter, sideSize * 0.330f, paint1dot3fSize);
      }
    }
  }

  private void drawCircles(Canvas canvas) {
    for (Pos pos : moves) {
      Square square;
      if (isPlayingWhite) {
        if (isPortrait) {
          square = BoardView.squareArray[pos.x][pos.y];
        } else {
          square = BoardViewLand.squareArray[pos.x][pos.y];
        }
      } else {
        if (isPortrait) {
          square = BoardView.squareArray[7 - pos.x][7 - pos.y];
        } else {
          square = BoardViewLand.squareArray[7 - pos.x][7 - pos.y];
        }
      }
      canvas.drawCircle((square.right + square.left) / 2,
              (square.bottom + square.top) / 2, squareSize / 4,
              square.color == 'b' ? paintLightCircles : paintDarkCircles);
    }
  }

  private void timeToUnlock() {
    lockScreenApp.saveInfoInt("amountOfJustSolvedLevels", INVALID);
    LockScreenReceiver.saveInfoForOuterLong("time2", System.currentTimeMillis(), getContext());
    stopTimerAndTimerTask();
    LockScreenReceiver.needToAlive = false;
    Board.figureOnBoard = new Figure[8][8];
    isEverythingSolved = true;
    LockScreenReceiver.saveInfoForOuterBoolean("isLocked2", false, lockScreenApp);
    lockScreenApp.finish();
    moves = null;
  }

  private void stopTimerAndTimerTask() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
    //myTimerTask = null;
  }

  private void touchAfterRightMoveWhenLockScreen(float evX, float evY) {
    amountOfLevelsToSolve = LockScreenReceiver.loadOuterInfoInt("amountOfLevelsToSolve3", getContext());
    if (amountOfLevelsToSolve <= 1) {
      timeToUnlock();
    } else {
      numberOfJustSolvedLevels = lockScreenApp.loadInfoInt("amountOfJustSolvedLevels");
      if (numberOfJustSolvedLevels == INVALID) {
        numberOfJustSolvedLevels = 0;
      }
      if (numberOfJustSolvedLevels >= amountOfLevelsToSolve) {
        timeToUnlock();
      } else {
        if (evX >= xNextLeft
                && evX <= xNextLeft + buttonsSize
                && evY >= yNextTop
                && evY <= yNextTop + buttonsSize) {//touching "forward" icon to get new level when not everything solved
          getNewLevel();
        }
      }
    }
  }

  private void getNewLevel() {
    isRightMove = false;
    isInitialChessPositionReady = false;
    moves = null;
    Board.figureOnBoard = new Figure[8][8];
    invalidate();
  }

  private void touchAfterRightMove(float evX, float evY) {
    lockScreenApp.saveInfoInt("alreadyResolved", INVALID);
    lockScreenApp.saveInfo("currentChessPositionAfterMove", null);
    lockScreenApp.saveInfoBoolean("isRightMove", false);
    if (numberOfSuccessfulAttempt >= ChessPositions.allStartPositions.size()) {//if we solved all 100 levels, we start solving from the beginning
      lockScreenApp.saveInfoInt("numberOfSuccessfulAttempt", INVALID);
      lockScreenApp.saveInfoInt("numberOfAttempt2", INVALID);
    }
    if (lockScreenApp.isLockScreen) {//touching screen after right move when lockScreenApp.isLockScreen
      touchAfterRightMoveWhenLockScreen(evX, evY);
    } else {//touching screen after right move when it is not lockScreen
      if (evX >= 0
              && evX <= sideSize
              && evY >= 0
              && evY <= sideSize * 8 / 7) {//touching board to exit when it is not lockScreen
        stopTimerAndTimerTask();
        lockScreenApp.finish();
      } else if (evX >= xNextLeft
              && evX <= xNextLeft + buttonsSize
              && evY >= yNextTop
              && evY <= yNextTop + buttonsSize) {//touching "forward" icon to get new level when it is not lockScreen
        getNewLevel();
      }
    }
  }

  private void touchAfterWrongMove(float evX, float evY) {
    if (evX >= xNextLeft && evX <= xNextLeft + buttonsSize && evY >= yNextTop
            && evY <= yNextTop + buttonsSize) {//touching "forward" icon to get new level
      whenTouchAfterWrongMove();
      lockScreenApp.saveInfoInt("alreadyResolved", INVALID);
      invalidate();
    } else if (isDrawingResolving) {
      if (evX <= xRefreshLeft + buttonsSize //touching "refresh" icon to get the same level
              && evX >= xRefreshLeft && evY >= yRefreshTop
              && evY <= yRefreshTop + buttonsSize) {
        whenTouchAfterWrongMove();
        touchRefresh();
      }
    }
  }

  private void whenTouchAfterWrongMove() {
    lockScreenApp.saveInfo("currentChessPositionAfterMove", null);
    lockScreenApp.saveInfoBoolean("isWrongMove", false);
    isWrongMove = false;
    isInitialChessPositionReady = false;
    Board.figureOnBoard = new Figure[8][8];
  }

  private void touchRefresh() {
    ChessPositions.refreshIndOfPositionAndUnsolvedLevels(lockScreenApp.loadInfoInt("oldIndOfPosition"),
            lockScreenApp.loadUnsolvedLevels(false), lockScreenApp.loadInfoBoolean("oldIsLevelFromUnsolved"), lockScreenApp);
    numberOfAlreadyResolved = (numberOfAlreadyResolved == INVALID) ? 1 : numberOfAlreadyResolved + 1;
    lockScreenApp.saveInfoInt("alreadyResolved", numberOfAlreadyResolved);
    isDrawingResolving = false;
    lockScreenApp.saveInfoBoolean("isDrawingResolving", false);
    invalidate();
  }

  private void startSecondActivity(boolean isSettings) {
    isInitialChessPositionReady = false;
    stopTimerAndTimerTask();
    LockScreenReceiver.needToAlive = false;
    if (isSettings) {
      LockScreenReceiver.startSecondActivitySettings = true;
    } else {
      LockScreenReceiver.startSecondActivityUnlock = true;
    }
    lockScreenApp.finish();
  }

  private void startSettingActivity() {
    isInitialChessPositionReady = false;
    stopTimerAndTimerTask();
    LockScreenReceiver.needToAlive = false;

    Intent intent = new Intent(lockScreenApp, SettingsActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    lockScreenApp.startActivity(intent);
    lockScreenApp.finish();
  }

  private void moveInTwoTouches1() {
    currentFigure = board.allFigures[itemInd];
    isDrawingCircles = true;
    Log.d(TAG, "  moving itemInd = " + itemInd + ",  " + board.allFigures[itemInd].type + "in 2 touches");
    startX = board.allFigures[itemInd].posX;
    startY = board.allFigures[itemInd].posY;
    board.findPossibleMove(itemInd);
    moves = board.possibleMove;
    isItemMoving = true;
  }

  private void moveInTwoTouches2(int itemIndex) {
    isSecondTouch = true;
    indOfPossibleMoveSecondTouch = itemIndex;
    movingCoor[itemInd].x = coorInCellsX[moves.get(itemIndex).x] - 10;
    movingCoor[itemInd].y = coorInCellsY[moves.get(itemIndex).y] - 10;
    isItemMoving = true;
  }

  private void afterSecondTouch() {
    movingCoor[itemInd].x = coorInCellsX[moves.get(indOfPossibleMoveSecondTouch).x];
    movingCoor[itemInd].y = coorInCellsY[moves.get(indOfPossibleMoveSecondTouch).y];
    board.boardAndFigureAfterMove(itemInd, indOfPossibleMoveSecondTouch);
    isSecondTouch = false;
    invalidate();
  }

  private void checkIfCastling() {
    int rookInd = board.boardAndFiguresAfterCastling(startX, finishX, startY);
    if (rookInd != -1) {
      movingCoor[rookInd].x = coorInCellsX[board.allFigures[rookInd].posX];
      movingCoor[rookInd].y = coorInCellsY[board.allFigures[rookInd].posY];
      invalidate(Math.max(0, (int) (movingCoor[itemInd].x - 2 * squareSize)),
              Math.max(0, (int) (movingCoor[itemInd].y - 5 * squareSize)),
              (int) Math.min(sideSize, (int) (movingCoor[itemInd].x + 2 * squareSize)),
              (int) Math.max(sideSize, (int) (movingCoor[itemInd].y + 5 * squareSize)));
    }
  }

  private void startDialogPawn() {
    DialogPawn dialogPawn = new DialogPawn();
    dialogPawn.chessView = this;
    dialogPawn.isWhite = currentFigure.isWhite;
    synchronized (this) {
      Log.d(TAG, "before start dialog");
      dialogPawn.show(lockScreenApp.getFragmentManager(), "string tag");//???
      Log.d(TAG, "dialog finished");
    }
    synchronized (this) {
      Log.d(TAG, "before dialog invalidate");
      invalidate(Math.max(0, (int) (movingCoor[itemInd].x - 2 * squareSize)),
              Math.max(0, (int) (movingCoor[itemInd].y - 2 * squareSize)),
              (int) Math.min(sideSize, (int) (movingCoor[itemInd].x + 2 * squareSize)),
              (int) Math.max(sideSize, (int) (movingCoor[itemInd].y + 2 * squareSize)));
      Log.d(TAG, "after dialog invalidate ");
      isNewItemChosen = false;
      isNewBoardReady = false;
    }
  }

  private void afterRightMove() {
    Log.d(TAG, "   !!!    right move");
    isDrawingCircles = false;
    refreshNumberOfAttempt(true);
    lockScreenApp.saveInfo("currentChessPositionAfterMove", board.currentChessPositionAfterMove() + whoseTurnToPlay);
    ChessPositions.removeSolvedPositionFromUnsolvedPositions(indOfPosition, lockScreenApp);
    lockScreenApp.saveInfoInt("indOfPosition", ChessPositions.findIndexOfNextPositionAfterRightMove(indOfPosition, lockScreenApp));
    stopTimerAndTimerTask();
    isRightMove = true;
    lockScreenApp.saveInfoBoolean("isRightMove", true);
    invalidate();
  }

  private void afterWrongMove() {
    Log.d(TAG, "   !!!    wrong move");
    isWorkingWithHandler = true;
    isDrawingCircles = false;
    refreshNumberOfAttempt(false);
    lockScreenApp.saveInfo("currentChessPositionAfterMove", board.currentChessPositionAfterMove() + whoseTurnToPlay);
    //lockScreenApp.saveInfoInt("IndOfCurrentChessPositionAfterMove", indOfPosition);
    numberOfSuccessfulAttempt = lockScreenApp.loadInfoInt("numberOfSuccessfulAttempt");

    lockScreenApp.saveInfoInt("oldIndOfPosition", indOfPosition);
    lockScreenApp.saveUnsolvedLevels(lockScreenApp.loadUnsolvedLevels(true), false);
    lockScreenApp.saveInfoBoolean("oldIsLevelFromUnsolved", lockScreenApp.loadInfoBoolean("isLevelFromUnsolved"));

    ChessPositions.saveUnsolvedPosition(indOfPosition, lockScreenApp);
    indOfPosition = ChessPositions.findIndexOfNextPositionAfterWrongMove(indOfPosition, lockScreenApp);
    lockScreenApp.saveInfoInt("indOfPosition", indOfPosition);

    stopTimerAndTimerTask();
    isWrongMove = true;
    lockScreenApp.saveInfoBoolean("isWrongMove", true);
    numberOfAlreadyResolved = lockScreenApp.loadInfoInt("alreadyResolved");
    numberOfPossibleResolvingAttempts = LockScreenReceiver.loadOuterInfoInt("amountOfAttemptsToResolve3", getContext());

    if (numberOfAlreadyResolved == INVALID && numberOfPossibleResolvingAttempts > 1
            || numberOfAlreadyResolved != INVALID && numberOfPossibleResolvingAttempts > numberOfAlreadyResolved + 1) {
      isDrawingResolving = true;
      lockScreenApp.saveInfoBoolean("isDrawingResolving", true);
    }
    invalidate();
  }


  @Override
  public boolean onTouchEvent(@NonNull MotionEvent event) {
    if (movingCoor == null) {
      Log.d(TAG, "problem with motion coordinates");
      return true;
    }
    float evX = event.getX();
    float evY = event.getY();
    switch (event.getAction()) {

      case MotionEvent.ACTION_DOWN:
        isDrawingCircles = false;
        if (isRightMove) {//touching screen after right move
          touchAfterRightMove(evX, evY);
        }

        if (!isEverythingSolved // not time to exit
                && evX >= xSettingCenter - buttonsSize / 2 //touching unlock icon to enter the secret code
                && evX <= xSettingCenter + buttonsSize / 2
                && evY >= ySettingCenter - buttonsSize / 2
                && evY <= ySettingCenter + buttonsSize / 2
                ) {
          if (lockScreenApp.CODE != LockScreenReceiver.INVALID) {
            startSecondActivity(true);
          } else {
            startSettingActivity();
          }
        }

        if (lockScreenApp.isLockScreen // not time to exit
                && !isEverythingSolved
                && evX >= xUnlockCenter - buttonsSize / 2 //touching unlock icon to enter the secret code
                && evX <= xUnlockCenter + buttonsSize / 2
                && evY >= yUnlockCenter - buttonsSize / 2
                && evY <= yUnlockCenter + buttonsSize / 2
                ) {
          startSecondActivity(false);
        }

        if (!isEverythingSolved
        && evX >= xInfoLeft && evX <= xInfoLeft + buttonsSize
                && evY >= yInfoTop && evY <= yInfoTop + buttonsSize) {//touching "info" icon
          createInfoDialog(getContext());
        } else if (isWrongMove) {
          touchAfterWrongMove(evX, evY);
        } else { //moving item in 2 touches
          for (int i = 0; i < movingCoor.length; i++) {
            if ((board.allFigures[i] != null) && evX >= movingCoor[i].x - squareSize / 2
                    && evX <= movingCoor[i].x + squareSize / 3
                    && evY >= movingCoor[i].y - squareSize / (1.1)
                    && evY <= movingCoor[i].y + squareSize / 10
                    && board.allFigures[i].isWhite == isWhiteTurnToPlay) {//choosing item to move
              itemInd = i;
              moveInTwoTouches1();
              invalidate();
              drag = true;
              dragX = evX - movingCoor[itemInd].x;
              dragY = evY - movingCoor[itemInd].y;
            }
          }
          if (!isItemMoving && moves != null) {//after choosing item we touch one of the possible cells
            for (int i = 0; i < moves.size(); i++) {
              Pos pos = moves.get(i);
              if (evX - coorInCellsX[pos.x] < squareSize / 2
                      && evX - coorInCellsX[pos.x] > -squareSize / 2
                      && evY - coorInCellsY[pos.y] +
                      (squareSize / (3.1f) + squareSize / 12) < squareSize / 2
                      && evY - coorInCellsY[pos.y] +
                      (squareSize / (3.1f) + squareSize / 12) > -squareSize / 2) {
                moveInTwoTouches2(i);
                invalidate();
                drag = true;
                dragX = evX - movingCoor[itemInd].x;
                dragY = evY - movingCoor[itemInd].y;
              }
            }
          }
        }
        break;

      case MotionEvent.ACTION_MOVE:
        if (drag) {
          movingCoor[itemInd].x = evX - dragX;
          movingCoor[itemInd].y = evY - dragY;
          invalidate(Math.max(0, (int) (movingCoor[itemInd].x - 2 * squareSize)),
                  Math.max(0, (int) (movingCoor[itemInd].y - 2 * squareSize)),
                  (int) Math.min(sideSize, (int) (movingCoor[itemInd].x + 2 * squareSize)),
                  (int) Math.max(sideSize, (int) (movingCoor[itemInd].y + 2 * squareSize)));
          boolean isMoveCorrect = false;
          for (int k = 0; k < moves.size(); k++) {
            Pos pos = moves.get(k);
            if ((movingCoor[itemInd].x >= coorInCellsX[pos.x] - squareSize / 2) &&
                    (movingCoor[itemInd].x <= coorInCellsX[pos.x] + squareSize / 2) &&
                    (movingCoor[itemInd].y >= coorInCellsY[pos.y] - squareSize / 2) &&
                    (movingCoor[itemInd].y <= coorInCellsY[pos.y] + squareSize / 2)) {
              //Log.d(TAG, "isMoveCorrect = true;");
              movingCoor[itemInd].x = coorInCellsX[pos.x];
              movingCoor[itemInd].y = coorInCellsY[pos.y];
              isMoveCorrect = true;
              board.boardAndFigureAfterMove(itemInd, k);
              if (board.isAlreadyCaptured) {
                Log.d(TAG, "time to stop");
                drag = false; //finish moving of this item
              }
              break;
            }
          }
          if (!isMoveCorrect) {
            movingCoor[itemInd].x = coorInCellsX[board.allFigures[itemInd].posX];
            movingCoor[itemInd].y = coorInCellsY[board.allFigures[itemInd].posY];
            //Log.d(TAG, "isMoveCorrect = false;");
          }
        }
        break;

      case MotionEvent.ACTION_UP:
        if (isSecondTouch) {
          afterSecondTouch();
        }
        drag = false;
        boolean isCaptured = false;
        if (board.isAlreadyCaptured) {
          board.isAlreadyCaptured = false;
          isCaptured = true;
        }
        isItemMoving = false;
        invalidate();
        if (currentFigure != null) {
          finishX = currentFigure.posX;
          int finishY = currentFigure.posY;
          if (currentFigure.type == Figure.Type.king) {// if castling - moving rook
            checkIfCastling();
          }
          if (currentFigure.type == Figure.Type.pawn
                  && (currentFigure.isWhite && currentFigure.posY == 7
                  || !currentFigure.isWhite && currentFigure.posY == 0)) {
            startDialogPawn();
          }
          if (startX != finishX || startY != finishY) { //check if this move was write or wrong
            lockScreenApp.saveInfo("enemyMove", null);
            char figureTypeChar = currentFigure.type.toChar();
            char colorChar = currentFigure.isWhite ? 'w' : 'b';
            if (startX == (ChessPositions.allAnswers.get(indOfPosition).charAt(2) - '1')
                    && startY == (ChessPositions.allAnswers.get(indOfPosition).charAt(3) - '1')
                    && finishX == (ChessPositions.allAnswers.get(indOfPosition).charAt(4) - '1')
                    && finishY == (ChessPositions.allAnswers.get(indOfPosition).charAt(5) - '1')
                    && figureTypeChar == ChessPositions.allAnswers.get(indOfPosition).charAt(1)
                    && colorChar == ChessPositions.allAnswers.get(indOfPosition).charAt(0)) {
              createMoveString(startX, startY, finishX, finishY, currentFigure.type, isCaptured, true, true);
              afterRightMove();
            } else {
              createMoveString(startX, startY, finishX, finishY, currentFigure.type, isCaptured, false, true);
              afterWrongMove();
            }
            startX = finishX;
            startY = finishY;
          }
        }
        break;
    }
    return true;
  }

  private void createMoveString(int startX, int startY, int finishX, int finishY, Figure.Type type, boolean isCaptured, boolean isMate, boolean isMyMove) {
    String str = "";
    switch (type) {
      case king:
        str = str + kingStr;
        break;
      case queen:
        str = str + queenStr;
        break;
      case rook:
        str = str + rookStr;
        break;
      case bishop:
        str = str + bishopStr;
        break;
      case pawn:
        str = str + pawnStr;
        break;
      case knight:
        str = str + knightStr;
        break;
    }
    lockScreenApp.saveInfo(isMyMove ? "move" : "enemyMove", str + (char) ('a' + startX) + (startY + 1) + (isCaptured ? captureStr : "-") +
            (char) ('a' + finishX) + (finishY + 1) + (isMate ? mateStr : ""));
  }

  protected void afterDialogPawn() {
    board.boardAndFiguresAfterPawnBecomesAnotherItem(this, chosenItem, itemInd);
  }

  private void drawAfterMove(Canvas canvas, boolean isRight) {

    if (!isRight || (lockScreenApp.loadInfoInt("amountOfJustSolvedLevels") < amountOfLevelsToSolve)) {
      drawBitmapWithColorFilter(canvas, bitmapButton,
              xNextLeft, yNextTop, xNextLeft + buttonsSize, yNextTop + buttonsSize);
      canvas.drawText(forward, xNextLeft + (buttonsSize - forwardWidth) / 2, yNextTop + buttonsSize / 2 + forwardHeight / 2.4f, iconPaint);
      if (isDrawingResolving) {
        drawBitmapWithColorFilter(canvas, bitmapButton,
                xRefreshLeft, yRefreshTop, xRefreshLeft + buttonsSize, yRefreshTop + buttonsSize);
        canvas.drawText(refresh, xRefreshLeft + (buttonsSize - refreshWidth) / 2, yRefreshTop + buttonsSize / 2 + refreshHeight / 2.3f, iconPaint);
      }
      if (isWorkingWithHandler) {
        isWorkingWithHandler = false;
        int kingInd = board.findKingIndex();
        int[] safetyMove;
        if (board.isCheck(kingInd, board.allFigures[kingInd].isWhite)) {
          safetyMove = board.findMoveAfterCheck(kingInd, board.allFigures[kingInd].isWhite);
          if (safetyMove != null) {
            makeMove(safetyMove);
          }
        } else {
          safetyMove = board.findArbitrarySafetyMove(board.allFigures[kingInd].isWhite);
          if (safetyMove == null) {
            safetyMove = board.findArbitraryPossibleMove(board.allFigures[kingInd].isWhite);
          }
          if (safetyMove != null) {
            makeMove(safetyMove);
          } else {
            //lockScreenApp.saveInfo("enemyMove", null);
            lockScreenApp.saveInfo(stalemate, null);
            Log.d(TAG, "____no winners_____stalemate__!!!!");
          }
        }
      }
    } else if (!lockScreenApp.isLockScreen) { //after right move when not lockScreen
      drawBitmapWithColorFilter(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.button7),
              xNextLeft, yNextTop, xNextLeft + buttonsSize, yNextTop + buttonsSize);
      canvas.drawText(forward, xNextLeft + (buttonsSize - forwardWidth) / 2, yNextTop + buttonsSize / 2 + forwardHeight / 2.4f, iconPaint);
    }
  }

  private void makeMove(int[] safetyMove) {
    int ind = safetyMove[3];
    createMoveString(board.allFigures[ind].posX, board.allFigures[ind].posY, safetyMove[0], safetyMove[1],
            board.allFigures[ind].type, safetyMove[2] == 1, false, false);
    int indToCapture = -1;
    if (safetyMove[2] == 1) {
      for (int i = 0; i < board.allFigures.length; i++) {
        if (board.allFigures[i] != null && board.allFigures[i].posX == safetyMove[0] && board.allFigures[i].posY == safetyMove[1]) {
          indToCapture = i;
          break;
        }
      }
    }
    lockScreenApp.handler.sendMessageDelayed(lockScreenApp.handler.obtainMessage(ind, safetyMove[0], safetyMove[1], indToCapture), 1000);// 0 - x, 1 - y , 2 - isCaptured
    lockScreenApp.handler.sendMessageDelayed(lockScreenApp.handler.obtainMessage(ind, board.allFigures[ind].posX, board.allFigures[ind].posY, null), 2000);
  }

  private void refreshNumberOfAttempt(boolean isRightMove) {
    numberOfAttempt = lockScreenApp.loadInfoInt("numberOfAttempt2");
    numberOfAttempt = (numberOfAttempt == INVALID) ? 1 : numberOfAttempt + 1;
    lockScreenApp.saveInfoInt("numberOfAttempt2", numberOfAttempt);
    if (isRightMove) {
      numberOfSuccessfulAttempt = lockScreenApp.loadInfoInt("numberOfSuccessfulAttempt");
      numberOfSuccessfulAttempt = (numberOfSuccessfulAttempt == INVALID) ? 1 : numberOfSuccessfulAttempt + 1;
      lockScreenApp.saveInfoInt("numberOfSuccessfulAttempt", numberOfSuccessfulAttempt);
      if (lockScreenApp.isLockScreen) {
        numberOfJustSolvedLevels = lockScreenApp.loadInfoInt("amountOfJustSolvedLevels");
        numberOfJustSolvedLevels = (numberOfJustSolvedLevels == INVALID) ? 1 : numberOfJustSolvedLevels + 1;
        lockScreenApp.saveInfoInt("amountOfJustSolvedLevels", numberOfJustSolvedLevels);
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

  private void createInfoDialog(Context context) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(title)
            .setMessage(prepareResults())
            .setCancelable(false)
            .setNegativeButton(ok,
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                      }
                    });
    AlertDialog alert = builder.create();
    alert.show();
  }

  private String prepareResults() {
    if (numberOfAttempt == INVALID) {
      numberOfAttempt = 0;
    }
    if (numberOfSuccessfulAttempt == INVALID) {
      numberOfSuccessfulAttempt = 0;
    }
    return currentProblemNum + " " + indOfPositionForResults + "\n" +
            text1 + "\n" + text2 + " " + numberOfSuccessfulAttempt + "\n" + text3 + " " + numberOfAttempt;
  }

//  class MyTimerTask extends TimerTask {
//    @Override
//    public void run() {
//      Log.d(TAG, "_____!!!!!   in run() of timer task");
//      lockScreenApp.runOnUiThread(new Runnable() {
//        @Override
//        public void run() {
//          invalidate();
//          Log.d(TAG, "runnable _ invalidating chessView");
//        }
//      });
//    }
//  }

  protected void handlerMethod(android.os.Message msg) {
    movingCoor[msg.what].x = coorInCellsX[msg.arg1];
    movingCoor[msg.what].y = coorInCellsY[msg.arg2];
    indOfCapturedPiece = msg.obj != null ? (int) msg.obj : -1;
    invalidate();
  }
}

