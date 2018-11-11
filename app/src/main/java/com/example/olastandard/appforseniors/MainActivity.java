package com.example.olastandard.appforseniors;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olastandard.appforseniors.Objects.CustomToolbar;

public class MainActivity extends AppCompatActivity {

    CustomToolbar _customToolbar;
    Button _toolbarBackButton;
    TextView _toolbarTitle;
    public Button _toolbarSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        _customToolbar = (CustomToolbar) findViewById(R.id.customToolbar);
        if (_customToolbar != null) {
            setSupportActionBar(_customToolbar);
        }
        _toolbarBackButton = (Button) findViewById(R.id.toolbarBackButton);
        _toolbarTitle = (TextView) findViewById(R.id.toolbarActivityTitle);
        _toolbarSaveButton = (Button) findViewById(R.id.toolbar_save);


        setBaseTouchListeners();
    }

    protected void callBackButton() {
        MainActivity.super.onBackPressed();
        finish();
    }

    protected void initAddlayout(int layout) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);
        ((FrameLayout) findViewById(R.id.main_content_below)).addView(view);
    }

    public void setBaseTouchListeners() {
        _toolbarBackButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((Button) v).setTextColor(getResources().getColor(R.color.white));
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((Button) v).setTextColor(getResources().getColor(R.color.black));
                        v.invalidate();
                        MainActivity.super.onBackPressed();
                        finish();
                        break;
                    }
                }
                return false;
            }
        });
    }

    public void showBackButton() {
        _toolbarBackButton.setVisibility(View.VISIBLE);
        _toolbarBackButton.setClickable(true);
    }

    public void hideBackButton() {
        _toolbarBackButton.setVisibility(View.INVISIBLE);
        _toolbarBackButton.setClickable(false);
    }

    public void changeBackButtonTitle(String backButtonTItle) {
        _toolbarBackButton.setText(backButtonTItle);
    }

    public void setTitle(String newTitle) {
        _toolbarTitle.setText(newTitle);
    }




    public void hideRightButton() {
        _toolbarSaveButton.setVisibility(View.INVISIBLE);
        _toolbarSaveButton.setClickable(false);
    }

    public void showRightButton() {
        _toolbarSaveButton.setVisibility(View.VISIBLE);
        _toolbarSaveButton.setClickable(true);
    }

    public void changeTitleForRightButton(String rightButtonTItle) {
        _toolbarSaveButton.setText(rightButtonTItle);
    }

}
