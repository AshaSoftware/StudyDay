package com.ashasoftware.studyday;

import java.util.Calendar;

/**
 * Created by natanmorais on 07/11/15.
 */
public class Aula {

    int Codigo;
    Calendar diaIni, diaFim;
    Materia materia;

    public Aula( Materia materia, long pIni, long pFim ) throws Exception {
        if( materia == null ) {
            throw new Exception();
        }

        this.materia = materia;
        diaIni = Calendar.getInstance();
        setDiaIni( pIni );
        diaFim = Calendar.getInstance();
        setDiaFim( pFim );
    }

    public Aula( int codigoMateria, long pIni, long pFim ) throws Exception {
        for( Materia m : App.getDatabase().getAllMaterias() ) {
            if( m.getCodigo() == codigoMateria ) {
                this.materia = m;
                break;
            }
        }

        if( this.materia == null ) {
            throw new Exception();
        }

        diaIni = Calendar.getInstance();
        setDiaIni( pIni );
        diaFim = Calendar.getInstance();
        setDiaFim( pFim );
    }

    public int getCodigo() {
        return Codigo;
    }

    public Materia getMateria() {
        return this.materia;
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

    public void setCodigo( int codigo ) {
        Codigo = codigo;
    }

    public void setMateria( Materia materia ) {
        this.materia = materia;
    }
}
