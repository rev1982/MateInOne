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
  public static final int INVALID = -1000;
  private boolean isLocked;
  private long CODE;
  private TextView textView;
  private TextView textView0;
  private static final Object monitor = new Object();
  private String code = "";
  private Toast toast1;
  private  boolean isSettings;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    isSettings = getIntent().getStringExtra("what").equals("settings");
    LockScreenReceiver.isStartSettings = isSettings;
    boolean isPortrait = false;
    isLocked = LockScreenReceiver.loadOuterInfoBoolean("isLocked2", this);
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      isPortrait = true;
    }

    PhoneStateListern.secondActivity = this;
    LockScreenReceiver.secondActivity = this;
    LockScreenReceiver.needToAlive = isLocked;
    Log.d(TAG, " SecondActivity onCreate ,   LockScreenReceiver.needToAlive = " + LockScreenReceiver.needToAlive + " isLocked = " + isLocked);
    CODE = LockScreenReceiver.loadOuterInfoLong("code2", this);
    Log.d(TAG, "loaded code = " + CODE);

    RadioGroup.LayoutParams layoutParams =
            new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
    Typeface typeface = Typefaces.get(this,"fonts/fontawesome.ttf");
    int fontSize = (int) getResources().getDimension(R.dimen.textsize);

    textView = new TextView(this);
    TextView textView00 = new TextView(this);
    textView00.setText("");

    TextView textViewArrow = new TextView(this);
    //textView000.setText("");
    textViewArrow.setTypeface(Typefaces.get(this, "fonts/fontawesome.ttf"));
    textViewArrow.setGravity(Gravity.LEFT);
    textViewArrow.setTextSize(fontSize);
    textViewArrow.setTextColor(getResources().getColor(R.color.mybg10));
    textViewArrow.setText("\n  " + getResources().getText(R.string.strArrow));
    textViewArrow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startLockScreenApp();
      }
    });

    textView0 = new TextView(this);
    textView0.setText(isSettings? getResources().getString(R.string.enterTheSecretCodeToOpenSettings)
            : getResources().getString(R.string.enterTheSecretCodeToUnlock));
    TextView textView1 = new TextView(this);
    textView1.setText(" ");
    textView0.setGravity(Gravity.CENTER);
    textView0.setTextColor(getResources().getColor(R.color.mybg9));
    textView0.setTextSize(fontSize);

    textView.setText("_ _ _ _ _ _");
    textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (fontSize / 6 * 5));
    textView.setGravity(Gravity.CENTER);
    textView.setTypeface(Typeface.SERIF, Typeface.BOLD);

    Button[] buttons = new Button[12]; //[14];

    TableLayout table = new TableLayout(this);
    table.setGravity(Gravity.CENTER);
    //table.addView(textView000);

//    View merge = (View) findViewById(R.id.mergesecond);
//    table.addView(merge);

    table.addView(textViewArrow);
    table.addView(textView0);
    table.addView(textView00);
    table.addView(textView);
    table.addView(textView1);

    TableRow tableRow1 = new TableRow(this);
    tableRow1.setGravity(Gravity.CENTER);
    TableRow tableRow2 = new TableRow(this);
    tableRow2.setGravity(Gravity.CENTER);

    for (int i = 0; i < 9; i++) {
      buttons[i] = new Button(this);
      buttons[i].setText(Integer.toString(i + 1));
    }
    buttons[9] = new Button(this);
    buttons[9].setText("0");
    buttons[10] = new Button(this);
    buttons[10].setTypeface(typeface);
    buttons[10].setText("\uF060");
    buttons[11] = new Button(this);
    buttons[11].setTypeface(typeface);
    buttons[11].setText("ok");
    TableRow tableRow3 = null;
    TableRow tableRow4 = null;
    if (isPortrait) {
      for (int i = 0; i < 3; i++) {
        buttons[i].setWidth(table.getWidth() / 3);
        tableRow1.addView(buttons[i]);
      }
      for (int i = 3; i < 6; i++) {
        tableRow2.addView(buttons[i]);
      }
      tableRow3 = new TableRow(this);
      tableRow3.setGravity(Gravity.CENTER);
      for (int i = 6; i < 9; i++) {
        tableRow3.addView(buttons[i]);
      }
      tableRow4 = new TableRow(this);
      tableRow4.setGravity(Gravity.CENTER);
      tableRow4.addView(buttons[9]);
      tableRow4.addView(buttons[10]);
      tableRow4.addView(buttons[11]);
    } else {
      for (int i = 0; i < 6; i++) {
        buttons[i].setWidth(table.getWidth() / 6);
        tableRow1.addView(buttons[i]);
      }
      for (int i = 6; i < 12; i++) {
        tableRow2.addView(buttons[i]);
      }
    }
    table.addView(tableRow1);
    table.addView(tableRow2);
    if (tableRow3 != null){
      table.addView(tableRow3);
      table.addView(tableRow4);
    }

    for (int i = 0; i < 11; i++) {
      buttons[i].setTypeface(typeface);
      buttons[i].setMinWidth(2 * fontSize);
      buttons[i].setMaxWidth(2 * fontSize);
      buttons[i].setMaxHeight((int)(1.2f * fontSize));
      buttons[i].setMinHeight((int)(1.2f * fontSize));
    }

    TextView textView2 = new TextView(this);
    textView2.setText(" ");
    textView2.setTextSize((fontSize / 2));
    table.addView(textView2);

