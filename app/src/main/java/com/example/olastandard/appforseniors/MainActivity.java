package com.example.olastandard.appforseniors;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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
    Button _toolbarSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _customToolbar = (CustomToolbar) findViewById(R.id.customToolbar);
        if (_customToolbar != null) {
            setSupportActionBar(_customToolbar);
        }
        _toolbarBackButton = (Button) findViewById(R.id.toolbarBackButton);
        _toolbarTitle = (TextView) findViewById(R.id.toolbarActivityTitle);
        _toolbarSaveButton = (Button) findViewById(R.id.toolbar_save);
    }

    protected void initAddlayout(int layout) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);
        ((FrameLayout) findViewById(R.id.main_content_below)).addView(view);
    }

    public void showBackButton() {
        _toolbarBackButton.setVisibility(View.VISIBLE);
        _toolbarSaveButton.setVisibility(View.VISIBLE);
    }

    public void hideBackButton() {
        _toolbarBackButton.setVisibility(View.GONE);
    }

    public void changeBackButtonTitle(String backButtonTItle) {
        _toolbarBackButton.setText(backButtonTItle);
    }

    public void setTitle(String newTitle) {
        _toolbarTitle.setText(newTitle);
    }

    public void hideRightButton() {
        _toolbarSaveButton.setVisibility(View.GONE);
    }

    public void showRightButton() {
        _toolbarSaveButton.setVisibility(View.VISIBLE);
    }

    public void changeTitleForRightButton(String rightButtonTItle) {
        _toolbarSaveButton.setText(rightButtonTItle);
    }

}
