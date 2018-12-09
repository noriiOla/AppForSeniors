package com.example.olastandard.appforseniors.VoiceNotes;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.Toast;

import com.example.olastandard.appforseniors.smsActivitys.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VoiceNotesManager {

    /*
        Odwołanie:VoiceNotesManager.getInstance
        naciśniećie start: metoda- startRecording()
        nacisniecie stop: metoda - stopRecording()
        nacisniecie play NA EKRANIE NAGRYWANIA : metoda - play()
        Nacisniecie back: removeTemporaryRecord()
        nacisniecie save:
            1. nameExist(String fileName) - if true- error ze podana nazwa juz istnieje (walidowac wczesniej czy nazwa nie jest pusta i czy nie ma kropek
            2. if false saveRecord(String recordName)
        nacisniecie play na po wybraniu nagrania z listy: metoda -  play(String fileName)
        pobranie dostepnych nagran: getRecordsNames()
    */

    private static VoiceNotesManager voiceNotesManager = null;

    String baseUrl = Environment.getExternalStorageDirectory().getAbsolutePath() + "/seniorAppNotes/";
    private MediaRecorder myAudioRecorder;
    private final String recordTemporaryName = "temp.3gp";

    public static VoiceNotesManager getInstance()
    {
        if (voiceNotesManager == null)
            voiceNotesManager = new VoiceNotesManager();

        return voiceNotesManager;
    }

    public VoiceNotesManager() {
    }

    public void startRecording() {
        if (createBaseDirIfNotExists() && myAudioRecorder == null) {
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

            myAudioRecorder.setOutputFile(baseUrl + recordTemporaryName);

            try {
                myAudioRecorder.prepare();
                myAudioRecorder.start();
            } catch (IllegalStateException ise) {
                System.out.println("Uwaga error");
                System.out.println(ise.getMessage());
            } catch (IOException ioe) {
                System.out.println("Uwaga error");
                System.out.println(ioe.getMessage());
            }
        }else {
            System.out.println("Uwaga error podczas tworzenia pliku");
        }
    }

    public static boolean createBaseDirIfNotExists() {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory() + "/seniorAppNotes/");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                System.out.println("Problem z tworzeniem folderu");
                ret = false;
            }
        }
        return ret;
    }

    public void stopRecording() {
        if (myAudioRecorder != null) {
            myAudioRecorder.stop();
            myAudioRecorder.release();
            myAudioRecorder = null;
        }
    }

    public void play() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(baseUrl + recordTemporaryName);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            System.out.println("odtwarzanie nagrania - cos poszlo nie tak");
            // make something
        }
    }

    public void play(String fileName) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(baseUrl + fileName + ".3gp");
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            // make something
        }
    }

    public List<String> getRecordsNames() {
        List<String> recordNames = new ArrayList<>();

        String path = baseUrl;
        File directory = new File(path);
        File[] files = directory.listFiles();

        String names = "";
       // Log.d("Files", "Size: "+ files.length);
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fn = files[i].getName();
                if (!fn.equals(recordTemporaryName)) {
                    fn = fn.substring(0, fn.length() - 4);
                    recordNames.add(fn);
                    names += fn;
                }
                //   Log.d("Files", "FileName:" + files[i].getName());
            }

            Toast.makeText(app.getContext(), names, Toast.LENGTH_LONG).show();
        }
        return recordNames;
    }

    public void saveRecord(String recordName) {
        File from = new File(baseUrl + recordTemporaryName);
        if (from != null) {
            File to = new File(baseUrl + recordName + ".3gp");
            from.renameTo(to);
        }
    }

    public boolean nameExist(String fileName) {
        for (String name : getRecordsNames()) {
            if (name.equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    //call it before back method
    private void removeTemporaryRecord() {
        File file = new File(baseUrl + recordTemporaryName);
        if (file != null) {
            file.delete();
        }
    }

    public void removeNoteByName(String name) {
        File file = new File(baseUrl + name + ".3gp");
        if (file != null) {
            file.delete();
        }
    }
}
