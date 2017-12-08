package com.example.her_1.afinal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by her_1 on 08/12/2017.
 */

public class Fecha {
    private static int dateNumeric(String date){
        String aux = "";
        for(char c: date.toCharArray()){
            if(Character.isDigit(c))
                aux += c;
        }
        return Integer.valueOf(aux);
    }
    public static boolean equals (String a1, String a2){
        if(dateNumeric(a1) == dateNumeric(a2))
            return true;
        return false;
    }
    public static Date sumarDiasAFecha(String a1, int dias){

        Date fecha = toDate(a1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }
    public static Date sumarDiasAFecha(Date fecha, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }
    public static Date toDate(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }
}
