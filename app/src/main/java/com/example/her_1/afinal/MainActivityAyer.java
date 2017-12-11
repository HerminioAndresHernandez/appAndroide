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
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivityAyer extends AppCompatActivity {

    ListView lista;
    ArrayList<String> array;
    ArrayAdapter<String> arrayadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ayer);

        FloatingActionButton bBack = (FloatingActionButton) findViewById(R.id.b_back);
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                MainActivityAyer.this.setResult(RESULT_OK, intent);
                MainActivityAyer.this.finish();
            }
        });

        lista = (ListView) findViewById(R.id.listItem);
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
                                synchronized (MainActivityAyer.this) {
                                    try {
                                        array.add(s);
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
                    arrayadapter.notifyDataSetChanged();
                }
            };
            asyncTask.execute();
        } else {
            Intent intent = new Intent();
            MainActivityAyer.this.setResult(RESULT_CANCELED, intent);
            MainActivityAyer.this.finish();
        }
    }
}
