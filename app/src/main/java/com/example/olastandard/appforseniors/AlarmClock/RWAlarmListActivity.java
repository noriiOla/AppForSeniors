package com.example.olastandard.appforseniors.AlarmClock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.olastandard.appforseniors.AddLinkActivity;
import com.example.olastandard.appforseniors.LinkAdapter;
import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsYesNoInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.RWLinksActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RWAlarmListActivity  extends MainActivity {

        @BindView(R.id.rwalarm_recycler_view)
        RecyclerView listOfNotes;
        @BindView(R.id.rwalarm_delete_button)
        Button buttonDelete;

        @BindView(R.id.rwalarm_switch_button)
        Button buttonEditNotes;
        @BindView(R.id.rwalarm_background)
        ConstraintLayout background;


        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
          ;
        ArrayList<String> arrayList=new ArrayList<>();
        ArrayList arrayListListView=new ArrayList<String>();

        FileOutputStream outputStream;
        //public static
        int listPosition=-1;
        int c=-1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            initAddlayout(R.layout.activity_rwalarm_list);
            ButterKnife.bind(this);
            this.background.setBackgroundColor(getResources().getColor(R.color.lightGray));
            initToolbar();
            addListeners();

            String[] items={};

            arrayListListView=new ArrayList<String>(Arrays.asList(items));
            // Save("Google,www.google.com\n");
            read();
            initRecyclerView(arrayListListView);
            buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
            buttonEditNotes.setBackground(getResources().getDrawable(R.drawable.button_shape_white));

            initList();
        }

        @Override
        protected void onStart() {
            super.onStart();


        }


        private void initList() {
            _toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), NewAlarmActivity.class));
                }
            });


        }

        public void addListeners() {
            this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),NewAlarmActivity.class));
                }
            });
        }


    private void read() {

        //Button mButton = (Button) findViewById(R.id.open_button);

        try {
            FileInputStream fis = this.getApplicationContext().openFileInput("savedFileClock");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            // String line = bufferedReader.readLine();
            System.out.println("--------------------------------------------");String data;

            while((data=bufferedReader.readLine( )) != null)
            {

                arrayList.add(data);

                data=data.split(",")[0];
                arrayListListView.add(data);
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


        @OnClick({R.id.rwalarm_switch_button})
        public void playNote() {


            listPosition=((AlarmAdapter) mAdapter).lastSelectedItem;
            if(arrayList.isEmpty()|| listPosition==-1){
                return;
            }




            (new PushDialogManager()).showDialogWithYesNoButtons(this, "Czy na pewno chcesz zmienić ustawienia alarmu ?", new PushDialogButtonsYesNoInterface() {


                @Override
                public void onYesButtonTap() {

                    changeSettingsAlarm();
                  System.out.println("-----------=Button ok selected Ok");
                }

                @Override
                public void onNoButtonTap() {
                    System.out.println("-----------=Button NO selected OK");
                }
            });
            onResume();

        }

        @OnClick({R.id.rwalarm_delete_button})
        public void deleteNote() {
            listPosition=((AlarmAdapter) mAdapter).lastSelectedItem;
            if(arrayList.isEmpty()|| listPosition==-1){
                return;
            }

            (new PushDialogManager()).showDialogWithYesNoButtons(this, "Czy na pewno chcesz usunąć alarm ?", new PushDialogButtonsYesNoInterface() {


                @Override
                public void onYesButtonTap() {
                    deletehelper(); System.out.println("-----------=Button ok selected Ok");
                }

                @Override
                public void onNoButtonTap() {
                    System.out.println("-----------=Button NO selected OK");
                }
            });



        }
        public void deletehelper(){
            String array=arrayList.get(listPosition);
            // Toast.makeText(getApplicationContext(),"rozmiar "+ arrayList.size() ,Toast.LENGTH_LONG).show();
            arrayList.remove(listPosition);
            arrayListListView.remove(listPosition);
            Boolean bol=  getApplicationContext().deleteFile("savedFileClock");
            String result="";
            Collections.reverse(arrayList);
            Collections.reverse(arrayListListView);
            for(String line : arrayList)
            {result+=line+"\n";}
            // Toast.makeText(getApplicationContext(),result ,Toast.LENGTH_LONG).show();
            try {
                outputStream = this.getApplicationContext().openFileOutput("savedFileClock", MODE_PRIVATE);

                outputStream.write(result.getBytes());



                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            onResume();

        }

        private void initRecyclerView(List<String> listOfVoiceNotesTitles) {
            listOfNotes.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            listOfNotes.setLayoutManager(mLayoutManager);
            mAdapter = new AlarmAdapter(arrayList, getApplicationContext());
            listOfNotes.setAdapter(mAdapter);

        }

        private void initToolbar() {
            setTitle("Budzik");
            changeTitleForRightButton("Nowy");
        }

        private void changeButtonsColor() {
            buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_red));
            buttonEditNotes.setBackground(getResources().getDrawable(R.drawable.button_shape_green));


        }

        public void updateSelectedItem(int index) {
            ((AlarmAdapter) mAdapter).lastSelectedItem = index;
          //  buttonEditNotes.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
            changeButtonsColor();

            listPosition=((AlarmAdapter) mAdapter).lastSelectedItem;
            String array=arrayList.get(listPosition).split(",")[1];

            if(array.equals("-")){

                buttonEditNotes.setText("Włącz");
            }
            else{

                buttonEditNotes.setText("Wyłącz");
            }
        }

        @Override
        protected void onResume()
        {
            super.onResume();
            String[] items={};
            arrayList= new ArrayList<>();
            arrayListListView=new ArrayList<String>(Arrays.asList(items));
          //  buttonEditNotes.setVisibility(View.INVISIBLE);
            read();
            initRecyclerView(arrayListListView);
            buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
            buttonEditNotes.setBackground(getResources().getDrawable(R.drawable.button_shape_white));

        }




    public void startAlarm() {
        if (arrayListListView.isEmpty()) {
            return;
        }
        //listPosition=((AlarmAdapter) mAdapter).lastSelectedItem;
       /* String array = (String)arrayListListView.get(listPosition);
        //zegar
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(LinksClockActivity.this, AlarmReceiver.class);
     //   pendingIntent = PendingIntent.getBroadcast(LinksClockActivity.this, 0, myIntent, 0);

        //https://luboganev.github.io/post/alarms-pending-intent/
        Integer hhelper = Integer.parseInt(array.split(":")[0]);
        Integer mhelper = Integer.parseInt(array.split(":")[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hhelper);
        calendar.set(Calendar.MINUTE, mhelper);
        Calendar now = Calendar.getInstance();
        long _alarm=0;
        if(calendar.getTimeInMillis() <= now.getTimeInMillis())
            _alarm = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY+1);
        else
            _alarm = calendar.getTimeInMillis();

        String newTime=hhelper.toString()+mhelper.toString();
        int newTome2=Integer.parseInt(newTime);
        PendingIntent appIntent = PendingIntent.getBroadcast(this,(int) newTome2, myIntent,PendingIntent.FLAG_ONE_SHOT);
      //  pendingIntents.add(appIntent);
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, _alarm , appIntent);*/
        String array = (String) arrayListListView.get(listPosition);
        //zegar
        AlarmManager  alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(RWAlarmListActivity.this, AlarmReceiver.class);
        //   pendingIntent = PendingIntent.getBroadcast(LinksClockActivity.this, 0, myIntent, 0);

        //https://luboganev.github.io/post/alarms-pending-intent/
        int hhelper = Integer.parseInt(array.split(":")[0]);
        int mhelper = Integer.parseInt(array.split(":")[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hhelper);
        calendar.set(Calendar.MINUTE, mhelper);

        PendingIntent appIntent = PendingIntent.getBroadcast(this, (int) (hhelper + mhelper), myIntent, PendingIntent.FLAG_ONE_SHOT);
        //  pendingIntents.add(appIntent);
       /* Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hhelper);
        calendar.set(Calendar.MINUTE, mhelper);*/
        Calendar now = Calendar.getInstance();
        long _alarm = 0;
        if (calendar.getTimeInMillis() <= now.getTimeInMillis())
            _alarm = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY + 1);
        else
            _alarm = calendar.getTimeInMillis();
        alarmManager.set(AlarmManager.RTC_WAKEUP, _alarm, appIntent);
    }


    public void changeSettingsAlarm()
    {
        if(arrayList.isEmpty()){
            return;
        }

        String data=arrayList.get(listPosition).split(",")[1];
        if (data.equals("-")){
            startAlarm();
            System.out.println("--------startalarm");
            switchAlarm("a");
        }
        else{
            switchAlarm("-");
        }

       /* for(int i=0;i<arrayList.size();i++)
        {      data=arrayList.get(i).split(",")[1];

            cancelOneAlarm(i);
        }
*/

        onResume();
    }

    public void switchAlarm(String sign) {

        if (arrayList.isEmpty()) {
            return;
        }
        String array = arrayList.get(listPosition);
        //Toast.makeText(getApplicationContext(),"rozmiar "+ arrayList.size() ,Toast.LENGTH_LONG).show();

        String data = arrayList.get(listPosition).split(",")[0];
        arrayList.set(listPosition, data + "," + sign);
        // arrayListListView.get(listPosition);

        Boolean bol = getApplicationContext().deleteFile("savedFileClock");
        String result = "";
        Collections.reverse(arrayList);
        Collections.reverse(arrayListListView);
        for (String line : arrayList) {
            result += line + "\n";
        }
      //  Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        try {
            outputStream = this.getApplicationContext().openFileOutput("savedFileClock", MODE_PRIVATE);

            outputStream.write(result.getBytes());


            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
