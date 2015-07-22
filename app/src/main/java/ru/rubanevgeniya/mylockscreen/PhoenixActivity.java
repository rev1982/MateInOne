package ru.rubanevgeniya.mylockscreen;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class PhoenixActivity extends Activity {
    private  static  final  String TAG = "Log PhoenixActivity : ";
    private  static  final  int FLAG1 = 1111;// to lockScreenActivity
    private  static  final  int FLAG2 = 5555;// to SecondActivity
    private  int flag;
    private  String isLockedString;

    @Override
    protected void onPause() {
        // Schedule to start a new intent in 10 seconds
        super.onPause();
        isLockedString = LockScreenReceiver.loadOuterInfo("isLocked",this);
        Log.d(TAG, "on pause");
        Log.d(TAG,"LockScreenReceiver.onTop = "+LockScreenReceiver.onTop+ "; LockScreenReceiver.needToAlive = "+LockScreenReceiver.needToAlive);
        if (LockScreenReceiver.onTop.toString().contains("SecondActivity")){
            flag = FLAG2;
        } else {
            flag = FLAG1;
        }
        if (LockScreenReceiver.onTop == getClass()) {
            LockScreenReceiver.onTop = null;
        }
        //if(isLockedString != null && isLockedString.equals("yes") ) {
        if(LockScreenReceiver.needToAlive ) {
            Log.d(TAG, "creating pendingIntent in onPause()");

            AlarmManager alarmMgr = (AlarmManager) this.getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(this, LockScreenReceiver.class);
            intent.setFlags(flag);
            intent.putExtra("code", flag);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 5000, pendingIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LockScreenReceiver.onTop = getClass();
    }


}
