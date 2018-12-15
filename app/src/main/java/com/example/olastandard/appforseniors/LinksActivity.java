package com.example.olastandard.appforseniors;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import butterknife.OnClick;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.olastandard.appforseniors.AlarmClock.AlarmReceiver;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.smsActivitys.MessagerListActivity;

public class LinksActivity extends MainActivity  {

  public String path=  Environment.DIRECTORY_DOWNLOADS;          ;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList arrayListListView=new ArrayList<String>();
    File file=new File (path+"/savedFile.txt");
    private ListView list ;
    private ArrayAdapter<String> adapter ;
    FileOutputStream outputStream;
    int listPosition;
    int c=-1;

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
        adapter=new ArrayAdapter<String>(this,R.layout.listview_links_item,R.id.txtview,arrayListListView)
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
        ListView listV=(ListView)findViewById(R.id.link_list_view);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_links);
        initToolbar();

        File dir=new File(path);

        dir.mkdir();

       ///
        String[] items={};

        arrayListListView=new ArrayList<String>(Arrays.asList(items));
       // Save("Google,www.google.com\n");
        read();

        adapter=new ArrayAdapter<String>(this,R.layout.listview_links_item,R.id.txtview,arrayListListView);
        ListView listV=(ListView)findViewById(R.id.link_list_view);
        listV.setAdapter(adapter);
       // Toast.makeText(getApplicationContext(),"rozmiar oncreate "+ arrayList.size() ,Toast.LENGTH_LONG).show();
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                listPosition=position;
                int count = 0;
                ListView listV=(ListView)findViewById(R.id.link_list_view);
                for (int i = 0; i <= listV.getLastVisiblePosition(); i++)
                {


                    if (listV.getChildAt(i) != null)
                    {
                        //1 mozliewy
                        listV.getChildAt(i).setBackgroundColor(Color.parseColor("#fffdd0"));
                    }
                }
               // listV.getChildAt(listPosition).setBackgroundColor(getResources().getColor(R.color.green));
                view.setBackgroundColor(getResources().getColor(R.color.green));
                findViewById(R.id.open_button).setBackgroundColor(getResources().getColor(R.color.green));
                findViewById(R.id.delete_button).setBackgroundColor(getResources().getColor(R.color.red));
            }
        });

        _toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddLinkActivity.class));
            }
        });

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
                ListView listV=(ListView)findViewById(R.id.link_list_view);



                for (int i = 0; i <= listV.getLastVisiblePosition(); i++)
                {

                    if (listV.getChildAt(i) != null)
                    {
                        listV.getChildAt(i).setBackgroundColor(Color.parseColor("#fffdd0"));


                    }
                }

                view.setBackgroundColor(getResources().getColor(R.color.green));
                //listV.getChildAt(listPosition).setBackgroundColor(getResources().getColor(R.color.green));
                findViewById(R.id.open_button).setBackgroundColor(getResources().getColor(R.color.green));
                findViewById(R.id.delete_button).setBackgroundColor(getResources().getColor(R.color.red));
            }
        }











    private void read() {

        //Button mButton = (Button) findViewById(R.id.open_button);

        try {
            FileInputStream fis = this.getApplicationContext().openFileInput("savedFile8");
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
    public  void loadText(){


                try
                {
//make a 'file' object

//Get data from this file using a file reader.
                    FileReader fr = new FileReader(file);
//To store the contents read via File Reader
                    BufferedReader br = new BufferedReader(fr);
//Read br and store a line in 'data', print data
                    String []data;
                    while(br.readLine( ) != null)
                    {
                        data = br.readLine( ).split(",");
                 //       arrayList.add(data);
                        //arrayListListView.add(data[0]);
                    }
                    br.close();
                    fr.close();

                }catch(IOException e){System.out.println("bad !");

                }

    }


    @OnClick({R.id.open_button})
    public void openclocl1(View view) {
        wlaczIwylacz();
        System.out.println("----------------wlacziwylocz");
       /* for(int i=0;i<arrayList.size();i++)
        {   String   data=arrayList.get(i).split(",")[1];

            cancelOneAlarm(i);
        }
        for(int i=0;i<arrayList.size();i++)
        {  String   data=arrayList.get(i).split(",")[1];
            if(data.equals("-")){}
            else{
            startOneAlarm(i);
            }
        }

        onResume();*/
    }


    public void wlaczIwylacz(){
        Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 1, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE,24 );
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

        /*AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent1 = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(
                getApplicationContext(), 1, myIntent1,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent1);*/
    }
    private void initToolbar() {
        showBackButton();

        showRightButton();
        changeTitleForRightButton("NOWE");
        setTitle(getResources().getString(R.string.web));
    }

    /*@OnClick({R.id.open_button})
    public void openAdres(View view) {

        if(arrayList.isEmpty()){
            return;
        }
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean result;
        if (!(result = (activeNetworkInfo != null && activeNetworkInfo.isConnected()))) {
          //  Toast.makeText(getApplicationContext(), "Brak dostepu do neta ", Toast.LENGTH_LONG).show();


            new PushDialogManager().showDialogWithOkButton(LinksActivity.this, getApplicationContext().getFilesDir()+"Brak dostepu do neta", new PushDialogButtonsOkInterface() {
                @Override
                public void onOkButtonTap() {
                    return;
                }
            });

        }
        else {
            String[] array = arrayList.get(listPosition).split(",");

            String url = array[1];//"http://www.google.com";
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }

        }*/

    @OnClick({R.id.delete_button})
        public void deleteLink(View view){

        if(arrayList.isEmpty()){
            return;
        }
            String array=arrayList.get(listPosition);
        Toast.makeText(getApplicationContext(),"rozmiar "+ arrayList.size() ,Toast.LENGTH_LONG).show();
            arrayList.remove(listPosition);
            arrayListListView.remove(listPosition);
           Boolean bol=  getApplicationContext().deleteFile("savedFile8");
             String result="";
        Collections.reverse(arrayList);
        Collections.reverse(arrayListListView);
        for(String line : arrayList)
        {result+=line+"\n";}
        Toast.makeText(getApplicationContext(),result ,Toast.LENGTH_LONG).show();
            try {
                outputStream = this.getApplicationContext().openFileOutput("savedFile8", MODE_PRIVATE);

                    outputStream.write(result.getBytes());



                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            onResume();


        }

    @OnClick({R.id.toolbar_save})
    public void addNewLinkActtivity(View view) {
        startActivity(new Intent(this, AddLinkActivity.class));



    }
}
