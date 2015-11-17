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

/**
 * Created by tiago on 17/11/15.
 */
public class Interval extends ArrayList<Interval.IntervalTime> {

    public Interval() {
    }

    public boolean isFree( long ini, long fim ) throws Exception {
        for( IntervalTime it : this ) {
            Log.v( "Tag","Entrou no for" );
            if( ini >= it.getIni() && ini <= it.getFim() || fim >= it.getIni() && fim <= it.getFim() ) {
                Log.v( "Tag","Bateu hora" );
                throw new Exception();
            }
        }
        return true;
    }

    public void add( long ini, long fim ) throws Exception {

        if( isFree( ini, fim ) ) {
            this.add( new IntervalTime( ini, fim ) );
        } else {
            throw new Exception();
        }
    }

    public void add( Aula aula ) {
        Calendar c = Calendar.getInstance();

        while( aula.getDia() != c.get( Calendar.DAY_OF_WEEK ) ) {
            c.add( Calendar.DAY_OF_MONTH, 1 );
        }

        c.set( Calendar.HOUR, aula.getIni().get( Calendar.HOUR ) );
        c.set( Calendar.MINUTE, aula.getIni().get( Calendar.MINUTE ) );

        do {
            for( ; c.get( Calendar.DAY_OF_MONTH ) <= c.getActualMaximum( Calendar.DAY_OF_MONTH ); c.add( Calendar.DAY_OF_MONTH,7 ) ) {
                Log.v( "Tag","Entrou no for " + c.get( Calendar.DAY_OF_MONTH ) + " " + c.get( Calendar.MONTH ) );
                this.add( new IntervalTime(
                        c.getTimeInMillis(),
                        c.getTimeInMillis() + (aula.getFim().getTimeInMillis() - aula.getIni().getTimeInMillis()) ) );
            }
        } while( c.get( Calendar.MONTH ) != 0 );
    }

    public void remove( long ini, long fim ) {
        for( IntervalTime i : this ) {
            if( i.getIni() == ini && i.getFim() == fim ) {
                remove( i );
                break;
            }
        }
    }

    public static Interval open() {
        File file = new File( App.myFolder(), "estudos" );
        if( file.exists() ) {
            try {
                FileInputStream fis = new FileInputStream( file );
                ObjectInputStream ois = new ObjectInputStream( fis );
                Interval i = (Interval) ois.readObject();
                ois.close();
                return i;
            } catch( Exception ignored ) {

            }
        }
        return new Interval();
    }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream( new File( App.myFolder(), "estudos" ) );
            ObjectOutputStream oos = new ObjectOutputStream( fos );
            oos.writeObject( this );
            oos.flush();
            oos.close();
        } catch( Exception ignored ) {
        }
    }

    public class IntervalTime {

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

        public IntervalTime( long ini, long fim ) {
            this.fim = fim;
            this.ini = ini;
        }
    }
}
