package com.example.olastandard.appforseniors.Navigation;

import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PlaceData;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsYesNoInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
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
    @BindView(R.id.navigation_button_add)
    Button buttonAdd;
    @BindView((R.id.navigation_button_call))
    Button buttonNavigateTo;
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
        initToolbar();
        dataManager = new NavigationDataManager();
        this.initList();
        addListeners();
        buttonAdd.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
    }

    private void initList() {
        placeData = new ArrayList<>();
        placeData = dataManager.read(getApplicationContext());
        List<PlaceData> placeList = placeData;
        Collections.sort(placeList);
        initRecyclerView(placeList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.initList();
    }

    private void initToolbar() {
        showBackButton();
        changeTitleForRightButton(getResources().getString(R.string.delete));
        setTitle(getResources().getString(R.string.add_note));
    }

    private void initRecyclerView(List<PlaceData> navigationList) {
        navigationView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        navigationView.setLayoutManager(mLayoutManager);
        mAdapter = new NavigationListAdapter(navigationList, getApplicationContext());
        navigationView.setAdapter(mAdapter);
    }

    private void reloadTableData(List<PlaceData> navigationList) {
        ((NavigationListAdapter)mAdapter).mDataset = navigationList;
        mAdapter.notifyDataSetChanged();
    }

    public void addListeners() {
        this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((NavigationListAdapter) mAdapter).lastSelectedItem == -1) {
                    new PushDialogManager().showDialogWithOkButton(NavigationListActivity.this,
                            "Żaden element z listy nie został wybrany", new PushDialogButtonsOkInterface() {
                                @Override
                                public void onOkButtonTap() {
                                }
                            });
                } else {
                    (new PushDialogManager()).showDialogWithYesNoButtons(NavigationListActivity.this,
                            "Czy na pewno chcesz usunąć adres?",
                            new PushDialogButtonsYesNoInterface() {

                                @Override
                                public void onYesButtonTap() {
                                    List<PlaceData> placeList = placeData;
                                    Collections.sort(placeList);
                                    PlaceData place = placeList.get(((NavigationListAdapter) mAdapter).lastSelectedItem);
                                    if (placeList.size() == 1) {
                                        placeList = new ArrayList<>();
                                    } else {
                                        placeList.remove(place);
                                    }
                                    dataManager.save((ArrayList) placeList, getApplicationContext());

                                    placeData = new ArrayList<>();
                                    placeData = dataManager.read(getApplicationContext());
                                    reloadTableData(placeData);
                                }

                                @Override
                                public void onNoButtonTap() {
                                }
                            });
                }
            }
        });
    }

    @OnClick({R.id.navigation_button_edit})
    public void editSelectedPlace() {
        if (((NavigationListAdapter) mAdapter).lastSelectedItem >= 0) {
            Intent intent = new Intent(getApplicationContext(), EditNavigationPlaceActivity.class);
            List<PlaceData> placeList = placeData;
            Collections.sort(placeList);
            PlaceData place = placeList.get(((NavigationListAdapter) mAdapter).lastSelectedItem);
            intent.putExtra("placeData", place);
            intent.putExtra("placeDataArray", placeData);
            this.startActivity(intent);
        }
    }

    @OnClick({R.id.navigation_button_add})
    public void addPlace() {
        startActivity(new Intent(this, AddAddressActivity.class));
    }

    @OnClick({R.id.navigation_button_call})
    public void navigateToPlace() {
        //TODO maybe set car, by foot etc
        if (((NavigationListAdapter) mAdapter).lastSelectedItem >= 0) {
            List<PlaceData> placeList = placeData;
            Collections.sort(placeList);
            PlaceData placeData = placeList.get(((NavigationListAdapter) mAdapter).lastSelectedItem);
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + placeData.getAddress());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    public void updateSelectedItem(int index) {
        ((NavigationListAdapter) mAdapter).lastSelectedItem = index;
        mAdapter.notifyDataSetChanged();
        changeButtonsColor();
    }

    private void changeButtonsColor() {
        buttonEdit.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
        buttonNavigateTo.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
    }
}