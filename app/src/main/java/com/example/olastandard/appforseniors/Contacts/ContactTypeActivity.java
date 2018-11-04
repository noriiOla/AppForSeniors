package com.example.olastandard.appforseniors.Contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

public class ContactTypeActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_contact_type);
        initToolbar();
        System.out.println("bla bla");
        LinearLayout app_layer = (LinearLayout) findViewById (R.id.go_to_contact);
        app_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ContactListActivity.class));

            }
        });
    }

    private void initToolbar() {
        showBackButton();
        hideRightButton();
        setTitle(R.string.choose_from_contact);
    }
}
