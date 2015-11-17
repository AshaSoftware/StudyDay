package com.ashasoftware.studyday;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by natanmorais on 13/11/15.
 */
public class Estudo {

    int Codigo, CodigoMateria;
    String Descricao;
    Calendar diaIni, diaFim;

    public Estudo(){}

    public Estudo( int pCodigoMateria, String pDescricao, long pInicio, long pFim ) {
        CodigoMateria = pCodigoMateria;
        Descricao = pDescricao;
        diaIni = GregorianCalendar.getInstance();
        diaIni.setTimeInMillis( pInicio );
        diaFim = GregorianCalendar.getInstance();
        diaFim.setTimeInMillis( pFim );
    }

    public int getCodigo() {
        return Codigo;
    }

    public int getCodigoMateria() {
        return CodigoMateria;
    }

    public String getDescricao() {
        return Descricao;
    }

    public Calendar getDiaIni() {
        return diaIni;
    }

    public Calendar getDiaFim() {
        return diaFim;
    }

    public void setCodigo( int codigo ) {
        Codigo = codigo;
    }

    public void setCodigoMateria(int codigoMateria) {
        CodigoMateria = codigoMateria;
    }

    public void setDescricao( String descricao ) {
        Descricao = descricao;
    }

    public void setDiaIni( long timestamp ) {
        diaIni.setTimeInMillis( timestamp );
    }

    public void setDiaFim( long timestamp ) {
        diaFim.setTimeInMillis( timestamp );
    }
}
