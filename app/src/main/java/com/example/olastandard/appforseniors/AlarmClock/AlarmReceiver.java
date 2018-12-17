package com.example.olastandard.appforseniors.AlarmClock;
import com.example.olastandard.appforseniors.smsActivitys.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import static android.support.v4.content.ContextCompat.startActivity;

public class AlarmReceiver extends BroadcastReceiver {
public static Ringtone ringtone;

    @Override
    public void onReceive( Context context, Intent intent) {
      //  read();

//        Calendar calendar = Calendar.getInstance();
//        Integer hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//        Integer minutes=Calendar.getInstance().get(Calendar.MINUTE);


//        String noe="";
////System.out.println("--------------------->alaement arraylisty --->"+arrayListListView.get(0));
////        System.out.println("--------------------->czas--->"+noe);
//        if(minutes.toString().length()==1){
//        noe=hour.toString()+":"+"0"+minutes.toString();
//        }
//        else{
//          noe=  hour.toString()+":"+minutes.toString();
//        }
//        if(hour.toString().length()==1){
//        noe="0"+noe;
//        }
//        if(!arrayListListView.contains(noe)){
//
//            return;
//        }

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        //Ringtone
                ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();


        sendNotification();
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
    private void sendNotification()
    {
        Context context = app.getContext();

        Intent notificationIntent = new Intent(context, AlarmActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.alarm_icon)
                .setContentTitle("Budzik")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] { 1000, 1000})
                .setContentText("Budzik")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(4, mBuilder.build());
    }




    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_example);
        initToolbar();
    }

    private void initToolbar() {
        showBackButton();
        hideRightButton();
        setTitle("Dzwonienie budzika");
    }*/
}
