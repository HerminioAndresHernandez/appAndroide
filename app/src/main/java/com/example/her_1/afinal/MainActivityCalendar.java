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
                Toast.makeText(getApplicationContext(), "Fecha seleccionada "+i2+"/"+Integer.valueOf(i1+1)+"/"+i, Toast.LENGTH_LONG).show();
                //text.setText(i2+"/"+Integer.valueOf(i1+1)+"/"+i);

                Intent intent = new Intent();
                String res = i2+"/"+Integer.valueOf(i1+1)+"/"+i;
                long resLong = Date.parse(res);
                intent.putExtra("result", res);
                intent.putExtra("resultLong", String.valueOf(resLong));
                MainActivityCalendar.this.setResult(RESULT_OK, intent);
                MainActivityCalendar.this.finish();
            }
        });

    }
}
