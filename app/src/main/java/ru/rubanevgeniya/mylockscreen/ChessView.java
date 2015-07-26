package ru.rubanevgeniya.mylockscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class ChessView extends View {
  private boolean isPortrait;
  private int oldIndex;
  private Set<String> oldLoadUnsolvedLevels;
  private String oldIsLevelFromUnsolved;
  private boolean isRightMove;
  private boolean isWrongMove;
  private boolean isItemMoving;
  private int amountOfLevelsToSolve;
  private ColorFilter colorFilter;
  private boolean isPlayingWhite;
  private char whoseTurnToPlay;
  private Paint paintDarkCircles = new Paint();
  private Paint paintLightCircles = new Paint();
  private Paint paint4fSize = new Paint();
  private Paint paint2fSize = new Paint();
  private Paint paint1dot3fSize = new Paint();
  private Paint paint1dot5fSize = new Paint();
  private Paint paint = new Paint();
  private Paint paintWithColorFilter = new Paint();
  private Paint fontPaint = new Paint();
  private Paint fontPaintLettersNumbers = new Paint();
  private float squareSize;
  private float sideSize;
  private int fontSize;
  private int DELTA;
  private final String[] LETTERS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
  private String startPosition;
  private int indOfPosition;
  private Board board;
  private boolean isInitialChessPositionReady;
  private final static float[] coorInDeskCellsFloatX = new float[8];
  private final static float[] coorInDeskCellsFloatY = new float[8];
  private int buttonsSize;
  private Coordinate[] movingCoordinates;
  private int itemInd = 0;
  private ArrayList<Pos> possibleCoordinates = new ArrayList<>();
  private boolean drag = false;
  private float dragX = 0;
  private float dragY = 0;
  private LockScreenApp lockScreenApp;
  private DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
  private DateFormat dateFormat2 = new SimpleDateFormat("hh:mm");
  protected Timer timer = new Timer();
  private boolean isIlluminatingCells;
  private int numberOfPossibleResolvingAttempts;
  private String numberOfAlreadyResolved;
  private Rect clipBound = new Rect();
  private Rect rect = new Rect();
  private Rect rectBitmap = new Rect();
  private Rect rect1 = new Rect();
  private int chessHeight = 0;
  private int chessWidth = 0;
  private float xUnlockCenter;
  private float yUnlockCenter;
  private float xRefreshLeft;
  private float yRefreshTop;
  private float xNextLeft;
  private float yNextTop;
  private String numberOfAttempt;
  private String numberOfSuccessfulAttempt;
  private int startX;
  private int finishX;
  private int startY;
  private int finishY;
  private Figure currentFigure;
  protected Figure.Type chosenItem;
  protected boolean isNewItemChosen = false;
  protected boolean isNewDeskReady = false;
  private boolean isDrawingResolving;
  protected MyTimerTask myTimerTask;
  //private float xAnswerCenter;
  //private float yAnswerCenter;
  private float xDateCenter;
  private float yDateCenter;
  private float xTimeCenter;
  private float yTimeCenter;
  private String numberOfJustSolvedLevels;
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
  private Typeface typeface;
  private static final String forward = "\uF061";
  private static final String refresh = "\uF021";
  float xInfoLeft;
  float yInfoTop;
  private boolean isWhiteTurnToPlay;


  public ChessView(Context context, AttributeSet attrs) {
    super(context, attrs);
    lockScreenApp = (LockScreenApp) getContext();
    isInitialChessPositionReady = false;
    Board.figureOnDesk = new Figure[8][8];
    ChessPositions.createInitialChessPosition();
    myTimerTask = new MyTimerTask();
    timer.schedule(myTimerTask,
            60000 - (Calendar.getInstance().getTimeInMillis() % 60000), 30000);
    float[] cmData = new float[]{
            0.3f, 0.59f, 0.11f, 0, 0,
            0.3f, 0.59f, 0.11f, 0, 0,
            0.3f, 0.59f, 0.11f, 0, 0,
            0, 0, 0, 1, 0,};
    ColorMatrix colorMatrix = new ColorMatrix(cmData);
    colorFilter = new ColorMatrixColorFilter(colorMatrix);

    numberOfPossibleResolvingAttempts = LockScreenReceiver.loadOuterInfoInt("amountOfAttemptsToResolve3", getContext());
    isIlluminatingCells = !LockScreenReceiver.loadOuterInfoBoolean("isNoIlluminating",getContext());
    amountOfLevelsToSolve = LockScreenReceiver.loadOuterInfoInt("amountOfLevelsToSolve3", getContext());
    numberOfAlreadyResolved = lockScreenApp.loadInfo("alreadyResolved");
    numberOfAttempt = lockScreenApp.loadInfo("numberOfAttempt");
    numberOfSuccessfulAttempt = lockScreenApp.loadInfo("numberOfSuccessfulAttempt");
    numberOfJustSolvedLevels = lockScreenApp.loadInfo("amountOfJustSolvedLevels");

    fontPaintLettersNumbers.setTextAlign(Paint.Align.CENTER);
    fontPaintLettersNumbers.setColor(Color.BLACK);
    paintDarkCircles.setAlpha(100);
    paintLightCircles.setColor(getResources().getColor(R.color.mybg2));
    paintDarkCircles.setColor(getResources().getColor(R.color.mybg4));
    typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/fontawesome.woff");
    paint.setAlpha(150);
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
    drawDateAndTime(canvas);
    drawChessPosition(canvas);
    if (isRightMove) {
      drawAfterMove(canvas, true);
    } else if (isWrongMove) {
      drawAfterMove(canvas, false);
    }
  }


  private void findCoordinatesInCells() {
    for (int i = 0; i < 8; i++) {
      int i1 = i - 8 * (i / 8);
      if (isPlayingWhite) {
        coorInDeskCellsFloatX[i] = sideSize / DELTA / 2 + squareSize / 2 + squareSize * (i1);
        coorInDeskCellsFloatY[7 - i] = sideSize / DELTA / 2 + squareSize * (i) + squareSize / 2 + squareSize / (3.1f) + squareSize / 12;
      } else {
        coorInDeskCellsFloatY[i] = sideSize / DELTA / 2 + squareSize * (i) + squareSize / 2 + squareSize / (3.1f) + squareSize / 12;
        coorInDeskCellsFloatX[7 - i] = sideSize / DELTA / 2 + squareSize / 2 + squareSize * (i1);
      }
    }
  }


  private void drawDateAndTime(Canvas canvas) {
    canvas.drawText(dateFormat2.format(Calendar.getInstance().getTime()), xTimeCenter, yTimeCenter, paint4fSize);
    canvas.drawText(dateFormat1.format(Calendar.getInstance().getTime()), xDateCenter, yDateCenter, paint1dot5fSize);
  }


  private void drawBitmapWithColorFilter(Canvas canvas, Bitmap bitmap, float left, float top, float right, float bottom) {
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


  private void drawBitmap(Canvas canvas, Bitmap bitmap, float left, float top, float right, float bottom) {
    rectBitmap.top = (int) (top);
    rectBitmap.left = (int) left;
    rectBitmap.right = (int) right;
    rectBitmap.bottom = (int) bottom;
    rect.top = 0;
    rect.left = 0;
    rect.right = bitmap.getWidth();
    rect.bottom = bitmap.getHeight();
    canvas.drawBitmap(bitmap, rect, rectBitmap, paint);
  }


  private int findFontSizeForChess() {
    int fSize = 4 * fontSize;
    findChessSize(fSize);
    if (Math.max(chessHeight, chessWidth) > 0.8 * squareSize) {
      do {
        fSize = fSize - 1;
        findChessSize(fSize);
      } while (Math.max(chessHeight, chessWidth) > 0.8 * squareSize);
    }
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
      canvas.drawText(LETTERS[i], coorInDeskCellsFloatX[i], sideSize / DELTA / (2.5f), fontPaintLettersNumbers);
      canvas.drawText(LETTERS[i], coorInDeskCellsFloatX[i], sideSize - sideSize / DELTA / (10f), fontPaintLettersNumbers);
      canvas.drawText(num, sideSize / DELTA / 5, coorInDeskCellsFloatY[i] - squareSize / 3, fontPaintLettersNumbers);
      canvas.drawText(num, sideSize - sideSize / DELTA / 3.5f, coorInDeskCellsFloatY[i] - squareSize / 3, fontPaintLettersNumbers);
    }
  }


  private void findStartPosition(Canvas canvas) {
    sideSize = Math.min(canvas.getHeight(), canvas.getWidth());
    if (canvas.getHeight() > canvas.getWidth()) {
      isPortrait = true;
    }
    buttonsSize = (int) (sideSize / 6.8f);
    DELTA = (int) (sideSize / (67.5));
    squareSize = (sideSize - sideSize / DELTA) / 8;
    fontSize = (int) (sideSize / 36);

    if (isPortrait) {
      xUnlockCenter = sideSize / 25 + buttonsSize / 2;
      yUnlockCenter = sideSize + sideSize / 4 + buttonsSize / 2;
      //xAnswerCenter = sideSize - sideSize / 4;
      //yAnswerCenter = sideSize + sideSize / 20;
      xTimeCenter = sideSize / 2 + sideSize / (3.2f);
      yTimeCenter = sideSize + sideSize / 4 + sideSize / 20 + sideSize / 30 - sideSize / 40;
      xDateCenter = xTimeCenter;
      yDateCenter = yTimeCenter + sideSize / 8 - sideSize / 30 - sideSize / 60;

    } else {
      xUnlockCenter = sideSize / 20 + buttonsSize / 2 + sideSize;
      yUnlockCenter = sideSize / 2 + sideSize / 4 + buttonsSize / 2;
      //xAnswerCenter = sideSize + sideSize / 4;
      //yAnswerCenter = sideSize / 20;
      xTimeCenter = sideSize + sideSize / (3f);
      yTimeCenter = sideSize / 2 + sideSize / 15 + sideSize / 20;
      xDateCenter = xTimeCenter;
      yDateCenter = yTimeCenter + sideSize / 12;
    }

    xInfoLeft = xUnlockCenter + buttonsSize / 2 + buttonsSize / 7;
    yInfoTop = yUnlockCenter - buttonsSize / 2;
    xNextLeft = xUnlockCenter + buttonsSize / 2 + buttonsSize / 7 + (buttonsSize + buttonsSize / 7);
    yNextTop = yUnlockCenter - buttonsSize / 2;
    xRefreshLeft = xNextLeft + buttonsSize + buttonsSize / 7;
    yRefreshTop = yNextTop;

    iconPaint.setTypeface(typeface);
    iconPaint.setColor(getResources().getColor(R.color.mybg9));
    int iconSize = findFontSizeForIcon(forward);
    iconPaint.setTextSize(iconSize);
    forwardHeight = iconHeight;
    forwardWidth = iconWidth;
    refreshHeight = iconHeight;
    refreshWidth = iconWidth;

    fontPaintLettersNumbers.setTextSize(fontSize);
    fontPaintLettersNumbers.setColor(getResources().getColor(R.color.mybg10));

    paint2fSize.setColor(getResources().getColor(R.color.mybg9));
    paint2fSize.setStyle(Paint.Style.FILL_AND_STROKE);
    paint2fSize.setTextAlign(Paint.Align.CENTER);
    paint2fSize.setTextSize(2 * fontSize);

    paint4fSize.setColor(getResources().getColor(R.color.mybg9));
    paint4fSize.setStyle(Paint.Style.FILL_AND_STROKE);
    paint4fSize.setTextAlign(Paint.Align.CENTER);
    paint4fSize.setTextSize(3 * fontSize);

    paint1dot3fSize.setColor(getResources().getColor(R.color.mybg9));
    paint1dot3fSize.setStyle(Paint.Style.FILL_AND_STROKE);
    paint1dot3fSize.setTextAlign(Paint.Align.LEFT);
    paint1dot3fSize.setTextSize(1.3f * fontSize);

    paint1dot5fSize.setColor(getResources().getColor(R.color.mybg9));
    paint1dot5fSize.setStyle(Paint.Style.FILL_AND_STROKE);
    paint1dot5fSize.setTextAlign(Paint.Align.CENTER);
    paint1dot5fSize.setTextSize(1.5f * fontSize);

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

    String loadedIndOfPosition = lockScreenApp.loadInfo("indOfPosition");
    if (loadedIndOfPosition != null) {
      indOfPosition = Integer.parseInt(loadedIndOfPosition);
    } else {
      indOfPosition = 0;
    }
    //Log.d(TAG,"indOfPosition = " + indOfPosition);
    if (indOfPosition >= ChessPositions.allStartPositions.size()) {
      indOfPosition = 0;
      Log.d(TAG, "troubles with indOfPosition, let it be = " + indOfPosition);
    }

    //begin check if it was screen rotation after move
    String currentChessPositionAfterMove = lockScreenApp.loadInfo("currentChessPositionAfterMove");

    if (currentChessPositionAfterMove != null) {//after screen rotation
      String isRightMoveString;
      String isWrongMoveString;
      String isDrawingResolvingString;
      isRightMoveString = lockScreenApp.loadInfo("isRightMove");
      isWrongMoveString = lockScreenApp.loadInfo("isWrongMove");
      isDrawingResolvingString = lockScreenApp.loadInfo("isDrawingResolving");
      if (isRightMoveString != null) {
        isRightMove = Boolean.parseBoolean(isRightMoveString);
      }
      if (isWrongMoveString != null) {
        isWrongMove = Boolean.parseBoolean(isWrongMoveString);
      }
      if (isDrawingResolvingString != null) {
        isDrawingResolving = Boolean.parseBoolean(isDrawingResolvingString);
      }
      startPosition = currentChessPositionAfterMove;
    } else {
      startPosition = ChessPositions.allStartPositions.get(indOfPosition);
    }
    //end check if it was screen rotation after move


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
      fontPaint.setTextAlign(Paint.Align.CENTER);
      Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Chess-7.TTF");
      fontPaint.setTypeface(typeface);

      board = new Board(startPosition);
      board.createStartPosition();
      int fontSizeForChess = findFontSizeForChess();
      isInitialChessPositionReady = true;
      movingCoordinates = new Coordinate[board.allFigures.length];
      for (int i = 0; i < board.allFigures.length; i++) {
        coordinate = new Coordinate(coorInDeskCellsFloatX[board.allFigures[i].posX],
                coorInDeskCellsFloatY[board.allFigures[i].posY]);
        movingCoordinates[i] = coordinate;
        if (board.allFigures[i].isWhite) {
          fontPaint.setColor(Color.WHITE);
        } else {
          fontPaint.setColor(Color.BLACK);
        }
        fontPaint.setTextSize(fontSizeForChess);
        Log.d(TAG, ("start position : " + board.allFigures[i].type + " " +
                board.allFigures[i].isWhite + " " + board.allFigures[i].posX + " " + board.allFigures[i].posY));
        canvas.drawText(board.allFigures[i].image, coorInDeskCellsFloatX[board.allFigures[i].posX],
                coorInDeskCellsFloatY[board.allFigures[i].posY], fontPaint);
      }
      isInitialChessPositionReady = true;

    } else {
      if (isIlluminatingCells && isDrawingCircles) { //illuminating cells by default
        drawCircles(canvas);
      }
      for (int i = 0; i < board.allFigures.length; i++) {
        if (board.allFigures[i] != null
                && movingCoordinates[i].x <= clipBound.right
                && movingCoordinates[i].x >= clipBound.left
                && movingCoordinates[i].y >= clipBound.top
                && movingCoordinates[i].y <= clipBound.bottom) {
          if (board.allFigures[i].isWhite) {
            fontPaint.setColor(Color.WHITE);
          } else {
            fontPaint.setColor(Color.BLACK);
          }
          canvas.drawText(board.allFigures[i].image, movingCoordinates[i].x,
                  movingCoordinates[i].y, fontPaint);
        }
      }
    }
    drawToMoveAndCheckmate(canvas);
  }

  private void drawToMoveAndCheckmate(Canvas canvas) {
    String blackOrWhiteToMove;

    if (amountOfLevelsToSolve == 1000) {
      amountOfLevelsToSolve = 1;
    }
    if (numberOfJustSolvedLevels == null) {
      numberOfJustSolvedLevels = "0";
    }
    int leftToSolve = amountOfLevelsToSolve - Integer.parseInt(numberOfJustSolvedLevels);
    if (leftToSolve == 1) {
      levels1 = level;
    } else if (leftToSolve == 0 || leftToSolve >= 5) {
      levels1 = levels3;
    } else if (leftToSolve <= 4 && leftToSolve > 1) {
      levels1 = levels;
    }
// else if (leftToSolve >= 5) {
//      levels1 = levels3;
//    }

    if (whoseTurnToPlay == 'b') {
      fontPaint.setColor(Color.BLACK);
      paint2fSize.setColor(getResources().getColor(R.color.mybg10));
      blackOrWhiteToMove = blackToMove;
    } else {
      fontPaint.setColor(Color.WHITE);
      paint2fSize.setColor(Color.WHITE);
      blackOrWhiteToMove = whiteToMove;
    }
    if (isPortrait) {
      canvas.drawText("o", sideSize / 11, sideSize + sideSize / 8 - sideSize / 30, fontPaint); //pawn
      canvas.drawText(blackOrWhiteToMove + " " + text22, sideSize / 2 + sideSize / 20,
              sideSize + sideSize / 8 - sideSize / 40 - sideSize / 30, paint2fSize);
      if (lockScreenApp.isLockScreen) {
        canvas.drawText(toUnlockSolve0 + " " + toUnlockSolve + " " + Integer.toString(leftToSolve) + " " +
                levels1, sideSize / 11, sideSize + sideSize / 8 + sideSize / 50, paint1dot3fSize);
        canvas.drawText(rusString + " " + numberOfJustSolvedLevels + " " + solved,
                sideSize / 11, sideSize + sideSize / 8 + sideSize / 20 + sideSize / 50, paint1dot3fSize);
      }
    } else {
      canvas.drawText("o", sideSize + sideSize / 7, sideSize / 3.2f - sideSize / 7 - sideSize / 20, fontPaint);//pawn
      canvas.drawText(blackOrWhiteToMove, sideSize + sideSize / 2 - sideSize / 10,
              sideSize / 4 - sideSize / 7 - sideSize / 25, paint2fSize);
      canvas.drawText(text22, sideSize + sideSize / 2 - sideSize / 10, sideSize / 3 - sideSize / 7 - sideSize / 16, paint2fSize);
      if (lockScreenApp.isLockScreen) {
        canvas.drawText(toUnlockSolve0, sideSize + sideSize / 9, sideSize / 7 + sideSize / 20 + sideSize / 70, paint1dot3fSize);
        canvas.drawText(toUnlockSolve + " " + Integer.toString(leftToSolve) + " " +
                levels1, sideSize + sideSize / 9, sideSize / 7 + 2 * sideSize / 20 + sideSize / 70, paint1dot3fSize);
        canvas.drawText(rusString + " " + numberOfJustSolvedLevels + " " + solved,
                sideSize + sideSize / 9, sideSize / 7 + 3 * sideSize / 20 + sideSize / 70, paint1dot3fSize);
      }
    }
  }


  private void drawCircles(Canvas canvas) {
    Square square;
    for (int i = 0; i < possibleCoordinates.size(); i++) {
      if (isPlayingWhite) {
        if (isPortrait) {
          square = BoardView.squareArray[possibleCoordinates.get(i).x][possibleCoordinates.get(i).y];
        } else {
          square = BoardViewLand.squareArray[possibleCoordinates.get(i).x][possibleCoordinates.get(i).y];
        }
      } else {
        if (isPortrait) {
          square = BoardView.squareArray[7 - possibleCoordinates.get(i).x][7 - possibleCoordinates.get(i).y];
        } else {
          square = BoardViewLand.squareArray[7 - possibleCoordinates.get(i).x][7 - possibleCoordinates.get(i).y];
        }
      }
      if (square.color == 'b') {
        canvas.drawCircle((square.right + square.left) / 2,
                (square.bottom + square.top) / 2, squareSize / 4, paintLightCircles);
      } else {
        canvas.drawCircle((square.right + square.left) / 2,
                (square.bottom + square.top) / 2, squareSize / 4, paintDarkCircles);
      }
    }
  }


  @Override
  public boolean onTouchEvent(MotionEvent event) {

    float evX = event.getX();
    float evY = event.getY();
    if (movingCoordinates != null) {

      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:

          isDrawingCircles = false;

          if (isRightMove && lockScreenApp.isLockScreen) {//touching screen after right move and unlocking the screen
            lockScreenApp.saveInfo("alreadyResolved", null);
            amountOfLevelsToSolve = LockScreenReceiver.loadOuterInfoInt("amountOfLevelsToSolve3",getContext());
            //Log.d(TAG, "amountOfLevelsToSolve = " + amountOfLevelsToSolve);

            lockScreenApp.saveInfo("currentChessPositionAfterMove", null);
            lockScreenApp.saveInfo("isRightMove", null);

            if (numberOfSuccessfulAttempt != null &&   //if we solved all levels
                    Integer.parseInt(numberOfSuccessfulAttempt) >= ChessPositions.allStartPositions.size()) {
              lockScreenApp.saveInfo("numberOfSuccessfulAttempt", null);
              lockScreenApp.saveInfo("numberOfAttempt", null);
            }

            if (amountOfLevelsToSolve == -1000
                    || amountOfLevelsToSolve == 1) {
              if (timer != null) {
                timer.cancel();
                timer = null;
              }
              isEverythingSolved = true;
              LockScreenReceiver.saveInfoForOuterBoolean("isLocked2", false, lockScreenApp);
              myTimerTask = null;
              //Log.d(TAG,"amountOfLevelsToSolve = null or 1");
              LockScreenReceiver.needToAlive = false;
              LockScreenReceiver.saveInfoForOuterLong("time2", System.currentTimeMillis(), getContext());
              //Log.d(TAG, "chessView : lockScreenApp.loadOuterInfo(\"time\") = " + lockScreenApp.loadOuterInfo("time"));
              Board.figureOnDesk = new Figure[8][8];
              lockScreenApp.saveInfo("amountOfJustSolvedLevels", null);//**
              lockScreenApp.finish();
              possibleCoordinates = null;

            } else {
              int needToSolve = amountOfLevelsToSolve;
              numberOfJustSolvedLevels = lockScreenApp.loadInfo("amountOfJustSolvedLevels");
              if (numberOfJustSolvedLevels == null) {
                numberOfJustSolvedLevels = "0";
              }
              int alreadySolvedInt = Integer.parseInt(numberOfJustSolvedLevels);
              //Log.d(TAG,"alreadySolved = "+(numberOfJustSolvedLevels));
              if (alreadySolvedInt >= needToSolve) {
                lockScreenApp.saveInfo("amountOfJustSolvedLevels", null);
                LockScreenReceiver.saveInfoForOuterLong("time2", System.currentTimeMillis(), getContext());
                if (timer != null) {
                  timer.cancel();
                  timer = null;
                }
                myTimerTask = null;
                LockScreenReceiver.needToAlive = false;
                Board.figureOnDesk = new Figure[8][8];
                isEverythingSolved = true;
                LockScreenReceiver.saveInfoForOuterBoolean("isLocked2",false,lockScreenApp);
                lockScreenApp.finish();
                possibleCoordinates = null;
              } else {
                isRightMove = false;
                isInitialChessPositionReady = false;
                possibleCoordinates = null;
                Board.figureOnDesk = new Figure[8][8];
                invalidate();
              }
            }

          } else if (isRightMove) {//touching screen after right move when it is not lockScreen
            Log.d(TAG, "touching screen after right move when it is not lockScreen");

            lockScreenApp.saveInfo("currentChessPositionAfterMove", null);
            lockScreenApp.saveInfo("isRightMove", null);

            if (numberOfSuccessfulAttempt != null &&   //if we solved all levels
                    Integer.parseInt(numberOfSuccessfulAttempt) >= ChessPositions.allStartPositions.size()) {
              lockScreenApp.saveInfo("numberOfSuccessfulAttempt", null);
              lockScreenApp.saveInfo("numberOfAttempt", null);
            }

            if (evX >= 0//touching desk to exit when it is not lockScreen
                    && evX <= sideSize
                    && evY >= 0
                    && evY <= sideSize + sideSize / 7
                    ) {
              lockScreenApp.saveInfo("alreadyResolved", null);
              if (timer != null) {
                timer.cancel();
                timer = null;
              }
              myTimerTask = null;
              Board.figureOnDesk = new Figure[8][8];
              lockScreenApp.finish();
            }
            if (evX >= xNextLeft//touching "forward" icon to get new level when it is not lockScreen
                    && evX <= xNextLeft + buttonsSize
                    && evY >= yNextTop
                    && evY <= yNextTop + buttonsSize
                    ) {
              isRightMove = false;
              isInitialChessPositionReady = false;
              Board.figureOnDesk = new Figure[8][8];
              possibleCoordinates = null;
              invalidate();
            }
          }

          if (!isEverythingSolved // not time to exit
                  && evX >= xUnlockCenter - buttonsSize / 2 //touching unlock icon to enter the secret code
                  && evX <= xUnlockCenter + buttonsSize / 2
                  && evY >= yUnlockCenter - buttonsSize / 2
                  && evY <= yUnlockCenter + buttonsSize / 2
                  ) {
            isInitialChessPositionReady = false;
            if (timer != null) {
              timer.cancel();
              timer = null;
              myTimerTask = null;
            }
            LockScreenReceiver.needToAlive = false;
            LockScreenReceiver.startSecondActivity = true;
            lockScreenApp.finish();

          }

          if (evX >= xInfoLeft //touching "info" icon
                  && evX <= xInfoLeft + buttonsSize
                  && evY >= yInfoTop
                  && evY <= yInfoTop + buttonsSize
                  ) {
            createInfoDialog(getContext());
          }

          if (isWrongMove) {

            lockScreenApp.saveInfo("currentChessPositionAfterMove", null);
            lockScreenApp.saveInfo("isWrongMove", null);

            if (evX >= xNextLeft//touching "forward" icon to get new level
                    && evX <= xNextLeft + buttonsSize
                    && evY >= yNextTop
                    && evY <= yNextTop + buttonsSize
                    ) {
              isWrongMove = false;
              isInitialChessPositionReady = false;
              Board.figureOnDesk = new Figure[8][8];
              lockScreenApp.saveInfo("alreadyResolved", null);
              invalidate();
            }

            numberOfPossibleResolvingAttempts = LockScreenReceiver.loadOuterInfoInt("amountOfAttemptsToResolve3", getContext());
            if (isDrawingResolving) {
              if (evX <= xRefreshLeft + buttonsSize //touching "refresh" icon to get the same level
                      && evX >= xRefreshLeft
                      && evY >= yRefreshTop
                      && evY <= yRefreshTop + buttonsSize
                      ) {
                ChessPositions.refreshIndOfPositionAndUnsolvedLevels(oldIndex,
                        oldLoadUnsolvedLevels, oldIsLevelFromUnsolved, lockScreenApp);
                isWrongMove = false;
                isInitialChessPositionReady = false;
                Board.figureOnDesk = new Figure[8][8];

                if (numberOfAlreadyResolved == null) {
                  lockScreenApp.saveInfo("alreadyResolved", "1");
                } else if (numberOfPossibleResolvingAttempts > Integer.parseInt(numberOfAlreadyResolved)) {
                  lockScreenApp.saveInfo("alreadyResolved", Integer.toString(Integer.parseInt(numberOfAlreadyResolved) + 1));
                }
                numberOfAlreadyResolved = lockScreenApp.loadInfo("alreadyResolved");
                isDrawingResolving = false;
                lockScreenApp.saveInfo("isDrawingResolving", null);
                invalidate();
              }
            }
          } else {

            for (int i = 0; i < movingCoordinates.length; i++) {
              if ((board.allFigures[i] != null) && evX >= movingCoordinates[i].x - squareSize / 2
                      && evX <= movingCoordinates[i].x + squareSize / 3
                      && evY >= movingCoordinates[i].y - squareSize / (1.1)
                      && evY <= movingCoordinates[i].y + squareSize / 10
                      && board.allFigures[i].isWhite == isWhiteTurnToPlay) {
                itemInd = i;
                currentFigure = board.allFigures[itemInd];
                isDrawingCircles = true;
                Log.d(TAG, "______________________________");
                Log.d(TAG, "itemInd = " + itemInd + ",  " + board.allFigures[i].type);
                startX = board.allFigures[itemInd].posX;
                startY = board.allFigures[itemInd].posY;
                // Log.d(TAG,"startX = " + board.allFigures[itemInd].positionX +
                //       ", startY = " + board.allFigures[itemInd].positionY);
                board.findPossibleMove(itemInd);
                possibleCoordinates = board.possibleMove;
                isItemMoving = true;

                invalidate();

                drag = true;
                dragX = evX - movingCoordinates[itemInd].x;
                dragY = evY - movingCoordinates[itemInd].y;
              }
            }

            if (!isItemMoving) {
              //Log.d(TAG, "before second touch");
              if (possibleCoordinates != null && possibleCoordinates.size() > 0) { //after choosing item we touch one of the possible cells

                //Log.d(TAG, "before second touch 2 ");
                for (int i = 0; i < possibleCoordinates.size(); i++) {

                  if (evX - coorInDeskCellsFloatX[possibleCoordinates.get(i).x] < squareSize / 2
                          && evX - coorInDeskCellsFloatX[possibleCoordinates.get(i).x] > -squareSize / 2
                          && evY - coorInDeskCellsFloatY[possibleCoordinates.get(i).y] +
                          (squareSize / (3.1f) + squareSize / 12) < squareSize / 2
                          && evY - coorInDeskCellsFloatY[possibleCoordinates.get(i).y] +
                          (squareSize / (3.1f) + squareSize / 12) > -squareSize / 2) {

                    //Log.d(TAG, "second touch");
                    isSecondTouch = true;
                    indOfPossibleMoveSecondTouch = i;
                    movingCoordinates[itemInd].x = coorInDeskCellsFloatX[possibleCoordinates.get(i).x] - 10;
                    movingCoordinates[itemInd].y = coorInDeskCellsFloatY[possibleCoordinates.get(i).y] - 10;
                    isItemMoving = true;
                    invalidate();
                    drag = true;
                    dragX = evX - movingCoordinates[itemInd].x;
                    dragY = evY - movingCoordinates[itemInd].y;
                  }
                }
              }
            }
          }
          break;

        case MotionEvent.ACTION_MOVE:
          if (drag) {
            movingCoordinates[itemInd].x = evX - dragX;
            movingCoordinates[itemInd].y = evY - dragY;

            invalidate(Math.max(0, (int) (movingCoordinates[itemInd].x - 2 * squareSize)),
                    Math.max(0, (int) (movingCoordinates[itemInd].y - 2 * squareSize)),
                    (int) Math.min(sideSize, (int) (movingCoordinates[itemInd].x + 2 * squareSize)),
                    (int) Math.max(sideSize, (int) (movingCoordinates[itemInd].y + 2 * squareSize)));

            boolean isMoveCorrect = false;

            for (int k = 0; k < possibleCoordinates.size(); k++) {
              if ((movingCoordinates[itemInd].x >= coorInDeskCellsFloatX[possibleCoordinates.get(k).x] - squareSize / 2) &&
                      (movingCoordinates[itemInd].x <= coorInDeskCellsFloatX[possibleCoordinates.get(k).x] + squareSize / 2) &&
                      (movingCoordinates[itemInd].y >= coorInDeskCellsFloatY[possibleCoordinates.get(k).y] - squareSize / 2) &&
                      (movingCoordinates[itemInd].y <= coorInDeskCellsFloatY[possibleCoordinates.get(k).y] + squareSize / (2))) {
                Log.d(TAG, "isMoveCorrect = true;");
                movingCoordinates[itemInd].x = coorInDeskCellsFloatX[possibleCoordinates.get(k).x];
                movingCoordinates[itemInd].y = coorInDeskCellsFloatY[possibleCoordinates.get(k).y];
                isMoveCorrect = true;
                board.deskAndFigureAfterMove(itemInd, k);
              }
              if (board.isAlreadyCaptured) {
                Log.d(TAG, "time to stop");
                drag = false; //finish moving of this item
              }
            }
            if (!isMoveCorrect) {
              movingCoordinates[itemInd].x = coorInDeskCellsFloatX[board.allFigures[itemInd].posX];
              movingCoordinates[itemInd].y = coorInDeskCellsFloatY[board.allFigures[itemInd].posY];
              Log.d(TAG, "isMoveCorrect = false;");
            }
          }
          break;

        case MotionEvent.ACTION_UP:

          if (isSecondTouch) {
            movingCoordinates[itemInd].x = coorInDeskCellsFloatX[possibleCoordinates.get(indOfPossibleMoveSecondTouch).x];
            movingCoordinates[itemInd].y = coorInDeskCellsFloatY[possibleCoordinates.get(indOfPossibleMoveSecondTouch).y];
            board.deskAndFigureAfterMove(itemInd, indOfPossibleMoveSecondTouch);
            isSecondTouch = false;
            invalidate();
          }

          drag = false;
          board.isAlreadyCaptured = false;
          isItemMoving = false;
          invalidate();
          if (currentFigure != null) {
            finishX = currentFigure.posX;
            finishY = currentFigure.posY;
            //Log.d(TAG,"finishX = " + currentFigure.positionX +
            //", finishY = " + currentFigure.positionY);

            if (currentFigure.type == Figure.Type.king) {// if castling - moving rook
              int rookInd = board.deskAndFiguresAfterCastling(startX, finishX, startY);
              if (rookInd != -1) {
                movingCoordinates[rookInd].x = coorInDeskCellsFloatX[board.allFigures[rookInd].posX];
                movingCoordinates[rookInd].y = coorInDeskCellsFloatY[board.allFigures[rookInd].posY];

                invalidate(Math.max(0, (int) (movingCoordinates[itemInd].x - 2 * squareSize)),
                        Math.max(0, (int) (movingCoordinates[itemInd].y - 5 * squareSize)),
                        (int) Math.min(sideSize, (int) (movingCoordinates[itemInd].x + 2 * squareSize)),
                        (int) Math.max(sideSize, (int) (movingCoordinates[itemInd].y + 5 * squareSize)));
              }
            }

            if (currentFigure.type == Figure.Type.pawn
                    && (currentFigure.isWhite && currentFigure.posY == 7
                    || !currentFigure.isWhite && currentFigure.posY == 0)) {
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
                invalidate(Math.max(0, (int) (movingCoordinates[itemInd].x - 2 * squareSize)),
                        Math.max(0, (int) (movingCoordinates[itemInd].y - 2 * squareSize)),
                        (int) Math.min(sideSize, (int) (movingCoordinates[itemInd].x + 2 * squareSize)),
                        (int) Math.max(sideSize, (int) (movingCoordinates[itemInd].y + 2 * squareSize)));

                Log.d(TAG, "after dialog invalidate ");
                isNewItemChosen = false;
                isNewDeskReady = false;
              }
            }
          }

          if (currentFigure != null && (startX != finishX || startY != finishY)) { //check if this move was write or wrong

            char figureTypeChar = ' ';
            char colorChar;
            switch (currentFigure.type) {
              case rook:
                figureTypeChar = 'R';
                break;
              case knight:
                figureTypeChar = 'N';
                break;
              case bishop:
                figureTypeChar = 'B';
                break;
              case queen:
                figureTypeChar = 'Q';
                break;
              case king:
                figureTypeChar = 'K';
                break;
              case pawn:
                figureTypeChar = 'P';
                break;
            }
            if (currentFigure.isWhite) {
              colorChar = 'w';
            } else {
              colorChar = 'b';
            }

            if (startX == (ChessPositions.allAnswers.get(indOfPosition).charAt(2) - 1 - 48)
                    && startY == (ChessPositions.allAnswers.get(indOfPosition).charAt(3) - 1 - 48)
                    && finishX == (ChessPositions.allAnswers.get(indOfPosition).charAt(4) - 1 - 48)
                    && finishY == (ChessPositions.allAnswers.get(indOfPosition).charAt(5) - 1 - 48)
                    && figureTypeChar == ChessPositions.allAnswers.get(indOfPosition).charAt(1)
                    && colorChar == ChessPositions.allAnswers.get(indOfPosition).charAt(0)) {

              //right move
              isDrawingCircles = false;
              refreshNumberOfAttempt(true);
              //Log.d(TAG, "current position after right move = " + board.currentChessPositionAfterMove() + whoseTurnToPlay);
              lockScreenApp.saveInfo("currentChessPositionAfterMove", board.currentChessPositionAfterMove() + whoseTurnToPlay);
              //Log.d(TAG,"we solved level " + indOfPosition);
              //Log.d(TAG,"ChessPositions.allStartPositions.size() = " + ChessPositions.allStartPositions.size());
              ChessPositions.removeSolvedPositionFromUnsolvedPositions(indOfPosition, lockScreenApp);
              int indOfNextPosition = ChessPositions.findIndexOfNextPositionAfterRightMove(indOfPosition, lockScreenApp);
              lockScreenApp.saveInfo("indOfPosition", Integer.toString(indOfNextPosition));
              //Log.d(TAG,"saved next level " + lockScreenApp.loadInfo("indOfPosition"));
              if (timer != null) {
                timer.cancel();
                timer = null;
              }
              myTimerTask = null;
              isRightMove = true;
              lockScreenApp.saveInfo("isRightMove", "true");
              invalidate();

            } else {
              //wrong move,     change problem, unsolved problem move to the end of arraylist
              Log.d(TAG, "   !!!    wrong move");
              isWorkingWithHandler = true;
              isDrawingCircles = false;
              refreshNumberOfAttempt(false);
              lockScreenApp.saveInfo("currentChessPositionAfterMove", board.currentChessPositionAfterMove() + whoseTurnToPlay);
              //Log.d(TAG, "current position after wrong move = " + board.currentChessPositionAfterMove()+whoseTurnToPlay);
              numberOfSuccessfulAttempt = lockScreenApp.loadInfo("numberOfSuccessfulAttempt");

              oldIndex = indOfPosition;
              oldLoadUnsolvedLevels = lockScreenApp.loadUnsolvedLevels();
              oldIsLevelFromUnsolved = lockScreenApp.loadInfo("isLevelFromUnsolved");

              ChessPositions.saveUnsolvedPosition(indOfPosition, lockScreenApp);
              indOfPosition = ChessPositions.findIndexOfNextPositionAfterWrongMove(indOfPosition, lockScreenApp);
              //Log.d(TAG,"   !!!    wrong move indOfNextPosition = " + indOfPosition);
              lockScreenApp.saveInfo("indOfPosition", Integer.toString(indOfPosition));

              if (timer != null) {
                timer.cancel();
                timer = null;
              }
              myTimerTask = null;

              isWrongMove = true;
              lockScreenApp.saveInfo("isWrongMove", "true");
              numberOfAlreadyResolved = lockScreenApp.loadInfo("alreadyResolved");
              numberOfPossibleResolvingAttempts = LockScreenReceiver.loadOuterInfoInt("amountOfAttemptsToResolve3", getContext());

              if (numberOfAlreadyResolved == null && numberOfPossibleResolvingAttempts > 1
                      || numberOfAlreadyResolved != null && numberOfPossibleResolvingAttempts > Integer.parseInt(numberOfAlreadyResolved) + 1) {
                isDrawingResolving = true;
                lockScreenApp.saveInfo("isDrawingResolving", "true");
              }
              invalidate();
            }
            startX = finishX;
            startY = finishY;
          }
          break;
      }
    } else {
      Log.d(TAG, "problem with motion coordinates");
    }
    return true;
  }


  protected void afterDialogPawn() {
    board.deskAndFiguresAfterPawnBecomesAnotherItem(this, chosenItem, itemInd);

  }


  private boolean isWorkingWithHandler = false;


  private void drawAfterMove(Canvas canvas, boolean isRight) {
    Bitmap bitmap;
    if (isRight) {
      bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yes200);
    } else {
      bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no2002);
    }
    drawBitmap(canvas, bitmap, (int) sideSize / 3, (int) sideSize / 3, (int) (2 * sideSize / 3), (int) (2 * sideSize / 3));
    if (!isRight) {
      drawBitmapWithColorFilter(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.button7),//next64
              xNextLeft, yNextTop, xNextLeft + buttonsSize, yNextTop + buttonsSize);
      canvas.drawText(forward, xNextLeft + (buttonsSize - forwardWidth) / 2, yNextTop + buttonsSize / 2 + forwardHeight / 2.4f, iconPaint);
      if (isDrawingResolving) {
        drawBitmapWithColorFilter(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.button7),//refresh64
                xRefreshLeft, yRefreshTop, xRefreshLeft + buttonsSize, yRefreshTop + buttonsSize);
        canvas.drawText(refresh, xRefreshLeft + (buttonsSize - refreshWidth) / 2, yRefreshTop + buttonsSize / 2 + refreshHeight / 2.3f, iconPaint);
      }

      if (isWorkingWithHandler) {
        isWorkingWithHandler = false;
        int kingInd = board.findKingIndex();
        int oldX;
        int oldY;
        int[] safetyMove;

        if (board.isCheck(kingInd, board.allFigures[kingInd].isWhite)) {
          oldX = board.allFigures[kingInd].posX;
          oldY = board.allFigures[kingInd].posY;
          safetyMove = board.findSafetyMove(kingInd, board.allFigures[kingInd].isWhite);
          lockScreenApp.handler.sendMessageDelayed(lockScreenApp.handler.obtainMessage(kingInd, safetyMove[0], safetyMove[1]), 1000);
          lockScreenApp.handler.sendMessageDelayed(lockScreenApp.handler.obtainMessage(kingInd, oldX, oldY), 2000);
        } else {
          safetyMove = board.findArbitrarySafetyMove(board.allFigures[kingInd].isWhite);
          if (safetyMove != null) {
            oldX = board.allFigures[safetyMove[2]].posX;
            oldY = board.allFigures[safetyMove[2]].posY;
            lockScreenApp.handler.sendMessageDelayed(lockScreenApp.handler.obtainMessage(safetyMove[2], safetyMove[0], safetyMove[1]), 1000); //1 - item index , 2 - xCoor, 3 - yCoor
            lockScreenApp.handler.sendMessageDelayed(lockScreenApp.handler.obtainMessage(safetyMove[2], oldX, oldY), 2000);
          } else {
            Log.d(TAG, "____no winners_____stalemate__!!!!");
          }
        }
      }


    }

    if (isRight && !lockScreenApp.isLockScreen) {
      drawBitmapWithColorFilter(canvas, BitmapFactory.decodeResource(getResources(), R.drawable.button7),//next64
              xNextLeft, yNextTop, xNextLeft + buttonsSize, yNextTop + buttonsSize);
      canvas.drawText(forward, xNextLeft + (buttonsSize - forwardWidth) / 2, yNextTop + buttonsSize / 2 + forwardHeight / 2.4f, iconPaint);
    }
  }


  private void refreshNumberOfAttempt(boolean isRightMove) {
    numberOfAttempt = lockScreenApp.loadInfo("numberOfAttempt");
    if (numberOfAttempt != null) {
      numberOfAttempt = Integer.toString(Integer.parseInt(numberOfAttempt) + 1);
      lockScreenApp.saveInfo("numberOfAttempt", numberOfAttempt);
    } else {
      numberOfAttempt = "1";
      lockScreenApp.saveInfo("numberOfAttempt", numberOfAttempt);
    }
    if (isRightMove) {

      numberOfSuccessfulAttempt = lockScreenApp.loadInfo("numberOfSuccessfulAttempt");
      if (numberOfSuccessfulAttempt != null) {
        numberOfSuccessfulAttempt = Integer.toString(Integer.parseInt(numberOfSuccessfulAttempt) + 1);
        lockScreenApp.saveInfo("numberOfSuccessfulAttempt", numberOfSuccessfulAttempt);
      } else {
        numberOfSuccessfulAttempt = "1";
        lockScreenApp.saveInfo("numberOfSuccessfulAttempt", numberOfSuccessfulAttempt);
      }

      if (lockScreenApp.isLockScreen) {
        numberOfJustSolvedLevels = lockScreenApp.loadInfo("amountOfJustSolvedLevels");
        if (numberOfJustSolvedLevels != null) {
          numberOfJustSolvedLevels = Integer.toString(Integer.parseInt(numberOfJustSolvedLevels) + 1);
          lockScreenApp.saveInfo("amountOfJustSolvedLevels", numberOfJustSolvedLevels);
        } else {
          numberOfJustSolvedLevels = "1";
          lockScreenApp.saveInfo("amountOfJustSolvedLevels", numberOfJustSolvedLevels);
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

  private void createInfoDialog(Context context) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(getResources().getString(R.string.Results))
            .setMessage(prepareResults())
            .setCancelable(false)
            .setNegativeButton(getResources().getString(R.string.Ok),
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                      }
                    });
    AlertDialog alert = builder.create();
    alert.show();
  }

  private String prepareResults() {
    if (numberOfAttempt == null) {
      numberOfAttempt = "0";
    }
    if (numberOfSuccessfulAttempt == null) {
      numberOfSuccessfulAttempt = "0";
    }
    return text1 + "\n" + text2 + " " + numberOfSuccessfulAttempt + "\n" + text3 + " " + numberOfAttempt;
  }

  class MyTimerTask extends TimerTask {
    @Override
    public void run() {
      Log.d(TAG, "_____!!!!!   in run() of timer task");
      lockScreenApp.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          invalidate();
          Log.d(TAG, "runnable _ invalidating chessView");
        }
      });
    }
  }


  protected void handlerMethod(android.os.Message msg) {
    movingCoordinates[msg.what].x = coorInDeskCellsFloatX[msg.arg1];
    movingCoordinates[msg.what].y = coorInDeskCellsFloatY[msg.arg2];
    invalidate();
  }


}

