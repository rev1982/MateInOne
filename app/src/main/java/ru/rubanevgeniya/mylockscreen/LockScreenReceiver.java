package ru.rubanevgeniya.mylockscreen;


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
import android.util.Log;

import java.util.List;


public class LockScreenReceiver extends BroadcastReceiver {
  public static final int INVALID = -1000;
  protected static Class onTop;
  protected static boolean needToAlive;
  protected static boolean isPhoneCalling;
  protected static LockScreenApp lockScreenApp;
  protected static SecondActivity secondActivity;
  protected static SettingsActivity settingsActivity;
  private boolean noApplicationStart;
  private Intent intent2;
  private long timeOfCreatingIntent2;
  private static String TAG = "Log LockScreenReceiver : ";
  //protected static boolean needToStartLock;
  protected static boolean startSecondActivityUnlock;
  protected static boolean startSecondActivitySettings;
  protected  static boolean isStartSettings;


  public LockScreenReceiver() {
    Log.i("LockScreenReceiver", "constructor");
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    long timeOfDelay;
    int delay = loadOuterInfoInt("delay3", context);
    timeOfDelay = (delay <= 0) ? 10000 : 1000 * delay;
    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    long currentTime = System.currentTimeMillis();
    long lastDecisionTime = loadOuterInfoLong("time2", context);
    int numOfLevelsToSolveToUnlockWhenScreenOn = loadOuterInfoInt("amountOfLevelsToSolve3",context);
    Long deltaTime = calcDeltaTime(loadOuterInfoInt("days3", context), loadOuterInfoInt("hours3", context), loadOuterInfoInt("minutes3", context));
    Log.d(TAG, " point 3 , ");
    Log.i(TAG, " intent.getAction() " + intent.getAction() + "; intent.getCategories() =  "
            + intent.getCategories());
    Log.d(TAG, " screenOn = " + pm.isScreenOn());
    Log.d(TAG, " onTop = " + onTop);
    Log.d(TAG, " intent.getIntExtra(\"code\",0) = " + intent.getIntExtra("code", 0));

    if (intent.getAction() == null) {
      //restart app after pressing home button (using AlarmService) if no phone calls and screen is On
      if (intent.getIntExtra("code", 0) > 0) {
        if ((onTop == null) && !isPhoneCalling && pm.isScreenOn() &&
                (lastDecisionTime == INVALID
                        || lastDecisionTime + deltaTime < currentTime)) {

          Log.d(TAG, " point 3.0.1 start activity ");
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
          //needToStartLock = false;
        } else if (!pm.isScreenOn()) {
          Log.d(TAG, " point 3.0.2 screen off , finish ");
          finishLockScreenApp();
        }
      } else {
        Log.d(TAG, " point 3.0.3 wrong intent.getIntExtra, don't start activity ");
      }

    } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
      //needToStartLock = false;
      finishLockScreenApp();
      saveInfoForOuterBoolean("isLocked2", false, context);
      if (System.currentTimeMillis() < timeOfCreatingIntent2 + timeOfDelay) {
        if (intent2 != null) {
          intent2.putExtra("code", -1111);
          timeOfCreatingIntent2 = 0;
        }
      }
      Log.d(TAG, " point 3.1 screen off ");

    } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON) && !isPhoneCalling
            && numOfLevelsToSolveToUnlockWhenScreenOn > 0) {
      ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
      List<ActivityManager.RunningAppProcessInfo> runningAppProcess = activityManager.getRunningAppProcesses();
      String firstProcess = runningAppProcess.get(0).pkgList[0];
      Log.d(TAG, " firstProcess = " + firstProcess + " , " +
              "it's permission = " + context.getPackageManager().checkPermission("android.permission.WAKE_LOCK", firstProcess));

      if ((firstProcess.contains("clock") || firstProcess.contains("alarm"))
              && context.getPackageManager().checkPermission("android.permission.WAKE_LOCK", firstProcess) == 0) {
        noApplicationStart = true;
        Log.d(TAG, " point 3.2.1 screen on, no need to start ");
      } else {
        final AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        final AudioManager.OnAudioFocusChangeListener audioListener = new AudioManager.OnAudioFocusChangeListener() {
          @Override
          public void onAudioFocusChange(int focusChange) {
            Log.d(TAG, "onAudioFocusChange");
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
              Log.d(TAG, "onAudioFocusChange  AUDIOFOCUS_LOSS_TRANSIENT");
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
              Log.d(TAG, "onAudioFocusChange  AUDIOFOCUS_GAIN");
              // Resume playback
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
              Log.d(TAG, "onAudioFocusChange  AUDIOFOCUS_LOSS");
              //audio.abandonAudioFocus(audioListener);
              // Stop playback
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
              Log.d(TAG, "onAudioFocusChange  AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
              //audio.abandonAudioFocus(audioListener);
              // Stop playback
            }
          }
        };
        int requestAudio = audio.requestAudioFocus(audioListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (requestAudio == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
          Log.d(TAG, "AUDIOFOCUS_REQUEST_GRANTED");
        } else {
          Log.d(TAG, "AUDIOFOCUS_REQUEST_FAILED");
          noApplicationStart = true;
        }
        SystemClock.sleep(1000);
        requestAudio = audio.requestAudioFocus(audioListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (requestAudio == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
          Log.d(TAG, "2 AUDIOFOCUS_REQUEST_GRANTED");
        } else {
          Log.d(TAG, "2 AUDIOFOCUS_REQUEST_FAILED");
          noApplicationStart = true;
        }
        audio.abandonAudioFocus(audioListener);

        if (!noApplicationStart) {
          Log.d(TAG, " point 3.2.2 screen on, creating pending intent with delay ");
          createPendingIntent(context, timeOfDelay);
        } else {
          Log.d(TAG, " point 3.2.2 screen on, no need to start ");
        }
      }
      noApplicationStart = false;

    } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) && !isPhoneCalling
            && numOfLevelsToSolveToUnlockWhenScreenOn > 0) {
      Log.d(TAG, " point 3.3 ACTION_BOOT_COMPLETED createPendingIntent ");
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
    //needToStartLock = true;
    if (loadOuterInfoLong("code2", context) != INVALID) {
      saveInfoForOuterBoolean("isLocked2", true, context);
    }
  }

  protected static long loadOuterInfoLong(String key, Context context) {
    SharedPreferences load = PreferenceManager.getDefaultSharedPreferences(context);
    return load.getLong(key, INVALID);
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


