package com.ashasoftware.studyday;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.NumberPicker;
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

    private final static DialogInterface.OnClickListener CANCEL_DIALOG = new DialogInterface.OnClickListener() {
        @Override
        public void onClick( DialogInterface dialog, int which ) {
            dialog.cancel();
        }
    };

    @Override
    public void onClick( View v ) {
        if( v == prevbutton ) {
            //Avança 1 mes.
            calendar.add( Calendar.MONTH, -1 );
            updateCalendar();
        } else if( v == nextbutton ) {
            //Retroce 1 mes.
            calendar.add( Calendar.MONTH, 1 );
            updateCalendar();
        } else if( v == monthtextview ) {
            AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );

            builder.setTitle( R.string.alert_dialog_month );
            builder.setCancelable( true );
            final NumberPicker numberPicker = new NumberPicker( getContext() );
            numberPicker.setMinValue( 1 );
            numberPicker.setMaxValue( 12 );
            numberPicker.setValue( calendar.get( Calendar.MONTH ) + 1 );
            builder.setView( numberPicker );
            builder.setPositiveButton( R.string.words_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick( DialogInterface dialog, int which ) {
                    calendar.set( Calendar.MONTH, numberPicker.getValue() - 1 );
                    updateCalendar();
                }
            } );

            builder.setNegativeButton( R.string.words_cancel, CANCEL_DIALOG );
            builder.create().show();
        } else if( v == yeatextview ) {
            AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );

            builder.setTitle( R.string.alert_dialog_year );
            builder.setCancelable( true );
            final NumberPicker numberPicker = new NumberPicker( getContext() );
            Calendar now = Calendar.getInstance();
            numberPicker.setMinValue( now.get( Calendar.YEAR ) - 10 );
            numberPicker.setMaxValue( now.get( Calendar.YEAR ) + 10 );
            numberPicker.setValue( calendar.get( Calendar.YEAR ) );
            builder.setView( numberPicker );
            builder.setPositiveButton( R.string.words_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick( DialogInterface dialog, int which ) {
                    calendar.set( Calendar.YEAR, numberPicker.getValue() );
                    updateCalendar();
                }
            } );

            builder.setNegativeButton( R.string.words_cancel, CANCEL_DIALOG );
            builder.create().show();
        }
    }

    private void updateCalendar() {
        calendarGridView.setAdapter( new DateViewAdapter( calendar.get( Calendar.YEAR ), calendar.get( Calendar.MONTH ) ) );
        updateShowCurrentDate();
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
            super.setTextSize( TypedValue.COMPLEX_UNIT_DIP, 16 );

            //Define o estilo da borda.
            BORDER_STYLE.setColor( Color.LTGRAY );
            BORDER_STYLE.setStrokeWidth( 1f );
            BORDER_STYLE.setStyle( Paint.Style.STROKE );
        }

        public Date getDate() {
            return date;
        }

        Drawable pencil = getResources().getDrawable( R.drawable.logo_pencil );
        Drawable book = getResources().getDrawable( R.drawable.logo_book );
        Drawable ball = getResources().getDrawable( R.drawable.logo_ball );
        Paint numberColor = new Paint( Paint.ANTI_ALIAS_FLAG );

        @Override
        protected void onDraw( Canvas canvas ) {
            super.onDraw( canvas );
            //Desenha a borda.
            canvas.drawRect( 0, 0, getWidth(), getHeight(), BORDER_STYLE );

            int count = 0;
            int offset = 0;

            for( Estudo estudo : App.getDatabase().getAllEstudos() ) {
                if( date.getTime() / 86400000 == estudo.getDiaIni().getTimeInMillis() / 86400000 ) {
                    count++;
                }
            }

            if( count > 0 ) {
                desenharIcone( canvas, book, count, offset );
                offset += 40;
            }

            count = 0;

            for( NaoEscolar ne : App.getDatabase().getAllNaoEscolares() ) {
                if( date.getTime() / 86400000 == ne.getDiaIni().getTimeInMillis() / 86400000 ) {
                    count++;
                }
            }

            if( count > 0 ) {
                desenharIcone( canvas, ball, count, offset );
                offset += 40;
            }

            count = 0;

            for( Aula aula : App.getDatabase().getAllAulas() ) {
                Log.v("Tag_Calendar", "Size Aulas:" + App.getDatabase().getAllAulas().size());
                if( date.getDay() == aula.getDia() ) {
                    count++;
                }
            }

            if( count > 0 ) {
                desenharIcone( canvas, pencil, count, offset );
                offset += 40;
            }
        }

        private void desenharIcone( Canvas canvas, Drawable icon, int count, int offset ) {
            numberColor.setColor( Color.RED );
            numberColor.setStyle( Paint.Style.FILL );

            icon.setBounds( getWidth() - 42 - offset, 6, getWidth() - 6 - offset, 42 );
            icon.draw( canvas );
            canvas.drawCircle( getWidth() - 12 - offset, 12, 10, numberColor );

            numberColor.setColor( Color.WHITE );
            numberColor.setTextAlign( Paint.Align.CENTER );
            numberColor.setTextSize( 13 );
            canvas.drawText( String.valueOf( count ), getWidth() - 12 - offset, 16, numberColor );
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
