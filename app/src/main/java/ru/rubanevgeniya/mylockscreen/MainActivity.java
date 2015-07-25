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
    finish();
    Log.d(TAG, " point 1: Start Service, finish MainActivity");
  }


}
