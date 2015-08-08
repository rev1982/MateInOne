package ru.rubanevgeniya.mylockscreen;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class PhoenixActivity extends Activity {
  private static final String TAG = "Log PhoenixActivity : ";
  private static final int CODE1 = 1111;// to lockScreenActivity
  private static final int CODE2 = 5555;// to SecondActivity

  @Override
  protected void onPause() {
    super.onPause();
    int code;
    Log.d(TAG, "on pause");
    Log.d(TAG, "LockScreenReceiver.onTop = " + LockScreenReceiver.onTop + "; LockScreenReceiver.needToAlive = " + LockScreenReceiver.needToAlive);
    if (LockScreenReceiver.onTop.toString().contains("SecondActivity")) {
      code = CODE2;
    } else {
      code = CODE1;
    }
    if (LockScreenReceiver.onTop == getClass()) {
      LockScreenReceiver.onTop = null;
    }
    if (LockScreenReceiver.needToAlive) {
      Log.d(TAG, "creating pendingIntent in onPause()");
      AlarmManager alarmMgr = (AlarmManager) this.getSystemService(ALARM_SERVICE);
      Intent intent = new Intent(this, LockScreenReceiver.class);
      intent.putExtra("code", code);
      PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
              PendingIntent.FLAG_CANCEL_CURRENT);
      alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 5000, pendingIntent);
    } else if (!LockScreenReceiver.needToAlive && LockScreenReceiver.startSecondActivityUnlock) {
      Log.d(TAG, " start SecondActivityUnlock from lockScreenApp.onPause ");
      Intent intent = new Intent(this, SecondActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.putExtra("what","unlock");
      startActivity(intent);
      LockScreenReceiver.startSecondActivityUnlock = false;
    } else if (!LockScreenReceiver.needToAlive && LockScreenReceiver.startSecondActivitySettings) {
      Log.d(TAG, " start SecondActivitySettings from lockScreenApp.onPause ");
      Intent intent = new Intent(this, SecondActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.putExtra("what","settings");
      startActivity(intent);
      LockScreenReceiver.startSecondActivitySettings = false;
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    LockScreenReceiver.onTop = getClass();
  }
}
