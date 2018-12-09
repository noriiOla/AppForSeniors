package com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.smsActivitys.MessagerActivity;
import com.example.olastandard.appforseniors.smsActivitys.MessagerListActivity;
import com.example.olastandard.appforseniors.smsActivitys.app;

import java.util.Calendar;

public class SmsReceiver  extends BroadcastReceiver {

    //interface
    private static SmsListener mListener;
    final MediaPlayer mp = MediaPlayer.create(app.getContext(), R.raw.never);

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            final SmsHelper smsHelper = new SmsHelper(app.getContext());

            if (smsHelper.isDefaultSmsApp(app.getContext())) {
                Bundle bundle = intent.getExtras();
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                String adress = messages[0].getOriginatingAddress();
                String message = messages[0].getMessageBody();

                sendNotification(smsHelper.getContactName(adress), message);

                smsHelper.saveSms(adress, message, "0", String.valueOf(Calendar.getInstance().getTime()), "inbox");
                mp.start();
            }
        }
        if (mListener != null) {
            mListener.messageReceived();
        }
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

    private void sendNotification(String title, String body)
    {
        Context context = app.getContext();

        Intent notificationIntent = new Intent(context, MessagerListActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.sms_icon)
                .setContentTitle(title)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] { 1000, 1000})
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(4, mBuilder.build());
    }
}
