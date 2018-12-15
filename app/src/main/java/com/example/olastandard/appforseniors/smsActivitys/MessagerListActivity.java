package com.example.olastandard.appforseniors.smsActivitys;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.olastandard.appforseniors.Navigation.NavigationListAdapter;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.Objects.PlaceData;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsYesNoInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.smsActivitys.smsAdapters.SmsPersonListAdapter;
import com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess.SmsHelper;
import com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess.SmsListener;
import com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess.SmsReceiver;

import java.util.Calendar;
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

    SmsReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_messager_list);
        ButterKnife.bind(this);

        this.background.setBackgroundColor(getResources().getColor(R.color.lightGray));
        initToolbar();
        addListeners();
        buttonDelete.setText(getResources().getString(R.string.delete));
//        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
//        buttonSelect.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
    }

    @Override
    protected void onStart() {
        super.onStart();

        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        buttonSelect.setBackground(getResources().getDrawable(R.drawable.button_shape_white));

        if (hasReadSmsPermission()) {
            smsHelper = new SmsHelper(getApplicationContext(), this);
            List<PersonSmsData> listaSmsow = smsHelper.actualizeListOfSms();
            initRecyclerView(listaSmsow);
        }

        smsReceiver = new SmsReceiver();
        smsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived() {
                smsHelper = new SmsHelper(MessagerListActivity.this.getApplicationContext(), MessagerListActivity.this);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<PersonSmsData> listaSmsow = smsHelper.actualizeListOfSms();
                        reloadTableData(listaSmsow);
                    }
                    }, 1000);
            }
        });
        
        ((SmsPersonListAdapter) mAdapter).lastSelectedItem = -1;
    }

    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    public void addListeners() {
        this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewSmsActivity.class);
                MessagerListActivity.this.startActivity(intent);
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
        changeTitleForRightButton(getResources().getString(R.string.newS));
    }

    private void changeButtonsColor() {
        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_red));
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

    private void reloadTableData(List<PersonSmsData> navigationList) {
        ((SmsPersonListAdapter)mAdapter).mDataset = navigationList;
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.sms_button_delete})
    public void showNewSmsView() {
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
                    reloadTableData(listaSmsow);
                }

                @Override
                public void onNoButtonTap() {

                }
            });
        }
    }

    public void updateSelectedItem(int index) {
        ((SmsPersonListAdapter)mAdapter).lastSelectedItem = index;
        mAdapter.notifyDataSetChanged();
        changeButtonsColor();
    }
}

