package com.example.olastandard.appforseniors.AlarmClock;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

public class AlarmIntentService extends IntentService {
    public AlarmIntentService() {
        super("AlarmIntentService");
        Intent i = new Intent(this, AlarmActivity.class);
        startActivity(i);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
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

        AlarmReceiver.completeWakefulIntent(intent);
    }
}