package com.example.olastandard.appforseniors.Navigation;

import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PlaceData;
import com.example.olastandard.appforseniors.R;

import java.util.ArrayList;
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
        placeData = dataManager.read(getApplicationContext());
        System.out.println("koniec czytania");
        dataManager.save("title,addres", getApplicationContext());
        //placeData.add(new PlaceData("miejsce 1","1600 Amphitheatre Parkway, Mountain+View, California"));
        initRecyclerView(placeData);
    }

    private void initList() {
        //placeData.add(new PlaceData("miejsce 1","1600 Amphitheatre Parkway, Mountain+View, California"));
        initRecyclerView(placeData);
        //init list from storage
        //List<ContactData> contactList = getContacts();
       // Collections.sort(contactList);
       // initRecyclerView(contactList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //this.initList();
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
        if (((NavigationListAdapter) mAdapter).lastSelectedItem >= 0) {
            /*
            Intent intent = new Intent(getApplicationContext(), EditContactActivity.class);
            List<ContactData> contactList = getContacts();
            Collections.sort(contactList);
            ContactData contactData = contactList.get(((ContactListAdapter) mAdapter).lastSelectedItem);
            intent.putExtra("contactData", contactData);
            this.startActivity(intent);
            */
        }
    }

    @OnClick({R.id.navigation_button_delete})
    public void deleteSelectedContact() {
        /*
        if (((ContactListAdapter) mAdapter).lastSelectedItem >= 0) {
            List<ContactData> contactList = getContacts();
            Collections.sort(contactList);
            ContactData contactData = contactList.get(((ContactListAdapter) mAdapter).lastSelectedItem);
            //TODO potwierdzenie usuwania
            deleteContact(getApplicationContext(), contactData.getNumebrOfPerson(), contactData.getNameOfPersion());
            this.initList();
            Toast toast = Toast.makeText(getApplicationContext(), "Usunięto kontakt", Toast.LENGTH_SHORT);

            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(30);
            toast.show();

        }
        */
    }

    @OnClick({R.id.navigation_button_call})
    public void callToSelectedContact() {
        /*
        if (((ContactListAdapter) mAdapter).lastSelectedItem >= 0) {
            List<ContactData> contactList = getContacts();
            Collections.sort(contactList);
            ContactData contactData = contactList.get(((ContactListAdapter) mAdapter).lastSelectedItem);
            Intent intent = new Intent(Intent.ACTION_CALL);

            intent.setData(Uri.parse("tel:" + contactData.getNumebrOfPerson()));
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
            }
            getApplicationContext().startActivity(intent);

        }
        */
    }



    public void updateSelectedItem(int index) {
        ((NavigationListAdapter) mAdapter).lastSelectedItem = index;
        mAdapter.notifyDataSetChanged();
        //changeButtonsColor();
    }
}