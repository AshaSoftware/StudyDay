package com.ashasoftware.studyday;

import java.util.Calendar;

/**
 * Created by natanmorais on 07/11/15.
 */
public class Aula {

    int Codigo;
    Calendar ini, fim;
    int dia;
    Materia materia;

    public Aula() {}

    public Aula( Materia materia, long pIni, long pFim, int dia ) throws Exception {
        if( materia == null ) {
            throw new Exception();
        }

        this.materia = materia;
        ini = Calendar.getInstance();
        setIni( pIni );
        fim = Calendar.getInstance();
        setFim( pFim );
        this.dia = dia;
    }

    public Aula( int codigoMateria, long pIni, long pFim, int dia ) throws Exception {
        for( Materia m : App.getDatabase().getAllMaterias() ) {
            if( m.getCodigo() == codigoMateria ) {
                this.materia = m;
                break;
            }
        }

        if( this.materia == null ) {
            throw new Exception();
        }

        ini = Calendar.getInstance();
        setIni( pIni );
        fim = Calendar.getInstance();
        setFim( pFim );
        this.dia = dia;
    }

    public int getCodigo() {
        return Codigo;
    }

    public Materia getMateria() {
        return this.materia;
    }

    public Calendar getIni() {
        return ini;
    }

    public Calendar getFim() {
        return fim;
    }

    public void setIni( long timestamp ) {
        ini.setTimeInMillis( timestamp );
    }

    public void setFim( long timestamp ) {
        fim.setTimeInMillis( timestamp );
    }

    public void setCodigo( int codigo ) {
        Codigo = codigo;
    }

    public void setMateria( Materia materia ) {
        this.materia = materia;
    }

    public int getDia() {
        return dia;
    }

    public void setDia( int dia ) {
        this.dia = dia;
    }

    public String dayToString (int dia) {
        switch (dia){
            case 0:
                return "Sunday";
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            default:
                return "Invalid";

        }
    }
}
