package com.example.olastandard.appforseniors;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olastandard.appforseniors.AlarmClock.AlarmReceiver;
import com.example.olastandard.appforseniors.AlarmClock.LinksClockActivity;
import com.example.olastandard.appforseniors.smsActivitys.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class SampleBootReceiver extends BroadcastReceiver {
    AlarmManager alarmManager;
    @Override
    public void onReceive(Context context, Intent intent) {
       //if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
           read();
        System.out.println("------------------------------------sample boot receiver moj  arraylist--------"+arrayList.size());String data;
            for(int i=0;i<arrayList.size();i++){
                String array = (String)arrayListListView.get(i);
                //zegar
                alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                Intent myIntent = new Intent(context, AlarmReceiver.class);
                //   pendingIntent = PendingIntent.getBroadcast(LinksClockActivity.this, 0, myIntent, 0);

                //https://luboganev.github.io/post/alarms-pending-intent/
                int hhelper = Integer.parseInt(array.split(":")[0]);
                int mhelper = Integer.parseInt(array.split(":")[1]);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hhelper);
                calendar.set(Calendar.MINUTE, mhelper);
                calendar.set(Calendar.SECOND,0);
                Calendar now = Calendar.getInstance();
                long _alarm=0;
                if(calendar.getTimeInMillis() <= now.getTimeInMillis())
                    _alarm = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY+1);
                else
                    _alarm = calendar.getTimeInMillis();
                System.out.println("-----> godzina "+(hhelper+mhelper));
                PendingIntent appIntent = PendingIntent.getBroadcast(context,(int) (hhelper+mhelper), myIntent,PendingIntent.FLAG_ONE_SHOT);
                //  pendingIntents.add(appIntent);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                alarmManager.set(AlarmManager.RTC_WAKEUP, _alarm , appIntent);
//       }
   }


        System.out.println("------------------------------------sample boot receiver moj--------");

   /* Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 16);
            calendar.set(Calendar.MINUTE, 51);

            AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent myIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent appIntent = PendingIntent.getBroadcast(context,(int) 9999, myIntent,PendingIntent.FLAG_ONE_SHOT);
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() ,  appIntent);*/

    }

    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList arrayListListView=new ArrayList<String>();


    private void read() {

        //Button mButton = (Button) findViewById(R.id.open_button);
        Context context = app.getContext();
        try { //
            //File file = new File("/data/data/com.example.olastandard.appforseniors/files/savedFileClock");
            //FileInputStream fis = new FileInputStream(file);
            FileInputStream fis =context.getApplicationContext().openFileInput("savedFileClock");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            // String line = bufferedReader.readLine();
            System.out.println("------------------------------------sample boot receiver moj odczyta--------");String data;
            String data2;
            String data1;
            while((data=bufferedReader.readLine( )) != null)
            {
                data1=data.split(",")[1];
                data2=data.split(",")[0];
                if(data1.equals("-")){}
                else {
                    arrayList.add(data);
                    arrayListListView.add(data2);
                }

                //arrayListListView.add(data);
            }
           /* if (line != null) {
                System.out.println(line);
               // arrayListListView.add(line);
                mButton.setText(line);
            }*/
            Collections.reverse(arrayList);
            Collections.reverse(arrayListListView);
        } catch (FileNotFoundException e) {
            Log.d("EXCEPTION", "File not found");
        } catch (UnsupportedEncodingException e) {
            Log.d("EXCEPTION", e.getMessage());
        } catch (IOException e) {
            Log.d("EXCEPTION", e.getMessage());
        }
    }
}