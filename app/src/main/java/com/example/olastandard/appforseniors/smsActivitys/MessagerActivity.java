package com.example.olastandard.appforseniors.smsActivitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

public class MessagerActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_messager);
        initToolbar();
    }

    private void initToolbar() {
        setTitle(getResources().getString(R.string.sms));
    }
}
