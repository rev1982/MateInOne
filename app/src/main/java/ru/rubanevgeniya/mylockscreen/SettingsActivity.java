package ru.rubanevgeniya.mylockscreen;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.ref.WeakReference;


public class SettingsActivity extends Activity {
  public static final int INVALID = -1000;
  private EditText editText1;
  private EditText editText2;
  private final Integer[] attemptsToResolve = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
  private final Integer[] levelsToSolve = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
  private final Integer[] days = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
  private final Integer[] hours = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
          11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
  private final Integer[] minutes = new Integer[]{0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
  private final Integer[] delays = new Integer[]{10, 20, 30, 40, 50, 60, 90, 120, 180};
  private static final String TAG = "Log SettingsActivity : ";
  private boolean isPortrait;
  private static boolean needToFinish;
  private Handler handler;
  private int smallFontSize;
  private static int mainColor;
  private static int pinkColor;
  private static int greenColor;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.setting_merge);
    LockScreenReceiver.needToAlive = false;
    LockScreenReceiver.settingsActivity = this;
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      isPortrait = true;
    }
    handler = new SettingsHandler(this);
    int fontSize = (int) getResources().getDimension(R.dimen.textsizesetting);
    smallFontSize = (fontSize / 8 * 5);
    mainColor = getResources().getColor(R.color.mybg4);
    pinkColor = getResources().getColor(R.color.pink);
    greenColor = getResources().getColor(R.color.green);

    final CheckBox checkBoxPassword = (CheckBox) findViewById(R.id.checkBox3);
    checkBoxPassword.setTextSize(smallFontSize);
    TextView textViewArrow = (TextView) findViewById(R.id.tArrow);
    textViewArrow.setTextSize(fontSize);
    textViewArrow.setTypeface(Typefaces.get(this, "fonts/fontawesome.ttf"));
    textViewArrow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startLockScreenApp();
      }
    });
    TextView textViewDelay = (TextView) findViewById(R.id.tDelay);
    textViewDelay.setTextSize(smallFontSize);
    TextView textViewDelay1 = (TextView) findViewById(R.id.tDelay1);
    textViewDelay1.setTextSize(smallFontSize);
    TextView textViewSettings = (TextView) findViewById(R.id.tSettings);
    textViewSettings.setTextSize(fontSize);
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


    editText1.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        String str1 = editText1.getText().toString();
        int len = str1.length();
        if (len < 7 && len > 0) {
          editText1.setTextColor(greenColor);
          editText1.setBackgroundColor(mainColor);
        } else if (len > 6) {
          editText1.setTextColor(Color.RED);
          editText1.setBackgroundColor(mainColor);
        }
        editText2.setText("");
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
    editText2 = (EditText) findViewById(R.id.et2);
    editText2.setTextSize(smallFontSize);
    editText2.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        String str2 = editText2.getText().toString();
        String str1 = editText1.getText().toString();
        if (str2.equals(str1)) {
          editText2.setBackgroundColor(mainColor);
          editText2.setTextColor(greenColor);
        } else if (str2.length() == 0 && checkBoxPassword.isChecked() && str1.length() > 0) {
          editText2.setBackgroundColor(pinkColor);
        } else if (str2.length() > 0 && checkBoxPassword.isChecked()) {
          editText2.setBackgroundColor(mainColor);
          editText2.setTextColor(Color.RED);
        }
      }

      @Override
      public void afterTextChanged(Editable s) {
        String str2 = editText2.getText().toString();
        String str1 = editText1.getText().toString();
        if (str2.equals(editText1.getText().toString()) && str2.length() > 0) {
          editText2.setBackgroundColor(mainColor);
          editText2.setTextColor(greenColor);
          LockScreenReceiver.saveInfoForOuterLong("code2", Long.parseLong(str2), getBaseContext());
        } else if (str2.length() == 0 && checkBoxPassword.isChecked() && str1.length() > 0) {
          editText2.setBackgroundColor(pinkColor);
        } else if ((str2.length() > 0 && checkBoxPassword.isChecked())) {
          editText2.setBackgroundColor(mainColor);
          editText2.setTextColor(Color.RED);
        }
      }
    });
    Spinner spinnerLevels = (Spinner) findViewById(R.id.spinner);
    forSpinner(spinnerLevels, "amountOfLevelsToSolve3", levelsToSolve);
    Spinner spinnerAttempts = (Spinner) findViewById(R.id.spinner2);

    spinnerLevels.setGravity(isPortrait ? Gravity.BOTTOM : Gravity.TOP);
    spinnerAttempts.setGravity(isPortrait ? Gravity.BOTTOM : Gravity.TOP);

    forSpinner(spinnerAttempts, "amountOfAttemptsToResolve3", attemptsToResolve);
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


    long code = LockScreenReceiver.loadOuterInfoLong("code2", getBaseContext());
    checkBoxPassword.setChecked(code != INVALID);
    if (code != INVALID) {
      editText1.setText(Long.toString(code));
      editText1.setTextColor(greenColor);
      editText2.setText(Long.toString(code));
      editText2.setTextColor(greenColor);
      editText1.setEnabled(true);
      editText2.setEnabled(true);
    } else {
      editText1.setEnabled(false);
      editText2.setEnabled(false);
    }
    checkBoxPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          editText1.setEnabled(true);
          editText2.setEnabled(true);
        } else {
          editText1.setEnabled(false);
          editText2.setEnabled(false);
          LockScreenReceiver.saveInfoForOuterLong("code2", INVALID, getBaseContext());
          editText1.setText("");
          editText2.setText("");
        }
      }
    });
  }

  private void forSpinner(final Spinner spinner, final String key1, final Integer[] dataArray) {
    ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, dataArray);
    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        LockScreenReceiver.saveInfoForOuterInt(key1, dataArray[position], getBaseContext());
        if (parent.getChildAt(position) != null) {
          ((TextView) parent.getChildAt(position)).setTextSize(smallFontSize);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    int amount = LockScreenReceiver.loadOuterInfoInt(key1, this);
    int position = 0;
    if (amount != INVALID) {
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
    Intent intent = new Intent(this, LockScreenApp.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    this.finish();
  }

  @Override
  public void onResume() {
    needToFinish = false;
    LockScreenReceiver.onTop = getClass();//***
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
    LockScreenReceiver.onTop = null;//***
    needToFinish = true;
    Log.d(TAG, " settings on pause, needToFinish = " + true);
    handler.sendMessageDelayed(handler.obtainMessage(0, 0, 0), 4000); //to finish settings if it is not just orientation change
    super.onPause();
  }

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

  @Override
  public  void onBackPressed(){
    startLockScreenApp();
  }

}
