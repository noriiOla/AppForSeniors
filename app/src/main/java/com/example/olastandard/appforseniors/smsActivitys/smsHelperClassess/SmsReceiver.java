package com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver  extends BroadcastReceiver {

    //interface
    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        mListener.messageReceived();
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
