package com.example.her_1.afinal;

import android.annotation.SuppressLint;
import android.os.Parcelable;
import android.text.format.DateFormat;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

/**
 * Created by her_1 on 03/12/2017.
 */

@SuppressLint("ParcelCreator")
public class DatosPartido {
    String facultad1, facultad2, actividad, resultado, lugar, fecha;

    public String getFacultad1() {
        return facultad1;
    }

    public void setFacultad1(String facultad1) {
        this.facultad1 = facultad1;
    }

    public String getFacultad2() {
        return facultad2;
    }

    public void setFacultad2(String facultad2) {
        this.facultad2 = facultad2;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = DateFormat.format("dd/MM/yyyy", new Date(fecha)).toString();
    }

    public DatosPartido(){
        facultad1 = null;
        facultad2 = null;
        lugar = null;
        actividad = null;
    }
    public String toString(){

        if(facultad1 != null && facultad2 != null)
            return  "DEPORTE " + actividad.toUpperCase() + "\r\n" +
                    fecha +" || "+ lugar + "\r\n" +
                    facultad1.toUpperCase() + " vs. " + facultad2.toUpperCase() + "\r\n" +
                    "RES: " + resultado + "\r\n"+
                    fecha;
        else
            return  "ACTIVIDAD CULTURAL "+actividad.toUpperCase() + "\r\n" +
                    fecha +" || "+ lugar + "\r\n" +
                    facultad1.toUpperCase() + "\r\n" +
                    "PUNTOS: " + resultado + "\r\n"+
                    fecha;
    }
}
