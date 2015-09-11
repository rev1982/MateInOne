package ru.rubanevgeniya.mateinone;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Set;

public class LockScreenApp extends PhoenixActivity {
  public static final String FONTS_FONTAWESOME = "fonts/fontawesome.ttf";
  protected BoardView boardView;
  protected ChessView chessView;
  protected BoardViewLand boardViewLand;
  protected boolean isLockScreen;
  private String toUnlock;
  protected Handler handler;
  private Toast toast;
  protected static Typeface typeface;
  protected String CODE;
  protected static final int DELTA = 16;

  public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    typeface = Typefaces.get(this, FONTS_FONTAWESOME);//Typeface.createFromAsset(getContext().getAssets(), "fonts/fontawesome.woff");
    setContentView(R.layout.activity_merge);
    PhoneStateListern.lockScreenApp = this;
    LockScreenReceiver.lockScreenApp = this;

    boardView = (BoardView) findViewById(R.id.BoardView);
    chessView = (ChessView) findViewById(R.id.ChessView);
    boardViewLand = (BoardViewLand) findViewById(R.id.BoardViewLand);
    toUnlock = getResources().getString(R.string.toUnlock);
    handler = new MyHandler(this);
    CODE = LockScreenReceiver.loadOuterInfoString("code2", this);
    if (LockScreenReceiver.loadOuterInfoBoolean("isLocked2", this)
            && !CODE.equals(LockScreenReceiver.INVALID_STR)
            && LockScreenReceiver.loadOuterInfoInt("amountOfLevelsToSolve3", this) > 0) {
      isLockScreen = true;
      LockScreenReceiver.needToAlive = true;
    } else {
      isLockScreen = false;
      LockScreenReceiver.needToAlive = false;
      LockScreenReceiver.saveInfoForOuterBoolean("isLocked2", false, this);
    }
    try {
      // initialize receiver
      startService(new Intent(this, ServiceToStartReceiver.class));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void saveInfo(String key, String value) {
    SharedPreferences save = getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor = save.edit();
    editor.putString(key, value);
    editor.apply();
  }

  protected void saveInfoInt(String key, int value) {
    SharedPreferences save = getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor = save.edit();
    editor.putInt(key, value);
    editor.apply();
  }

  protected int loadInfoInt(String key) {
    SharedPreferences save = getPreferences(MODE_PRIVATE);
    return save.getInt(key, -1000);
  }

  protected void saveInfoBoolean(String key, boolean value) {
    SharedPreferences save = getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor = save.edit();
    editor.putBoolean(key, value);
    editor.apply();
  }

  protected boolean loadInfoBoolean(String key) {
    SharedPreferences save = getPreferences(MODE_PRIVATE);
    return save.getBoolean(key, false);
  }

  protected void saveUnsolvedLevels(Set<String> unsolvedLevels, boolean isNew) {
    SharedPreferences save = getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor = save.edit();
    editor.putStringSet(isNew ? "unsolvedLevels" : "oldUnsolvedLevels", unsolvedLevels);
    editor.apply();
  }

  protected Set<String> loadUnsolvedLevels(boolean isNew) {
    SharedPreferences load = getPreferences(MODE_PRIVATE);
    return load.getStringSet(isNew ? "unsolvedLevels" : "oldUnsolvedLevels", null);
  }

  protected String loadInfo(String key) {
    SharedPreferences save = getPreferences(MODE_PRIVATE);
    return save.getString(key, null);
  }

  @Override
  public void onDestroy() {
    if (chessView.timer != null) {
      chessView.timer.cancel();
      chessView.timer = null;
    }
    chessView = null;
    if (handler != null) {
      handler.removeCallbacksAndMessages(null);
    }
    super.onDestroy();
  }

  @Override
  public void onBackPressed() {
    if (isLockScreen) {
      if (toast != null) {
        toast.cancel();
      }
      toast = Toast.makeText(this, toUnlock, Toast.LENGTH_LONG);
      toast.show();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public void onResume() {
    if (isLockScreen) {
      LockScreenReceiver.needToAlive = true;
    }
    super.onResume();
  }

  @Override
  public void onPause() {
    if (chessView.alert != null) {
      chessView.alert.dismiss();
    }
    super.onPause();
  }

  static class MyHandler extends Handler {
    WeakReference<LockScreenApp> wrActivity;

    public MyHandler(LockScreenApp activity) {
      wrActivity = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      LockScreenApp activity = wrActivity.get();
      if (activity != null) {
        activity.chessView.handlerMethod(msg);
      }
    }
  }
}
