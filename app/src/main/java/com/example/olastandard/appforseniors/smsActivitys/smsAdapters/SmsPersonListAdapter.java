package com.example.olastandard.appforseniors.smsActivitys.smsAdapters;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmsPersonListAdapter extends RecyclerView.Adapter<SmsPersonListAdapter.ViewHolder> {
    public List<PersonSmsData> mDataset;
    Context context;
    public int lastSelectedItem = -1;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sms_person_name)
        public TextView personName;
        @BindView(R.id.sms_last_sms_data)
        public TextView lastSmsData;
        @BindView(R.id.sms_cell_background)
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
            background.setBackgroundColor(mcontext.getResources().getColor(R.color.redColor));
        }

        public void clearBackgroundColor() {
            background.setBackgroundColor(mcontext.getResources().getColor(R.color.white));
        }

        @OnClick(R.id.sms_person_list_cell)
        public void showEventDetail() {
            ((MessagerListActivity)mcontext).updateSelectedItem(getPosition());
        }
    }

    public SmsPersonListAdapter(List<PersonSmsData> myDataset, Context context) {
        this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public SmsPersonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sms_person_list_cell, parent, false);

        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.lastSmsData.setText(mDataset.get(position).getListOfSms().get(0).getMsg());
        holder.personName.setText(mDataset.get(position).getNameOfPersion());
        if (position == lastSelectedItem) {
            holder.changeBackgroundColorToGreen();
            if (mDataset.get(position).getListOfSms().get(0).getReadState().equals("0")) {
                holder.lastSmsData.setTextColor(context.getResources().getColor(R.color.redColor));
                holder.personName.setTextColor(context.getResources().getColor(R.color.redColor));
            }else {
                holder.lastSmsData.setTextColor(context.getResources().getColor(R.color.black));
                holder.personName.setTextColor(context.getResources().getColor(R.color.black));
            }
        }else {
            if (mDataset.get(position).getListOfSms().get(0).getReadState().equals("0")) {
                holder.lastSmsData.setTextColor(context.getResources().getColor(R.color.redColor));
                holder.personName.setTextColor(context.getResources().getColor(R.color.redColor));
                holder.clearBackgroundColor();
            }else {
                holder.clearBackgroundColor();
                holder.lastSmsData.setTextColor(context.getResources().getColor(R.color.black));
                holder.personName.setTextColor(context.getResources().getColor(R.color.black));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}