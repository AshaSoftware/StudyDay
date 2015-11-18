package com.ashasoftware.studyday;

import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tiago on 17/11/15.
 */
public class Interval{
    int Codigo;

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    private long ini;

    public long getIni() {
        return ini;
    }

    public void setIni( long ini ) {
        this.ini = ini;
    }

    public long getFim() {
        return fim;
    }

    public void setFim( long fim ) {
        this.fim = fim;
    }

    private long fim;

    public Interval( long ini, long fim ) {
        this.fim = fim;
        this.ini = ini;
    }

    public Interval() {
    }

    public boolean isFree( long ini, long fim ) throws Exception {
        Log.v("Tag_isFree", "Entrou na função");
        List<Interval> it = App.getDatabase().getAllIntervals();
        Log.v("Tag_isFree", "Existem " + it.size() + " intervalos no banco");
        for( Interval i : it) {
            Log.v( "Tag_isFree","Entrou no for" );
            Log.v( "Tag_isFree","NE: " + ini + " |Aula: " + i.getIni() );
            if( ini >= i.getIni() && ini <= i.getFim() || fim >= i.getIni() && fim <= i.getFim() || ini <= i.getIni() && fim >= i.getFim()) {
                Log.v( "Tag_isFree","Bateu hora" );
                throw new Exception();
            }
        }
        return true;
    }

    public void add( long ini, long fim ) throws Exception {
        Log.v( "Tag_AddNE","Entrou pra adicionar");
        try{
            Log.v( "Tag_AddNE","Vai tentar");
            isFree(ini, fim);
            Log.v("Tag_AddNE", "Opa! Livre");
            App.getDatabase().addInterval(ini, fim);
        }catch(Exception e){
            Log.v( "Tag_AddNE","Epa! Ocupado");
            throw e;
        }
    }

    public void add( Aula aula ) {
        Calendar c = Calendar.getInstance();

        while( aula.getDia() != c.get( Calendar.DAY_OF_WEEK ) ) {
            c.add( Calendar.DAY_OF_MONTH, 1 );
        }

        c.set( Calendar.HOUR, aula.getIni().get( Calendar.HOUR ) );
        Log.v("Tag_addAula", "Hora: " + c.get(Calendar.HOUR));
        c.set( Calendar.MINUTE, aula.getIni().get( Calendar.MINUTE ) );

        do {
            Log.v( "Tag_AddAula_While","Entrou no while " + c.get( Calendar.DAY_OF_MONTH ) + "/" + c.get( Calendar.MONTH ) + "|  |" + c.getActualMaximum( Calendar.DAY_OF_MONTH ) );
            for(int i=c.get( Calendar.DAY_OF_MONTH ) ; i <= c.getActualMaximum( Calendar.DAY_OF_MONTH ); i+=7 ) {
                Log.v( "Tag_AddAula_For","Entrou no for " + c.get( Calendar.DAY_OF_MONTH ) + " " + c.get( Calendar.MONTH ) );
                App.getDatabase().addInterval(c.getTimeInMillis(), c.getTimeInMillis() + (aula.getFim().getTimeInMillis() - aula.getIni().getTimeInMillis()));
                Log.v("Tag_AddAula", "Diferença: " + (aula.getFim().getTimeInMillis() - aula.getIni().getTimeInMillis())/60000);
                c.add(Calendar.DATE,7);
            }
        } while( c.get( Calendar.MONTH ) != 0 );
    }

    public void remove( long ini, long fim ) {
       for( Interval i : App.getDatabase().getAllIntervals() ) {
            if( i.getIni() == ini && i.getFim() == fim ) {
                App.getDatabase().deleteInterval(i.getCodigo());
                break;
            }
        }
    }
}
