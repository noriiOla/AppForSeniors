package com.example.olastandard.appforseniors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ManuActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_manu);
        initToolbar();
    }

    private void initToolbar() {
        hideBackButton();
        hideRightButton();
        setTitle(getResources().getString(R.string.menu));
    }

}
