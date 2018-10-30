package com.example.olastandard.appforseniors;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import butterknife.OnClick;

public class AddLinkActivity extends MainActivity {

    EditText UrlText;
    EditText Address;

    FileOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_add_link);
        initToolbar();
        File dir=new File(path);
        dir.mkdirs();

        _toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSave(v);
            }
        });
    }


    public String path=  Environment.getExternalStorageDirectory()+
            "/linki";

    File file = null;
    private void initToolbar() {
        showBackButton();
        showRightButton();
        hideNewButton();
        setTitle(getResources().getString(R.string.web));
    }




    public void buttonSave (View view)
    {

        file = new File (path + "/savedFile.txt");
        EditText urlTextEdit=(EditText) findViewById(R.id.urlL);
        EditText addressTextEdit=(EditText) findViewById(R.id.urlL);
        String saveText= urlTextEdit.getText().toString()+","+addressTextEdit.getText().toString();

        urlTextEdit.setText("");
        addressTextEdit.setText("");

        //Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        Save (file, saveText);
        Toast.makeText(getApplicationContext(), "Zapisano "+ file, Toast.LENGTH_LONG).show();
    }

    public  void Save(File file, String data)
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
            outputStream = this.getApplicationContext().openFileOutput("savedFile", Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        read();
//       file.mkdirs();
//
//        FileOutputStream fos = null;
//        try
//        {
//            fos = new FileOutputStream(file);
//
//        }
//        catch (FileNotFoundException e) {e.printStackTrace();}
//        try
//        {
//            try
//            {
//
//                    fos.write(data.getBytes());
//
//                        fos.write("\n".getBytes());
//
//            }
//            catch (IOException e) {e.printStackTrace();}
//        }
//        finally
//        {
//            try
//            {
//                fos.close();
//            }
//            catch (IOException e) {e.printStackTrace();}
//        }

    }

    private void read() {
        try {
            FileInputStream fis = this.getApplicationContext().openFileInput("savedFile");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = bufferedReader.readLine();
            System.out.println("--------------------------------------------");
            if (line != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            Log.d("EXCEPTION", "File not found");
        } catch (UnsupportedEncodingException e) {
            Log.d("EXCEPTION", e.getMessage());
        } catch (IOException e) {
            Log.d("EXCEPTION", e.getMessage());
        }
    }





}
