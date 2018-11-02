package com.example.olastandard.appforseniors.smsActivitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.smsActivitys.smsAdapters.MessagerAdapter;

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
        changeTitleForRightButton(getResources().getString(R.string.newS));
    }
}
