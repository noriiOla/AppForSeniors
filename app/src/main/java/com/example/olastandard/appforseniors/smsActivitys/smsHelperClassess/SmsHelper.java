package com.example.olastandard.appforseniors.smsActivitys.smsHelperClassess;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.Objects.Sms;

import java.security.Timestamp;
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

    public SmsHelper(Context context) {
        this.context = context;
    }

        public String getContactName(final String phoneNumber)
    {
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName = phoneNumber;
        Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null);

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

        Cursor c = contextActivity.getContentResolver().query(message, null, null, null, null);

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

    public void deleteSms(PersonSmsData dataToDelete) {
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = contextActivity.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        contextActivity.startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                if (c.getString(c.getColumnIndexOrThrow("address")) != null) {
                    String phoneNumber = repairNumber(c.getString(c.getColumnIndexOrThrow("address")));
                    if (dataToDelete.getNumebrOfPerson().equals(phoneNumber)) {
                        context.getContentResolver().delete(Uri.parse("content://sms/" + c.getString(0)), null, null);
                    }
                }

                c.moveToNext();
            }
        }
    }

    public void markMessageRead(PersonSmsData smsData) {

        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndexOrThrow("address")) != null) {
                    String phoneNumber = repairNumber(cursor.getString(cursor.getColumnIndexOrThrow("address")));
                    if (phoneNumber.equals(smsData.getNumebrOfPerson()) && (cursor.getInt(cursor.getColumnIndex("read")) == 0)) {
                        if (cursor.getString(cursor.getColumnIndex("body")).startsWith(smsData.getListOfSms().get(smsData.getListOfSms().size() - 1).getMsg())) {
                            String SmsMessageId = cursor.getString(cursor.getColumnIndex("_id"));
                            ContentValues values = new ContentValues();
                            values.put("read", true);
                            context.getContentResolver().update(Uri.parse("content://sms/inbox"), values, "_id=" + SmsMessageId, null);
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    public boolean saveSms(String phoneNumber, String message, String readState, String time, String folderName) {
        boolean ret = false;
        Long tsLong = System.currentTimeMillis()/1000;
        long ts = Calendar.getInstance().getTimeInMillis();

        try {
            ContentValues values = new ContentValues();
            values.put("address", phoneNumber);
            values.put("body", message);
            values.put("read", readState); //"0" for have not read sms and "1" for have read sms
            values.put("date", ts);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Uri uri = Telephony.Sms.Sent.CONTENT_URI;
                if(folderName.equals("inbox")){
                    uri = Telephony.Sms.Inbox.CONTENT_URI;
                }
                context.getContentResolver().insert(uri, values);
            }
            else {
                /* folderName  could be inbox or sent */
                context.getContentResolver().insert(Uri.parse("content://sms/" + folderName), values);
            }

            ret = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = false;
        }
        return ret;
    }

    public boolean isDefaultSmsApp(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return context.getPackageName().equals(Telephony.Sms.getDefaultSmsPackage(context));
        }
        return false;
    }
}