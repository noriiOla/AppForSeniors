package com.example.olastandard.appforseniors.AlarmClock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class AlarmActivity  extends MainActivity {
    AlarmManager alarmManager;
    PendingIntent appIntent;
    private Integer hour1;


    /* @BindView(R.id.time_of_alarm)
     Button textTime;
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_alarm);
        initToolbar();
        int _id = (int) System.currentTimeMillis();

        long millis=System.currentTimeMillis();
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(millis);
        Integer hours=c.get(Calendar.HOUR);
        Integer minutes=c.get(Calendar.MINUTE);
        Integer hour1= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Integer minutes1=Calendar.getInstance().get(Calendar.MINUTE);


        String noe="";
        if(hour1.toString().length()==1){
        noe+="0";
        }
        noe+= hour1.toString()+":";
        if(minutes1.toString().length()==1){
            noe+="0";
        }
        noe+=minutes1.toString();


        String newTime=hours.toString()+minutes.toString();

        int newTome2=Integer.parseInt(newTime);
        System.out.println("----> alarm activity");
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
        appIntent = PendingIntent.getBroadcast(this,(int) newTome2 , myIntent,PendingIntent.FLAG_ONE_SHOT);



        String newTime2=hours.toString()+":"+minutes.toString();
        //alarmButton.setBackground(getResources().getDrawable(R.drawable.floating_button_shape_green));
       // textTime.setText(newTime2);
        TextView text = (TextView)findViewById(R.id.time_of_alarm);
        text.setText(noe);
    }

    @OnClick({R.id.button_stop_alarm})
    public void cancelAlarm(View v) {
        if (alarmManager!= null) {
            alarmManager.cancel(appIntent);
            if(AlarmReceiver.ringtone!=null) {
                AlarmReceiver.ringtone.stop();
            }
        }
        Button text = (Button)findViewById(R.id.button_stop_alarm);
        text.setVisibility(View.GONE);

        long millis=System.currentTimeMillis();
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(millis);
        Integer hours=c.get(Calendar.HOUR);
        Integer minutes=c.get(Calendar.MINUTE);

        String newTime=hours.toString()+minutes.toString();
        int newTome2=Integer.parseInt(newTime);
        System.out.println("----> alarm activity");
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);

        Calendar now = Calendar.getInstance();
        long _alarm=0;
        if(c.getTimeInMillis() <= now.getTimeInMillis())
            _alarm = c.getTimeInMillis() + (AlarmManager.INTERVAL_DAY+1);
        else
            _alarm = c.getTimeInMillis();
        appIntent = PendingIntent.getBroadcast(this,(int) newTome2 , myIntent,PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, _alarm , appIntent);

        _toolbarBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void openApp(){
        Intent i = new Intent(this, AlarmActivity.class);
       /* Bundle b = new Bundle();
        b.putInt(Constants.ALARM_ID, intent.getExtras().getInt(Constants.ALARM_ID));
        if(intent.getExtras().containsKey(Constants.SNOOZE_ALARM)){
            b.putString(Constants.SNOOZE_ALARM, intent.getExtras().getString(Constants.SNOOZE_ALARM));
        }
        i.putExtras(b);*/
        //THESE ARE THE FLAGS NEEDED TO START THE ACTIVITY AND TO PREVENT THE BUG
        //(CLEAR_TASK is crucial for the bug and new task is needed to start activity from outside of an activity)
        //  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        System.out.println("----> on intentservice");
        startActivity(i);

    }
    private void initToolbar() {
        showBackButton();
        hideRightButton();
        changeBackButtonTitle("Zamknij");
        setTitle("Alarm");
    }
}