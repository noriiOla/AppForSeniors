package com.example.olastandard.appforseniors.smsActivitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.olastandard.appforseniors.ExampleActivity;
import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsYesNoInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.smsActivitys.smsAdapters.SmsPersonListAdapter;
import com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess.SmsHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessagerListActivity extends MainActivity {

    @BindView(R.id.sms_recycler_view)
    RecyclerView listOfSms;
    @BindView(R.id.sms_button_select)
    Button buttonSelect;
    @BindView(R.id.sms_button_delete)
    Button buttonDelete;
    @BindView(R.id.sms_background)
    ConstraintLayout background;

    private SmsHelper smsHelper;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_messager_list);
        ButterKnife.bind(this);
        this.background.setBackgroundColor(getResources().getColor(R.color.lightGray));
        initToolbar();
        addListeners();
        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
        buttonSelect.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            smsHelper = new SmsHelper(getApplicationContext(), this);
            List<PersonSmsData> listaSmsow = smsHelper.actualizeListOfSms();
            initRecyclerView(listaSmsow);

        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_SMS}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    smsHelper = new SmsHelper(getApplicationContext(), this);
                    List<PersonSmsData> listaSmsow = smsHelper.actualizeListOfSms();
                    initRecyclerView(listaSmsow);

                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    public void addListeners() {
        this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                (new PushDialogManager()).showDialogWithYesNoButtons(MessagerListActivity.this,"Czy na pewno chcesz usunąć wszystkie wiadomości od zaznaczonego kontaktu?", new PushDialogButtonsYesNoInterface() {

                    @Override
                    public void onYesButtonTap() {
                        smsHelper.deleteSms(smsHelper.getListOfPersonsData().get(((SmsPersonListAdapter)mAdapter).lastSelectedItem));

                        smsHelper = new SmsHelper(MessagerListActivity.this.getApplicationContext(), MessagerListActivity.this);
                        List<PersonSmsData> listaSmsow = smsHelper.actualizeListOfSms();
                        initRecyclerView(listaSmsow);
                    }

                    @Override
                    public void onNoButtonTap() {

                    }
                });
            }
        });
    }

    private void initRecyclerView(List<PersonSmsData> listOfPersonsSmsData) {

        listOfSms.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        listOfSms.setLayoutManager(mLayoutManager);
        mAdapter = new SmsPersonListAdapter(listOfPersonsSmsData, getApplicationContext());
        listOfSms.setAdapter(mAdapter);
       // listOfSms.addItemDecoration(new com.example.olastandard.appforseniors.Objects.DividerItemDecoration(this));
    }

    private void initToolbar() {
        setTitle(getResources().getString(R.string.sms));
        changeTitleForRightButton(getResources().getString(R.string.delete));
    }

    private void changeButtonsColor() {
        buttonSelect.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
    }

    @OnClick({R.id.sms_button_select})
    public void showSelectedSms() {
        if (((SmsPersonListAdapter)mAdapter).lastSelectedItem >= 0) {
            Intent intent = new Intent(getApplicationContext(), MessagerActivity.class);
            PersonSmsData smsData = smsHelper.getListOfPersonsData().get(((SmsPersonListAdapter)mAdapter).lastSelectedItem);
            intent.putExtra("smsData", smsData);
            this.startActivity(intent);
        }
    }

    @OnClick({R.id.sms_button_delete})
    public void showNewSmsView() {
        Intent intent = new Intent(getApplicationContext(), NewSmsActivity.class);
        this.startActivity(intent);
    }

    public void updateSelectedItem(int index) {
        ((SmsPersonListAdapter)mAdapter).lastSelectedItem = index;
        mAdapter.notifyDataSetChanged();
        changeButtonsColor();
    }

}
