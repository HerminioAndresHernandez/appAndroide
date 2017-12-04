package com.example.her_1.afinal;

import android.text.format.DateFormat;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

/**
 * Created by her_1 on 03/12/2017.
 */

public class DatosPartido {
    String facultad1, facultad2, deporte, resultado, lugar;
    Date fecha;

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

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public DatosPartido(String f1, String f2, String d, String r, String l, String f){
        facultad1 = f1;
        facultad2 = f2;

        deporte = d;
        resultado = r;
    }
    public DatosPartido(String f1, String f2){
        facultad1 = f1;
        facultad2 = f2;

    }
    public String toString(){
        return facultad1.toUpperCase() + " vs. " + facultad2.toUpperCase();
    }


}
