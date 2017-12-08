package com.example.her_1.afinal;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivityManana extends AppCompatActivity {


    ListView lista;
    ArrayList<String> array;
    ArrayAdapter<String> arrayadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manana);

        FloatingActionButton bBack3 = (FloatingActionButton) findViewById(R.id.b_back3);
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

        lista = (ListView) findViewById(R.id.listItem3);
        array = new ArrayList<>();
        arrayadapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, array);
        lista.setAdapter(arrayadapter);

        /**Check internet conection*/
        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected()) {
            /**Hilo*/
            AsyncTask<?, ?, ?> asyncTask = new AsyncTask<Object, String, Object>() {
                @Override
                protected Object doInBackground(Object... params) {
                    ArrayList<String> aux = getIntent().getStringArrayListExtra("datos");
                    while(true){
                        for(String s: aux){
                            synchronized (MainActivityManana.this) {
                                try {
                                    //MainActivityAyer.this.wait(1000);
                                    array.add(s);
                                    //this.publishProgress(counter);

                                    //Log.d(TAG, "Van " + counter + " segundos");
                                } catch (InternalError e) {
                                    e.printStackTrace();
                                }
                            }
                            this.publishProgress(s);
                        }
                        break;
                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate(String... values) {
                    super.onProgressUpdate(values);
                    //Toast.makeText(MainActivityAyer.this, values[0].toString(), Toast.LENGTH_SHORT).show();
                    arrayadapter.notifyDataSetChanged();
                }
            };
            asyncTask.execute();
            //new DownloadWebpageTask().execute(stringUrl);
        } else {
            Intent intent = new Intent();
            MainActivityManana.this.setResult(RESULT_CANCELED, intent);
            MainActivityManana.this.finish();
        }
    }
}