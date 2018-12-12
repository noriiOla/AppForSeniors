package com.example.olastandard.appforseniors;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.olastandard.appforseniors.Adapters.MenuItemAdapter;
import com.example.olastandard.appforseniors.AlarmClock.ClockListActivity;
import com.example.olastandard.appforseniors.Contacts.ContactTypeActivity;
import com.example.olastandard.appforseniors.Navigation.NavigationListActivity;
import com.example.olastandard.appforseniors.Objects.MenuItem;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.VoiceNotes.VoiceNotesList;
import com.example.olastandard.appforseniors.smsActivitys.MessagerListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManuActivity extends MainActivity {
    @BindView(R.id.menu_grid_view) GridView gridView;
    @BindView(R.id.menu_button_select) Button buttonSelect;
//    @BindView(R.id.menu_card_view)
//    CardView menuCardView;
    private static final int SMS_PERMISSION_CODE = 0;

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

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            final String packageName = this.getPackageName();
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
            startActivity(intent);
        }

        initToolbar();
        initGridView();
        buttonSelect.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        if (!hasPermission()) {
            showRequestPermissionsInfoAlertDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    new PushDialogManager().showDialogWithOkButton(this, "Bez zezwolenia część funkcji aplikacji nie będzie działać poprawnie. Możesz zmienić zezwolenia w opcjach aplikacji.", new PushDialogButtonsOkInterface() {
                        @Override
                        public void onOkButtonTap() {
                        }
                    });

                }
                return;
            }
        }
    }

    private void showRequestPermissionsInfoAlertDialog() {
        new PushDialogManager().showDialogWithOkButton(this, getResources().getString(R.string.sms_persmission), new PushDialogButtonsOkInterface() {
            @Override
            public void onOkButtonTap() {
                requestReadAndSendSmsPermission();
            }
        });
    }

    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;

    }

    private void requestReadAndSendSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.RECORD_AUDIO},
                SMS_PERMISSION_CODE);
    }

    public void initGridView() {

        final MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this, menuItems);
        gridView.setAdapter(menuItemAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                LinearLayout actualSelectedView = (LinearLayout) view.findViewById(R.id.grid_cell_background);
                if (actualSelectedView != null) {
                    changeSelectButtonColor();

                    for (int i=0; i < menuItemAdapter.getCount() ; i++) {
                        if (parent.getChildAt(i) != null) {
                            LinearLayout f_l = (LinearLayout) parent.getChildAt(i).findViewById(R.id.grid_cell_background);
                            if (f_l != null) {
                                f_l.setBackgroundColor(getResources().getColor(R.color.white));
                            }
                        }
                    }

                    actualSelectedView.setBackgroundColor(getResources().getColor(R.color.green));
                    gridSelectedPosition = position;
                }
            }
        });
    }

    private void initToolbar() {
        hideBackButton();
        hideRightButton();

        setTitle(getResources().getString(R.string.menu));
    }

    private void changeSelectButtonColor() {
        buttonSelect.setBackground(getResources().getDrawable(R.drawable.button_shape_green));

        //this.buttonSelect.setBackgroundColor(getResources().getColor(R.color.green));
    }

    @OnClick({R.id.menu_button_select})
    public void showSelectedOption() {
        if (gridSelectedPosition >= 0 && gridSelectedPosition < menuItems.length) {
            switch (menuItems[gridSelectedPosition].text) {
                case R.string.sms:
                  //  startActivity(new Intent(this, ExampleActivity.class));

                    if (hasSmsPermission()) {
                        startActivity(new Intent(this, MessagerListActivity.class));
                    }else {
                        showPermiossionError("Przed otworzeniem tej funkcji proszę zezwolić w ustawieniach aplikacji na korzystanie z czytania smsów i kontaktów oraz wysyłania smsów");
                    }
                    break;
                case R.string.call:
                    if (hasContactPermission()) {
                        startActivity(new Intent(this, ContactTypeActivity.class));
                    }else {
                        showPermiossionError("Przed otworzeniem tej funkcji proszę zezwolić w ustawieniach aplikacji na korzystanie z czytania i modyfikacji kontaktów oraz dzwonienia");
                    }
                    break;
                case R.string.alarm:
                    startActivity(new Intent(this,ClockListActivity.class));
                    break;
                case R.string.gps:
                    startActivity(new Intent(this, NavigationListActivity.class));
                    break;
                case R.string.notes:
                    if (hasNotesPermission()) {
                        startActivity(new Intent(this, VoiceNotesList.class));
                    }else {
                        showPermiossionError("Przed otworzeniem tej funkcji proszę zezwolić w ustawieniach aplikacji na nagrywanie oraz zapis i odczyta danych z pamieci komorki");
                    }
                    break;
                case R.string.web:
                    startActivity(new Intent(this, LinksActivity.class));
                    break;
            }
        }
    }

    public void showPermiossionError(String withMessage) {
        new PushDialogManager().showDialogWithOkButton(this, withMessage, new PushDialogButtonsOkInterface() {
            @Override
            public void onOkButtonTap() {
                requestReadAndSendSmsPermission();
            }
        });
    }

    public boolean hasSmsPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasNotesPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasContactPermission() {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED;
    }
}
