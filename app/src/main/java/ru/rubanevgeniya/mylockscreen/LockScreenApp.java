package ru.rubanevgeniya.mylockscreen;
// TODO: remove unneded resources and fonts
// TODO: auto-format all java and xml files (Ctrl-A, Ctrl-Alt-L)
// TODO: remove unneded comments
// TODO: remove unneded imports
// TODO: remove unneded methods
// TODO: remove more than 2 subsequent empty lines
// TODO: remove debug output Log.d(), Log.i() and System.out, System err
// TODO: find and execute all TODOs in all files
// TODO: check method access: most method should be protected or private. public if needed, package-access is unusual

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import java.lang.ref.WeakReference;
import java.util.Set;

public class LockScreenApp extends PhoenixActivity {
  protected BoardView boardView;
  protected ChessView chessView;
  protected BoardViewLand boardViewLand;
  protected boolean isLockScreen;
  private String toUnlock;
  private static String TAG = "Logs LockScreenApp = ";
  protected Handler handler;


  public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    Log.d(TAG, "_____________________________creating app!!!______________");
    setContentView(R.layout.activity_merge);
    MyPhoneStateListener.lockScreenApp = this;
    LockScreenReceiver.lockScreenApp = this;
    boardView = (BoardView) findViewById(R.id.BoardView);
    chessView = (ChessView) findViewById(R.id.ChessView);
    boardViewLand = (BoardViewLand) findViewById(R.id.BoardViewLand);
    toUnlock = getResources().getString(R.string.toUnlock);
    handler = new MyHandler(this);
    isLockScreen = LockScreenReceiver.loadOuterInfoBoolean("isLocked2",this);
      if (isLockScreen) {
        LockScreenReceiver.needToAlive = true;
        Log.d(TAG, "  lock screen !!!");
      } else {
        LockScreenReceiver.needToAlive = false;
        Log.d(TAG, "  not lock screen !!!");
      }

    try {
      // initialize receiver
      startService(new Intent(this, MyService2.class));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void saveInfo(String key, String value) {
    SharedPreferences save = getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor = save.edit();
    editor.putString(key, value);
    editor.commit();
  }


  protected void saveUnsolvedLevels(Set<String> unsolvedLevels) {
    SharedPreferences save = getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor = save.edit();
    editor.putStringSet("unsolvedLevels", unsolvedLevels);
    editor.commit();
  }

  protected Set<String> loadUnsolvedLevels() {
    SharedPreferences save = getPreferences(MODE_PRIVATE);
    return save.getStringSet("unsolvedLevels", null);
  }

  protected String loadInfo(String key) {
    SharedPreferences save = getPreferences(MODE_PRIVATE);
    return save.getString(key, null);
  }

//  protected String loadOuterInfo(String key) {//to get info from other activity
//    SharedPreferences load = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//    return load.getString(key, null);
//  }


//    // TODO: extract a constant with a self-describing name

  @Override
  public void onDestroy() {
    if (chessView.timer != null) {
      chessView.timer.cancel();
      chessView.timer = null;
    }
    chessView.myTimerTask = null;
    Log.d(TAG, " lockScreenApp is on destroy !!");
    chessView = null;
    if (handler != null) {
      handler.removeCallbacksAndMessages(null);
    }
    super.onDestroy();
  }


  @Override
  public void onBackPressed() {
    if (isLockScreen) {
      Toast toast = Toast.makeText(this, toUnlock, Toast.LENGTH_LONG);
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
    Log.d(TAG, " lockScreenApp onResume");
    super.onResume();

  }


  @Override
  public void onStart() {
    super.onStart();
    Log.d(TAG, " lockScreenApp onStart");
  }

  @Override
  public void onRestart() {
    Log.d(TAG, " lockScreenApp onRestart");
    super.onRestart();
  }


  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      Log.d(TAG, " lockScreenApp got focus");
    } else {
      Log.d(TAG, " lockScreenApp lost focus");
    }
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
