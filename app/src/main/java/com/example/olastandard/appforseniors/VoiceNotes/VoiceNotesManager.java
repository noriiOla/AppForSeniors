package com.example.olastandard.appforseniors.VoiceNotes;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;

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

    interface CallbackPlayer {
        void onPlayEnd();
    }

    private static VoiceNotesManager voiceNotesManager = null;

    String baseUrl = Environment.getExternalStorageDirectory().getAbsolutePath() + "/seniorAppNotes/";
    private MediaRecorder myAudioRecorder;
    private final String recordTemporaryName = "temp.3gp";
    private boolean isPlayed = false;
    private MediaPlayer player;

    public static VoiceNotesManager getInstance() {
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
        } else {
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
            try {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
            } catch (RuntimeException stopException) {
                System.out.println("Exception audio stop");
                System.out.println(stopException.getMessage());
            }
        }
    }

    public void play(CallbackPlayer callback) {
        playAudio(baseUrl + recordTemporaryName, callback);
    }

    public void play(String fileName, CallbackPlayer callback) {
        playAudio(baseUrl + fileName + ".3gp", callback);
    }

    private void playAudio(String file, final CallbackPlayer callback) {
        if (!isPlayed) {
            player = new MediaPlayer();
            try {
                player.setDataSource(file);
                player.prepare();
                player.start();
                isPlayed = true;
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("Bład: " + e.getStackTrace());
            }

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                    isPlayed = false;
                    if (callback != null) {
                        callback.onPlayEnd();
                    }
                }

                ;
            });
        }
    }

    public void stopAudio() {
        if (player != null && isPlayed) {
            player.stop();
            player.release();
            isPlayed = false;
        }
    }

    public void renameNote(String newName, String oldName) {
        File file = new File(baseUrl + oldName + ".3gp");
        File newFile = new File(baseUrl + newName + ".3gp");
        file.renameTo(newFile);
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
            }
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
