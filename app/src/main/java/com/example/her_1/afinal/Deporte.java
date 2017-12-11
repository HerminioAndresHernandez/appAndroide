package com.example.her_1.afinal;

/**
 * Created by her_1 on 03/12/2017.
 */

public class Deporte extends Evento {

    String facultad2, deporte,resultado;

    public Deporte(String deporte, String facultad2, int id, String lugar, String fecha, String facultad1, String resultado){

        super(id,lugar,fecha,facultad1);
        this.facultad2 = facultad2;
        this.deporte = deporte;
        this.resultado = resultado;
    }

    public Deporte(){
        super();
    }

    public String getFacultad2() {
        return facultad2;
    }

    public void setFacultad2(String facultad2) {
        this.facultad2 = facultad2;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String toString(){

        return  "DEPORTE " + deporte.toUpperCase() + "\r\n" +
                getFecha() +" || "+ getLugar() + "\r\n" +
                getFacultad1().toUpperCase() + " vs. " + facultad2.toUpperCase() + "\r\n" +
                "RES: " + resultado;
    }
}
