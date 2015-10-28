package com.ashasoftware.studyday;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    //View utilizada para exibir os dias.
    private GridView calendarGridView;

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
        calendarGridView = (GridView) v.findViewById( R.id.calendar );

        //Atualiza a exibicao do mes e ano atual na parte superior do calendario.
        updateShowCurrentDate();

        //Adiciona os eventos.
        monthtextview.setOnClickListener( this );
        yeatextview.setOnClickListener( this );
        prevbutton.setOnClickListener( this );
        nextbutton.setOnClickListener( this );

        //Preenche o calendario com os dias.
        calendarGridView.setAdapter( new DateViewAdapter() );
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
            calendarGridView.setAdapter( new DateViewAdapter( calendar.get( Calendar.YEAR ), calendar.get( Calendar.MONTH ) ) );
            updateShowCurrentDate();
        } else if( v == nextbutton ) {
            //Retroce 1 mes.
            calendar.add( Calendar.MONTH, 1 );
            calendarGridView.setAdapter( new DateViewAdapter( calendar.get( Calendar.YEAR ), calendar.get( Calendar.MONTH ) ) );
            updateShowCurrentDate();
        }
    }

    private void updateShowCurrentDate() {
        SimpleDateFormat ydate = new SimpleDateFormat( getResources().getString( R.string.year_pattern ) );

        monthtextview.setText( getResources().getTextArray( R.array.months )[calendar.get( Calendar.MONTH )] );
        yeatextview.setText( ydate.format( calendar.getTime() ) );
    }

    private class DateView extends TextView {

        //Cria a borda.
        private final Paint BORDER_STYLE = new Paint( Paint.ANTI_ALIAS_FLAG );
        private final Date date;

        public DateView( Date date ) {
            super( CalendarView.this.getContext() );

            this.date = date;

            Date today = new Date();

            if( today.getYear() == date.getYear() && today.getMonth() == date.getMonth() &&
                today.getDate() == date.getDate() ) {
                super.setTextColor( Color.WHITE );
                super.setBackgroundColor( Color.argb( 255, 49, 167, 210 ) );
            } else if( date.getMonth() != calendar.get( Calendar.MONTH ) ) {
                super.setTextColor( Color.LTGRAY );
            }

            super.setGravity( Gravity.CENTER );
            super.setText( String.valueOf( date.getDate() ) );

            //Define o estilo da borda.
            BORDER_STYLE.setColor( Color.LTGRAY );
            BORDER_STYLE.setStrokeWidth( 1f );
            BORDER_STYLE.setStyle( Paint.Style.STROKE );
        }

        public Date getDate() {
            return date;
        }

        @Override
        protected void onDraw( Canvas canvas ) {
            super.onDraw( canvas );
            //Desenha a borda.
            canvas.drawRect( 0, 0, getWidth(), getHeight(), BORDER_STYLE );
        }
    }

    private class DateViewAdapter extends BaseAdapter {

        private final Calendar calendar;
        private final ArrayList<View> dates = new ArrayList<>();
        private final int totalOfWeeks;

        public DateViewAdapter( int year, int month ) {
            calendar = new GregorianCalendar( year, month, 1 );

            totalOfWeeks = calendar.getActualMaximum( Calendar.WEEK_OF_MONTH );
            final int totalOfDays = totalOfWeeks * 7;

            calendar.add( Calendar.DATE, -calendar.get( Calendar.DAY_OF_WEEK ) + 1 );

            for( int i = 0; i < totalOfDays; i++ ) {
                dates.add( new DateView( calendar.getTime() ) );
                calendar.add( Calendar.DATE, 1 );
            }
        }

        public DateViewAdapter() {
            this( Calendar.getInstance().get( Calendar.YEAR ),
                  Calendar.getInstance().get( Calendar.MONTH ) );
        }

        @Override
        public int getCount() {
            return dates.size();
        }

        @Override
        public Object getItem( int position ) {
            return null;
        }

        @Override
        public long getItemId( int position ) {
            return 0;
        }

        @Override
        public View getView( int position, View convertView, ViewGroup parent ) {
            View v = dates.get( position );
            v.setLayoutParams( new GridView.LayoutParams( GridView.LayoutParams.MATCH_PARENT, calendarGridView.getHeight() / totalOfWeeks ) );
            return v;
        }
    }
}
