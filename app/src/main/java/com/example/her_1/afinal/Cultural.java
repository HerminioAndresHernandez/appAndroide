package com.example.her_1.afinal;

/**
 * Created by Ignacio on 11/12/2017.
 */

public class Cultural extends Evento{

        private String puntos;
    private String actividad;

    public Cultural(String puntos,String actividad, int id, String lugar, String fecha, String facultad1){

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

    public String getPuntos() {
        return puntos;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }


    public String toString(){
            return  "ACTIVIDAD CULTURAL "+actividad.toUpperCase() + "\r\n" +
                    getFecha() +" || "+ getLugar() + "\r\n" +
                    getFacultad1().toUpperCase() + "\r\n" +
                    "PUNTOS: " + puntos + "\r\n";

    }
}
