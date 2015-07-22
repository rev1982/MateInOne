package ru.rubanevgeniya.mylockscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity {
  private static final String TAG = "Log MainActivity : ";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    startService(new Intent(this, MyService2.class));

    // start LockScreenApp
//        Intent intent1 = new Intent(this,LockScreenApp.class);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent1);

    finish();

    Log.d(TAG, " point 1 ");
  }


}
