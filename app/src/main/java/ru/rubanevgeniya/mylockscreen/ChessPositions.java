package ru.rubanevgeniya.mylockscreen;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class ChessPositions {
  protected static ArrayList<String> allStartPositions;
  protected static ArrayList<String> allAnswers;
  private static String TAG = "Log ChessPositions = ";

  protected static void createInitialChessPosition() {
    Log.d(TAG,"createInitialChessPosition()" );
    allStartPositions = new ArrayList<String>();
    allAnswers = new ArrayList<String>();
    //allStartPositions.add("bK78wB77wK76wN36w");
    allStartPositions.add("bp57bK78wB77wK76wN36w");
    allAnswers.add("wN3657");
    allStartPositions.add("bB56bp66bp55wp54bK13wp63bp22wR32wK21wN41b");
    allAnswers.add("bB5612");
    allStartPositions.add("bR58bR68bK78bp27bp37bp67bP87bp16bN36bB46bp76wN74wp13wB33wp22wp32wp62wP72wp82wR41wR61wK71w");
    allAnswers.add("wN7486");
    allStartPositions.add("bR18bR78bK88bP77bP87bP16bB36wN86wp83wp12bQ22wp62wp72wR11wR51wK71w");
    allAnswers.add("wN8667");
  }

  protected static void saveUnsolvedPosition(int indOfPosition, LockScreenApp lockScreenApp) {
    Set<String> loadUnsolvedLevels = lockScreenApp.loadUnsolvedLevels();
    //if (!(loadUnsolvedLevels != null)) {
    if (loadUnsolvedLevels == null) {
      Log.d(TAG, "saving 1-st unsolved position " + indOfPosition);
      Set<String> setOfUnsolvedPositions = new HashSet<>();
      setOfUnsolvedPositions.add(Integer.toString(indOfPosition));
      lockScreenApp.saveUnsolvedLevels(setOfUnsolvedPositions);
    } else {
      Log.d(TAG, "saving (adding) unsolved position " + indOfPosition);
      loadUnsolvedLevels.add(Integer.toString(indOfPosition));
      lockScreenApp.saveUnsolvedLevels(loadUnsolvedLevels);
    }
  }

  protected static void removeSolvedPositionFromUnsolvedPositions(int indOfPosition, LockScreenApp lockScreenApp) {
    Set<String> loadUnsolvedLevels = lockScreenApp.loadUnsolvedLevels();
    if (loadUnsolvedLevels != null) {
      loadUnsolvedLevels.remove(Integer.toString(indOfPosition));
      lockScreenApp.saveUnsolvedLevels(loadUnsolvedLevels);
    }
  }

  protected static void refreshIndOfPositionAndUnsolvedLevels(int oldIndex,
                                                              Set<String> oldLoadUnsolvedLevels, String oldIsLevelFromUnsolved, LockScreenApp lockScreenApp) {
    lockScreenApp.saveInfo("indOfPosition", Integer.toString(oldIndex));
    lockScreenApp.saveInfo("isLevelFromUnsolved", oldIsLevelFromUnsolved);
    lockScreenApp.saveUnsolvedLevels(oldLoadUnsolvedLevels);

  }

  protected static int findIndexOfNextPositionAfterRightMove(int indOfPosition, LockScreenApp lockScreenApp) {
    int index = -1;
    Set<String> loadUnsolvedLevels = lockScreenApp.loadUnsolvedLevels();
    Log.d(TAG, "loadUnsolvedLevels = " + loadUnsolvedLevels);
    if (lockScreenApp.loadInfo("isLevelFromUnsolved") == null) {//first "round" of indexes
      if (indOfPosition < allStartPositions.size() - 1) {
        Log.d(TAG, "  _1 first round of indexes__");
        index = indOfPosition + 1;
      }
      if (indOfPosition == allStartPositions.size() - 1) {//first "round" finished
        if (loadUnsolvedLevels == null//!= null
                || loadUnsolvedLevels.size() == 0) {//was no unsolved level during first round
          Log.d(TAG, "  _2 first \"round\" finished_ start from the beginning!!!!!_");
          index = 0;   //   start from the beginning!!!!!

        } else {
          lockScreenApp.saveInfo("isLevelFromUnsolved", "levelFromUnsolved");// start solving position from unsolved positions
          Object[] unsolvedLevels = loadUnsolvedLevels.toArray();
          Log.d(TAG, "  _3_start solving position from unsolved positions_");
          index = Integer.parseInt(unsolvedLevels[0].toString());
        }
      }
    } else {// indexes from unsolved levels
      Object[] unsolvedLevels = loadUnsolvedLevels.toArray();
      if (unsolvedLevels.length == 0) {
        Log.d(TAG, "  _4  solved all levels from unsolvedLevels, so start from the beginning__");
        index = 0; //solved all levels from unsolvedLevels, so start from the beginning
        lockScreenApp.saveInfo("isLevelFromUnsolved", null);

      } else {
        Log.d(TAG, "  _5 indexes from unsolved levels__");
        index = Integer.parseInt(unsolvedLevels[0].toString());
      }
    }

    return index;
  }

  protected static int findIndexOfNextPositionAfterWrongMove(int indOfPosition, LockScreenApp lockScreenApp) {
    int index = -1;
    Set<String> loadUnsolvedLevels = lockScreenApp.loadUnsolvedLevels();
    if (lockScreenApp.loadInfo("isLevelFromUnsolved") == null) {//first "round" of indexes
      if (indOfPosition < allStartPositions.size() - 1) {
        Log.d(TAG, "  _1 first \"round\" of indexes__");
        index = indOfPosition + 1;
      }
      if (indOfPosition == allStartPositions.size() - 1) {//first "round" finished
        if (loadUnsolvedLevels == null) {//was no unsolved level during first round
          Log.d(TAG, "  _2 first \"round\" finished_solving last level again and again_");
          index = indOfPosition;   //   solving last level again and again
        } else {
          lockScreenApp.saveInfo("isLevelFromUnsolved", "levelFromUnsolved");// start solving position from unsolved positions
          Object[] unsolvedLevels = loadUnsolvedLevels.toArray();
          Log.d(TAG, "  _3_start solving position from unsolved positions_");
          index = Integer.parseInt(unsolvedLevels[0].toString());
        }
      }
    } else {// indexes from unsolved levels
      Log.d(TAG, "  _5_indexes from unsolved levels_");
      Object[] unsolvedLevels = loadUnsolvedLevels.toArray();
      int j = 0;
      do {
        index = Integer.parseInt(unsolvedLevels[j].toString());
        j++;
      } while (index == indOfPosition && unsolvedLevels.length > 1);
    }
    return index;
  }

}