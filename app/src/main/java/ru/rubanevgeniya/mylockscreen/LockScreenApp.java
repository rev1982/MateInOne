package ru.rubanevgeniya.mylockscreen;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Set;

public class LockScreenApp extends PhoenixActivity {
    protected BoardView boardView;
    protected ChessView chessView;
    protected BoardViewLand boardViewLand;
    protected boolean isLockScreen;
    private  String toUnlock;
    private static String TAG = "Log = ";
    protected  Handler handler;
    private String isLockedString;


    public  void  onCreate(Bundle savedInstanceState) {

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
        isLockedString = loadOuterInfo("isLocked");

        if (isLockedString != null){
            if (isLockedString.equals("yes")){
                isLockScreen = true;
                LockScreenReceiver.needToAlive = true;
                Log.d(TAG,"  lock screen !!!");
            } else if (isLockedString.equals("no")){
                isLockScreen = false;
                LockScreenReceiver.needToAlive = false;
                Log.d(TAG,"  not lock screen !!!");
            } else {
                Log.d(TAG,"   need to lock ??????????????????");
            }
        } else {
            Log.d(TAG,"   need to lock ??????????????????");
        }


//        if (getIntent().getAction() == null || LockScreenReceiver.needToStartLock) {
//            if (LockScreenReceiver.needToStartLock){
//                LockScreenReceiver.needToStartLock = false;
//            }
//            isLockScreen = true;
//            saveInfoForOuter("isLocked","yes");
//            //LockScreenReceiver.needToStartLock = false;
//            LockScreenReceiver.needToAlive = true;
//            Log.d(TAG,"lock screen !!!");
//        }
//
//        if (getIntent().getFlags() == 3333){  // intent from setting activity when it is not lock screen
//            isLockScreen = false;
//            saveInfoForOuter("isLocked","no");
//            LockScreenReceiver.needToAlive = false;
//            Log.d(TAG,"not lock screen !!!");
//        }
//        Log.d(TAG, "getIntent().getAction() =  " + getIntent().getAction());//turn on -> null, start by myself -> android.intent.action.MAIN



        try {
            // initialize receiver
            startService(new Intent(this, MyService2.class));
        } catch (Exception e){}

    }


    protected void saveInfo(String key, String value) {
        SharedPreferences save = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = save.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected void saveInfoForOuter(String key, String value){
        SharedPreferences save = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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

    protected String loadOuterInfo(String key) {//to get info from other activity
        SharedPreferences load = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        return load.getString(key, null);
    }

    protected  void startSecondActivity(){
//        Intent intent = new Intent(this,SecondActivity.class);
//        if (!isLockScreen) {
//            intent.setFlags(1);
//        }
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);




        //LockScreenReceiver.secondActivityNeedToCreate = true;
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(this, LockScreenReceiver.class);
        intent2.setFlags(5555);
        intent2.putExtra("code", 5555);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent2,
                PendingIntent.FLAG_CANCEL_CURRENT);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 500, pendingIntent);
        Log.d(TAG, " creating pending intent SecondActivity ");




        finish();
    }


    @Override
    public void onDestroy(){
        if(chessView.timer != null) {
            chessView.timer.cancel();
            chessView.timer = null;
        }
        chessView.myTimerTask = null;
        Log.d(TAG, " lockScreenApp is on destroy !!");
        chessView = null;

        if (handler != null)
           handler.removeCallbacksAndMessages(null);

        super.onDestroy();
    }


    @Override
    public void  onBackPressed(){
        if (isLockScreen) {
            Toast toast = Toast.makeText(this, toUnlock , Toast.LENGTH_LONG);
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
    public void onStart(){
        super.onStart();
        Log.d(TAG, " lockScreenApp onStart");
    }

    @Override
    public void onRestart(){
        Log.d(TAG, " lockScreenApp onRestart");
        super.onRestart();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            Log.d(TAG," lockScreenApp got focus");
        }else {
            Log.d(TAG," lockScreenApp lost focus");
        }
    }



    static class MyHandler extends Handler {

        WeakReference<LockScreenApp> wrActivity;

        public MyHandler(LockScreenApp activity) {
            wrActivity = new WeakReference<LockScreenApp>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LockScreenApp activity = wrActivity.get();
            if (activity != null)
                activity.chessView.handlerMethod(msg);
        }
    }




}
