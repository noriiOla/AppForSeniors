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
        System.out.println("----> alarm activity");
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
         appIntent = PendingIntent.getBroadcast(this,(int) _id , myIntent,PendingIntent.FLAG_ONE_SHOT);

    }
    private void cancelAlarm(View v) {
        if (alarmManager!= null) {
            alarmManager.cancel(appIntent);
            AlarmReceiver.ringtone.stop();

        }}
        public void openApp(){
            Intent i = new Intent(this, AlarmActivity.class);
       /* Bundle b = new Bundle();
        b.putInt(Constants.ALARM_ID, intent.getExtras().getInt(Constants.ALARM_ID));
        if(intent.getExtras().containsKey(Constants.SNOOZE_ALARM)){
            b.putString(Constants.SNOOZE_ALARM, intent.getExtras().getString(Constants.SNOOZE_ALARM));
        }
        i.putExtras(b);*/
            //THESE ARE THE FLAGS NEEDED TO START THE ACTIVITY AND TO PREVENT THE BUG
            //(CLEAR_TASK is crucial for the bug and new task is needed to start activity from outside of an activity)
            //  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            System.out.println("----> on intentservice");
            startActivity(i);

        }
    private void initToolbar() {
        showBackButton();
        hideRightButton();
        setTitle("EXAMPLE");
    }
}

