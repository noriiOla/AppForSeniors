package com.example.olastandard.appforseniors.Contacts;
import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.ManuActivity;
import com.example.olastandard.appforseniors.Objects.ContactData;
import com.example.olastandard.appforseniors.R;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddContactActivity  extends MainActivity {
    @BindView(R.id.contact_name_input)
    public EditText contactNameInput;

    @BindView(R.id.contact_number_inputt)
    public EditText contactNumberInput;

    @BindView(R.id.button_save_contact)
    public Button saveContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_add_contact);
        ButterKnife.bind(this);

        initToolbar();

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_CONTACTS},1);
        }

        saveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactName = contactNameInput.getText().toString();
                System.out.println("NAZWA KONTAKTU : "+contactName);
                String contactNumber = contactNumberInput.getText().toString();
                insertContact(contactName,contactNumber);
                startActivity(new Intent(v.getContext(), ContactListActivity.class));

            }
        });
    }
    //TODO Walidacja danych

    private void insertContact( String displayName, String phoneNumber)
    {
        long rowContactId = getRawContactId();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rowContactId);
        Uri addContactsUri = ContactsContract.Data.CONTENT_URI;

        // Add an empty contact and get the generated id.
        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

        // Put contact display name value.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);

        getContentResolver().insert(addContactsUri, contentValues);
        System.out.println("******Dodano*****");
    }


    private void initToolbar() {
        showBackButton();
        changeTitleForRightButton(getResources().getString(R.string.save));
        setTitle(R.string.new_number);
    }

    // This method will only insert an empty data to RawContacts.CONTENT_URI
    // The purpose is to get a system generated raw contact id.
    private long getRawContactId()
    {
        // Inser an empty contact.
        ContentValues contentValues = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        // Get the newly created contact raw id.
        long ret = ContentUris.parseId(rawContactUri);
        return ret;
    }
}
