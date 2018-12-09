package com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.olastandard.appforseniors.smsActivitys.MessagerActivity;
import com.example.olastandard.appforseniors.smsActivitys.app;

import java.util.Calendar;

public class SmsReceiver  extends BroadcastReceiver {

    //interface
    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_DELIVER_ACTION)) {
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

                smsHelper.saveSms(adress, message, "0", String.valueOf(Calendar.getInstance().getTime()), "inbox");
            }

            if (mListener != null) {
                mListener.messageReceived();
            }
        }
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
