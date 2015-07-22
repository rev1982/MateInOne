package ru.rubanevgeniya.mylockscreen;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;


public class MyPhoneStateListener extends PhoneStateListener {

    protected static LockScreenApp lockScreenApp;
    protected static SecondActivity secondActivity;
    private  static  final  String TAG = "Log MyPhoneStateList: ";

    public void onCallStateChanged(int state, String incomingNumber) {

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE: //no activity
                Log.d(TAG, "IDLE");
                LockScreenReceiver.isPhoneCalling = false;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK://At least one call exists that is dialing, active, or on hold, and no calls are ringing or waiting.
                Log.d(TAG, "OFFHOOK");
                LockScreenReceiver.isPhoneCalling = true;
                LockScreenReceiver.needToAlive = false;
                if (lockScreenApp != null){
                    lockScreenApp.finish();
                }
                if (secondActivity != null){
                    secondActivity.finish();
                }
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG, "RINGING");
                LockScreenReceiver.isPhoneCalling = true;
                break;
        }
    }
}
