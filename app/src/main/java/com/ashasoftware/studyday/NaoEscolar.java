package com.ashasoftware.studyday;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by natanmorais on 02/11/15.
 * Modified by tiago on 03/11/15.
 */
public class NaoEscolar {

    int Codigo;
    String Nome, Descricao;
    Calendar diaIni, diaFim;

    public NaoEscolar(){}

    public NaoEscolar( String pNome, String pDescricao, long pInicio, long pFim ) {
        Nome = pNome;
        Descricao = pDescricao;
        diaIni = GregorianCalendar.getInstance();
        diaIni.setTimeInMillis( pInicio );
        diaFim = GregorianCalendar.getInstance();
        diaFim.setTimeInMillis( pFim );
    }

    public int getCodigo() {
        return Codigo;
    }

    public String getNome() {
        return Nome;
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

    public void setNome( String nome ) {
        Nome = nome;
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
