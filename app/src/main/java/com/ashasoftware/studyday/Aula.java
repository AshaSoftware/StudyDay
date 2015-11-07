package com.ashasoftware.studyday;

import java.util.Calendar;

/**
 * Created by natanmorais on 07/11/15.
 */
public class Aula {

    int Codigo, CodigoMateria;
    Calendar diaIni, diaFim;

    public Aula(int codigoMateria, long pIni, long pFim) {
        CodigoMateria = codigoMateria;
        setDiaIni(pIni);
        setDiaFim(pFim);
    }

    public int getCodigo() {
        return Codigo;
    }

    public int getCodigoMateria() {
        return CodigoMateria;
    }

    public Calendar getDiaIni() {
        return diaIni;
    }

    public Calendar getDiaFim() {
        return diaFim;
    }

    public void setDiaIni( long timestamp ) {
        diaIni.setTimeInMillis( timestamp );
    }

    public void setDiaFim( long timestamp ) {
        diaFim.setTimeInMillis( timestamp );
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public void setCodigoMateria(int codigoMateria) {
        CodigoMateria = codigoMateria;
    }
}
