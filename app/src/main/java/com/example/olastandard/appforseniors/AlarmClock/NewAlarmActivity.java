package com.example.olastandard.appforseniors.AlarmClock;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.olastandard.appforseniors.AddLinkActivity;
import com.example.olastandard.appforseniors.LinksActivity;
import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.R;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

//https://blog.mikesir87.io/2013/04/android-creating-an-alarm-with-alarmmanager/
public class NewAlarmActivity extends MainActivity {
    EditText et;

    EditText et2;
    EditText et3;
    EditText et4;
    InputMethodManager imm;
    FileOutputStream outputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_new_alarm);
        initToolbar();

         et = (EditText) findViewById(R.id.editText2);
        et.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "2")});
         et2 = (EditText) findViewById(R.id.editText);
        et2.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "9")}); //jesli godzina 25 np to zredukowac do 23
         et3 = (EditText) findViewById(R.id.editText5);
        et3.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "5")});
         et4 = (EditText) findViewById(R.id.editText7);
        et4.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "9")});

         imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.RESULT_SHOWN,0);
       et.setFocusableInTouchMode(true);
        et.requestFocus();
        _toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                buttonSave(v);

            }
        });

        _toolbarBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);


            }
        });
          /*if ( et.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        }*/

        et = (EditText) findViewById(R.id.editText2);

        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //if(et.getText().length()==0){return;}
                et = (EditText) findViewById(R.id.editText);
                et.requestFocus();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        et = (EditText) findViewById(R.id.editText);

        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if(et.getText().length()==0){return;}
                et = (EditText) findViewById(R.id.editText5);
                et.requestFocus();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        et = (EditText) findViewById(R.id.editText5);

        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if(et.getText().length()==0){return;}
                et = (EditText) findViewById(R.id.editText7);
                et.requestFocus();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
    }

    private void initToolbar() {
        showBackButton();
        showRightButton();
        changeTitleForRightButton("Zapisz");
        setTitle("Nowy Alarm");

    }


    public void setFocusLeft(View v){
        View view = this.getCurrentFocus();
        switch (view.getId()) {
            case R.id.editText2:

                break;

            case R.id.editText:

                et = (EditText) findViewById(R.id.editText2);
                et.requestFocus();
                break;
            case R.id.editText5:
                et = (EditText) findViewById(R.id.editText);
                et.requestFocus();
                break;
            case R.id.editText7:
                et = (EditText) findViewById(R.id.editText5);
                et.requestFocus();
                break;
        }

    }


    public void setFocusRight(View v){
        View view = this.getCurrentFocus();
        switch (view.getId()) {
            case R.id.editText2:
                et = (EditText) findViewById(R.id.editText);
                et.requestFocus();
                break;

            case R.id.editText:

                et = (EditText) findViewById(R.id.editText5);
                et.requestFocus();
                break;
            case R.id.editText5:
                et = (EditText) findViewById(R.id.editText7);
                et.requestFocus();
                break;
            case R.id.editText7:

                break;
        }

    }
    public void buttonSave (View view)
    {

        if(et.getText().equals("")){

            new PushDialogManager().showDialogWithOkButton(NewAlarmActivity.this, "Nie podano danych", new PushDialogButtonsOkInterface() {
                @Override
                public void onOkButtonTap() {
                    return;
                }
            });

            return;
        }

        if(et.getText().equals("")|| et2.getText().equals("")||et3.getText().equals("")||et4.getText().equals("")){

            new PushDialogManager().showDialogWithOkButton(NewAlarmActivity.this, "Nie podano danych", new PushDialogButtonsOkInterface() {
                @Override
                public void onOkButtonTap() {
                    return;
                }
            });

            return;
        }
        else {
            ArrayList ad = new ArrayList<String>();
            ad.add("4");
            ad.add("5");
            ad.add("6");
            ad.add("7");
            ad.add("8");
            ad.add("9");

            et = (EditText) findViewById(R.id.editText2);
            et2= (EditText) findViewById(R.id.editText);
            String helperSec = et2.getText().toString();
            if (et.getText().toString().equals("2") && ad.contains(et2.getText().toString())) {
                helperSec = "3";
                new PushDialogManager().showDialogWithOkButton(NewAlarmActivity.this, "Podano nieporawny zakres godzinowy", new PushDialogButtonsOkInterface() {
                    @Override
                    public void onOkButtonTap() {
                        return;
                    }
                });

                return;
            }
            else {
                Log.e("", "");
                //editText2

                String hour = et.getText().toString() + helperSec;
                String minute = et3.getText().toString() + et4.getText().toString();

                String data = hour + ":" + minute + "," + "-" + "\n";

                try {
                    int hhelper = Integer.parseInt(hour);
                    int mhelper = Integer.parseInt(minute);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hhelper);
                    calendar.set(Calendar.MINUTE, mhelper);

                } catch (NumberFormatException e) {
                    new PushDialogManager().showDialogWithOkButton(NewAlarmActivity.this, "Niepoprawnie podana godzina", new PushDialogButtonsOkInterface() {
                        @Override
                        public void onOkButtonTap() {
                            return;
                        }
                    });
                    return;
                }
                String data1 = hour + ":" + minute;
                if (read(data1) == false) {

                    // Toast.makeText(getApplicationContext(), "istnieje juz nazwa podac inna", Toast.LENGTH_LONG).show();
                    new PushDialogManager().showDialogWithOkButton(NewAlarmActivity.this, "Podana godzina została dodana wczesniej", new PushDialogButtonsOkInterface() {
                        @Override
                        public void onOkButtonTap() {
                            return;
                        }
                    });
                    return;
                }else {
                    try {
                        outputStream = this.getApplicationContext().openFileOutput("savedFileClock", MODE_APPEND);
                        outputStream.write(data.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //  imm.toggleSoftInput(InputMethodManager.RESULT_HIDDEN,0);
                    finish();
                }
            }
        }
    }
    private boolean read(String name) {

        // TextView urlTextEdit=(TextView) findViewById(R.id.textView);
        try {
            FileInputStream fis = this.getApplicationContext().openFileInput("savedFileClock");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String data;
            while((data=bufferedReader.readLine( )) != null)
            {

                data=data.split(",")[0];
                // Toast.makeText(getApplicationContext(), data+" "+name, Toast.LENGTH_LONG).show();
                if (data.equals(name)){
                    // Toast.makeText(getApplicationContext(), "Nazwa linku wystapila", Toast.LENGTH_LONG).show();
                    return false;
                }


            }
        } catch (FileNotFoundException e) {
            Log.d("EXCEPTION", "File not found");
        } catch (UnsupportedEncodingException e) {
            Log.d("EXCEPTION", e.getMessage());
        } catch (IOException e) {
            Log.d("EXCEPTION", e.getMessage());
        }
        return true;
    }


}
