package com.example.olastandard.appforseniors.PushDIalog;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.olastandard.appforseniors.R;

public class PushDialogManager {

    public void showDialogWithYesNoButtons(AppCompatActivity activity, String dialogMessage, final PushDialogButtonsYesNoInterface buttonsListeners) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_push_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.push_dialog_text);
        text.setText(dialogMessage);

        Button buttonNo = (Button) dialog.findViewById(R.id.push_button_no);
        buttonNo.setVisibility(View.VISIBLE);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsListeners.onNoButtonTap();
                dialog.dismiss();
            }
        });

        Button buttonYes = (Button) dialog.findViewById(R.id.push_button_yes);
        buttonYes.setVisibility(View.VISIBLE);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsListeners.onYesButtonTap();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showDialogWithOkButton(AppCompatActivity activity, String dialogMessage, final PushDialogButtonsOkInterface buttonsListeners) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_push_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.push_dialog_text);
        text.setText(dialogMessage);
        Button buttonOk = (Button) dialog.findViewById(R.id.push_button_ok);
        buttonOk.setVisibility(View.VISIBLE);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsListeners.onOkButtonTap();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
