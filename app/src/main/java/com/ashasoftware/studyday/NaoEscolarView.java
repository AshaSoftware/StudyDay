package com.ashasoftware.studyday;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by tiago on 06/11/15.
 */
public class NaoEscolarView extends RelativeLayout {

    private final View border;
    private final TextView title, description, interval;
    private NaoEscolar naoEscolar;
    private final ImageView edit, delete;
    private OnCommandListener onCommandListener;

    public NaoEscolarView() {
        this( App.getContext(), null );
    }

    public NaoEscolarView( Context context ) {
        this( context, null );
    }

    public NaoEscolarView( Context context, AttributeSet attrs ) {
        super( context, attrs );

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate( R.layout.layout_naoescolar_item, this, false );
        addView( v );

        border = v.findViewById( R.id.naoescolar_color_indicator );
        title = (TextView) v.findViewById( R.id.naoescolar_title );
        description = (TextView) v.findViewById( R.id.naoescolar_desc );
        interval = (TextView) v.findViewById( R.id.naoescolar_interval );
        edit = (ImageView) v.findViewById( R.id.naoescolar_edit );
        delete = (ImageView) v.findViewById( R.id.naoescolar_delete );

        setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onClick( NaoEscolarView.this, naoEscolar );
                }
            }
        } );

        edit.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onEdit( NaoEscolarView.this, naoEscolar );
                }
            }
        } );

        delete.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onDelete( NaoEscolarView.this, naoEscolar );
                }
            }
        } );
    }

    public NaoEscolar getNaoEscolar() {
        return naoEscolar;
    }

    public void setNaoEscolar( NaoEscolar naoEscolar ) {
        this.naoEscolar = naoEscolar;

        long time_now = Calendar.getInstance().getTimeInMillis();
        long time_start = naoEscolar.getDiaIni().getTimeInMillis();
        long time_end = naoEscolar.getDiaFim().getTimeInMillis();

        if( time_now < time_start )
            border.setBackgroundColor( getResources().getColor( R.color.task_not_started ) );
        else if( time_now > time_end )
            border.setBackgroundColor( getResources().getColor( R.color.task_completed ) );
        else
            border.setBackgroundColor( getResources().getColor( R.color.task_in_progress ) );

        title.setText( naoEscolar.getNome() );
        description.setText( naoEscolar.getDescricao() );
        String t1 = App.getDateFormated( naoEscolar.getDiaIni().getTime() ) + " ( " + App.getTimeFormated( naoEscolar.getDiaIni().getTime() ) + " )";
        String t2 = App.getDateFormated( naoEscolar.getDiaFim().getTime() ) + " ( " + App.getTimeFormated( naoEscolar.getDiaFim().getTime() ) + " )";
        interval.setText( t1 + "   -   " + t2 );
    }

    public void setOnCommandListener( OnCommandListener onCommandListener ) {
        this.onCommandListener = onCommandListener;
    }

    public interface OnCommandListener {

        void onEdit( View v, NaoEscolar ne );

        void onDelete( View v, NaoEscolar ne );

        void onClick( View v, NaoEscolar ne );
    }
}