//    buttons[12] = new Button(this);
//    buttons[12].setText(getResources().getString(R.string.backToChess) + " " + "\u265F ");
//    buttons[12].requestLayout();
//    table.addView(buttons[12]);
//    buttons[13] = new Button(this);
//    buttons[13].setText(getResources().getString(R.string.Settings));
//    table.addView(buttons[13]);

    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        v.setPressed(true);
        int ind = v.getId();
        String text = textView.getText().toString();
        //String.valueOf(textView.getText());
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
            if (isSettings) {
              checkCodeToStartSettings(code);
            } else {
              checkCode(code);
            }
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
    for (Button button : buttons) {
      button.setOnClickListener(onClickListener);
    }
    table.setLayoutParams(layoutParams);
    LinearLayout linearLayout =  new LinearLayout(this);//(LinearLayout) findViewById(R.id.secondActLayout); //new LinearLayout(this);
    RadioGroup.LayoutParams layoutParams2 =
            new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
    linearLayout.addView(table);
    linearLayout.setGravity(Gravity.CENTER_VERTICAL);
    linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
    linearLayout.setBackgroundColor(getResources().getColor(R.color.mybg4));
    setContentView(linearLayout, layoutParams2);
    //setContentView(R.layout.second_act_merge);

  }

  private void checkCode(String text) {
    Log.d(TAG, "check code = " + text);
    if (text != null && text.length() > 0 && Long.parseLong(text) == CODE) {
    //if (CODE == INVALID || (text != null && text.length() > 0 && Long.parseLong(text) == CODE) || !isLocked) {
      LockScreenReceiver.saveInfoForOuterBoolean("isLocked2", false, this);
      LockScreenReceiver.needToAlive = false;
      this.finish();
    } else {
      textView0.setText(getResources().getString(R.string.tryOnceMore));
      textView.setText("_ _ _ _ _ _");
    }
  }

  private void checkCodeToStartSettings(String text) {
    CODE = LockScreenReceiver.loadOuterInfoLong("code2", this);
    Log.d(TAG, "loaded code = " + CODE);
    //if (CODE == INVALID || (text != null && text.length() > 0 && Long.parseLong(text) == CODE)) {
    if (text != null && text.length() > 0 && Long.parseLong(text) == CODE) {
      Intent intent = new Intent(this, SettingsActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
      LockScreenReceiver.needToAlive = false;
      this.finish();
    } else {
      textView0.setText(getResources().getString(R.string.tryOnceMore));
      textView.setText("_ _ _ _ _ _");
//      if (toast1 != null) {
//        toast1.cancel();
//      }
//      toast1 = Toast.makeText(this, getResources().getString(R.string.enterTheSecretCodeAndThenPressSettings), Toast.LENGTH_SHORT);
//      toast1.show();
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
    startLockScreenApp();
  }
}
