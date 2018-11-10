package com.example.olastandard.appforseniors.Contacts;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.ContactData;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.smsActivitys.MessagerActivity;
import com.example.olastandard.appforseniors.smsActivitys.smsAdapters.SmsPersonListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactListActivity extends MainActivity {
    public static final int REQUEST_READ_CONTACTS = 79;

    @BindView(R.id.contact_recycler_view)
    RecyclerView contactView;
    @BindView(R.id.contact_button_edit)
    Button buttonEdit;
    @BindView(R.id.contact_button_delete)
    Button buttonDelete;
    @BindView(R.id.contact_background)
    ConstraintLayout background;


    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_list_contact);
        ButterKnife.bind(this);
        this.background.setBackgroundColor(getResources().getColor(R.color.crem));
        initToolbar();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            this.initList();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
    }

    private void initList(){
        List<ContactData> contactList = getContacts();
        Collections.sort(contactList);
        initRecyclerView(contactList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("ON RESUME");
        this.initList();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    List<ContactData> contactList = getContacts();
                    initRecyclerView(contactList);

                } else {
                    // TODO: popup information
                }
                return;
            }
        }
    }

    private List<ContactData> getContacts(){
        List<ContactData> contacts = new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.add(new ContactData(name,phoneNumber));
            System.out.println("name: " + name + "  number: "+phoneNumber);

        }
        phones.close();
        return contacts;
    }

    private void initToolbar() {
        showBackButton();
        hideRightButton();
        setTitle(R.string.choose_from_contact); //TODO:change title
    }

    private void initRecyclerView(List<ContactData> contactList) {
        contactView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        contactView.setLayoutManager(mLayoutManager);
        mAdapter = new ContactListAdapter(contactList, getApplicationContext());
        contactView.setAdapter(mAdapter);
        contactView.addItemDecoration(new com.example.olastandard.appforseniors.Objects.DividerItemDecoration(this));
    }

    @OnClick({R.id.contact_button_edit})
    public void editSelectedContact() {
        if (((ContactListAdapter)mAdapter).lastSelectedItem >= 0) {
            Intent intent = new Intent(getApplicationContext(), EditContactActivity.class);
            List<ContactData> contactList = getContacts();
            Collections.sort(contactList);
            ContactData contactData = contactList.get(((ContactListAdapter)mAdapter).lastSelectedItem);
            intent.putExtra("contactData", contactData);
            this.startActivity(intent);
        }
    }

    public void updateSelectedItem(int index) {
        ((ContactListAdapter)mAdapter).lastSelectedItem = index;
        mAdapter.notifyDataSetChanged();
        //changeButtonsColor();
    }

}
