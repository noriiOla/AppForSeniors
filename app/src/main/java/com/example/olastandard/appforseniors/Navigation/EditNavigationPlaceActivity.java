package com.example.olastandard.appforseniors.Navigation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PlaceData;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditNavigationPlaceActivity extends MainActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private PlaceData placeData;
    ArrayList<PlaceData> placeDataArray;

    @BindView(R.id.autoCompleteTextView)
    public AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.navi_place_name)
    public TextView placeTitle;

    private static final String TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;

    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_autocomplete);

        ButterKnife.bind(this);

        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);

        mGoogleApiClient = new GoogleApiClient.Builder(EditNavigationPlaceActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        AutocompleteFilter filter =
                new AutocompleteFilter.Builder().setCountry("PL").build();
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, filter);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        initToolbar();
        addListeners();

        placeData = (PlaceData) getIntent().getSerializableExtra("placeData");
        placeDataArray = (ArrayList<PlaceData>) getIntent().getSerializableExtra("placeDataArray");

        placeTitle.setText(placeData.getTitle());
        autoCompleteTextView.setText(placeData.getAddress());
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
        }
    };

    public void addListeners() {
        this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titleText = placeTitle.getText().toString();
                String address = autoCompleteTextView.getText().toString();
                if (titleText.isEmpty()) {
                    showDialogBox("Wpisz tytuł");
                } else {
                    if (address.isEmpty()) {
                        showDialogBox("Wpisz adres");
                    } else {
                        NavigationDataManager navigationDataManager = new NavigationDataManager();
                        navigationDataManager.edit(titleText, address, placeData.getTitle(), placeDataArray, getApplicationContext());
                        startActivity(new Intent(getApplicationContext(), NavigationListActivity.class));
                    }
                }
            }
        });
    }

    private void showDialogBox(String text) {
        new PushDialogManager().showDialogWithOkButton(this, text, new PushDialogButtonsOkInterface() {
            @Override
            public void onOkButtonTap() {
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "Google Places API connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());
        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();

    }

    void initToolbar() {
        showBackButton();
        changeTitleForRightButton(getResources().getString(R.string.save));
        setTitle(R.string.new_number);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}