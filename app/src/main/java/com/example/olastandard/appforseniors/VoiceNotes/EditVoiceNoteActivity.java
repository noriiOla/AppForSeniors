package com.example.olastandard.appforseniors.VoiceNotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsYesNoInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditVoiceNoteActivity extends MainActivity {

    @BindView(R.id.note_title_input)
    public EditText placeTitle;

    @BindView(R.id.record_action)
    public Button buttonRecordAction;

    private VoiceNotesManager voiceNotesManager;
    private boolean isRecording = false;
    private boolean isNoteExisting = true;
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_add_voice_note);
        ButterKnife.bind(this);
        initToolbar();
        voiceNotesManager = VoiceNotesManager.getInstance();
        changeButtonsColor();
        title = (String) getIntent().getSerializableExtra("title");
        placeTitle.setText(title);
        initListeners();
    }

    private void saveNoteThenOpenList() {
        if (!isNoteExisting) {
            showDialogBox("Nagraj wiadomość");
        }else{
            voiceNotesManager.saveRecord(placeTitle.getText().toString());
            startActivity(new Intent(EditVoiceNoteActivity.this, VoiceNotesList.class));
        }
    }


    private void showDialogBox(String text) {
        new PushDialogManager().showDialogWithOkButton(this, text, new PushDialogButtonsOkInterface() {
            @Override
            public void onOkButtonTap() {
            }
        });
    }

    @OnClick({R.id.record_action})
    public void handleActionOnNotes() {
        if (isRecording) {
            voiceNotesManager.stopRecording();
            isRecording = false;
            isNoteExisting = true;
            buttonRecordAction.setText(R.string.start_recording);
        } else {
            if (isNoteExisting) {
                (new PushDialogManager()).showDialogWithYesNoButtons(this,
                        "Czy chcesz usunąć nagranie i rozpocząć nowe?",
                        new PushDialogButtonsYesNoInterface() {
                            @Override
                            public void onYesButtonTap() {
                                startNewRecord();
                            }

                            @Override
                            public void onNoButtonTap() {
                            }
                        });
            } else {
                startNewRecord();
            }
        }
    }

    private void startNewRecord() {
        isRecording = true;
        voiceNotesManager.startRecording();
        buttonRecordAction.setText(R.string.stop_recording);
    }

    private void initToolbar() {
        showBackButton();
        changeTitleForRightButton(getResources().getString(R.string.save));
        setTitle("Dodaj notatkę");
    }

    private void changeButtonsColor() {
        buttonRecordAction.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
    }

    private void initListeners() {
        this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titleText = placeTitle.getText().toString();
                if (titleText.isEmpty()) {
                    showDialogBox("Wpisz tytuł");
                } else {
                    saveNoteThenOpenList();
                }
            }
        });

        this._toolbarBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                (new PushDialogManager()).showDialogWithYesNoButtons(EditVoiceNoteActivity.this,
                        "Czy chcesz aby notatka została zapisana?",
                        new PushDialogButtonsYesNoInterface() {
                            @Override
                            public void onYesButtonTap() {
                                saveNoteThenOpenList();
                            }

                            @Override
                            public void onNoButtonTap() {
                            }
                        });
            }
        });
    }
}
