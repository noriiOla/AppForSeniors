package com.example.olastandard.appforseniors.Navigation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.olastandard.appforseniors.Objects.PlaceData;
import com.example.olastandard.appforseniors.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationListAdapter extends RecyclerView.Adapter<com.example.olastandard.appforseniors.Navigation.NavigationListAdapter.ViewHolder> {
    public List<PlaceData> mDataset;
    Context context;
    public int lastSelectedItem = -1;



    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.navigation_title)
        public TextView navigationTitle;
        @BindView(R.id.navigation_address)
        public TextView navigationAddress;
        @BindView(R.id.navigation_item_background)
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

        @OnClick(R.id.navigation_list_item)
        public void showEventDetail() {
            ((NavigationListActivity)mcontext).updateSelectedItem(getPosition());
        }
    }

    public NavigationListAdapter(List<PlaceData> myDataset, Context context) {
        this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public com.example.olastandard.appforseniors.Navigation.NavigationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                                           int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.navigation_list_item, parent, false);

        com.example.olastandard.appforseniors.Navigation.NavigationListAdapter.ViewHolder vh =
                new com.example.olastandard.appforseniors.Navigation.NavigationListAdapter.ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(com.example.olastandard.appforseniors.Navigation.NavigationListAdapter.ViewHolder holder, int position) {
        holder.navigationAddress.setText(mDataset.get(position).getAddress());
        holder.navigationTitle.setText(mDataset.get(position).getTitle());
        if (position == lastSelectedItem) {
            holder.changeBackgroundColorToGreen();
        }else {
            holder.clearBackgroundColor();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}