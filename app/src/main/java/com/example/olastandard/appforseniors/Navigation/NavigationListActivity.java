package com.example.olastandard.appforseniors.Navigation;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PlaceData;
import com.example.olastandard.appforseniors.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationListActivity extends MainActivity {

    ArrayList<PlaceData> placeData = new ArrayList<>();

    @BindView(R.id.navigation_recycler_view)
    RecyclerView navigationView;
    @BindView(R.id.navigation_button_edit)
    Button buttonEdit;
    @BindView(R.id.navigation_button_delete)
    Button buttonDelete;
    @BindView(R.id.navigation_list_background)
    ConstraintLayout background;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NavigationDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_navigation_list_);
        ButterKnife.bind(this);
        this.background.setBackgroundColor(getResources().getColor(R.color.crem));
        initToolbar();
        dataManager = new NavigationDataManager();
        this.initList();
    }

    private void initList() {
        placeData = new ArrayList<>();
        placeData = dataManager.read(getApplicationContext());
        initRecyclerView(placeData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.initList();
    }

    private void initToolbar() {
        showBackButton();
        hideRightButton();
        setTitle(R.string.choose_from_contact); //TODO:change title
    }

    private void initRecyclerView(List<PlaceData> navigationList) {
        navigationView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        navigationView.setLayoutManager(mLayoutManager);
        mAdapter = new NavigationListAdapter(navigationList, getApplicationContext());
        navigationView.setAdapter(mAdapter);
        navigationView.addItemDecoration(new com.example.olastandard.appforseniors.Objects.DividerItemDecoration(this));
    }

    @OnClick({R.id.navigation_button_edit})
    public void editSelectedPlace() {
        startActivity(new Intent(this, AddAddressActivity.class));
    }

    @OnClick({R.id.navigation_button_delete})
    public void deleteSelectedPlace() {
        if (((NavigationListAdapter) mAdapter).lastSelectedItem >= 0) {
            List<PlaceData> placeList = placeData;
            Collections.sort(placeList);
            PlaceData place = placeList.get(((NavigationListAdapter)mAdapter).lastSelectedItem);
            //TODO potwierdzenie usuwania
            if(placeList.size()==1) {
                placeList = new ArrayList<>();
            } else{
                placeList.remove(place);
            }
            dataManager.save((ArrayList)placeList, getApplicationContext());
            this.initList();

            Toast toast = Toast.makeText(getApplicationContext(), "Usunięto miejsce", Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(30);
            toast.show();

        }
    }

    @OnClick({R.id.navigation_button_call})
    public void navigateToPlace() {
        //TODO maybe set car, by foot etc
        if (((NavigationListAdapter)mAdapter).lastSelectedItem >= 0) {
            List<PlaceData> placeList = placeData;
            Collections.sort(placeList);
            PlaceData placeData = placeList.get(((NavigationListAdapter)mAdapter).lastSelectedItem);
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+placeData.getAddress());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    public void updateSelectedItem(int index) {
        ((NavigationListAdapter) mAdapter).lastSelectedItem = index;
        mAdapter.notifyDataSetChanged();
        //changeButtonsColor();
    }

    public void getCurrentLocation(int index){

    }

    public void navigateToPlace(int index){

    }
}