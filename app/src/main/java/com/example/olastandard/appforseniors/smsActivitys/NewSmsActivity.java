package com.example.olastandard.appforseniors.smsActivitys;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewSmsActivity extends MainActivity {

    @BindView(R.id.keyboardview)
    KeyboardView keyboardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_new_sms);
        ButterKnife.bind(this);
        Keyboard newTypeOfKeyboard = new Keyboard(this, R.xml.keyboard);
        keyboardView.setKeyboard( newTypeOfKeyboard );

        keyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
    }

    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override public void onKey(int primaryCode, int[] keyCodes)
        {
            //Here check the primaryCode to see which key is pressed
            //based on the android:codes property
            if(primaryCode==1)
            {
                Log.i("Key","You just pressed 1 button");
            }
        }

        @Override public void onPress(int arg0) {
        }

        @Override public void onRelease(int primaryCode) {
        }

        @Override public void onText(CharSequence text) {
        }

        @Override public void swipeDown() {
        }

        @Override public void swipeLeft() {
        }

        @Override public void swipeRight() {
        }

        @Override public void swipeUp() {
        }
    };
//
//    public void openKeyboard(View v)
//    {
//        mKeyboardView.setVisibility(View.VISIBLE);
//        mKeyboardView.setEnabled(true);
//        if( v!=null)((InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
//    }
}
