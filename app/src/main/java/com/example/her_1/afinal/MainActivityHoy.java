package com.example.her_1.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityHoy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hoy);

        Button bBack2 = (Button) findViewById(R.id.b_back2);
        bBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                MainActivityHoy.this.setResult(RESULT_OK, intent);
                MainActivityHoy.this.finish();
            }
        });
    }
}
