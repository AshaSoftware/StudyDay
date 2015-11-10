package com.ashasoftware.studyday;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tiago on 10/11/15.
 */
public class AvaliacaoView extends RelativeLayout {

    private final View border;
    private final TextView nome, desc, nota;
    private Avaliacao avaliacao;
    private final ImageView edit, delete;
    private OnCommandListener onCommandListener;

    public AvaliacaoView() {
        this( App.getContext(), null );
    }

    public AvaliacaoView( Context context ) {
        this( context, null );
    }

    public AvaliacaoView( Context context, AttributeSet attrs ) {
        super( context, attrs );

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate( R.layout.layout_avaliacao_list_item, this, false );
        addView( v );

        border = v.findViewById( R.id.avaliacao_color_indicator );
        nome = (TextView) v.findViewById( R.id.avaliacao_nome );
        desc = (TextView) v.findViewById( R.id.avaliacao_desc );
        nota = (TextView) v.findViewById( R.id.avaliacao_nota );
        edit = (ImageView) v.findViewById( R.id.avaliacao_edit );
        delete = (ImageView) v.findViewById( R.id.avaliacao_delete );

        setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onClick( AvaliacaoView.this, avaliacao );
                }
            }
        } );

        edit.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onEdit( AvaliacaoView.this, avaliacao );
                }
            }
        } );

        delete.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onDelete( AvaliacaoView.this, avaliacao );
                }
            }
        } );
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao( Avaliacao avaliacao ) {
        this.avaliacao = avaliacao;

        border.setBackgroundColor( avaliacao.getMateria().getCor() );
        nome.setText( avaliacao.getNome() + " (" + avaliacao.getMateria().getNome() + ")" );
        desc.setText( avaliacao.getDescricao() );
        nota.setText( String.valueOf( avaliacao.getNota() ) );
    }

    public void setOnCommandListener( OnCommandListener onCommandListener ) {
        this.onCommandListener = onCommandListener;
    }

    public interface OnCommandListener {

        void onEdit( View v, Avaliacao avaliacao );

        void onDelete( View v, Avaliacao avaliacao );

        void onClick( View v, Avaliacao avaliacao );
    }
}
