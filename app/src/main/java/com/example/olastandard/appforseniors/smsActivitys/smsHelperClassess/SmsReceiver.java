package com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver  extends BroadcastReceiver {

//
//    private static final String TAG = "SmsBroadcastReceiver";
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
//            String smsSender = "";
//            String smsBody = "";
//            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
//                smsBody += smsMessage.getMessageBody();
//            }
//
//            if (smsBody.startsWith("cos nie wiem co")) {
//                Log.d(TAG, "Sms with condition detected");
//                Toast.makeText(context, "BroadcastReceiver caught conditional SMS: " + smsBody, Toast.LENGTH_LONG).show();
//            }
//            Log.d(TAG, "SMS detected: From " + smsSender + " With text " + smsBody);
//        }
//    }

    //interface
    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();
            //Check the sender to filter messages which we require to read

            if (sender.equals("GADGETSAINT"))
            {

                String messageBody = smsMessage.getMessageBody();

                //Pass the message text to interface
                mListener.messageReceived(messageBody);

            }
        }

    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
