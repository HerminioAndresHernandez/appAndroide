package com.example.her_1.afinal;

import android.text.format.DateFormat;
import java.util.Date;

/**
 * Created by Ignacio on 11/12/2017.
 */

public class Evento {

    private int id;
    private String lugar, fecha,facultad1;

    public Evento(int id, String lugar, String fecha, String facultad1){

        this.id = id;
        this.lugar = lugar;
        this.fecha = fecha;
        this.facultad1 = facultad1;
    }

    public Evento(){

    }

    public String getLugar() {
        return lugar;
    }

    public int getId() {
        return id;
    }

    public String getFacultad1() {
        return facultad1;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFacultad1(String facultad1) {
        this.facultad1 = facultad1;
    }

    public void setFecha(long fecha) {
        this.fecha = DateFormat.format("dd/MM/yyyy", new Date(fecha)).toString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
