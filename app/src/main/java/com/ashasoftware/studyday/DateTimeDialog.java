package com.ashasoftware.studyday;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by tiago on 14/11/15.
 */
public class DateTimeDialog {

    private final AlertDialog.Builder builder;
    private Calendar calendar;
    private final TextView tab1, tab2;
    private final DatePicker date;
    private final TimePicker time;
    private int mode = 0;
    private OkClickListener okClickListener;

    public DateTimeDialog( final Context context, final Calendar calendar ) {
        this.calendar = calendar;

        if( this.calendar == null ) {
            this.calendar = GregorianCalendar.getInstance();
        }

        //Cria o construtor da janela de dialogo.
        builder = new AlertDialog.Builder( context );
        builder.setTitle( context.getResources().getText( R.string.words_select_a ) );

        //Habilita os botoes.
        builder.setNegativeButton( R.string.words_cancel, null );
        builder.setPositiveButton( R.string.words_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {

                if( okClickListener != null ) {
                    time.clearFocus();
                    okClickListener.okClick( date.getYear(),
                                             date.getMonth(),
                                             date.getDayOfMonth(),
                                             time.getCurrentHour(),
                                             time.getCurrentMinute() );
                }
            }
        } );

        //Infla o layout e adiciona-o.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate( R.layout.layout_datetimepicker, null );
        builder.setView( v );

        DatePicker date = (DatePicker) v.findViewById( R.id.date );
        date.updateDate( calendar.get( Calendar.YEAR ),
                         calendar.get( Calendar.MONTH ),
                         calendar.get( Calendar.DAY_OF_MONTH ) );

        TimePicker time = (TimePicker) v.findViewById( R.id.time );
        time.setCurrentHour( calendar.get( Calendar.HOUR ) );
        time.setCurrentMinute( calendar.get( Calendar.MINUTE ) );

        tab1 = (TextView) v.findViewById( R.id.tab1 );
        tab1.setText( context.getResources().getString( R.string.words_date ) );
        tab1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                DateTimeDialog.this.date.setVisibility( View.VISIBLE );
                DateTimeDialog.this.time.setVisibility( View.GONE );
            }
        } );

        tab2 = (TextView) v.findViewById( R.id.tab2 );
        tab2.setText( context.getResources().getString( R.string.words_time ) );
        tab2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                DateTimeDialog.this.date.setVisibility( View.GONE );
                DateTimeDialog.this.time.setVisibility( View.VISIBLE );
            }
        } );

        this.date = date;
        this.time = time;
    }

    public DateTimeDialog setOkClickListener( OkClickListener okClickListener ) {
        this.okClickListener = okClickListener;
        return this;
    }

    public DateTimeDialog setMode( int mode ) {
        this.mode = mode;

        if( mode == 0 ) {
            tab1.setVisibility( View.VISIBLE );
            tab2.setVisibility( View.VISIBLE );
            tab1.performClick();
        } else if( mode == 1 ) {
            tab1.setVisibility( View.VISIBLE );
            tab2.setVisibility( View.GONE );
            date.setVisibility( View.VISIBLE );
            time.setVisibility( View.GONE );
            tab1.performClick();
        } else if( mode == 2 ) {
            tab2.setVisibility( View.VISIBLE );
            tab1.setVisibility( View.GONE );
            time.setVisibility( View.VISIBLE );
            date.setVisibility( View.GONE );
            tab2.performClick();
        }

        return this;
    }

    public void show() {
        builder.show();
    }

    public interface OkClickListener {

        void okClick( int year, int month, int day, int hour, int minute );
    }
}
