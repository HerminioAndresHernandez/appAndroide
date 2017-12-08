package com.example.her_1.afinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivityCalendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calendar);


        CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String res = String.format("%02d",i2)+"/"+String.format("%02d",Integer.valueOf(i1+1))+"/"+i;
                Toast.makeText(getApplicationContext(), "Fecha seleccionada "+res, Toast.LENGTH_LONG).show();
                //text.setText(i2+"/"+Integer.valueOf(i1+1)+"/"+i);
                Intent intent = new Intent();
                long resLong = Date.parse(res);
                intent.putExtra("result", res);
                intent.putExtra("resultLong", String.valueOf(resLong));
                MainActivityCalendar.this.setResult(RESULT_OK, intent);
                MainActivityCalendar.this.finish();
            }
        });

    }
}
