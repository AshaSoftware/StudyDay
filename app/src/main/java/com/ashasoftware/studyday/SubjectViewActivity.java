package com.ashasoftware.studyday;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

/**
 * Created by tiago on 05/11/15.
 */
public class SubjectViewActivity extends AppCompatActivity implements SubjectView.OnCommandListener {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.layout_subject_view_activity );

        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null ) {
            actionBar.setTitle( R.string.words_subjects );
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Toast.makeText( SubjectViewActivity.this, "12345", Toast.LENGTH_SHORT ).show();
            }
        } );

        ListView lv = (ListView) findViewById( R.id.subject_view_list );
        lv.setAdapter( new SubjectViewAdapter( App.getDatabase().getAllMaterias() ) );
    }

    @Override
    public void onEdit( View v, Materia m ) {

    }

    @Override
    public void onDelete( View v, Materia m ) {

    }

    @Override
    public void onClick( View v, Materia m ) {
        Toast.makeText( getBaseContext(), m.getNome(), Toast.LENGTH_SHORT ).show();
    }

    private class SubjectViewAdapter extends BaseAdapter {

        private List<Materia> materias;

        public SubjectViewAdapter( List<Materia> materias ) {
            this.materias = materias;
        }

        @Override
        public int getCount() {
            return materias.size();
        }

        @Override
        public Object getItem( int position ) {
            return null;
        }

        @Override
        public long getItemId( int position ) {
            return position;
        }

        @Override
        public View getView( int position, View convertView, ViewGroup parent ) {
            Materia m = materias.get( position );
            LayoutInflater inflater = getLayoutInflater();

            if( convertView == null ) {
                SubjectView sv = new SubjectView();
                sv.setMateria( m );
                sv.setOnCommandListener( SubjectViewActivity.this );
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, 150 );

                sv.setLayoutParams( lp );

                convertView = sv;
            }

            return convertView;
        }
    }
}
