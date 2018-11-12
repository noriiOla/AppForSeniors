package com.example.olastandard.appforseniors.AlarmClock;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TimePicker;

public class MyDialogFragment extends DialogFragment {
        private int timeHour;
        private int timeMinute;
        private Handler handler;
    public MyDialogFragment(){}

	@SuppressLint("ValidFragment")
    public MyDialogFragment(Handler handler){
            this.handler = handler;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle bundle = getArguments();
            timeHour = bundle.getInt(MyConstant.MyConstants.HOUR);
            timeMinute = bundle.getInt(MyConstant.MyConstants.MINUTE);
            TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    timeHour = hourOfDay;
                    timeMinute = minute;
                    Bundle b = new Bundle();
                    b.putInt(MyConstant.MyConstants.HOUR, timeHour);
                    b.putInt(MyConstant.MyConstants.MINUTE, timeMinute);
                    Message msg = new Message();
                    msg.setData(b);
                    handler.sendMessage(msg);
                }
            };
            return new TimePickerDialog(getActivity(), listener, timeHour, timeMinute, false);
        }
}
