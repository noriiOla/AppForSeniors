package com.example.olastandard.appforseniors;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.OnClick;
import android.view.View.OnClickListener;
public class LinksActivity extends MainActivity  {

  public String path=  Environment.DIRECTORY_DOWNLOADS
          ;
    ArrayList<String[]> arrayList=new ArrayList<>();
    ArrayList<String> arrayListListView=new ArrayList<>();
    File file=new File (path+"/savedFile.txt");
    private ListView list ;
    private ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_links);
        initToolbar();

        File dir=new File(path);
        dir.mkdir();
        _toolbarNewButton = (Button) findViewById(R.id.toolbar_new);


       loadText();
        list = (ListView) findViewById(R.id.link_list_view);
        adapter = new ArrayAdapter<String>(this, R.layout.listview_links_item, arrayListListView);

        list.setAdapter(adapter);


        _toolbarNewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddLinkActivity.class));
            }
        });


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
                        arrayList.add(data);
                        arrayListListView.add(data[0]);
                    }
                    br.close();
                    fr.close();

                }catch(IOException e){System.out.println("bad !");

                }

    }






    private void initToolbar() {
        showBackButton();
        hideRightButton();
        showNewButton();
        setTitle(getResources().getString(R.string.web));
    }

    @OnClick({R.id.open_button})
    public void openAdres(View view) {
        String url="http://www.google.com";
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);


        }



    @OnClick({R.id.toolbar_new})
    public void addNewLinkActtivity(View view) {
        startActivity(new Intent(this, AddLinkActivity.class));



    }
}
