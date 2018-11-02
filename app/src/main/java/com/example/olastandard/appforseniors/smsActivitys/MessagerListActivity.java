package com.example.olastandard.appforseniors.smsActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.GridView;

import com.example.olastandard.appforseniors.ExampleActivity;
import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
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

        this.background.setBackgroundColor(getResources().getColor(R.color.crem));
        initToolbar();

        smsHelper = new SmsHelper(getApplicationContext(), this);
        List<PersonSmsData> listaSmsow = smsHelper.actualizeListOfSms();
        initRecyclerView(listaSmsow);
    }

    private void initRecyclerView(List<PersonSmsData> listOfPersonsSmsData) {

        listOfSms.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        listOfSms.setLayoutManager(mLayoutManager);
        mAdapter = new SmsPersonListAdapter(listOfPersonsSmsData, getApplicationContext());
        listOfSms.setAdapter(mAdapter);
        listOfSms.addItemDecoration(new com.example.olastandard.appforseniors.Objects.DividerItemDecoration(this));
    }

    private void initToolbar() {
        setTitle(getResources().getString(R.string.sms));
        changeTitleForRightButton(getResources().getString(R.string.delete));
    }

    private void changeButtonsColor() {
        this.buttonSelect.setBackgroundColor(getResources().getColor(R.color.green));
        this.buttonDelete.setBackgroundColor(getResources().getColor(R.color.green));
    }

    @OnClick({R.id.sms_button_select})
    public void showSelectedSms() {
        Intent intent = new Intent(getApplicationContext(), MessagerActivity.class);
       // intent.putExtra("event_indeks", getAdapterPosition());
        this.startActivity(intent);
    }

    public void updateSelectedItem(int index) {
        ((SmsPersonListAdapter)mAdapter).lastSelectedItem = index;
        mAdapter.notifyDataSetChanged();
        changeButtonsColor();
    }

}
