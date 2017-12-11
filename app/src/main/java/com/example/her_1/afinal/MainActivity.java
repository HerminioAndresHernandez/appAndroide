package com.example.her_1.afinal;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    public static final int GET_RESULTA = 1;
    public static final int GET_RESULTH = 2;
    public static final int GET_RESULTM = 3;
    public static final int GET_RESULTC = 4;

    private static final String TAG =  MainActivity.class.getCanonicalName();

    private boolean active = false;
    private boolean json = false;

    /**Check internet conection*/
    public static ConnectivityManager connMgr;
    public static NetworkInfo networkInfo;
    public static ArrayList<String> datos;

    OkHttpClient client = new OkHttpClient();
    Request request_partidos;
    Request request_culturales;

    Intent intentAyer;
    Intent intentHoy;
    Intent intentManana;

    TextView text;

    JSONArray jsonjArray_partidos;
    JSONArray jsonjArray_culturales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.textView);

        intentAyer = new Intent(MainActivity.this, MainActivityAyer.class);
        intentAyer.putStringArrayListExtra("datos", new ArrayList<String>());
        intentHoy = new Intent(MainActivity.this, MainActivityHoy.class);
        intentHoy.putStringArrayListExtra("datos", new ArrayList<String>());
        intentManana = new Intent(MainActivity.this, MainActivityManana.class);
        intentManana.putStringArrayListExtra("datos", new ArrayList<String>());

        request_partidos = new Request.Builder().url("http://192.168.1.9:8080/OlimpicRestServer/olimpic/getpartidos").build();
        request_culturales = new Request.Builder().url("http://192.168.1.9:8080/OlimpicRestServer/olimpic/getculturales").build();

        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date auxDate = new Date();

        String res = formater.format(auxDate);
        text.setText(res);

        client.newCall(request_partidos).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d(TAG, "NO ANDAAAAAAAAAAAAAAAAAAAAAAA");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    jsonjArray_partidos = new JSONArray(responseData);
                    synchronized (MainActivity.this) {
                        active = true;
                        MainActivity.this.notifyAll();
                    }
                    Log.d(TAG, "ANDAAAAAAAAAAAAAAAAAAAAAAA33333333");
                } catch (JSONException e) {Log.d(TAG, "NO ANDAAAAAAAAAAAAAAAAAAAAAAA2");}
            }
        });

        client.newCall(request_culturales).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d(TAG, "NO ANDAAAAAAAAAAAAAAAAAAAAAAA");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    jsonjArray_culturales = new JSONArray(responseData);
                    synchronized (MainActivity.this) {
                        active = true;
                        MainActivity.this.notifyAll();
                    }
                    Log.d(TAG, "ANDAAAAAAAAAAAAAAAAAAAAAAA33333333");
                } catch (JSONException e) {Log.d(TAG, "NO ANDAAAAAAAAAAAAAAAAAAAAAAA2");}
            }
        });

        /**Check internet conection*/
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        FloatingActionButton bAyer = (FloatingActionButton) findViewById(R.id.b_ayer);
        bAyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(intentAyer, GET_RESULTA);
            }
        });
        FloatingActionButton bHoy = (FloatingActionButton) findViewById(R.id.b_hoy);
        bHoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(intentHoy, GET_RESULTH);
            }
        });
        FloatingActionButton bManana = (FloatingActionButton) findViewById(R.id.b_manana);
        bManana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(intentManana, GET_RESULTM);
            }
        });
        this.start();
    }
    public void start(){
        if (networkInfo != null && networkInfo.isConnected()) {
            /**Hilo*/
            AsyncTask<?, ?, ?> asyncTask = new AsyncTask<Object, Evento, Object>() {
                @Override
                protected Object doInBackground(Object... params) {

                    while (!active) {
                        synchronized (MainActivity.this) {
                            try {
                                MainActivity.this.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    intentAyer.getStringArrayListExtra("datos").clear();
                    intentHoy.getStringArrayListExtra("datos").clear();
                    intentManana.getStringArrayListExtra("datos").clear();

                    for (int i = 0; i < jsonjArray_partidos.length(); i++) {
                        synchronized (MainActivity.this) {
                            try {
                                JSONObject jsonObject = jsonjArray_partidos.getJSONObject(i);
                                Deporte dato = new Deporte();
                                dato.setDeporte(jsonObject.getString("deporte"));
                                dato.setLugar(jsonObject.getString("lugar"));
                                dato.setResultado(jsonObject.getString("resultado"));
                                dato.setFecha(Long.valueOf(jsonObject.getString("fecha")).longValue());
                                JSONObject jsonObjectF = jsonObject.getJSONObject("facultad1");
                                dato.setFacultad1(jsonObjectF.getString("nombre"));
                                jsonObjectF = jsonObject.getJSONObject("facultad2");
                                dato.setFacultad2(jsonObjectF.getString("nombre"));
                                this.publishProgress(dato);
                            } catch (InternalError e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    for (int i = 0; i < jsonjArray_culturales.length(); i++) {
                        synchronized (MainActivity.this){
                            try {
                                JSONObject jsonObject = jsonjArray_culturales.getJSONObject(i);
                                Cultural dato = new Cultural();
                                dato.setActividad(jsonObject.getString("actividad"));
                                dato.setLugar(jsonObject.getString("lugar"));
                                dato.setPuntos(jsonObject.getInt("puntos"));
                                dato.setFecha(Long.valueOf(jsonObject.getString("fecha")).longValue());
                                JSONObject jsonObjectF = jsonObject.getJSONObject("facultad1");
                                dato.setFacultad1(jsonObjectF.getString("nombre"));
                                this.publishProgress(dato);
                            } catch (InternalError e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    active = false;
                    return null;
                }

                @Override
                protected void onProgressUpdate(Evento... values) {
                    super.onProgressUpdate(values);
                    if(Fecha.equals(values[0].getFecha(), text.getText().toString())){
                        datos = intentHoy.getStringArrayListExtra("datos");
                        datos.add(values[0].toString());
                        intentHoy.putStringArrayListExtra("datos", datos);
                    }else{
                        if(Fecha.sumarDiasAFecha(text.getText().toString(), -1).equals(Fecha.toDate(values[0].getFecha().toString()))){
                            datos = intentAyer.getStringArrayListExtra("datos");
                            datos.add(values[0].toString());
                            intentAyer.putStringArrayListExtra("datos", datos);
                        }else {
                            if (Fecha.sumarDiasAFecha(text.getText().toString(), +1).equals(Fecha.toDate(values[0].getFecha().toString()))) {
                                datos = intentManana.getStringArrayListExtra("datos");
                                datos.add(values[0].toString());
                                intentManana.putStringArrayListExtra("datos", datos);
                            }
                        }
                    }
                }
            };
            asyncTask.execute();
            //new DownloadWebpageTask().execute(stringUrl);
        } else {
            mensajeToast(this, "No hay conexion a internet");
        }
    }
    @Override
    protected void onActivityResult ( int RequestCode, int ResultCode, Intent data){
        if (RequestCode == GET_RESULTA) {
            if (ResultCode != RESULT_OK) {
                MainActivity.mensajeToast(this, "No hay conexion a internet");
            }
        }
        if (RequestCode == GET_RESULTH) {
            if (ResultCode != RESULT_OK) {
                MainActivity.mensajeToast(this, "No hay conexion a internet");
            }
        }
        if (RequestCode == GET_RESULTM) {
            if (ResultCode != RESULT_OK) {
                MainActivity.mensajeToast(this, "No hay conexion a internet");
            }
        }
    }
    public static void mensajeToast(MainActivity a, String msj){
        Toast toast = Toast.makeText(a, msj, Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}