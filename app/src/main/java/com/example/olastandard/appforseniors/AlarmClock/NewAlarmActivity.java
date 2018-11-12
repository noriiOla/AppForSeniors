package com.example.olastandard.appforseniors.AlarmClock;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

//https://blog.mikesir87.io/2013/04/android-creating-an-alarm-with-alarmmanager/
public class NewAlarmActivity extends MainActivity {
    EditText et;

    EditText et2;
    EditText et3;
    EditText et4;
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
        et.requestFocus();
        _toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSave(v);
            }
        });
        if ( et.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void initToolbar() {
        showBackButton();
        showRightButton();
        changeTitleForRightButton("NOWY");
        setTitle("Nowy Alarm");

    }
    public void buttonSave (View view)
    {

        if(et.getText().equals("")|| et2.getText().equals("")||et3.getText().equals("")||et4.getText().equals("")){
            return;
        }
        ArrayList ad =new ArrayList<String>();
        ad.add("4");
        ad.add("5");
        ad.add("6");
        ad.add("7");
        ad.add("8");
        ad.add("9");

        String helperSec=et2.getText().toString();
        if(et.getText().toString().equals("2")&& ad.contains(et2.getText().toString())){
        helperSec="3";
        }
        String hour=et.getText().toString()+helperSec;
        String minute=et3.getText().toString()+et4.getText().toString();

        String data=hour+":"+minute+":"+" Wyłaczone"+"\n";
        int hhelper=Integer.parseInt(hour);
        int mhelper=Integer.parseInt(minute);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hhelper);
        calendar.set(Calendar.MINUTE, mhelper);

        try {
            outputStream = this.getApplicationContext().openFileOutput("savedFileClock", MODE_APPEND);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

}
