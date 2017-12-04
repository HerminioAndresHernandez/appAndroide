package com.example.her_1.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityManana extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manana);

        Button bBack3 = (Button) findViewById(R.id.b_back3);
        bBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Obtener informacion del intent anterior
                 Intent intAnt = getIntent();
                 String num1 = intAnt.getStringExtra("number1");
                 String num2 = intAnt.getStringExtra("number2");
                 **/


                Intent intent = new Intent();
                MainActivityManana.this.setResult(RESULT_OK, intent);
                MainActivityManana.this.finish();
            }
        });
    }
}