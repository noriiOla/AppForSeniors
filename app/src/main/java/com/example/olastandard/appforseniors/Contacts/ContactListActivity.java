package com.example.olastandard.appforseniors.Contacts;

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
import com.example.olastandard.appforseniors.smsActivitys.smsAdapters.SmsPersonListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListActivity extends MainActivity {
    public static final int REQUEST_READ_CONTACTS = 79;

    @BindView(R.id.contact_recycler_view)
    RecyclerView contactView;
    @BindView(R.id.contact_button_select)
    Button buttonSelect;
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
            List<ContactData> contactList = getContacts();
            initRecyclerView(contactList);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                REQUEST_READ_CONTACTS);
        }
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
                    // permission denied,Disable the
                    // functionality that depends on this permission.
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
        setTitle(R.string.choose_number); //TODO:change title
    }

    private void initRecyclerView(List<ContactData> contactList) {
        System.out.println("rozmiar listy");
        System.out.println(contactList.size());
        contactView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        contactView.setLayoutManager(mLayoutManager);
        mAdapter = new ContactListAdapter(contactList, getApplicationContext());
        contactView.setAdapter(mAdapter);
        contactView.addItemDecoration(new com.example.olastandard.appforseniors.Objects.DividerItemDecoration(this));
    }

}
