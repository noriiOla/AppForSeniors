package com.example.olastandard.appforseniors.VoiceNotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.olastandard.appforseniors.Contacts.ContactListAdapter;
import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.Objects.ContactData;
import com.example.olastandard.appforseniors.Objects.PersonSmsData;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsYesNoInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.R;
import com.example.olastandard.appforseniors.smsActivitys.MessagerActivity;
import com.example.olastandard.appforseniors.smsActivitys.smsAdapters.SmsPersonListAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoiceNotesList extends MainActivity {

    @BindView(R.id.voice_notes_recycler_view)
    RecyclerView listOfNotes;
    @BindView(R.id.voice_notes_button_delete)
    Button buttonDelete;
    @BindView(R.id.voice_notes_play_stop)
    Button buttonPlayStop;
    @BindView(R.id.button_edit_notes)
    Button buttonEditNotes;
    @BindView(R.id.voice_notes_background)
    ConstraintLayout background;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    VoiceNotesManager voiceNotesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_voice_notes_list);
        ButterKnife.bind(this);
        this.background.setBackgroundColor(getResources().getColor(R.color.lightGray));
        initToolbar();
        addListeners();
        voiceNotesManager = VoiceNotesManager.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        buttonEditNotes.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        buttonPlayStop.setBackground(getResources().getDrawable(R.drawable.button_shape_white));
        buttonPlayStop.setText(getResources().getString(R.string.play));
        initList();
        ((NotesAdapter) mAdapter).lastSelectedItem = -1;
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAudio();
    }

    private void initList() {
        List<String> notesList = VoiceNotesManager.getInstance().getRecordsNames();
        Collections.sort(notesList);
        initRecyclerView(notesList);
    }

    public void addListeners() {
        this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopAudio();
                startActivity(new Intent(getApplicationContext(), AddVoiceNoteActivity.class));
            }
        });
    }

    @OnClick({R.id.voice_notes_play_stop})
    public void playNote() {
        if (((NotesAdapter) mAdapter).lastSelectedItem >= 0 && ((NotesAdapter)mAdapter).getmDataset().size() > ((NotesAdapter) mAdapter).lastSelectedItem) {
            String selectedTitle = ((NotesAdapter)mAdapter).getmDataset().get(((NotesAdapter) mAdapter).lastSelectedItem);
            if (selectedTitle != null) {
                if (buttonPlayStop.getText().equals(getResources().getString(R.string.play))) {
                    buttonPlayStop.setText(getResources().getString(R.string.stop_play_2));
                    voiceNotesManager.play(selectedTitle, new VoiceNotesManager.CallbackPlayer() {
                        @Override
                        public void onPlayEnd() {
                            buttonPlayStop.setText(getResources().getString(R.string.play));
                        }
                    });
                }else {
                    stopAudio();
                }
            }
        }
    }

    public void stopAudio() {
        buttonPlayStop.setText(getResources().getString(R.string.play));
        voiceNotesManager.stopAudio();
    }


    @OnClick({R.id.voice_notes_button_delete})
    public void deleteNote() {
        if (((NotesAdapter) mAdapter).lastSelectedItem >= 0 && ((NotesAdapter)mAdapter).getmDataset().size() > ((NotesAdapter) mAdapter).lastSelectedItem) {
            String selectedTitle = ((NotesAdapter)mAdapter).getmDataset().get(((NotesAdapter) mAdapter).lastSelectedItem);
            if (selectedTitle != null) {
                stopAudio();

                if (((NotesAdapter) mAdapter).lastSelectedItem >= 0) {
                    List<String> titles = VoiceNotesManager.getInstance().getRecordsNames();
                    //Collections.sort(titles);
                    final String note = titles.get(((NotesAdapter) mAdapter).lastSelectedItem);
                    (new PushDialogManager()).showDialogWithYesNoButtons(this,
                            "Czy na pewno chcesz usunąć notatkę " + note,
                            new PushDialogButtonsYesNoInterface() {
                                @Override
                                public void onYesButtonTap() {
                                    voiceNotesManager.removeNoteByName(note);
                                    List<String> titlesList = VoiceNotesManager.getInstance().getRecordsNames();
                                    Collections.sort(titlesList);
                                    reloadTableData(titlesList);
                                    //initList();
                                }

                                @Override
                                public void onNoButtonTap() {
                                }
                            });
                }
            }
        }


    }

    @OnClick({R.id.button_edit_notes})
    public void editNote() {
        if (((NotesAdapter) mAdapter).lastSelectedItem >= 0 && ((NotesAdapter)mAdapter).getmDataset().size() > ((NotesAdapter) mAdapter).lastSelectedItem) {
            String selectedTitle = ((NotesAdapter) mAdapter).getmDataset().get(((NotesAdapter) mAdapter).lastSelectedItem);
            if (selectedTitle != null) {
                stopAudio();
                Intent intent = new Intent(getApplicationContext(), EditVoiceNoteActivity.class);
                intent.putExtra("title", selectedTitle);
                this.startActivity(intent);
            }
        }
    }

    private void initRecyclerView(List<String> listOfVoiceNotesTitles) {
        listOfNotes.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        listOfNotes.setLayoutManager(mLayoutManager);
        mAdapter = new NotesAdapter(listOfVoiceNotesTitles, getApplicationContext());
        listOfNotes.setAdapter(mAdapter);
    }

    private void initToolbar() {
        setTitle(getResources().getString(R.string.notes_title));
        changeTitleForRightButton(getResources().getString(R.string.newS));
    }

    private void changeButtonsColor() {
        buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_red));
        buttonEditNotes.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
        buttonPlayStop.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
    }

    public void updateSelectedItem(int index) {
        stopAudio();
        ((NotesAdapter) mAdapter).lastSelectedItem = index;
        mAdapter.notifyDataSetChanged();
        changeButtonsColor();
    }

    private void reloadTableData(List<String> contactData) {
        ((NotesAdapter)mAdapter).mDataset = contactData;
        mAdapter.notifyDataSetChanged();
    }
}
