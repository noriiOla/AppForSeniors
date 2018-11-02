package com.example.olastandard.appforseniors.smsActivitys;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.Objects.Sms;
import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess.SmsHelper;

import java.util.ArrayList;
import java.util.List;

public class MessagerListActivity extends MainActivity {

    private SmsHelper smsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_messager_list);
        smsHelper = new SmsHelper(getApplicationContext(), this);

        List<PersonSmsData> listaSmsow = smsHelper.actualizeListOfSms();
        Double s = 0.1;

    }


}
