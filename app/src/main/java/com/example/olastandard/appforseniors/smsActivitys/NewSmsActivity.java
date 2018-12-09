package com.example.olastandard.appforseniors.smsActivitys;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.olastandard.appforseniors.Contacts.ContactListActivity;
import com.example.olastandard.appforseniors.Contacts.ContactListActivityType;
import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.ContactData;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess.SmsHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewSmsActivity extends MainActivity {

    List<PersonSmsData> smsData;
  ///  PersonSmsData smsData;

//    @BindView(R.id.keyboardview)
//    KeyboardView keyboardView;

    @BindView(R.id.new_sms_addContact)
    Button buttonAddContact;

    @BindView(R.id.new_sms_contacts)
    TextView contactsList;

    @BindView(R.id.new_sms_msg)
    EditText msgText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_new_sms);
        ButterKnife.bind(this);

        contactsList.setMovementMethod(new ScrollingMovementMethod());

        PersonSmsData data = (PersonSmsData) getIntent().getSerializableExtra("smsData");
        smsData = new ArrayList<>();

        initToolbar();
        contactsList.setText("");

        if (data != null) {
            smsData.add(data);
            addContactName(data.getNameOfPersion());
        }

        addListeners();

        buttonAddContact.setBackground(getResources().getDrawable(R.drawable.floating_button_shape_green));
    }

    public void addListeners() {
        this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (msgText.getText().toString().trim().equals("")) {
                    (new PushDialogManager()).showDialogWithOkButton(NewSmsActivity.this, "Treść wiadomości jest pusta. Uzupełnij ją.", new PushDialogButtonsOkInterface(){

                        @Override
                        public void onOkButtonTap() {

                        }
                    });
                }else {
                    if (contactsList.getText().toString().trim().equals("")) {
                        (new PushDialogManager()).showDialogWithOkButton(NewSmsActivity.this, "Lista osób do których chcesz wysłać jest pusta, uzupełnij ją", new PushDialogButtonsOkInterface(){

                            @Override
                            public void onOkButtonTap() {

                            }
                        });
                    }else {
                        for (PersonSmsData singleData : smsData) {
                            sendSMS(singleData.getNumebrOfPerson(), msgText.getText().toString());
                        }
                        finish();
                    }
                }
            }
        });
    }


    public void addContactName(String newContact) {
        if (contactsList.getText().equals("")) {
            String text = newContact;
            contactsList.setText(text);
        }else {
            String text = newContact +  "\n" + contactsList.getText();
            contactsList.setText(text);
        }
    }

    @OnClick({R.id.new_sms_addContact})
    public void addContact() {

        Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
        intent.putExtra("typeOfView", ContactListActivityType.conactListView.selectContact);
        this.startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            PersonSmsData bla = (PersonSmsData) data.getSerializableExtra("contactData");

            Boolean needAddCntact = true;

            for (PersonSmsData singleData : smsData) {
                if (singleData.getNameOfPersion().equals(bla.getNameOfPersion())) {
                    needAddCntact = false;
                    break;
                }
            }

            if (needAddCntact) {
                smsData.add(bla);
                addContactName(bla.getNameOfPersion());
            }
        }
    }

    private void initToolbar() {
        setTitle(getResources().getString(R.string.write));
        changeTitleForRightButton(getResources().getString(R.string.send));
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            final SmsHelper smsHelper = new SmsHelper(app.getContext());

            if ( smsHelper.saveSms(phoneNo, msg, "1", String.valueOf(Calendar.getInstance().getTime()), "")) {
                smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            }



        } catch (Exception ex) {
            (new PushDialogManager()).showDialogWithOkButton(this, "Nie udało się wysłać wiadomości, spróbuj ponownie", new PushDialogButtonsOkInterface(){

                @Override
                public void onOkButtonTap() {

                }
            });
        }
    }


//
//    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
//        @Override public void onKey(int primaryCode, int[] keyCodes)
//        {
//            //Here check the primaryCode to see which key is pressed
//            //based on the android:codes property
//            if(primaryCode==1)
//            {
//                Log.i("Key","You just pressed 1 button");
//            }
//        }
//
//        @Override public void onPress(int arg0) {
//        }
//
//        @Override public void onRelease(int primaryCode) {
//        }
//
//        @Override public void onText(CharSequence text) {
//        }
//
//        @Override public void swipeDown() {
//        }
//
//        @Override public void swipeLeft() {
//        }
//
//        @Override public void swipeRight() {
//        }
//
//        @Override public void swipeUp() {
//        }
//    };
//
//    public void openKeyboard(View v)
//    {
//        mKeyboardView.setVisibility(View.VISIBLE);
//        mKeyboardView.setEnabled(true);
//        if( v!=null)((InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
//    }
}
