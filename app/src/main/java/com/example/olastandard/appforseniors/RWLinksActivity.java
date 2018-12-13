package com.example.olastandard.appforseniors;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsYesNoInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
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
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;




public class RWLinksActivity extends MainActivity {

    @BindView(R.id.rwlinks_recycler_view)
    RecyclerView listOfNotes;
    @BindView(R.id.rwlinks_button_delete)
    Button buttonDelete;

    @BindView(R.id.rwlinks_button_open)
    Button buttonEditNotes;
    @BindView(R.id.rwlinks_background)
    ConstraintLayout background;


    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public String path=  Environment.DIRECTORY_DOWNLOADS;          ;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList arrayListListView=new ArrayList<String>();
    File file=new File (path+"/savedFile.txt");
    private ListView list ;
    private ArrayAdapter<String> adapter ;
    FileOutputStream outputStream;
    //public static
    int listPosition=-1;
    int c=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_rwlinks);
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
                startActivity(new Intent(getApplicationContext(), AddLinkActivity.class));
            }
        });


    }

    public void addListeners() {
        this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddLinkActivity.class));
            }
        });
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
    @OnClick({R.id.rwlinks_button_open})
    public void playNote() {
        listPosition=((LinkAdapter) mAdapter).lastSelectedItem;
        if(arrayList.isEmpty() || listPosition==-1){
            return;
        }
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean result;
        if (!(result = (activeNetworkInfo != null && activeNetworkInfo.isConnected()))) {
            //  Toast.makeText(getApplicationContext(), "Brak dostepu do neta ", Toast.LENGTH_LONG).show();


            new PushDialogManager().showDialogWithOkButton(RWLinksActivity.this, "Włącz internet", new PushDialogButtonsOkInterface() {
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

    }

    @OnClick({R.id.rwlinks_button_delete})
    public void deleteNote() {
        listPosition=((LinkAdapter) mAdapter).lastSelectedItem;
        if(arrayList.isEmpty()|| listPosition==-1){
            return;
        }

        (new PushDialogManager()).showDialogWithYesNoButtons(this, "Czy na pewno chcesz usunąć link ?", new PushDialogButtonsYesNoInterface() {


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
        Boolean bol=  getApplicationContext().deleteFile("savedFile8");
        String result="";
        Collections.reverse(arrayList);
        Collections.reverse(arrayListListView);
        for(String line : arrayList)
        {result+=line+"\n";}
        // Toast.makeText(getApplicationContext(),result ,Toast.LENGTH_LONG).show();
        try {
            outputStream = this.getApplicationContext().openFileOutput("savedFile8", MODE_PRIVATE);

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
        mAdapter = new LinkAdapter(arrayListListView, getApplicationContext());
        listOfNotes.setAdapter(mAdapter);

    }

    private void initToolbar() {
        setTitle("Internet");
        changeTitleForRightButton(getResources().getString(R.string.newS));
    }

    private void changeButtonsColor() {
        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_red));
        buttonEditNotes.setBackground(getResources().getDrawable(R.drawable.button_shape_green));

    }

    public void updateSelectedItem(int index) {
        ((LinkAdapter) mAdapter).lastSelectedItem = index;

        mAdapter.notifyDataSetChanged();
        changeButtonsColor();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        String[] items={};
        arrayList= new ArrayList<>();
        arrayListListView=new ArrayList<String>(Arrays.asList(items));

        read();
        initRecyclerView(arrayListListView);
        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        buttonEditNotes.setBackground(getResources().getDrawable(R.drawable.button_shape_white));

    }
}
