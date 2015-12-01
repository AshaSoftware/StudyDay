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
public class EstudoView extends RelativeLayout {

    private final View border;
    private final TextView title, description, interval;
    private Estudo naoEscolar;
    private final ImageView edit, delete;
    private OnCommandListener onCommandListener;

    public EstudoView() {
        this( App.getContext(), null );
    }

    public EstudoView( Context context ) {
        this( context, null );
    }

    public EstudoView( Context context, AttributeSet attrs ) {
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
                    onCommandListener.onClick( EstudoView.this, naoEscolar );
                }
            }
        } );

        edit.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onEdit( EstudoView.this, naoEscolar );
                }
            }
        } );

        delete.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onDelete( EstudoView.this, naoEscolar );
                }
            }
        } );
    }

    public Estudo getNaoEscolar() {
        return naoEscolar;
    }

    public void setNaoEscolar( Estudo naoEscolar ) {
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

        title.setText( "" + naoEscolar.getCodigoMateria() );
        description.setText( naoEscolar.getDescricao() );
        String t1 = App.getDateFormated( naoEscolar.getDiaIni().getTime() );
        interval.setText( t1 );
    }

    public void setOnCommandListener( OnCommandListener onCommandListener ) {
        this.onCommandListener = onCommandListener;
    }

    public interface OnCommandListener {

        void onEdit( View v, Estudo ne );

        void onDelete( View v, Estudo ne );

        void onClick( View v, Estudo ne );
    }
}
