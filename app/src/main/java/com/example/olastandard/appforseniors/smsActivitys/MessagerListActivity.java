package com.example.olastandard.appforseniors.smsActivitys;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.olastandard.appforseniors.ExampleActivity;
import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
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

    private static final int SMS_PERMISSION_CODE = 0;

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

        if (!hasReadSmsPermission()) {
            showRequestPermissionsInfoAlertDialog();
        }else {
            smsHelper = new SmsHelper(getApplicationContext(), this);
            List<PersonSmsData> listaSmsow = smsHelper.actualizeListOfSms();
            initRecyclerView(listaSmsow);
        }
    }

    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            Toast.makeText(this,"shouldShowRequestPermissionRationale(), no permission requested", Toast.LENGTH_SHORT).show();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                SMS_PERMISSION_CODE);
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
                    buttonSelect.setVisibility(View.GONE);
                    buttonDelete.setVisibility(View.GONE);
                    this._toolbarSaveButton.setVisibility(View.GONE);

                }
                return;
            }
        }
    }

    public void addListeners() {
        this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (((SmsPersonListAdapter) mAdapter).lastSelectedItem == -1) {
                    new PushDialogManager().showDialogWithOkButton(MessagerListActivity.this, "Żadna wiadomość nie została wybrana", new PushDialogButtonsOkInterface() {
                        @Override
                        public void onOkButtonTap() {

                        }
                    });
                } else {

                    (new PushDialogManager()).showDialogWithYesNoButtons(MessagerListActivity.this, "Czy na pewno chcesz usunąć wszystkie wiadomości od zaznaczonego kontaktu?", new PushDialogButtonsYesNoInterface() {

                        @Override
                        public void onYesButtonTap() {
                            smsHelper.deleteSms(smsHelper.getListOfPersonsData().get(((SmsPersonListAdapter) mAdapter).lastSelectedItem));

                            smsHelper = new SmsHelper(MessagerListActivity.this.getApplicationContext(), MessagerListActivity.this);
                            List<PersonSmsData> listaSmsow = smsHelper.actualizeListOfSms();
                            initRecyclerView(listaSmsow);
                        }

                        @Override
                        public void onNoButtonTap() {

                        }
                    });
                }
            }
        });
    }

    private void initRecyclerView(List<PersonSmsData> listOfPersonsSmsData) {
        listOfSms.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        listOfSms.setLayoutManager(mLayoutManager);
        mAdapter = new SmsPersonListAdapter(listOfPersonsSmsData, getApplicationContext());
        listOfSms.setAdapter(mAdapter);
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

    private void showRequestPermissionsInfoAlertDialog() {
        new PushDialogManager().showDialogWithOkButton(this, getResources().getString(R.string.sms_persmission), new PushDialogButtonsOkInterface() {
            @Override
            public void onOkButtonTap() {
                requestReadAndSendSmsPermission();
            }
        });
    }
}
