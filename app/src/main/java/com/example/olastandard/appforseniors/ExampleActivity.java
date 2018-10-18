package com.example.olastandard.appforseniors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ExampleActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_example);
        initToolbar();
    }

    private void initToolbar() {
        showBackButton();
        hideRightButton();
        setTitle("EXAMPLE");
    }
}
