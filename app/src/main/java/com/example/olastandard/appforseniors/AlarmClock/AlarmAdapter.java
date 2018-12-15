package com.example.olastandard.appforseniors.AlarmClock;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.olastandard.appforseniors.VoiceNotes.VoiceNotesList;

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

import com.example.olastandard.appforseniors.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmAdapter  extends RecyclerView.Adapter<com.example.olastandard.appforseniors.AlarmClock.AlarmAdapter.ViewHolder> {
    public List<String> mDataset;
    Context context;
    public int lastSelectedItem = -1;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.alarm_name)
        public TextView notesName;

        @BindView(R.id.alarm_switched)
        public TextView notesName2;

        @BindView(R.id.alarm_background)
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
            background.setBackgroundColor(mcontext.getResources().getColor(R.color.white));
        }

        @OnClick(R.id.alarm_list_cell)
        public void showEventDetail() {
            ((RWAlarmListActivity) mcontext).updateSelectedItem(getPosition());
        }
    }

    public AlarmAdapter(List<String> myDataset, Context context) {
        this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public com.example.olastandard.appforseniors.AlarmClock.AlarmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                           int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_cell, parent, false);

        com.example.olastandard.appforseniors.AlarmClock.AlarmAdapter.ViewHolder vh = new com.example.olastandard.appforseniors.AlarmClock.AlarmAdapter.ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(com.example.olastandard.appforseniors.AlarmClock.AlarmAdapter.ViewHolder holder, int position) {

       String status=mDataset.get(position).split(",")[1];
       if(status.equals("-")){
           status="Wyłączony";

       }
       else {
           status="Włączony";
           holder.notesName2.setTextColor(Color.parseColor("#2eabd6"));
           holder.notesName.setTextColor(Color.parseColor("#2eabd6"));
       }

        holder.notesName.setText(mDataset.get(position).split(",")[0]);
        holder.notesName2.setText(status);

         status=mDataset.get(position).split(",")[1];
        if (position == lastSelectedItem) {
            holder.changeBackgroundColorToGreen();

            // RWLinksActivity.listPosition=position;
        } else {
            holder.clearBackgroundColor();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}