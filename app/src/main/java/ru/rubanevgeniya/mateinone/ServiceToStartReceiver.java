package ru.rubanevgeniya.mateinone;


import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.TelephonyManager;

public class ServiceToStartReceiver extends android.app.Service {
  private BroadcastReceiver mReceiver;
  private BroadcastReceiver phoneReceiver;

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    KeyguardManager.KeyguardLock kl;
    KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
    kl = km.newKeyguardLock("IN");
    kl.disableKeyguard();

    mReceiver = new LockScreenReceiver();
    IntentFilter filter1 = new IntentFilter(Intent.ACTION_SCREEN_ON);
    filter1.addAction(Intent.ACTION_SCREEN_OFF);
    filter1.addAction(Intent.ACTION_BOOT_COMPLETED);
    registerReceiver(mReceiver, filter1);

    phoneReceiver = new PhoneReceiver();
    TelephonyManager telephonyManager = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
    IntentFilter filterPhone = new IntentFilter(telephonyManager.ACTION_PHONE_STATE_CHANGED);
    registerReceiver(phoneReceiver, filterPhone);
  }

  @Override
  public void onDestroy() {
    unregisterReceiver(mReceiver);
    unregisterReceiver(phoneReceiver);
    super.onDestroy();
  }
}
