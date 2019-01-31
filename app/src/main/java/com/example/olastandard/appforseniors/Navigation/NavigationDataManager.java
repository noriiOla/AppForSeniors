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
    public String path = Environment.DIRECTORY_DOWNLOADS;
    String fileName = "NaviAddress";

    public NavigationDataManager() {
        this.initFile();
    }

    public void initFile() {
        File dir = new File(path);
        dir.mkdir();
    }

    public void deleteFile(Context context) {
        context.deleteFile(fileName);
    }

    public ArrayList<PlaceData> read(Context context) {
        ArrayList<PlaceData> arrayList = new ArrayList();

        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String data;

            while ((data = bufferedReader.readLine()) != null) {
                System.out.println(data);
                String title = data;
                String address = bufferedReader.readLine(); //DANGEROUS
                PlaceData placeData = new PlaceData(title, address);
                arrayList.add(placeData);
            }
            fis.close();

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

    public boolean save(ArrayList<PlaceData> placeData, Context context) {
        FileOutputStream outputStream;
        String lineTitle = "";
        String lineAddress = "";
        deleteFile(context);
        initFile();
        try {
            outputStream = context.openFileOutput(fileName, context.MODE_PRIVATE);
            for (PlaceData place : placeData) {
                lineTitle = addNewLineSignToString(place.getTitle());
                lineAddress = addNewLineSignToString(place.getAddress());
                outputStream.write(lineTitle.getBytes());
                outputStream.write(lineAddress.getBytes());
            }
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void edit(String newTitle, String newAddress, String oldTitle,
                     ArrayList<PlaceData> placeData, Context context) {
        for (PlaceData place : placeData) {
            if (place.getTitle().equals(oldTitle)) {
                place.setTitle(newTitle);
                place.setAddress(newAddress);
            }
        }
        save(placeData, context);
    }

    private String addNewLineSignToString(String line) {
        return line + "\n";
    }
}
