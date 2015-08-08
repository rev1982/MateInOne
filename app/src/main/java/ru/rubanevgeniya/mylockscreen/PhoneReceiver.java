package ru.rubanevgeniya.mylockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class PhoneReceiver extends BroadcastReceiver {
  private TelephonyManager telephony;

  @Override
  public void onReceive(Context context, Intent intent) {
    PhoneStateListern phoneListener = new PhoneStateListern();
    telephony = (TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE);
    telephony.listen(phoneListener, android.telephony.PhoneStateListener.LISTEN_CALL_STATE);
  }

  // when finish, stop listen to changes
  public void onDestroy() {
    telephony.listen(null, android.telephony.PhoneStateListener.LISTEN_NONE);
  }
}
