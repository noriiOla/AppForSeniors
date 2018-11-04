package com.example.olastandard.appforseniors.smsActivitys.smsAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.R;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagerAdapter extends RecyclerView.Adapter {

    private PersonSmsData mDataset;
    Context context;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public MessagerAdapter(PersonSmsData myDataset, Context context) {
        this.mDataset = myDataset;
        this.mDataset.reverseListOfSms();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(this.context)
                    .inflate(R.layout.sms_send_cell, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(this.context)
                    .inflate(R.layout.sms_received_cell, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) viewHolder).bind(mDataset, i);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) viewHolder).bind(mDataset, i);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.getListOfSms().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataset.getListOfSms().get(position).getFolderName().equals("inbox")) {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }else {
            return VIEW_TYPE_MESSAGE_SENT;
        }
    }

    public class SentMessageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_message_name_send)
        public TextView messageName;
        @BindView(R.id.text_message_body_send)
        public TextView messageText;
        @BindView(R.id.text_message_time_send)
        public TextView messageDate;

        Context mcontext;

        public SentMessageHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mcontext = v.getContext();
        }

        void bind(PersonSmsData personData, int indexOfSms) {
            messageText.setText(personData.getListOfSms().get(indexOfSms).getMsg());
            messageDate.setText(personData.getListOfSms().get(indexOfSms).getTime());
        }
    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_message_name_receive)
        public TextView messageName;
        @BindView(R.id.text_message_body_receive)
        public TextView messageText;
        @BindView(R.id.text_message_time_receive)
        public TextView messageDate;

        Context mcontext;

        public ReceivedMessageHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mcontext = v.getContext();
        }

        void bind(PersonSmsData personData, int indexOfSms) {
            messageText.setText(personData.getListOfSms().get(indexOfSms).getMsg());
            messageName.setText(personData.getNameOfPersion());
            messageDate.setText(personData.getListOfSms().get(indexOfSms).getTime());
        }
    }
}
