package ru.rubanevgeniya.mylockscreen;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.ref.WeakReference;


public class SettingsActivity extends Activity {
  private EditText editText1;
  private EditText editText2;
  private final Integer[] levelsToSolveOrAttemptsToResolve = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
  private final Integer[] days = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
  private final Integer[] hours = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
  private final Integer[] minutes = new Integer[]{0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
  private final Integer[] delays = new Integer[]{10, 20, 30, 40, 50, 60, 90, 120, 180};
  private static final String TAG = "Log SettingsActivity : ";
  private boolean isPortrait;
  private static boolean needToFinish;


  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    LockScreenReceiver.needToAlive = false;
    LockScreenReceiver.settingsActivity = this;
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      isPortrait = true;
    }

    handler = new SettingsHandler(this);
    int fontSize = (int) getResources().getDimension(R.dimen.textsizesetting);
    int smallFontSize = (fontSize / 8 * 5);

    TextView textViewDelay = (TextView) findViewById(R.id.tDelay);
    textViewDelay.setTextSize(smallFontSize);
    TextView textViewDelay1 = (TextView) findViewById(R.id.tDelay1);
    textViewDelay1.setTextSize(smallFontSize);

    TextView textViewSettings = (TextView) findViewById(R.id.tSettings);
    textViewSettings.setTextSize(fontSize);
    TextView textViewEnterSecretCode = (TextView) findViewById(R.id.tEnterSecretCode);
    textViewEnterSecretCode.setTextSize(smallFontSize);
    TextView textViewOnlyDigits = (TextView) findViewById(R.id.tOnlyDigits);
    textViewOnlyDigits.setTextSize(fontSize / 8 * 4);
    TextView textViewApprove = (TextView) findViewById(R.id.tApprove);
    textViewApprove.setTextSize(smallFontSize);
    TextView textViewNumOfLevels = (TextView) findViewById(R.id.tNumOfLevels);
    textViewNumOfLevels.setTextSize(smallFontSize);
    if (!isPortrait) {
      textViewNumOfLevels.setText(getResources().getString(R.string.SetNumberOfLevelsToSolveBeforeUnlockHorizontal));
    }
    TextView textViewNumOfAttempts = (TextView) findViewById(R.id.tNumOfAttempts);
    textViewNumOfAttempts.setTextSize(smallFontSize);
    TextView textViewAmountOfTimeToLiveDeviceUnlock = (TextView) findViewById(R.id.tAmountOfTimeToLiveDeviceUnlock);
    textViewAmountOfTimeToLiveDeviceUnlock.setTextSize(smallFontSize);
    TextView textViewDays = (TextView) findViewById(R.id.tDays);
    textViewDays.setTextSize(smallFontSize);
    TextView textViewHours = (TextView) findViewById(R.id.tHours);
    textViewHours.setTextSize(smallFontSize);
    TextView textViewMinutes = (TextView) findViewById(R.id.tMinutes);
    textViewMinutes.setTextSize(smallFontSize);

    editText1 = (EditText) findViewById(R.id.et1);
    editText1.setTextSize((smallFontSize));
    editText2 = (EditText) findViewById(R.id.et2);
    editText2.setTextSize(smallFontSize);
    Button buttonBack = (Button) findViewById(R.id.btnBack);
    buttonBack.setTextSize(smallFontSize);
    Button buttonCancel = (Button) findViewById(R.id.btnCancel);
    buttonCancel.setTextSize(smallFontSize);
    Button buttonEnter = (Button) findViewById(R.id.btnEnter);
    buttonEnter.setTextSize(smallFontSize);
    Spinner spinnerLevels = (Spinner) findViewById(R.id.spinner);
    forSpinner(spinnerLevels, "amountOfLevelsToSolve3", levelsToSolveOrAttemptsToResolve);
    Spinner spinnerAttempts = (Spinner) findViewById(R.id.spinner2);

    if (isPortrait) {
      spinnerLevels.setGravity(Gravity.BOTTOM);
      spinnerAttempts.setGravity(Gravity.BOTTOM);
    } else {
      spinnerLevels.setGravity(Gravity.TOP);
      spinnerAttempts.setGravity(Gravity.TOP);
    }

