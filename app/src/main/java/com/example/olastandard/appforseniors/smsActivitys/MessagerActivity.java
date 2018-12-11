package com.example.olastandard.appforseniors.smsActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.smsActivitys.smsAdapters.MessagerAdapter;
import com.example.olastandard.appforseniors.smsActivitys.smsAdapters.SmsPersonListAdapter;
import com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess.SmsHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagerActivity extends MainActivity {

    PersonSmsData smsData;

    @BindView(R.id.messager_recyclerView)
    RecyclerView messagerSmsList;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_messager);
        ButterKnife.bind(this);

        smsData = (PersonSmsData) getIntent().getSerializableExtra("smsData");

        if (smsData == null) {
            callBackButton();
        }else {
            initToolbar(smsData.getNameOfPersion());
            initRecyclerView();
        }

        addListeners();
        if (smsData.getListOfSms().get(smsData.getListOfSms().size() - 1).getReadState().equals("0")) {
            (new SmsHelper(getApplicationContext(), this)).markMessageRead(smsData);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SmsHelper smsHelper = new SmsHelper(getApplicationContext(), this);
        List<PersonSmsData> listaOsob = smsHelper.actualizeListOfSms();
        for (PersonSmsData data: listaOsob) {
            if(data.getNameOfPersion().equals(smsData.getNameOfPersion())) {
                smsData = data;
                mAdapter = new MessagerAdapter(smsData, getApplicationContext());
                messagerSmsList.setAdapter(mAdapter);
                messagerSmsList.scrollToPosition(smsData.getListOfSms().size() - 1);
                break;
            }
        }
    }

    public void addListeners() {
        this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewSmsActivity.class);
                intent.putExtra("smsData", smsData);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        messagerSmsList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        messagerSmsList.setLayoutManager(mLayoutManager);
        mAdapter = new MessagerAdapter(smsData, getApplicationContext());
        messagerSmsList.setAdapter(mAdapter);
        messagerSmsList.scrollToPosition(smsData.getListOfSms().size() - 1);
    }

    private void initToolbar(String name) {
        setTitle(name);
        changeTitleForRightButton(getResources().getString(R.string.write));
    }
}
