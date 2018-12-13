package com.example.olastandard.appforseniors.AlarmClock;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsYesNoInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.R;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

    public class ListClock2Activity extends MainActivity {

       /* @BindView(R.id.voice_notes_recycler_view)
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

            initList();
        }

        private void initList() {
            List<String> notesList = VoiceNotesManager.getInstance().getRecordsNames();
            initRecyclerView(notesList);
        }

        public void addListeners() {
            this._toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), AddVoiceNoteActivity.class));
                }
            });
        }

        @OnClick({R.id.voice_notes_play_stop})
        public void playNote() {
            if (((NotesAdapter) mAdapter).lastSelectedItem >= 0) {
                List<String> titles = VoiceNotesManager.getInstance().getRecordsNames();
                Collections.sort(titles);
                String note = titles.get(((NotesAdapter) mAdapter).lastSelectedItem);
                voiceNotesManager.play(note);
            }
        }

        @OnClick({R.id.voice_notes_button_delete})
        public void deleteNote() {
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
                                initList();
                            }

                            @Override
                            public void onNoButtonTap() {
                            }
                        });
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
            setTitle("Notatki");
            changeTitleForRightButton(getResources().getString(R.string.newS));
        }

        private void changeButtonsColor() {
            buttonDelete.setBackground(getResources().getDrawable(R.drawable.button_shape_red));
            buttonEditNotes.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
            buttonPlayStop.setBackground(getResources().getDrawable(R.drawable.button_shape_green));
        }

        public void updateSelectedItem(int index) {
            ((NotesAdapter) mAdapter).lastSelectedItem = index;
            mAdapter.notifyDataSetChanged();
            changeButtonsColor();
        }*/
    }
