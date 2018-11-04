package com.example.olastandard.appforseniors.Contacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.olastandard.appforseniors.Objects.ContactData;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.smsActivitys.MessagerListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.smsActivitys.MessagerListActivity;
import com.example.olastandard.appforseniors.smsActivitys.smsAdapters.SmsPersonListAdapter.ViewHolder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ContactListAdapter extends RecyclerView.Adapter<com.example.olastandard.appforseniors.Contacts.ContactListAdapter.ViewHolder> {
    private List<ContactData> mDataset;
    Context context;
    public int lastSelectedItem = -1;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.contact_name)
        public TextView contactName;
        @BindView(R.id.contact_number)
        public TextView contactNumber;
        @BindView(R.id.contact_item_background)
        public LinearLayout background;

        Context mcontext;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mcontext = v.getContext();
        }

        public void changeBackgroundColorToGreen() {
            background.setBackgroundColor(mcontext.getResources().getColor(R.color.green));
        }

        public void changeBackgroundColorToRed() {
            background.setBackgroundColor(mcontext.getResources().getColor(R.color.red));
        }

        public void clearBackgroundColor() {
            background.setBackgroundColor(mcontext.getResources().getColor(R.color.crem));
        }

        @OnClick(R.id.contact_list_item)
        public void showEventDetail() {
            ((MessagerListActivity)mcontext).updateSelectedItem(getPosition());
        }
    }

    public ContactListAdapter(List<ContactData> myDataset, Context context) {
        this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public com.example.olastandard.appforseniors.Contacts.ContactListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                                                             int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item, parent, false);

        com.example.olastandard.appforseniors.Contacts.ContactListAdapter.ViewHolder vh =
                new com.example.olastandard.appforseniors.Contacts.ContactListAdapter.ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(com.example.olastandard.appforseniors.Contacts.ContactListAdapter.ViewHolder holder, int position) {
        holder.contactNumber.setText(mDataset.get(position).getNumebrOfPerson());
        holder.contactName.setText(mDataset.get(position).getNameOfPersion());
        if (position == lastSelectedItem) {
            holder.changeBackgroundColorToGreen();
        }else {
            //if (mDataset.get(position).getListOfSms().get(0).getReadState().equals("0")) {
          //      holder.changeBackgroundColorToRed();
           // }else {
           //     holder.clearBackgroundColor();
           // }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}