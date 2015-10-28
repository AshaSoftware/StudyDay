package com.ashasoftware.studyday;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by tiago on 27/10/15.
 */
public class CalendarView extends FrameLayout implements View.OnClickListener {

    //São as views utilizadas para exibir o nome do mes e ano, na parte superior do calendario.
    private final TextView monthtextview, yeatextview;
    //Contem a data atual exibida pelo calendario.
    private Calendar calendar;
    //São os botoes utilizados para avançar ou retroceder os meses.
    private final ImageView prevbutton, nextbutton;

    public CalendarView( Context context ) {
        this( context, null );
    }

    public CalendarView( Context context, AttributeSet attrs ) {
        super( context, attrs );

        //Infla uma view a partir do layout.
        View v = inflate( context, R.layout.layout_calendar_view, null );

        //Adiciona a view.
        addView( v );

        //Recupera a data atual.
        calendar = GregorianCalendar.getInstance();

        //Recupera todos os controles.
        monthtextview = (TextView) v.findViewById( R.id.month_value );
        yeatextview = (TextView) v.findViewById( R.id.year_value );
        prevbutton = (ImageView) v.findViewById( R.id.prev_button );
        nextbutton = (ImageView) v.findViewById( R.id.next_button );

        //Atualiza a exibicao do mes e ano atual na parte superior do calendario.
        updateShowCurrentDate();

        //Adiciona os eventos.
        monthtextview.setOnClickListener( this );
        yeatextview.setOnClickListener( this );
        prevbutton.setOnClickListener( this );
        nextbutton.setOnClickListener( this );
    }

    public void setDate( int year, int month, int day ) {
        if( year >= 1970 ) calendar.set( Calendar.YEAR, 1970 );
        if( month >= 0 && month < 12 ) calendar.set( Calendar.MONTH, month );
        if( day >= 1 && day <= 31 ) calendar.set( Calendar.DATE, day );
    }

    @Override
    public void onClick( View v ) {
        if( v == prevbutton ) {
            //Avança 1 mes.
            calendar.add( Calendar.MONTH, -1 );
            updateShowCurrentDate();
        } else if( v == nextbutton ) {
            //Retroce 1 mes.
            calendar.add( Calendar.MONTH, 1 );
            updateShowCurrentDate();
        }
    }

    private void updateShowCurrentDate() {
        SimpleDateFormat ydate = new SimpleDateFormat( getResources().getString( R.string.year_pattern ) );

        monthtextview.setText( getResources().getTextArray( R.array.months )[calendar.get( Calendar.MONTH )] );
        yeatextview.setText( ydate.format( calendar.getTime() ) );
    }
}
