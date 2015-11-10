package com.ashasoftware.studyday;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tiago on 09/11/15.
 */
public class AulaView extends RelativeLayout {

    private final View border;
    private final TextView nome, interval;
    private Aula aula;
    private final ImageView edit, delete;
    private OnCommandListener onCommandListener;

    public AulaView() {
        this( App.getContext(), null );
    }

    public AulaView( Context context ) {
        this( context, null );
    }

    public AulaView( Context context, AttributeSet attrs ) {
        super( context, attrs );

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate( R.layout.layout_aula_list_item, this, false );
        addView( v );

        border = v.findViewById( R.id.aula_color_indicator );
        nome = (TextView) v.findViewById( R.id.aula_nome_materia );
        interval = (TextView) v.findViewById( R.id.aula_interval );
        edit = (ImageView) v.findViewById( R.id.aula_edit );
        delete = (ImageView) v.findViewById( R.id.aula_delete );

        setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onClick( AulaView.this, aula );
                }
            }
        } );

        edit.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onEdit( AulaView.this, aula );
                }
            }
        } );

        delete.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onDelete( AulaView.this, aula );
                }
            }
        } );
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula( Aula aula ) {
        this.aula = aula;

        border.setBackgroundColor( aula.getMateria().getCor() );

        nome.setText( aula.getMateria().getNome() );
        String t1 = App.getDateFormated( aula.getDiaIni().getTime() ) + " ( " + App.getTimeFormated( aula.getDiaIni().getTime() ) + " )";
        String t2 = App.getDateFormated( aula.getDiaFim().getTime() ) + " ( " + App.getTimeFormated( aula.getDiaFim().getTime() ) + " )";
        interval.setText( t1 + "   -   " + t2 );
    }

    public void setOnCommandListener( OnCommandListener onCommandListener ) {
        this.onCommandListener = onCommandListener;
    }

    public interface OnCommandListener {

        void onEdit( View v, Aula ne );

        void onDelete( View v, Aula ne );

        void onClick( View v, Aula ne );
    }
}
