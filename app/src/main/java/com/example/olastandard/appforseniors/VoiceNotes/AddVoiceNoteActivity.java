package com.example.olastandard.appforseniors.VoiceNotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olastandard.appforseniors.Contacts.ContactListAdapter;
import com.example.olastandard.appforseniors.Contacts.EditContactActivity;
import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.ContactData;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.smsActivitys.NewSmsActivity;
import com.google.android.gms.common.data.DataBufferUtils;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddVoiceNoteActivity  extends MainActivity {

    @BindView(R.id.start_recording)
    public Button buttonStartRecording;

    @BindView(R.id.stop_recording)
    public Button buttonStopRecording;
    VoiceNotesManager voiceNotesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_add_voice_note);
        ButterKnife.bind(this);
        initToolbar();
        voiceNotesManager = VoiceNotesManager.getInstance();
        changeButtonsColor();
    }

    @OnClick({R.id.start_recording})
    public void startRecordingNote() {
        voiceNotesManager.startRecording();
        Toast.makeText(getApplicationContext(),"Pocatek nagrywania" ,Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.stop_recording})
    public void stopRecordingNote() {
        voiceNotesManager.stopRecording();
        Toast.makeText(getApplicationContext(),"Koniec nagrywania" ,Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.save_record})
    public void saveRecordedNote() {
        System.out.println();
        String name = generateRandomString();
        voiceNotesManager.saveRecord(name);
        (new PushDialogManager()).showDialogWithOkButton(this,
                "zapisano nagranie: " + name , new PushDialogButtonsOkInterface(){
            @Override
            public void onOkButtonTap() {
            }
            });
    }

    private void initToolbar() {
        showBackButton();
        changeTitleForRightButton(getResources().getString(R.string.save));
        setTitle("Dodaj notatkę");
    }

    private String generateRandomString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

    private void changeButtonsColor() {
        buttonStartRecording.setBackground(getResources().getDrawable(R.drawable.button_shape_red));
        buttonStopRecording.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
        //buttonPlayStop.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
    }
}
