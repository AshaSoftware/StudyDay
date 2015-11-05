package com.ashasoftware.studyday;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tiago on 05/11/15.
 */
public class SubjectView extends RelativeLayout {

    private final TextView title, teacher;
    private final View border;
    private final ImageView edit, delete;
    private Materia materia;
    private OnCommandListener onCommandListener;

    public SubjectView() {
        this( App.getContext(), null );
    }

    public SubjectView( Context context ) {
        this( context, null );
    }

    public SubjectView( Context context, AttributeSet attrs ) {
        super( context, attrs );

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate( R.layout.layout_subject_item, this, false );
        addView( v );

        border = v.findViewById( R.id.subject_color_indicator );
        title = (TextView) v.findViewById( R.id.subject_title );
        teacher = (TextView) v.findViewById( R.id.subject_teacher );
        edit = (ImageView) v.findViewById( R.id.subject_edit );
        delete = (ImageView) v.findViewById( R.id.subject_delete );

        setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onClick( SubjectView.this, materia );
                }
            }
        } );

        edit.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onEdit( SubjectView.this, materia );
                }
            }
        } );

        delete.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( onCommandListener != null ) {
                    onCommandListener.onDelete( SubjectView.this, materia );
                }
            }
        } );
    }

    public void setMateria( Materia materia ) {
        this.materia = materia;
        border.setBackgroundColor( materia.getCor() );
        title.setText( materia.getNome() );
        teacher.setText( getResources().getText( R.string.words_teacher ) + ": " + materia.getProfessor() );

    }

    public void setOnCommandListener( OnCommandListener onCommandListener ) {
        this.onCommandListener = onCommandListener;
    }

    public interface OnCommandListener {

        void onEdit( View v, Materia m );

        void onDelete( View v, Materia m );

        void onClick( View v, Materia m );
    }
}
