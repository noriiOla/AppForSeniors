package com.example.olastandard.appforseniors;

import android.os.Bundle;
import android.widget.Button;

import com.example.olastandard.appforseniors.Menagers.PushDialogButtonsOkInterface;
import com.example.olastandard.appforseniors.Menagers.PushDialogButtonsYesNoInterface;
import com.example.olastandard.appforseniors.Menagers.PushDialogManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExampleActivity extends MainActivity {
    @BindView(R.id.show_dialog_button)
    Button button;

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
