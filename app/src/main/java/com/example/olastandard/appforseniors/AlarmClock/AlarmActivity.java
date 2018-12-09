package com.example.olastandard.appforseniors.AlarmClock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

public class AlarmActivity  extends MainActivity {
    AlarmManager alarmManager;
    PendingIntent appIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_alarm);
        initToolbar();
        int _id = (int) System.currentTimeMillis();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
         appIntent = PendingIntent.getBroadcast(this,(int) _id , myIntent,PendingIntent.FLAG_ONE_SHOT);

    }
    private void cancelAlarm(View v) {
        if (alarmManager!= null) {
            alarmManager.cancel(appIntent);
            AlarmReceiver.ringtone.stop();

        }}
    private void initToolbar() {
        showBackButton();
        hideRightButton();
        setTitle("EXAMPLE");
    }
}

