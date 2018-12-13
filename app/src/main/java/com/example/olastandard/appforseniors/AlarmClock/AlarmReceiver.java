package com.example.olastandard.appforseniors.AlarmClock;
import com.example.olastandard.appforseniors.smsActivitys.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v4.app.NotificationCompat;

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

        sendNotification();
    }


    private void sendNotification()
    {
        Context context = app.getContext();

        Intent notificationIntent = new Intent(context, AlarmActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.sms_icon)
                .setContentTitle("Budzik")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] { 1000, 1000})
                .setContentText("Budzik")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(4, mBuilder.build());
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
