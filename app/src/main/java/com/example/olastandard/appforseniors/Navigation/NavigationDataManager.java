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
import java.io.InputStreamReader;
import java.util.ArrayList;


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
                System.out.println(data);
                String title = data;
                String address = bufferedReader.readLine( ); //DANGEROUS
                PlaceData placeData = new PlaceData(title, address);
                arrayList.add(placeData);
            }

        } catch (FileNotFoundException e) {
            Log.d("EXCEPTION", "File not found");
            return arrayList;
        } catch (Exception e) {
            Log.d("EXCEPTION", e.getMessage());
        }
        return arrayList;
    }

    public boolean save(String lineTitle, String lineAddress, Context context) {
        FileOutputStream outputStream;
        lineTitle = addNewLineSignToString(lineTitle);
        lineAddress = addNewLineSignToString(lineAddress);
        try {
            outputStream = context.openFileOutput(fileName, context.MODE_APPEND);
            outputStream.write(lineTitle.getBytes());
            outputStream.write(lineAddress.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String addNewLineSignToString(String line){
        return line+"\n";
    }
}
