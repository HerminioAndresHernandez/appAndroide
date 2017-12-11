package com.example.her_1.afinal;

/**
 * Created by Ignacio on 11/12/2017.
 */

public class Cultural extends Evento{

    private int puntos;
    private String actividad;

    public Cultural(int puntos,String actividad, int id, String lugar, String fecha, String facultad1){

        super(id,lugar,fecha,facultad1);
        this.puntos = puntos;
        this.actividad = actividad;
    }

    public Cultural(){
        super();
    }

    public String getActividad() {
        return actividad;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
