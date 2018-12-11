package com.example.olastandard.appforseniors.Contacts;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.ContactData;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditContactActivity extends MainActivity {
    @BindView(R.id.contact_name_input)
    public EditText contactNameInput;

    @BindView(R.id.button_save_contact)
    public Button saveContact;

    @BindView(R.id.contact_number_inputt)
    public EditText contactNumberInput;

    ContactData contactData;

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
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},2);
        }
        contactData = (ContactData) getIntent().getSerializableExtra("contactData");
        contactNameInput.setText(contactData.getNameOfPersion());
        contactNumberInput.setText(contactData.getNumebrOfPerson());

        saveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactName = contactNameInput.getText().toString();
                String contactNumber = contactNumberInput.getText().toString();
                if(contactName.isEmpty() || contactNumber.isEmpty()){
                    showDialogBox("Wpisz numer oraz nazwę");
                }
                else {
                    updateNameAndNumber(v.getContext(), contactData.getNumebrOfPerson(), contactName, contactNumber);
                    startActivity(new Intent(v.getContext(), ContactListActivity.class));
                }
            }
        });
    }

    private void showDialogBox(String text) {
        new PushDialogManager().showDialogWithOkButton(this, text, new PushDialogButtonsOkInterface() {
            @Override
            public void onOkButtonTap() {
            }
        });
    }

    private void initToolbar() {
        showBackButton();
        changeTitleForRightButton(getResources().getString(R.string.save));
        setTitle(R.string.new_number);
    }

    private final static String[] DATA_COLS = {
            ContactsContract.Data.MIMETYPE,
            ContactsContract.Data.DATA1,//phone number
            ContactsContract.Data.CONTACT_ID
    };

    public static boolean updateNameAndNumber(final Context context, String number, String newName, String newNumber) {
        if (context == null || number == null || number.trim().isEmpty()) return false;
        if (newNumber != null && newNumber.trim().isEmpty()) newNumber = null;
        if (newNumber == null) return false;

        String contactId = getContactId(context, number);
        if (contactId == null) return false;

        //selection for name
        String where = String.format(
                "%s = '%s' AND %s = ?",
                DATA_COLS[0], //mimetype
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                DATA_COLS[2]/*contactId*/);

        String[] args = {contactId};

        ArrayList<ContentProviderOperation> operations = new ArrayList<>();

        operations.add(
                ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(where, args)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, newName)
                        .build()
        );

        //change selection for number
        where = String.format(
                "%s = '%s' AND %s = ?",
                DATA_COLS[0],//mimetype
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                DATA_COLS[1]/*number*/);

        //change args for number
        args[0] = number;

        operations.add(
                ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(where, args)
                        .withValue(DATA_COLS[1]/*number*/, newNumber)
                        .build()
        );

        try {
            ContentProviderResult[] results = context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getContactId(Context context, String number) {
        if (context == null) return null;

        Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.NUMBER + "=?",
                new String[]{number},
                null
        );

        if (cursor == null || cursor.getCount() == 0) return null;

        cursor.moveToFirst();
        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
        cursor.close();
        return id;
    }
}
