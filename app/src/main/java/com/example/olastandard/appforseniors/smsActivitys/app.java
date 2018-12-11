package com.example.olastandard.appforseniors.smsActivitys;


import android.app.Application;
import android.content.Context;
import android.content.Intent;

//import com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess.MmsReceiver;
import com.example.olastandard.appforseniors.SampleBootReceiver;
import com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess.SmsReceiver;

public class app extends Application {

    private static Context context;

    public static Context getContext() { return context; }

    public void setContext(Context context_) { context = context_; }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();

        startService(new Intent(this, SmsReceiver.class));
        startService(new Intent(this, SampleBootReceiver.class));

    }
}