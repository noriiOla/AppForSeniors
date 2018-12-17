package com.example.olastandard.appforseniors.Navigation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    @BindView(R.id.navigation_button_delete)
    Button buttonDelete;
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
        buttonEdit.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        buttonNavigateTo.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
    }

    private void initList() {
        placeData = new ArrayList<>();
        placeData = dataManager.read(getApplicationContext());
        List<PlaceData> placeList = placeData;
        Collections.sort(placeList);
        initRecyclerView(placeList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        buttonEdit.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        buttonNavigateTo.setBackground(getResources().getDrawable(R.drawable.button_shape_white));

    }


    @Override
    protected void onResume() {
        super.onResume();
        this.initList();
        buttonEdit.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        buttonNavigateTo.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
    }

    private void initToolbar() {
        showBackButton();
        changeTitleForRightButton("Nowa");
        setTitle(getResources().getString(R.string.navigation_list));
    }

    private void initRecyclerView(List<PlaceData> navigationList) {
        navigationView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        navigationView.setLayoutManager(mLayoutManager);
        mAdapter = new NavigationListAdapter(navigationList, getApplicationContext());
        navigationView.setAdapter(mAdapter);
    }

    private void reloadTableData(List<PlaceData> navigationList) {
        ((NavigationListAdapter) mAdapter).mDataset = navigationList;
        mAdapter.notifyDataSetChanged();
    }

    public void addListeners() {
        this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isConnectionSetted()) {
                    showDialogBox("Brak dostępu do intenetu");
                } else {
                    Intent intent = new Intent(getApplicationContext(), AddAddressActivity.class);
                    intent.putExtra("placeDataArray", placeData);
                    NavigationListActivity.this.startActivity(intent);
                }
            }
        });
    }

    @OnClick({R.id.navigation_button_edit})
    public void editSelectedPlace() {
        if (!isConnectionSetted()) {
            showDialogBox("Brak dostępu do intenetu");
        } else {
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
    }

    private void showDialogBox(String text) {
        new PushDialogManager().showDialogWithOkButton(this, text, new PushDialogButtonsOkInterface() {
            @Override
            public void onOkButtonTap() {
            }
        });
    }

    private boolean isConnectionSetted() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (!(activeNetworkInfo != null && activeNetworkInfo.isConnected())) {
            return false;
        }
        return true;
    }

    @OnClick({R.id.navigation_button_delete})
    public void deletePlace() {
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

    @OnClick({R.id.navigation_button_call})
    public void navigateToPlace() {
        if (!isConnectionSetted()) {
            showDialogBox("Brak dostępu do intenetu");
        } else {
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
    }

    public void updateSelectedItem(int index) {
        ((NavigationListAdapter) mAdapter).lastSelectedItem = index;
        mAdapter.notifyDataSetChanged();
        changeButtonsColor();
    }

    private void changeButtonsColor() {
        buttonEdit.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
        buttonNavigateTo.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
    }
}