package ru.rubanevgeniya.mylockscreen;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class SecondActivity extends PhoenixActivity {
  private static final String TAG = "Logs SecondActivity : ";
  private String isLockedString;
  private String CODE;
  private TextView textView;
  private TextView textView0;
  private static final Object monitor = new Object();
  private String code = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    boolean isPortrait = false;
    isLockedString = LockScreenReceiver.loadOuterInfo("isLocked", this);
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      isPortrait = true;
    }

    MyPhoneStateListener.secondActivity = this;
    LockScreenReceiver.secondActivity = this;
    if (isLockedString != null && isLockedString.equals("yes")) {
      LockScreenReceiver.needToAlive = true;
    } else if (isLockedString != null && isLockedString.equals("no")) {
      LockScreenReceiver.needToAlive = false;
    }
    Log.d(TAG, " SecondActivity onCreate ,   LockScreenReceiver.needToAlive = " + LockScreenReceiver.needToAlive + " isLockedString = " + isLockedString);
    CODE = LockScreenReceiver.loadOuterInfo("code",this);
    Log.d(TAG, "loaded code = " + CODE);

    RadioGroup.LayoutParams layoutParams =
            new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

    Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/fontawesome.woff");

    int fontSize = (int) getResources().getDimension(R.dimen.textsize);

    textView = new TextView(this);
    TextView textView00 = new TextView(this);
    textView00.setText("");
    TextView textView000 = new TextView(this);
    textView000.setText("");

    textView0 = new TextView(this);
    textView0.setText(getResources().getString(R.string.enterTheSecretCode));
    TextView textView1 = new TextView(this);
    textView1.setText(" ");
    textView0.setGravity(Gravity.CENTER);
    textView0.setTextColor(getResources().getColor(R.color.mybg9));
    textView0.setTypeface(Typeface.SERIF, Typeface.BOLD);
    textView0.setTextSize(fontSize);

    textView.setText("_ _ _ _ _ _");
    textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (fontSize / 6 * 5));
    textView.setGravity(Gravity.CENTER);
    textView.setTypeface(Typeface.SERIF, Typeface.BOLD);

    Button[] buttons = new Button[14];

    TableLayout table = new TableLayout(this);
    table.setGravity(Gravity.CENTER);
    table.addView(textView000);
    table.addView(textView0);
    table.addView(textView00);
    table.addView(textView);
    table.addView(textView1);

    if (isPortrait) {

      TableRow tableRow1 = new TableRow(this);
      tableRow1.setGravity(Gravity.CENTER);
      for (int i = 0; i < 3; i++) {
        buttons[i] = new Button(this);
        buttons[i].setText(Integer.toString(i + 1));
        buttons[i].setWidth(table.getWidth() / 3);
        tableRow1.addView(buttons[i]);
      }
      table.addView(tableRow1);

      TableRow tableRow2 = new TableRow(this);
      tableRow2.setGravity(Gravity.CENTER);
      for (int i = 3; i < 6; i++) {
        buttons[i] = new Button(this);
        buttons[i].setText(Integer.toString(i + 1));
        tableRow2.addView(buttons[i]);
      }
      table.addView(tableRow2);

      TableRow tableRow3 = new TableRow(this);
      tableRow3.setGravity(Gravity.CENTER);
      for (int i = 6; i < 9; i++) {
        buttons[i] = new Button(this);
        buttons[i].setText(Integer.toString(i + 1));
        tableRow3.addView(buttons[i]);
      }
      table.addView(tableRow3);

      TableRow tableRow4 = new TableRow(this);
      tableRow4.setGravity(Gravity.CENTER);
      buttons[9] = new Button(this);
      buttons[9].setText("   0   ");
      buttons[10] = new Button(this);
      buttons[10].setTypeface(typeface);
      buttons[10].setText("\uF060");
      buttons[11] = new Button(this);
      buttons[11].setTypeface(typeface);
      buttons[11].setText("\uF064");
      buttons[11].setRotation(180);

      tableRow4.addView(buttons[9]);
      tableRow4.addView(buttons[10]);
      tableRow4.addView(buttons[11]);
      table.addView(tableRow4);

    } else {

      TableRow tableRow1 = new TableRow(this);
      tableRow1.setGravity(Gravity.CENTER);
      for (int i = 0; i < 6; i++) {
        buttons[i] = new Button(this);
        buttons[i].setText(Integer.toString(i + 1));
        buttons[i].setWidth(table.getWidth() / 6);
        tableRow1.addView(buttons[i]);
      }
      table.addView(tableRow1);

      TableRow tableRow2 = new TableRow(this);
      tableRow2.setGravity(Gravity.CENTER);
      for (int i = 6; i < 9; i++) {
        buttons[i] = new Button(this);
        buttons[i].setText(Integer.toString(i + 1));
        tableRow2.addView(buttons[i]);
      }
      buttons[9] = new Button(this);
      buttons[9].setText("   0   ");
      buttons[10] = new Button(this);
      buttons[10].setTypeface(typeface);
      buttons[10].setText("\uF060");
      buttons[11] = new Button(this);
      buttons[11].setTypeface(typeface);
      buttons[11].setText("\uF064");
      buttons[11].setRotation(180);
      buttons[8].setText("   9   ");
      buttons[7].setText("   8   ");
      buttons[6].setText("   7   ");

      tableRow2.addView(buttons[9]);
      tableRow2.addView(buttons[10]);
      tableRow2.addView(buttons[11]);
      table.addView(tableRow2);
    }

    TextView textView2 = new TextView(this);
    textView2.setText(" ");
    textView2.setTextSize((fontSize / 2));
    table.addView(textView2);

    buttons[12] = new Button(this);
    buttons[12].setText(getResources().getString(R.string.backToChess) + " " + "\u265F ");
    buttons[12].requestLayout();
    table.addView(buttons[12]);
    buttons[13] = new Button(this);
    buttons[13].setText(getResources().getString(R.string.Settings));
    table.addView(buttons[13]);


    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        v.setPressed(true);
        int ind = v.getId();
        String text = textView.getText().toString();
        if (text.equals("_ _ _ _ _ _")) {
          text = "";
          code = "";
        }

        for (int i = 0; i < 9; i++) {
          if (i == ind) {
            textView.setText(text + "*");
            code = code + (i + 1);
            break;
          }
        }
        switch (ind) {
          case 9:
            textView.setText(text + "*");
            code = code + (0);
            break;
          case 10:
            textView.setText("_ _ _ _ _ _");
            code = "";
            break;
          case 11:
            checkCode(code);
            break;
          case 12:
            startLockScreenApp();
            break;
          case 13:
            checkCodeToStartSettings(code);
            break;

        }

        synchronized (monitor) {
          try {
            monitor.wait(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          v.setPressed(false);
          monitor.notifyAll();
        }
      }
    };


    for (int i = 0; i < buttons.length; i++) {
      buttons[i].setId(i);
      buttons[i].setTextSize((fontSize / 6 * 5));
      buttons[i].setTextColor(getResources().getColor(R.color.mybg9));
    }

    for (int i = 0; i < buttons.length; i++) {
      buttons[i].setOnClickListener(onClickListener);
    }

    table.setLayoutParams(layoutParams);
    LinearLayout linearLayout = new LinearLayout(this);
    RadioGroup.LayoutParams layoutParams2 =
            new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
    linearLayout.addView(table);
    linearLayout.setGravity(Gravity.CENTER_VERTICAL);
    linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
    linearLayout.setBackgroundColor(getResources().getColor(R.color.mybg4));
    setContentView(linearLayout, layoutParams2);

  }


  private void checkCode(String text) {
    Log.d(TAG, "check code :  getIntent().getFlags() = " + getIntent().getFlags());
    if (CODE == null
            || text.equals(CODE)
            || (isLockedString != null && isLockedString.equals("no"))
            ) {
      LockScreenReceiver.saveInfoForOuter("isLocked", "no", this);
      LockScreenReceiver.needToAlive = false;
      this.finish();
    } else {
      textView0.setText(getResources().getString(R.string.tryOnceMore));
      textView.setText("_ _ _ _ _ _");
    }
  }

  private void checkCodeToStartSettings(String text) {
    CODE = LockScreenReceiver.loadOuterInfo("code",this);
    Log.d(TAG, "loaded code = " + CODE);
    if (CODE == null
            || text.equals(CODE)
            ) {
      Intent intent = new Intent(this, SettingsActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
      LockScreenReceiver.needToAlive = false;
      this.finish();
    } else {
      Toast.makeText(this, getResources().getString(R.string.enterTheSecretCodeAndThenPressSettings), Toast.LENGTH_SHORT).show();
    }
  }

  protected void startLockScreenApp() {
    Intent intent = new Intent(this, LockScreenApp.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    Log.d(TAG, " creating intent LockScreenApp from SecondActivity ");
    LockScreenReceiver.needToAlive = false;//to prevent creating pending intent when second activity is on pause
    this.finish();
  }

  @Override
  public void onBackPressed() {
    if (isLockedString != null && isLockedString.equals("yes")) {
      Toast toast = Toast.makeText(this, getResources().getString(R.string.toUnlockEnter), Toast.LENGTH_LONG);
      toast.show();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public void onResume() {
    Log.d(TAG, " SecondActivity onResume");
    super.onResume();
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, " SecondActivity onDestroy");
    super.onDestroy();
  }

  @Override
  public void onStop() {
    Log.d(TAG, " SecondActivity onStop");
    super.onStop();
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.d(TAG, " SecondActivity onStart");
  }

  @Override
  public void onRestart() {
    Log.d(TAG, " SecondActivity onRestart");
    super.onRestart();
  }


}
