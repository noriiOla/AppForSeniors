package com.example.olastandard.appforseniors.Contacts;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.ContactData;
import com.example.olastandard.appforseniors.R;

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

    @OnClick({R.id.contact_button_delete})
    public void deleteSelectedContact() {
        if (((ContactListAdapter)mAdapter).lastSelectedItem >= 0) {
            List<ContactData> contactList = getContacts();
            Collections.sort(contactList);
            ContactData contactData = contactList.get(((ContactListAdapter)mAdapter).lastSelectedItem);
            //TODO potwierdzenie usuwania
            deleteContact(getApplicationContext(), contactData.getNumebrOfPerson(), contactData.getNameOfPersion());
            this.initList();
            Toast toast = Toast.makeText(getApplicationContext(), "Usunięto kontakt", Toast.LENGTH_SHORT);

            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(30);
            toast.show();
        }
    }

    @OnClick({R.id.contact_button_call})
    public void callToSelectedContact() {
        if (((ContactListAdapter)mAdapter).lastSelectedItem >= 0) {
            List<ContactData> contactList = getContacts();
            Collections.sort(contactList);
            ContactData contactData = contactList.get(((ContactListAdapter)mAdapter).lastSelectedItem);
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("tel:" + contactData.getNumebrOfPerson()));
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE},1);
            }
            getApplicationContext().startActivity(intent);

        }
    }

    public static boolean deleteContact(Context ctx, String phone, String name) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
        Cursor cur = ctx.getContentResolver().query(contactUri, null, null, null, null);
        try {
            if (cur.moveToFirst()) {
                do {
                    if (cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)).equalsIgnoreCase(name)) {
                        String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                        ctx.getContentResolver().delete(uri, null, null);
                        return true;
                    }

                } while (cur.moveToNext());
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            cur.close();
        }
        return false;
    }

    public void updateSelectedItem(int index) {
        ((ContactListAdapter)mAdapter).lastSelectedItem = index;
        mAdapter.notifyDataSetChanged();
        //changeButtonsColor();
    }

}
