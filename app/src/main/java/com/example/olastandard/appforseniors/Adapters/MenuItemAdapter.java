package com.example.olastandard.appforseniors.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olastandard.appforseniors.Objects.MenuItem;
import com.example.olastandard.appforseniors.R;

public class MenuItemAdapter extends BaseAdapter {

    private final Context mContext;
    private final MenuItem[] menuItems;

    public MenuItemAdapter(Context context, MenuItem[] menuItems) {
        this.mContext = context;
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.grid_menu_item, null);
        }

        final ImageView iconImageView = (ImageView)convertView.findViewById(R.id.menu_item_icon);
        final TextView iconTextView = (TextView)convertView.findViewById(R.id.menu_item_text);


        // 4
        iconImageView.setImageResource(menuItems[position].icon);
        iconTextView.setText(menuItems[position].text);

        return convertView;
    }
}