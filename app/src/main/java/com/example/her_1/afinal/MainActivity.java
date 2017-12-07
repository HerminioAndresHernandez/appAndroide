package com.example.her_1.afinal;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;


public class MainActivity extends AppCompatActivity {

    public static final int GET_RESULTA = 1;
    public static final int GET_RESULTH = 2;
    public static final int GET_RESULTM = 3;
    public static final int GET_RESULTC = 4;

    public static final String fecha = null;
    private int counter = 0;
    private boolean active = false;
    public DatosPartido dato;
    OkHttpClient client = new OkHttpClient();

    /**Check internet conection*/
    public static ConnectivityManager connMgr;
    public static NetworkInfo networkInfo;
    public static ArrayList<String> datos;



    Intent intentAyer;

    TextView text;

    JSONArray jsonjArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.textView);
        intentAyer = new Intent(MainActivity.this, MainActivityAyer.class);
        intentAyer.putStringArrayListExtra("datos", new ArrayList<String>());

        Request request = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();

        /**Check internet conection*/
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        FloatingActionButton bSelect = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        bSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this, MainActivityCalendar.class);
                startActivityForResult(intent, GET_RESULTC);
            }
        });
        Button bAyer = (Button) findViewById(R.id.b_ayer);
        bAyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, MainActivityAyer.class);
                //intent.putStringArrayListExtra("datos", datos);
                startActivityForResult(intent, GET_RESULTA);
            }
        });
        Button bHoy = (Button) findViewById(R.id.b_hoy);
        bHoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHoy = new Intent (MainActivity.this, MainActivityHoy.class);

                startActivityForResult(intentHoy, GET_RESULTH);
            }
        });
        Button bManana = (Button) findViewById(R.id.b_mañana);
        bManana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentManana = new Intent(MainActivity.this, MainActivityManana.class);
                startActivityForResult(intentManana, GET_RESULTM);
            }
        });



        if (networkInfo != null && networkInfo.isConnected()) {
            /**Hilo*/
            AsyncTask<?, ?, ?> asyncTask = new AsyncTask<Object, DatosPartido, Object>() {
                @Override
                protected Object doInBackground(Object... params) {
                    while (true) {
                        try {
                            /**Reemplazar por el servicio*/
                            jsonjArray = new JSONArray(getPartidos());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        while (!active) {
                            synchronized (MainActivity.this) {
                                try {
                                    MainActivity.this.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            for (int i = 0; i < jsonjArray.length(); i++) {
                                synchronized (MainActivity.this) {
                                    try {
                                        JSONObject jsonObject = jsonjArray.getJSONObject(i);
                                        //MainActivityAyer.this.wait(1000);
                                        //if(jsonObject.getString("fecha").equals())
                                        dato = new DatosPartido();
                                        dato.setActividad(jsonObject.getString("deporte"));
                                        dato.setLugar(jsonObject.getString("lugar"));
                                        dato.setResultado(jsonObject.getString("resultado"));
                                        dato.setFecha(Long.valueOf(jsonObject.getString("fecha")).longValue());
                                        JSONObject jsonObjectF = jsonObject.getJSONObject("facultad1");
                                        dato.setFacultad1(jsonObjectF.getString("nombre"));
                                        jsonObjectF = jsonObject.getJSONObject("facultad2");
                                        dato.setFacultad2(jsonObjectF.getString("nombre"));
                                        this.publishProgress(dato);
                                        //Log.d(TAG, "Van " + counter + " segundos");
                                    } catch (InternalError e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //if (!active)

                                counter++;
                                active = false;
                            }
                            break;
                        }
                        return null;
                    }
                }

                @Override
                protected void onProgressUpdate(DatosPartido... values) {
                    super.onProgressUpdate(values);
                    //mensajeToast(MainActivity.this, values[0].toString());
                    //arrayadapter.notifyDataSetChanged();
                    //mensajeToast(MainActivity.this,"compara: " + values[0].getFecha() +" con " + text.getText());
                    //if(dato.getFecha().equals(text.getText())){
                        datos = intentAyer.getStringArrayListExtra("datos");
                        datos.add(values[0].toString());
                        intentAyer.putStringArrayListExtra("datos", datos);
                        //mensajeToast(MainActivity.this,intentAyer.getStringArrayListExtra("datos").toString());
                   // }
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
            if (ResultCode == RESULT_OK) {
                MainActivity.mensajeToast(this, "Retorna de AYER");
            }else {
                MainActivity.mensajeToast(this, "No hay conexion a internet");
            }
        }
        if (RequestCode == GET_RESULTH) {
            if (ResultCode == RESULT_OK) {
                MainActivity.mensajeToast(this, "Retorna de HOY");
            }else {
                MainActivity.mensajeToast(this, "No hay conexion a internet");
            }
        }
        if (RequestCode == GET_RESULTM) {
            if (ResultCode == RESULT_OK) {
                MainActivity.mensajeToast(this, "Retorna de MAÑANA");
                //tv.setText(data.getStringExtra("result"));
            }else {
                MainActivity.mensajeToast(this, "No hay conexion a internet");
            }
        }
        if (RequestCode == GET_RESULTC) {
            if (ResultCode == RESULT_OK) {

                text.setText(data.getStringExtra("result"));
                //text.setText(data.getStringExtra("resultLong"));
                synchronized (MainActivity.this) {
                    active = true;
                    MainActivity.this.notifyAll();
                }
            }else {
                MainActivity.mensajeToast(this, "No hay conexion a internet");
            }
        }
    }
    public static void mensajeToast(MainActivity a, String msj){
        Toast toast = Toast.makeText(a, msj, Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
    public static  String getCulturales(){
        return "[\n" +
                "    {\n" +
                "        \"id\":1,\n" +
                "        \"puntos\":8,\n" +
                "        \"actividad\":\"Pintura\",\n" +
                "        \"facultad\":{\"id\":1,\"nombre\":\"Exactas\"},\n" +
                "        \"fecha\":1512494151103,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },{\n" +
                "        \"id\":2,\n" +
                "        \"puntos\":7,\n" +
                "        \"actividad\":\"Pintura\",\n" +
                "        \"facultad\":{\"id\":2,\"nombre\":\"Economicas\"},\n" +
                "        \"fecha\":1512494151103,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },{\n" +
                "        \"id\":3,\n" +
                "        \"puntos\":9,\n" +
                "        \"actividad\":\"Musica\",\n" +
                "        \"facultad\":{\"id\":2,\"nombre\":\"Economicas\"},\n" +
                "        \"fecha\":1512494151103,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },{\n" +
                "        \"id\":4,\n" +
                "        \"puntos\":0,\n" +
                "        \"actividad\":\"Pintura\",\n" +
                "        \"facultad\":{\"id\":3,\"nombre\":\"Humanas\"},\n" +
                "        \"fecha\":1512580551103,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },{\n" +
                "        \"id\":5,\n" +
                "        \"puntos\":0,\n" +
                "        \"actividad\":\"Musica\",\n" +
                "        \"facultad\":{\"id\":4,\"nombre\":\"Veterinarias\"},\n" +
                "        \"fecha\":1512580551103,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },{\n" +
                "        \"id\":6,\n" +
                "        \"puntos\":0,\n" +
                "        \"actividad\":\"Pintura\",\n" +
                "        \"facultad\":{\"id\":3,\"nombre\":\"Humanas\"},\n" +
                "        \"fecha\":1512580551103,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },{\n" +
                "        \"id\":7,\n" +
                "        \"puntos\":0,\n" +
                "        \"actividad\":\"Escultura\",\n" +
                "        \"facultad\":{\"id\":1,\"nombre\":\"Exactas\"},\n" +
                "        \"fecha\":1512666951103,\"lugar\":\"CCU\"\n" +
                "    },{\n" +
                "        \"id\":8,\n" +
                "        \"puntos\":0,\n" +
                "        \"actividad\":\"Musica\",\n" +
                "        \"facultad\":{\"id\":3,\"nombre\":\"Humanas\"},\n" +
                "        \"fecha\":1512666951103,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },{\n" +
                "        \"id\":9,\n" +
                "        \"puntos\":0,\n" +
                "        \"actividad\":\"Pintura\",\n" +
                "        \"facultad\":{\"id\":4,\"nombre\":\"Veterinarias\"},\n" +
                "        \"fecha\":1512666951103,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    }\n" +
                "]";
    }
    public static  String getPartidos(){
        return "[\n" +
                "    {\n" +
                "        \"id\":1,\n" +
                "        \"resultado\":\"87-67\",\n" +
                "        \"deporte\":\"Basquet\",\n" +
                "        \"facultad1\":{\n" +
                "            \"id\":1,\n" +
                "            \"nombre\":\"Exactas\"},\n" +
                "        \"facultad2\":{\n" +
                "            \"id\":2,\n" +
                "            \"nombre\":\"Economicas\"},\n" +
                "        \"fecha\":1512247864308,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\":2,\n" +
                "        \"resultado\":\"3-2\",\n" +
                "        \"deporte\":\"Ping Pong\",\n" +
                "        \"facultad1\":{\n" +
                "            \"id\":2,\n" +
                "            \"nombre\":\"Economicas\"},\n" +
                "        \"facultad2\":{\n" +
                "            \"id\":3,\n" +
                "            \"nombre\":\"Humanas\"},\n" +
                "        \"fecha\":1512247864308,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\":3,\n" +
                "        \"resultado\":\"2-3\",\n" +
                "        \"deporte\":\"Voley Femenino\",\n" +
                "        \"facultad1\":{\n" +
                "            \"id\":2,\n" +
                "            \"nombre\":\"Economicas\"},\n" +
                "        \"facultad2\":{\n" +
                "            \"id\":4,\n" +
                "            \"nombre\":\"Veterinarias\"},\n" +
                "        \"fecha\":1512247864308,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\":4,\n" +
                "        \"resultado\":null,\n" +
                "        \"deporte\":\"Futbol\",\n" +
                "        \"facultad1\":{\n" +
                "            \"id\":3,\n" +
                "            \"nombre\":\"Humanas\"},\n" +
                "        \"facultad2\":{\n" +
                "                \"id\":4,\n" +
                "                \"nombre\":\"Veterinarias\"},\n" +
                "        \"fecha\":1512334264308,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\":5,\n" +
                "        \"resultado\":null,\n" +
                "        \"deporte\":\"Basquet\",\n" +
                "        \"facultad1\":{\n" +
                "            \"id\":4,\n" +
                "            \"nombre\":\"Veterinarias\"},\n" +
                "        \"facultad2\":{\n" +
                "            \"id\":1,\n" +
                "            \"nombre\":\"Exactas\"},\n" +
                "        \"fecha\":1512334264308,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\":6,\n" +
                "        \"resultado\":null,\n" +
                "        \"deporte\":\"Handball\",\n" +
                "        \"facultad1\":{\n" +
                "            \"id\":3,\n" +
                "            \"nombre\":\"Humanas\"},\n" +
                "        \"facultad2\":{\n" +
                "            \"id\":2,\n" +
                "            \"nombre\":\"Economicas\"},\n" +
                "        \"fecha\":1512334264308,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\":7,\n" +
                "        \"resultado\":null,\n" +
                "        \"deporte\":\"Fultbol\",\n" +
                "        \"facultad1\":{\n" +
                "            \"id\":1,\n" +
                "            \"nombre\":\"Exactas\"},\n" +
                "        \"facultad2\":{\n" +
                "            \"id\":2,\n" +
                "            \"nombre\":\"Economicas\"},\n" +
                "        \"fecha\":1512420664308,\n" +
                "        \"lugar\":\"Campus\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\":8,\n" +
                "        \"resultado\":null,\n" +
                "        \"deporte\":\"Fultbol\",\n" +
                "        \"facultad1\":{\n" +
                "            \"id\":3,\n" +
                "            \"nombre\":\"Humanas\"},\n" +
                "        \"facultad2\":{\n" +
                "            \"id\":4,\n" +
                "            \"nombre\":\"Veterinarias\"},\n" +
                "        \"fecha\":1512420664308,\n" +
                "        \"lugar\":\"Estadio\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\":9,\n" +
                "        \"resultado\":null,\n" +
                "        \"deporte\":\"Ping Pon\",\n" +
                "        \"facultad1\":{\n" +
                "            \"id\":1,\n" +
                "            \"nombre\":\"Exactas\"},\n" +
                "        \"facultad2\":null,\n" +
                "        \"fecha\":1512420664308,\n" +
                "        \"lugar\":\"CCU\"\n" +
                "    }\n" +
                "]";
    }
}
