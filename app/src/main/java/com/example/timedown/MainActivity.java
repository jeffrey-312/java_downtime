package com.example.timedown;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static long Durtime;
    private long leftTime = Durtime;
    private Chronometer chronometer;
    private Button btn_start,btn_base,settime,btn_stop;
    private EditText etmin,etsec;

    //////////////////
    private TimePickerDialog timePickerDialog;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    //Calendar calendar;
    int second;
    int minute;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        /*
        calendar = Calendar.getInstance();
        second = calendar.get(Calendar.SECOND);
        minute = calendar.get(Calendar.MINUTE);
         */

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeSet(TimePicker view, int min, int sec) {
                DecimalFormat decimalFormat = new DecimalFormat("00");
                Durtime = min*60 + sec;
                leftTime = Durtime;
                setChronometerText();
            }
        };
    }
    public void picker(View view) {
        timePickerDialog = new TimePickerDialog(this, onTimeSetListener, minute,second, true);
        timePickerDialog.show();
    }

    private void initView() {
        chronometer = findViewById(R.id.chronometer);
        btn_start = findViewById(R.id.btnStart);
        btn_base = findViewById(R.id.btnReset);
        btn_stop = findViewById(R.id.btnStop);
        settime = findViewById(R.id.settime);
        etmin = findViewById(R.id.etmin);
        etsec = findViewById(R.id.etsec);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.start();
            }
        });
        btn_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                leftTime = Durtime;
                setChronometerText();
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
            }
        });

        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minute = Integer.parseInt(etmin.getText().toString());
                second = Integer.parseInt(etsec.getText().toString());
                Durtime = minute*60 + second;
                leftTime = Durtime;
                setChronometerText();
                chronometer.stop();
            }
        });
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                leftTime--;
                setChronometerText();
                if(leftTime == 0 ){
                    Toast.makeText(MainActivity.this,"时间到了~",Toast.LENGTH_SHORT).show();
                    chronometer.stop();
                    return;
                }
            }
        });
        setChronometerText();
    }

    public void setChronometerText()
    {
        Date d = new Date(leftTime * 1000);
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        chronometer.setText(timeFormat.format(d));
    }
}