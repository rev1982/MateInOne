package ru.rubanevgeniya.mateinone;


import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.PowerManager;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import java.util.List;


public class LockScreenReceiver extends BroadcastReceiver {
  public static final int INVALID = -1000;
  public static final String INVALID_STR = "-1000";
  protected static Class onTop;
  protected static boolean needToAlive;
  protected static boolean isPhoneCalling;
  protected static LockScreenApp lockScreenApp;
  protected static SecondActivity secondActivity;
  protected static SettingsActivity settingsActivity;
  private boolean noApplicationStart;
  private Intent intent2;
  private long timeOfCreatingIntent2;
  protected static boolean startSecondActivityUnlock;
  protected static boolean startSecondActivitySettings;
  protected static boolean isStartSettings;



  public LockScreenReceiver() {

  }

  @Override
  public void onReceive(Context context, Intent intent) {
    long timeOfDelay;
    int delay = loadOuterInfoInt("delay3", context);
    timeOfDelay = (delay <= 0) ? 10000 : 1000 * delay;
    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    long currentTime = System.currentTimeMillis();
    long lastDecisionTime = loadOuterInfoLong("time2", context);
    int numOfLevelsToSolveToUnlockWhenScreenOn = loadOuterInfoInt("amountOfLevelsToSolve3", context);
    Long deltaTime = calcDeltaTime(loadOuterInfoInt("days3", context), loadOuterInfoInt("hours3", context), loadOuterInfoInt("minutes3", context));

    if (intent.getAction() == null) {
      //restart app after   pressing home button (using AlarmService) if no phone calls and screen is On!!
      if (intent.getIntExtra("code", 0) > 0) {
        if ((onTop == null) && !isPhoneCalling && pm.isScreenOn() &&
                (lastDecisionTime == INVALID
                        || lastDecisionTime + deltaTime < currentTime)) {

          Intent intent11;
          if (intent.getIntExtra("code", 0) == 1111) {
            if (lockScreenApp != null) {
              needToAlive = false;
              lockScreenApp.finish();
            }
            intent11 = new Intent(context, LockScreenApp.class);
            intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent11);
          } else if (intent.getIntExtra("code", 0) == 5555) {
            if (secondActivity != null) {
              needToAlive = false;
              secondActivity.finish();
            }
            intent11 = new Intent(context, SecondActivity.class);
            intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent11.putExtra("what", isStartSettings ? "settings" : "unlock");
            context.startActivity(intent11);
          }
        } else if (!pm.isScreenOn()) {
          finishLockScreenApp();
        }
      }
    } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
      finishLockScreenApp();
      saveInfoForOuterBoolean("isLocked2", false, context);
      if (System.currentTimeMillis() < timeOfCreatingIntent2 + timeOfDelay) {
        if (intent2 != null) {
          intent2.putExtra("code", -1111);
          timeOfCreatingIntent2 = 0;
        }
      }

    } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON) && !isPhoneCalling
            && numOfLevelsToSolveToUnlockWhenScreenOn > 0) {
      ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
      List<ActivityManager.RunningAppProcessInfo> runningAppProcess = activityManager.getRunningAppProcesses();
      String firstProcess = runningAppProcess.get(0).pkgList[0];

      if ((firstProcess.contains("clock") || firstProcess.contains("alarm"))
              && context.getPackageManager().checkPermission("android.permission.WAKE_LOCK", firstProcess) == 0) {
        noApplicationStart = true;
      } else {
        final AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        final AudioManager.OnAudioFocusChangeListener audioListener = new AudioManager.OnAudioFocusChangeListener() {
          @Override
          public void onAudioFocusChange(int focusChange) {

          }
        };
        int requestAudio = audio.requestAudioFocus(audioListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (requestAudio != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
          noApplicationStart = true;
        }
        SystemClock.sleep(1000);
        requestAudio = audio.requestAudioFocus(audioListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (requestAudio != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
          noApplicationStart = true;
        }
        audio.abandonAudioFocus(audioListener);

        if (!noApplicationStart) {
          createPendingIntent(context, timeOfDelay);
        }
      }
      noApplicationStart = false;

    } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) && !isPhoneCalling
            && numOfLevelsToSolveToUnlockWhenScreenOn > 0) {
      createPendingIntent(context, timeOfDelay);
    }
  }

  protected void createPendingIntent(Context context, long timeOfDelay) {
    AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    intent2 = new Intent(context, LockScreenReceiver.class);
    intent2.putExtra("code", 1111);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent2,
            PendingIntent.FLAG_CANCEL_CURRENT);
    alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + timeOfDelay, pendingIntent);
    timeOfCreatingIntent2 = System.currentTimeMillis();
    if (!loadOuterInfoString("code2", context).equals(INVALID_STR)) {
      saveInfoForOuterBoolean("isLocked2", true, context);
    }
  }

  protected static long loadOuterInfoLong(String key, Context context) {
    SharedPreferences load = PreferenceManager.getDefaultSharedPreferences(context);
    return load.getLong(key, INVALID);
  }

  protected static String loadOuterInfoString(String key, Context context) {
    SharedPreferences load = PreferenceManager.getDefaultSharedPreferences(context);
    return load.getString(key, INVALID_STR);
  }

  protected static Boolean loadOuterInfoBoolean(String key, Context context) {
    SharedPreferences load = PreferenceManager.getDefaultSharedPreferences(context);
    return load.getBoolean(key, false);
  }

  protected static void saveInfoForOuterBoolean(String key, Boolean value, Context context) {
    SharedPreferences save = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = save.edit();
    editor.putBoolean(key, value);
    editor.apply();
  }

  protected static void saveInfoForOuterInt(String key, int value, Context context) {
    SharedPreferences save = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = save.edit();
    editor.putInt(key, value);
    editor.apply();
  }

  protected static void saveInfoForOuterLong(String key, long value, Context context) {
    SharedPreferences save = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = save.edit();
    editor.putLong(key, value);
    editor.apply();
  }

  protected static void saveInfoForOuterString(String key, String value, Context context) {
    SharedPreferences save = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = save.edit();
    editor.putString(key, value);
    editor.apply();
  }

  protected static int loadOuterInfoInt(String key, Context context) {
    SharedPreferences load = PreferenceManager.getDefaultSharedPreferences(context);
    return load.getInt(key, INVALID);
  }

  private long calcDeltaTime(int days, int hours, int minutes) {
    long resultInSeconds = 0;
    if (days != INVALID) {
      resultInSeconds = resultInSeconds + days * 24 * 3600;
    }
    if (hours != INVALID) {
      resultInSeconds = resultInSeconds + hours * 3600;
    }
    if (minutes != INVALID) {
      resultInSeconds = resultInSeconds + days * 60;
    }
    return resultInSeconds;
  }

  private void finishLockScreenApp() {
    LockScreenReceiver.needToAlive = false;
    if (lockScreenApp != null) {
      lockScreenApp.finish();
    }
    if (secondActivity != null) {
      secondActivity.finish();
    }
    if (settingsActivity != null) {
      settingsActivity.finish();
    }
  }
}


