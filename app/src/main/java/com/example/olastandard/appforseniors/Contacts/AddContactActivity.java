package com.example.olastandard.appforseniors.Contacts;
import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

import java.util.ArrayList;

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
                String contactNumber = contactNumberInput.getText().toString();
                insertContact(contactName,contactNumber);
                startActivity(new Intent(v.getContext(), ContactListActivity.class));

            }
        });
    }
    //TODO Walidacja danych

    private void insertContact( String displayName, String phoneNumber)
    {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName) // Name of the person
                .build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(
                        ContactsContract.Data.RAW_CONTACT_ID,   rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber) // Number of the person
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); // Type of mobile number
        try
        {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        }
        catch (Exception e)
        {
            // error
        }
    }

    private void initToolbar() {
        showBackButton();
        changeTitleForRightButton(getResources().getString(R.string.save));
        setTitle(R.string.new_number);
    }

}
