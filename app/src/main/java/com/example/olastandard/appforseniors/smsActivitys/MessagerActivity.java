package com.example.olastandard.appforseniors.smsActivitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.R;

public class MessagerActivity extends MainActivity {

    PersonSmsData smsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_messager);
        smsData = (PersonSmsData) getIntent().getSerializableExtra("smsData");
        if (smsData == null) {
            callBackButton();
        }else {
            initToolbar(smsData.getNameOfPersion());
        }
    }

    private void initToolbar(String name) {
        setTitle(name);
    }
}
