package com.example.olastandard.appforseniors.AlarmClock;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

import static android.support.v4.content.ContextCompat.startActivity;

public class AlarmReceiver extends WakefulBroadcastReceiver {
public static    Ringtone ringtone;
    @Override
    public void onReceive( Context context, Intent intent) {
      //  AlarmMenuActivity.getTextView2().setText("Enough Rest. Do Work Now!");
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        //Ringtone
                ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
        ;
       // context.startActivity(new Intent( context,AlarmActivity.class));
     //   startActivity();
       // context.startActivity(intent);


    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_example);
        initToolbar();
    }

    private void initToolbar() {
        showBackButton();
        hideRightButton();
        setTitle("Dzwonienie budzika");
    }*/
}
