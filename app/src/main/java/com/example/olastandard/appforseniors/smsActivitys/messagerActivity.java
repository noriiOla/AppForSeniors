package com.example.olastandard.appforseniors.smsActivitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

import butterknife.ButterKnife;

public class messagerActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_messager);
        ButterKnife.bind(this);
        //https://www.dev2qa.com/android-chat-app-example-using-recyclerview/
    }

    public void setupView() {

    }

}
