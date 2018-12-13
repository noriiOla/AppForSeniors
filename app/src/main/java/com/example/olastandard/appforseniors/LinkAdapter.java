package com.example.olastandard.appforseniors;

import android.content.Context;
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

public class LinkAdapter  extends RecyclerView.Adapter<LinkAdapter.ViewHolder> {
    private List<String> mDataset;
    Context context;
    public int lastSelectedItem = -1;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.links_name)
        public TextView notesName;

        @BindView(R.id.links_background)
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

        @OnClick(R.id.notes_list_cell)
        public void showEventDetail() {
            ((VoiceNotesList) mcontext).updateSelectedItem(getPosition());
        }
    }

    public LinkAdapter(List<String> myDataset, Context context) {
        this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public com.example.olastandard.appforseniors.LinkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                                       int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.link_cell, parent, false);

        com.example.olastandard.appforseniors.LinkAdapter.ViewHolder vh = new com.example.olastandard.appforseniors.LinkAdapter.ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(LinkAdapter.ViewHolder holder, int position) {
        holder.notesName.setText(mDataset.get(position));
        if (position == lastSelectedItem) {
            holder.changeBackgroundColorToGreen();

        } else {
            holder.clearBackgroundColor();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}