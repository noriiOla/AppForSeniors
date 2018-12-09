package com.example.olastandard.appforseniors.AlarmClock;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.olastandard.appforseniors.LinksActivity;
import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.R;

import java.io.Console;
import java.io.FileOutputStream;
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
       // imm.toggleSoftInput(InputMethodManager.RESULT_SHOWN,0);
        //et.setFocusableInTouchMode(true);
        et.requestFocus();
        _toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSave(v);
            }
        });
        /*if ( et.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        }*/
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

            new PushDialogManager().showDialogWithOkButton(NewAlarmActivity.this, "nie podano danych", new PushDialogButtonsOkInterface() {
                @Override
                public void onOkButtonTap() {
                    return;
                }
            });

            return;
        }

        if(et.getText().equals("")|| et2.getText().equals("")||et3.getText().equals("")||et4.getText().equals("")){

            new PushDialogManager().showDialogWithOkButton(NewAlarmActivity.this, "nie podano danych", new PushDialogButtonsOkInterface() {
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
            }
            Log.e("","");
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

            }catch( NumberFormatException e){
                new PushDialogManager().showDialogWithOkButton(NewAlarmActivity.this, "Niepoprawnie podana godzina", new PushDialogButtonsOkInterface() {
                    @Override
                    public void onOkButtonTap() {
                        return;
                    }
                });
                return;
            }


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
