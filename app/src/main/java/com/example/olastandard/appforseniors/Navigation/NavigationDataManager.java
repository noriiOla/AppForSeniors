package com.example.olastandard.appforseniors.Navigation;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.olastandard.appforseniors.Objects.PlaceData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static android.content.Context.MODE_APPEND;

public class NavigationDataManager {
    public String path=  Environment.DIRECTORY_DOWNLOADS;
    String fileName = "NaviAddress";

    public NavigationDataManager() {
        File dir=new File(path);
        dir.mkdir();
    }

    public ArrayList<PlaceData> read(Context context) {

        ArrayList<PlaceData> arrayList = new ArrayList();

        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String data;

            while((data=bufferedReader.readLine( )) != null) {
                PlaceData placeData = new PlaceData(data.split(",")[0], data.split(",")[1]);
                arrayList.add(placeData);

            }

        } catch (FileNotFoundException e) {
            Log.d("EXCEPTION", "File not found");
            return arrayList;
        } catch (UnsupportedEncodingException e) {
            Log.d("EXCEPTION", e.getMessage());
        } catch (IOException e) {
            Log.d("EXCEPTION", e.getMessage());
        }
        return arrayList;
    }


    public boolean save(String line, Context context) {
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, MODE_APPEND);
            outputStream.write(line.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
