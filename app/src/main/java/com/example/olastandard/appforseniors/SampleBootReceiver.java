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
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            read();

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
                Calendar now = Calendar.getInstance();
                long _alarm=0;
                if(calendar.getTimeInMillis() <= now.getTimeInMillis())
                    _alarm = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY+1);
                else
                    _alarm = calendar.getTimeInMillis();

                PendingIntent appIntent = PendingIntent.getBroadcast(context,(int) (hhelper+mhelper), myIntent,PendingIntent.FLAG_ONE_SHOT);
                //  pendingIntents.add(appIntent);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                alarmManager.set(AlarmManager.RTC_WAKEUP, _alarm , appIntent);
        }
    }




    /*Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 19);
            calendar.set(Calendar.MINUTE, 33);

            AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent myIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent appIntent = PendingIntent.getBroadcast(context,(int) 9999, myIntent,PendingIntent.FLAG_ONE_SHOT);
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() ,  appIntent);}*/

    }

    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList arrayListListView=new ArrayList<String>();


    private void read() {

        //Button mButton = (Button) findViewById(R.id.open_button);

        try { //
            File file = new File("/data/data/com.example.olastandard.appforseniors/files/savedFileClock");
            FileInputStream fis = new FileInputStream(file);//this.getApplicationContext().openFileInput("savedFileClock");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            // String line = bufferedReader.readLine();
            System.out.println("--------------------------------------------");String data;

            while((data=bufferedReader.readLine( )) != null)
            {
                data=data.split(",")[1];
                if(data.equals("-")){}
                else {
                    arrayList.add(data);
                    arrayListListView.add(data.split(",")[0]);
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