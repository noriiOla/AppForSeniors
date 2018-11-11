package com.example.olastandard.appforseniors;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.olastandard.appforseniors.Adapters.MenuItemAdapter;
import com.example.olastandard.appforseniors.Contacts.ContactTypeActivity;
import com.example.olastandard.appforseniors.Navigation.AddAddressActivity;
import com.example.olastandard.appforseniors.Objects.MenuItem;
import com.example.olastandard.appforseniors.smsActivitys.MessagerListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManuActivity extends MainActivity {
    @BindView(R.id.menu_grid_view) GridView gridView;
    @BindView(R.id.menu_button_select) Button buttonSelect;

    private final MenuItem[] menuItems = new MenuItem[]{new MenuItem(R.drawable.call_icon, R.string.call),
            new MenuItem(R.drawable.sms_icon, R.string.sms) ,
            new MenuItem(R.drawable.notes_icon, R.string.notes) ,
            new MenuItem(R.drawable.alarm_icon, R.string.alarm) ,
            new MenuItem(R.drawable.gps_icon, R.string.gps) ,
            new MenuItem(R.drawable.web_icon, R.string.web)};
    private int gridSelectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_manu);
        ButterKnife.bind(this);
        initToolbar();
        initGridView();
    }

    public void initGridView() {

        final MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this, menuItems);
        gridView.setAdapter(menuItemAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                changeSelectButtonColor();
                for (int i=0; i < menuItemAdapter.getCount() ; i++){
                    parent.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.crem));
                }
                gridSelectedPosition = position;
                view.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });
    }

    private void initToolbar() {
        hideBackButton();
        hideRightButton();
        hideNewButton();
        setTitle(getResources().getString(R.string.menu));
    }

    private void changeSelectButtonColor() {
        this.buttonSelect.setBackgroundColor(getResources().getColor(R.color.green));
    }

    @OnClick({R.id.menu_button_select})
    public void showSelectedOption() {
        if (gridSelectedPosition >= 0 && gridSelectedPosition < menuItems.length) {
            switch (menuItems[gridSelectedPosition].text) {
                case R.string.sms:
                    startActivity(new Intent(this, MessagerListActivity.class));
                    break;
                case R.string.call:
                    startActivity(new Intent(this, ContactTypeActivity.class));
                    break;
                case R.string.alarm:
                    startActivity(new Intent(this, ExampleActivity.class));
                    break;
                case R.string.gps:
                    startActivity(new Intent(this, AddAddressActivity.class));
                    break;
                case R.string.notes:
                    startActivity(new Intent(this, AddLinkActivity.class));
                    break;
                case R.string.web:
                    startActivity(new Intent(this, LinksActivity.class));
                    break;
            }
        }
    }
}
