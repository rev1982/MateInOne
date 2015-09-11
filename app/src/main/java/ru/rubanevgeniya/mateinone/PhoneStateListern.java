package ru.rubanevgeniya.mateinone;

import android.telephony.TelephonyManager;


public class PhoneStateListern extends android.telephony.PhoneStateListener {

  protected static LockScreenApp lockScreenApp;
  protected static SecondActivity secondActivity;

  public void onCallStateChanged(int state, String incomingNumber) {
    switch (state) {
      case TelephonyManager.CALL_STATE_IDLE: //no activity
        LockScreenReceiver.isPhoneCalling = false;
        break;
      case TelephonyManager.CALL_STATE_OFFHOOK://At least one call exists that is dialing, active, or on hold, and no calls are ringing or waiting.
        LockScreenReceiver.isPhoneCalling = true;
        LockScreenReceiver.needToAlive = false;
        if (lockScreenApp != null) {
          lockScreenApp.finish();
        }
        if (secondActivity != null) {
          secondActivity.finish();
        }
        break;
      case TelephonyManager.CALL_STATE_RINGING:
        LockScreenReceiver.isPhoneCalling = true;
        break;
    }
  }
}
