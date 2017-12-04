package com.example.her_1.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    public static final int GET_RESULTA = 1;
    public static final int GET_RESULTH = 2;
    public static final int GET_RESULTM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bAyer = (Button) findViewById(R.id.b_ayer);
        bAyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(MainActivity.this, MainActivityAyer.class);

                startActivityForResult(intent, GET_RESULTA);
            }
        });
        Button bHoy = (Button) findViewById(R.id.b_hoy);
        bHoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(MainActivity.this, MainActivityHoy.class);

                startActivityForResult(intent, GET_RESULTH);
            }
        });
        Button bManana = (Button) findViewById(R.id.b_mañana);
        bManana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(MainActivity.this, MainActivityManana.class);

                startActivityForResult(intent, GET_RESULTM);
            }
        });
    }
    @Override
    protected void onActivityResult ( int RequestCode, int ResultCode, Intent data){
        if (RequestCode == GET_RESULTA) {
            TextView text = (TextView) findViewById(R.id.textView);
            if (ResultCode == RESULT_OK) {
                text.setText("Retorna de AYER");
            }else {
                text.setText("Sin conexion a Internet");
            }
        }
        if (RequestCode == GET_RESULTH) {
            TextView text = (TextView) findViewById(R.id.textView);
            if (ResultCode == RESULT_OK) {
                text.setText("Retorna de HOY");
            }else {
                text.setText("Sin conexion a Internet");
            }
        }
        if (RequestCode == GET_RESULTM) {
            TextView text = (TextView) findViewById(R.id.textView);
            if (ResultCode == RESULT_OK) {
                text.setText("Retorna de MAÑANA");
                //tv.setText(data.getStringExtra("result"));
            }else {
                text.setText("Sin conexion a Internet");
            }
        }
    }
}
