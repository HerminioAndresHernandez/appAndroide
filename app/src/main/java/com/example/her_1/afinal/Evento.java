package com.example.her_1.afinal;

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

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
