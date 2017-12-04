package com.example.her_1.afinal;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivityAyer extends AppCompatActivity {

    private int counter = 0;
    private static final String TAG =  MainActivity.class.getCanonicalName();
    private boolean active = true;
    public DatosPartido dato;

    ListView lista;
    ArrayList<DatosPartido> array;
    ArrayAdapter<DatosPartido> arrayadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ayer);
        TextView text = (TextView) findViewById(R.id.textView);
        active = true;

        Button bBack = (Button) findViewById(R.id.b_back);
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                active = false;
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
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            /**Hilo*/
            AsyncTask<?, ?, ?> asyncTask = new AsyncTask<Object, Integer, Object>() {
                @Override
                protected Object doInBackground(Object... params) {
                    while (counter < 40) {
                        synchronized (MainActivityAyer.this) {
                            try {
                                //MainActivityAyer.this.wait(1000);
                                dato = new DatosPartido(String.valueOf(counter), String.valueOf(counter));
                                array.add(dato);
                                this.publishProgress(counter);

                                //Log.d(TAG, "Van " + counter + " segundos");
                            } catch (InternalError e) {
                                e.printStackTrace();
                            }
                        }
                        if (!active)
                            break;
                        counter++;
                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);

                    arrayadapter.notifyDataSetChanged();
                }
            };
            asyncTask.execute();
            //new DownloadWebpageTask().execute(stringUrl);
        } else {
            Intent intent = new Intent();
            MainActivityAyer.this.setResult(RESULT_CANCELED, intent);
            MainActivityAyer.this.finish();
        }
    }
    private String generateJson(){
        return "hola";
    }
}
