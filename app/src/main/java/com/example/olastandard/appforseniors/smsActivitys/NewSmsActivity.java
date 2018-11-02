package com.example.olastandard.appforseniors.smsActivitys;

import android.os.Bundle;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

import butterknife.ButterKnife;

public class NewSmsActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_new_sms);
        ButterKnife.bind(this);

    }
}
