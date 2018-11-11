package com.example.olastandard.appforseniors.Contacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

public class ContactTypeActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_contact_type);
        initToolbar();

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE},1);
        }

        setListeners();
    }

    private void setListeners(){
        Button go_to_contact_list = (Button) findViewById (R.id.go_to_contact);
        Button call_at = (Button) findViewById (R.id.call_at_number);
        Button newNumber = (Button) findViewById (R.id.go_to_new_number_activity);

        go_to_contact_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ContactListActivity.class));

            }
        });

        call_at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                if (ActivityCompat.checkSelfPermission(v.getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        newNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), AddContactActivity.class));
            }
        });

    }

    private void initToolbar() {
        showBackButton();
        hideRightButton();
        setTitle(R.string.choose_from_contact);
    }
}
