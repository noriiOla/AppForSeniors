package com.example.olastandard.appforseniors.Contacts;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.ContactData;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
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

        }if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},2);
        }
        contactData = (ContactData) getIntent().getSerializableExtra("contactData");

        contactNameInput.setText(contactData.getNameOfPersion());
        contactNumberInput.setText(contactData.getNumebrOfPerson());

        saveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: walidacja
                String contactName = contactNameInput.getText().toString();
                String contactNumber = contactNumberInput.getText().toString();
                updateContactList(v.getContext(), contactName,contactNumber);
                startActivity(new Intent(v.getContext(), ContactListActivity.class));
            }
        });
    }

    private void initToolbar() {
        showBackButton();
        changeTitleForRightButton(getResources().getString(R.string.save));
        setTitle(R.string.new_number);
    }

    public static boolean updateContactList(Context context, String name, String newPhoneNumber){
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        System.out.println("-------EDIT----------" + name);
        String where = ContactsContract.Data.DISPLAY_NAME + " = ? AND " +
                ContactsContract.Data.MIMETYPE + " = ? AND " +
                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE) + " = ? ";

        String[] params = new String[] {name,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME)};
        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, params)
                .withValue(ContactsContract.CommonDataKinds.Phone.DATA, newPhoneNumber)
                .build());
        try {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            System.out.println("successful update");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