    forSpinner(spinnerAttempts, "amountOfAttemptsToResolve3", levelsToSolveOrAttemptsToResolve);
    Spinner spinnerDays = (Spinner) findViewById(R.id.spinner3);
    forSpinner(spinnerDays, "days3", days);
    Spinner spinnerHours = (Spinner) findViewById(R.id.spinner4);
    forSpinner(spinnerHours, "hours3", hours);
    Spinner spinnerMinutes = (Spinner) findViewById(R.id.spinner5);
    forSpinner(spinnerMinutes, "minutes3", minutes);
    Spinner spinnerDelays = (Spinner) findViewById(R.id.spinner0);
    forSpinner(spinnerDelays, "delay3", delays);

    CheckBox checkBoxIlluminating = (CheckBox) findViewById(R.id.checkBox2);
    checkBoxIlluminating.setTextSize(smallFontSize);
    boolean isNoIlluminating = LockScreenReceiver.loadOuterInfoBoolean("isNoIlluminating", this);
    if (!isNoIlluminating) {
      checkBoxIlluminating.setChecked(true);
    }

    checkBoxIlluminating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          LockScreenReceiver.saveInfoForOuterBoolean("isNoIlluminating", false, getBaseContext());
        } else {
          LockScreenReceiver.saveInfoForOuterBoolean("isNoIlluminating", true, getBaseContext());
        }
      }
    });


    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        switch (v.getId()) {
          case R.id.btnCancel:
            editText1.setText("");
            editText2.setText("");
            break;
          case R.id.btnBack:
            startLockScreenApp();
            break;
          case R.id.btnEnter:
            checkSecretCode();
            break;
        }
      }
    };

    buttonEnter.setOnClickListener(onClickListener);
    buttonCancel.setOnClickListener(onClickListener);
    buttonBack.setOnClickListener(onClickListener);
  }

  private void forSpinner(Spinner spinner, final String key1, final Integer[] dataArray) {
    ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, dataArray);
    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        LockScreenReceiver.saveInfoForOuterInt(key1, dataArray[position], getBaseContext());
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    int amount = LockScreenReceiver.loadOuterInfoInt(key1, this);
    int position = 0;
    if (amount != -1000) {
      for (int i = 0; i < dataArray.length; i++) {
        if (dataArray[i] == amount) {
          position = i;
          break;
        }
      }
      spinner.setSelection(position);
    }
  }


  protected void startLockScreenApp() {
    Log.d(TAG, " start lockScreenApp from settings");

    AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    Intent intent2 = new Intent(this, LockScreenReceiver.class);
    intent2.setFlags(1111);
    intent2.putExtra("code", 1111);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent2,
            PendingIntent.FLAG_CANCEL_CURRENT);
    alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 500, pendingIntent);

    this.finish();
  }

  private void checkSecretCode() {
    String code1 = editText1.getText().toString();
    String code2 = editText2.getText().toString();
    if (code1.length() > 6) {
      Toast toast = Toast.makeText(this, getResources().getString(R.string.lengthIsMoteThen6), Toast.LENGTH_LONG);
      toast.show();
      editText1.setText("");
      editText2.setText("");
    } else if (code1.length() == 0) {
      Toast toast = Toast.makeText(this, getResources().getString(R.string.lengthIs0), Toast.LENGTH_LONG);
      toast.show();
    } else if (!code1.equals(code2)) {
      Toast toast = Toast.makeText(this, getResources().getString(R.string.approveSecretCodeCorrectly), Toast.LENGTH_LONG);
      toast.show();
      editText2.setText("");
    } else {
      LockScreenReceiver.saveInfoForOuterLong("code2", Long.parseLong(code1), this);
      Log.d(TAG, "saving code = " + code1);

    }
  }


  @Override
  public void onResume() {
    needToFinish = false;
    Log.d(TAG, " SettingActivity onResume, needToFinish = " + false);
    super.onResume();
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, " Settings onDestroy");
    needToFinish = false;
    super.onDestroy();
  }

  @Override
  public void onPause() {
    needToFinish = true;
    Log.d(TAG, " settings on pause, needToFinish = " + true);
    handler.sendMessageDelayed(handler.obtainMessage(0, 0, 0), 4000); //to finish settings if it is not just orientation change

    super.onPause();
  }

  Handler handler;

  protected void handlerMethod() {
    if (needToFinish) {
      Log.d(TAG, "handler got message what time to finish settings needToFinish " + true);
      finish();
    }
  }

  static class SettingsHandler extends Handler {

    WeakReference<SettingsActivity> wrActivity;

    public SettingsHandler(SettingsActivity activity) {
      wrActivity = new WeakReference<SettingsActivity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      SettingsActivity activity = wrActivity.get();
      if (activity != null)
        activity.handlerMethod();
    }
  }


}
