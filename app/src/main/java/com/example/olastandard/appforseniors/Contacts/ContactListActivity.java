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
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Navigation.NavigationDataManager;
import com.example.olastandard.appforseniors.Navigation.NavigationListActivity;
import com.example.olastandard.appforseniors.Objects.ContactData;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsYesNoInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
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
    @BindView(R.id.contact_button_call)
    Button buttonCall;
    @BindView(R.id.contact_button_choose)
    Button buttonChoose;


    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_list_contact);
        ButterKnife.bind(this);
        initToolbar();
        this.initList();

        ContactListActivityType.conactListView result = (ContactListActivityType.conactListView) getIntent().getSerializableExtra("typeOfView");

        if (result != null && result == ContactListActivityType.conactListView.selectContact) {
            this.buttonEdit.setVisibility(View.GONE);
            this.buttonDelete.setVisibility(View.GONE);
            this.buttonCall.setVisibility(View.GONE);
            this.buttonChoose.setVisibility(View.VISIBLE);
            this.buttonChoose.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        }
        this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), AddContactActivity.class));
            }
        });

    }

    private void initList() {
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

    private List<ContactData> getContacts() {
        List<ContactData> contacts = new ArrayList<>();
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
        };

        String sortBy = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"; //sorted by name

        Cursor phones = null;
        try {
            phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, sortBy);
        } catch (SecurityException e) {
            Log.e("ERR", e.getMessage());
        }

        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (isValidPhoneNumber(phoneNumber)) {
                contacts.add(new ContactData(name, phoneNumber));

            }
        }
        phones.close();
        return contacts;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return android.util.Patterns.PHONE.matcher(phoneNumber).matches();
    }

    private void initToolbar() {
        showBackButton();
        setTitle(getResources().getString((R.string.contact_lista)));
        changeTitleForRightButton(getResources().getString(R.string.newL));
    }

    private void initRecyclerView(List<ContactData> contactList) {
        contactView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        contactView.setLayoutManager(mLayoutManager);
        mAdapter = new ContactListAdapter(contactList, getApplicationContext());
        contactView.setAdapter(mAdapter);
    }

    @OnClick({R.id.contact_button_edit})
    public void editSelectedContact() {
        if (((ContactListAdapter) mAdapter).lastSelectedItem >= 0) {
            Intent intent = new Intent(getApplicationContext(), EditContactActivity.class);
            List<ContactData> contactList = getContacts();
            Collections.sort(contactList);
            ContactData contactData = contactList.get(((ContactListAdapter) mAdapter).lastSelectedItem);
            intent.putExtra("contactData", contactData);
            this.startActivity(intent);
        }
    }

    @OnClick({R.id.contact_button_delete})
    public void deleteSelectedContact() {
        if (((ContactListAdapter) mAdapter).lastSelectedItem >= 0) {
            List<ContactData> contactList = getContacts();
            Collections.sort(contactList);
            ContactData contactData = contactList.get(((ContactListAdapter) mAdapter).lastSelectedItem);
            showDialogBoxDeleteItem(contactData);
        }
    }

    private void showDialogBoxDeleteItem(final ContactData contactData) {
        new PushDialogManager().showDialogWithYesNoButtons(this, "Czy na pewno chcesz usunąć ten kontakt?", new PushDialogButtonsYesNoInterface() {
            @Override
            public void onYesButtonTap() {
                deleteContact(contactData);
            }

            @Override
            public void onNoButtonTap() {
            }
        });
    }

    private void deleteContact(ContactData contactData){
        deleteContact(getApplicationContext(), contactData.getNumebrOfPerson(), contactData.getNameOfPersion());
        List<ContactData> contactList = getContacts();
        Collections.sort(contactList);
        reloadTableData(contactList);
        //initList();
    }

    private void reloadTableData(List<ContactData> contactData) {
        ((ContactListAdapter)mAdapter).mDataset = contactData;
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.contact_button_call})
    public void callToSelectedContact() {
        if (((ContactListAdapter) mAdapter).lastSelectedItem >= 0) {
            List<ContactData> contactList = getContacts();
            Collections.sort(contactList);
            ContactData contactData = contactList.get(((ContactListAdapter) mAdapter).lastSelectedItem);
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("tel:" + contactData.getNumebrOfPerson()));
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
            }
            getApplicationContext().startActivity(intent);

        }
    }

    @OnClick(R.id.contact_button_choose)
    public void backWithSelectedContact() {
        List<ContactData> contactList = getContacts();
        Collections.sort(contactList);
        ContactData contactData = contactList.get(((ContactListAdapter) mAdapter).lastSelectedItem);

        Intent previousScreen = new Intent(getApplicationContext(), MessagerActivity.class);
        PersonSmsData smsData = new PersonSmsData(contactData.getNumebrOfPerson());
        smsData.setNameOfPersion(contactData.getNameOfPersion());
        previousScreen.putExtra("contactData", smsData);
        setResult(200, previousScreen);
        finish();
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
        ((ContactListAdapter) mAdapter).lastSelectedItem = index;
        mAdapter.notifyDataSetChanged();
        changeButtonsColor();
    }

    private void changeButtonsColor() {
        buttonEdit.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
        buttonCall.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
    }

}
