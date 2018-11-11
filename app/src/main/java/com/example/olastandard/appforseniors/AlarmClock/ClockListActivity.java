package com.example.olastandard.appforseniors.AlarmClock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.olastandard.appforseniors.AddLinkActivity;
import com.example.olastandard.appforseniors.R;

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

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

import butterknife.OnClick;


public class ClockListActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

      super.onCreate(savedInstanceState);
    initAddlayout(R.layout.activity_clock_list);
    initToolbar();




        ///
        String[] items={};

        arrayListListView=new ArrayList<String>(Arrays.asList(items));
        // Save("Google,www.google.com\n");
        read();

        adapter=new ArrayAdapter<String>(this,R.layout.listview_ac__item,R.id.txtview100,arrayListListView);
        ListView listV=(ListView)findViewById(R.id.link_list_view2);
        listV.setAdapter(adapter);
        // Toast.makeText(getApplicationContext(),"rozmiar oncreate "+ arrayList.size() ,Toast.LENGTH_LONG).show();
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                listPosition=position;
                int count = 0;
                ListView listV=(ListView)findViewById(R.id.link_list_view2);
                for (int i = 0; i <= listV.getLastVisiblePosition(); i++)
                {


                    if (listV.getChildAt(i) != null)
                    {
                        //1 mozliewy
                        listV.getChildAt(i).setBackgroundColor(Color.parseColor("#fffdd0"));
                    }
                }
                 listV.getChildAt(listPosition).setBackgroundColor(getResources().getColor(R.color.green));
                view.setBackgroundColor(getResources().getColor(R.color.green));
                //findViewById(R.id.open_button).setBackgroundColor(getResources().getColor(R.color.green));
                findViewById(R.id.delete_button2).setBackgroundColor(getResources().getColor(R.color.red));


            }
        });


        _toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewAlarmActivity.class));
            }
        });



}


    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private void cancelAlarm() {
        if (alarmManager!= null) {
            alarmManager.cancel(pendingIntent);
            AlarmReceiver.ringtone.stop();

        }}
public void startAlarm(){
    if(arrayListListView.isEmpty()){
        return;
    }
    String array=arrayListListView.get(listPosition);
    //zegar
    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    Intent myIntent = new Intent(ClockListActivity.this, AlarmReceiver.class);
    pendingIntent = PendingIntent.getBroadcast(ClockListActivity.this, 0, myIntent, 0);


    int hhelper=Integer.parseInt(array.split(":")[0]);
    int mhelper=Integer.parseInt( array.split(":")[1]);
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, hhelper);
    calendar.set(Calendar.MINUTE, mhelper);

    //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+3000, pendingIntent);


//    if(arrayListListView.isEmpty()){
//        return;
//    }
//    String array=arrayListListView.get(listPosition);
//    //   Toast.makeText(getApplicationContext(),"rozmiar "+ arrayList.size() ,Toast.LENGTH_LONG).show();
//    // arrayList.remove(listPosition);
//    arrayListListView.remove(listPosition);
//    Boolean bol=  getApplicationContext().deleteFile("savedFileClock");
//    String result="";
//
//    for(String line : arrayListListView)
//    {result+=line+"\n";}
//    Toast.makeText(getApplicationContext(),result ,Toast.LENGTH_LONG).show();
//    try {
//        outputStream = this.getApplicationContext().openFileOutput("savedFileClock", MODE_PRIVATE);
//
//        outputStream.write(result.getBytes());
//
//
//
//        outputStream.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    onResume();
}


    @Override
    protected void onResume()
    {
        super.onResume();
        String[] items={};
        arrayList= new ArrayList<>();
        arrayListListView=new ArrayList<String>(Arrays.asList(items));

        read();
        // Toast.makeText(getApplicationContext(),"rozmiarallw resume "+ arrayListListView.size() ,Toast.LENGTH_LONG).show();
        //  Toast.makeText(getApplicationContext(),"rozmiar resume "+ arrayList.size() ,Toast.LENGTH_LONG).show();
        adapter=new ArrayAdapter<String>(this,R.layout.listview_ac__item,R.id.txtview100,arrayListListView)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);

                // Set a background color for ListView regular row/item
                view.setBackgroundColor(Color.parseColor("#fffdd0"));


                return view;
            }
        };
        ListView listV=(ListView)findViewById(R.id.link_list_view2);
        int count = 0;


        listV.setAdapter(adapter);
      /*  for (int i = 0; i <= listV.getLastVisiblePosition(); i++)
        {
            if (listV.getChildAt(i) != null)
            {
                listV.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.crem));

            }
        }*/

    }
    private void initToolbar() {
        showBackButton();
        showRightButton();
        setTitle("Zegar");
    }
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList <String>arrayListListView=new ArrayList<String>();

    private ListView list ;
    private ArrayAdapter<String> adapter ;
    FileOutputStream outputStream;
    int listPosition;

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


                arrayListListView.add(data);
                data=data.split(":")[0];

              //  arrayList.add(data);
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

    public  void Save(String data)
    { /*PrintWriter out =null;
        try {
             out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            out.println(data);
            out.close();
            Toast.makeText(getApplicationContext(), "pokz "+ file, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            out.close();
        }
*/


        try {
            outputStream = this.getApplicationContext().openFileOutput("savedFile1", MODE_APPEND);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }}


    class ItemList implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            listPosition=position;

            int count = 0;
            ListView listV=(ListView)findViewById(R.id.link_list_view2);



            for (int i = 0; i <= listV.getLastVisiblePosition(); i++)
            {

                if (listV.getChildAt(i) != null)
                {
                    listV.getChildAt(i).setBackgroundColor(Color.parseColor("#fffdd0"));


                }
            }

            view.setBackgroundColor(getResources().getColor(R.color.green));
            //listV.getChildAt(listPosition).setBackgroundColor(getResources().getColor(R.color.green));
         //   findViewById(R.id.open_button).setBackgroundColor(getResources().getColor(R.color.green));
            findViewById(R.id.delete_button2).setBackgroundColor(getResources().getColor(R.color.red));
        }
    }



  /*  @OnClick({R.id.open_button2})//wlaczy budzik
    public void openAdres(View view) {

        for(String line : arrayList)
        {result+=line+"\n";}
        Toast.makeText(getApplicationContext(),result ,Toast.LENGTH_LONG).show();
        try {
            outputStream = this.getApplicationContext().openFileOutput("savedFileClock", MODE_PRIVATE);

            outputStream.write(result.getBytes());



            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        onResume();


    }*/
    @OnClick({R.id.delete_button2})
    public void deleteLink2(View view){

        if(arrayListListView.isEmpty()){
            return;
        }
        String array=arrayListListView.get(listPosition);
     //   Toast.makeText(getApplicationContext(),"rozmiar "+ arrayList.size() ,Toast.LENGTH_LONG).show();
       // arrayList.remove(listPosition);
        arrayListListView.remove(listPosition);
        Boolean bol=  getApplicationContext().deleteFile("savedFileClock");
        String result="";

        for(String line : arrayListListView)
        {result+=line+"\n";}
        Toast.makeText(getApplicationContext(),result ,Toast.LENGTH_LONG).show();
        try {
            outputStream = this.getApplicationContext().openFileOutput("savedFileClock", MODE_PRIVATE);

            outputStream.write(result.getBytes());



            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        onResume();


    }

}
