package com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.Objects.Sms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SmsHelper {

    private List<PersonSmsData> listOfPersonsData;
    private Context context;
    private AppCompatActivity contextActivity;

    public List<PersonSmsData> getListOfPersonsData() {
        return listOfPersonsData;
    }

    public SmsHelper(Context context, AppCompatActivity contextActivity) {
        listOfPersonsData = new ArrayList<>();
        this.context = context;
        this.contextActivity = contextActivity;
    }

//    public static final String SMS_CONDITION = "Some condition";
//
//    public static boolean isValidPhoneNumber(String phoneNumber) {
//        return android.util.Patterns.PHONE.matcher(phoneNumber).matches();
//    }
//
//    public static void sendDebugSms(String number, String smsBody) {
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage(number, null, smsBody, null, null);
//    }

    public String getContactName(final String phoneNumber)
    {
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName="";
        Cursor cursor = contextActivity.getContentResolver().query(uri,projection,null,null,null);

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName = cursor.getString(0);
            }
        }

        return contactName;
    }

    public String repairNumber(String phoneNumber) {
        if(phoneNumber.length() < 12) {
            phoneNumber = "+48" + phoneNumber;
        }
        return phoneNumber;
    }

    public void setNewSms(Sms newSms, String phoneNumber) {
        phoneNumber = repairNumber(phoneNumber);
        PersonSmsData data = getPersonDataFromListBy(phoneNumber);
        if (data != null) {
            data.addNewSmsToList(newSms);
        }else {
            PersonSmsData newData = new PersonSmsData(phoneNumber);
            newData.addNewSmsToList(newSms);
            String nameOfPerson = this.getContactName(phoneNumber);
            newData.setNameOfPersion(nameOfPerson == "" ? phoneNumber : nameOfPerson);
            listOfPersonsData.add(newData);
        }
    }

    private PersonSmsData getPersonDataFromListBy(String numberOfPhone) {

        for (PersonSmsData personSmsData : this.listOfPersonsData) {
            if (personSmsData.getNumebrOfPerson().equals(numberOfPhone)) {
                return personSmsData;
            }
        }
        return null;
    }

    public List<Sms> getSmsFromNumber(String numberOfPhone) {
        for (PersonSmsData personSmsData : this.listOfPersonsData) {
            if (personSmsData.getNumebrOfPerson().equals(numberOfPhone)) {
                return personSmsData.getListOfSms();
            }
        }
        return new ArrayList<>();
    }

    public String timeStampToDate(long timeStamp) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        String finalDateString = formatter.format(calendar.getTime());
        return finalDateString;
    }

    public List<PersonSmsData> actualizeListOfSms() {
        Sms objSms;
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = contextActivity.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        contextActivity.startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));

                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                long timestamp = c.getLong(c.getColumnIndexOrThrow("date"));

                objSms.setTime(timeStampToDate(timestamp));

                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }

                if (c.getString(c.getColumnIndexOrThrow("address")) != null) {
                    this.setNewSms(objSms, c.getString(c.getColumnIndexOrThrow("address")));
                }

                c.moveToNext();
            }
        }

        return listOfPersonsData;
    }
}