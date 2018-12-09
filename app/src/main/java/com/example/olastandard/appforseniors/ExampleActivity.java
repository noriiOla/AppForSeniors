package com.example.olastandard.appforseniors;

import android.os.Bundle;
import android.widget.Button;

import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsYesNoInterface;
import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;
import com.example.olastandard.appforseniors.VoiceNotes.VoiceNotesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExampleActivity extends MainActivity {
    @BindView(R.id.show_dialog_button)
    Button button;

    @BindView(R.id.button_nagraj)
    Button buttonNagraj;

    @BindView(R.id.button_zatrzymaj)
    Button buttonZatrzymaj;

    @BindView(R.id.button_odtworz)
    Button buttonOdtworz;

    @BindView(R.id.pokazDostepne)
    Button buttonPokazDostepne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_example);
        ButterKnife.bind(this);

        initToolbar();
    }

    private void initToolbar() {
        showBackButton();
        hideRightButton();
        setTitle("EXAMPLE");
    }

    @OnClick(R.id.button_nagraj)
    public void nagraj() {
        VoiceNotesManager.getInstance().startRecording();
    }

    @OnClick(R.id.button_zatrzymaj)
    public void zatrzymaj() {
        VoiceNotesManager.getInstance().stopRecording();
    }

    @OnClick(R.id.button_odtworz)
    public void odtworz() {
        VoiceNotesManager.getInstance().play(); //odtwarza - tylko z ekranu nagrywania
       // VoiceNotesManager.getInstance().play("not1"); //odtwarza- tylko z listy dostepnych nagran
    }

    @OnClick(R.id.pokazDostepne)
    public void pokazDostepne() {
        VoiceNotesManager.getInstance().getRecordsNames(); //zwraca liste nagran
    }

    @OnClick(R.id.button_usun)
    public void usunNagranie() {
        //VoiceNotesManager.getInstance().removeTemporaryRecord();  //tylko przy po nacisnieciu (wroc)
        VoiceNotesManager.getInstance().removeNoteByName("not1"); //z listy

    }

    @OnClick(R.id.button_zapisz)
    public void zapiszNagranie() {
        VoiceNotesManager.getInstance().saveRecord("not1"); //po nacisnieciu zapisz na ekranie nagrywania
    }


    @OnClick(R.id.show_dialog_button)
    public void showDialogTap() {
      //showDialogWithOkButton();
      showDialogWithYesNoButton();
    }

    private void showDialogWithOkButton() {
        (new PushDialogManager()).showDialogWithOkButton(this, "Czy na pewno chcesz usunac...", new PushDialogButtonsOkInterface() {

            @Override
            public void onOkButtonTap() {
                System.out.println("-----------=Button OK selected OK");
            }
        });
    }

    private void showDialogWithYesNoButton() {
        (new PushDialogManager()).showDialogWithYesNoButtons(this, "Czy na pewno chcesz usunac... nie wiem co napisac ale pisze aby bylo... ?", new PushDialogButtonsYesNoInterface() {


            @Override
            public void onYesButtonTap() {
                System.out.println("-----------=Button YES selected OK");
            }

            @Override
            public void onNoButtonTap() {
                System.out.println("-----------=Button NO selected OK");
            }
        });
    }
}
