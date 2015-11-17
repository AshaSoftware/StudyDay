package com.ashasoftware.studyday;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by tiago on 05/11/15.
 */
public class SubjectViewActivity extends AppCompatActivity implements SubjectView.OnCommandListener {

    private ListView list;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.layout_subject_view_activity );

        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null ) {
            actionBar.setTitle( R.string.words_subjects );
        }

        //Define o evento de clique para o botao flutuante.
        findViewById( R.id.fab ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                new AddOrEditSubjectDialog( SubjectViewActivity.this, null ).show();
            }
        } );

        list = (ListView) findViewById( R.id.subject_view_list );

        //Carrega a lista de matérias.
        update();
    }

    private void update() {
        //Carrega a lista de matérias e a exibe.
        list.setAdapter( new SubjectViewAdapter( App.getDatabase().getAllMaterias() ) );
    }

    @Override
    public void onEdit( View v, Materia m ) {
        new AddOrEditSubjectDialog( this, m ).show();
    }

    @Override
    public void onDelete( View v, final Materia m ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "Delete: " + m.getNome() );
        builder.setNegativeButton( R.string.words_no, null );
        builder.setPositiveButton( R.string.words_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                App.getDatabase().deleteMateria( m.getCodigo() );
                App.getDatabase().deleteAllAulas(m.getCodigo());
                App.getDatabase().deleteAllAvaliacoes(m.getCodigo());
                update();
            }
        } );
        builder.show();
    }

    @Override
    public void onClick( View v, Materia m ) {
        Toast.makeText( getBaseContext(), m.getNome(), Toast.LENGTH_SHORT ).show();
    }

    //"Converte" cada matéria em uma view.
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

            if( convertView == null ) {
                SubjectView sv = new SubjectView();
                sv.setMateria( m );
                sv.setOnCommandListener( SubjectViewActivity.this );
                return sv;
            }

            return convertView;
        }
    }

    public class AddOrEditSubjectDialog implements RatingBar.OnRatingBarChangeListener, AmbilWarnaDialog.OnAmbilWarnaListener {

        private final AlertDialog.Builder builder;
        private final EditText name, teacher;
        private final RatingBar subjectLevel, teacherLevel;
        private final View subjectColorValue;
        private final Materia materia;
        private int subjectColor;

        public AddOrEditSubjectDialog( Context context, Materia materia ) {
            this.materia = materia;

            //Cria o construtor da janela de dialogo.
            builder = new AlertDialog.Builder( context );
            //Habilita dois botões.
            builder.setNegativeButton( R.string.words_cancel, null );
            builder.setPositiveButton( R.string.words_ok, ok );
            //Define o titulo da janela.
            builder.setTitle( materia == null ? R.string.words_add_subject : R.string.words_edit_subject );
            //Infla o conteudo da janela e adiciona-o.
            LayoutInflater li = (LayoutInflater) App.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View v = li.inflate( R.layout.layout_add_subject_dialog, null );
            builder.setView( v );

            //Gera um cor aleatório toda vez que a janela aparecer.
            Random rand = new Random();
            subjectColor = Color.argb( 255,
                                       rand.nextInt( 256 ),
                                       rand.nextInt( 256 ),
                                       rand.nextInt( 256 ) );

            //Obtem os controles da janela.
            name = (EditText) v.findViewById( R.id.subject_name );
            teacher = (EditText) v.findViewById( R.id.prof_name );
            subjectLevel = (RatingBar) v.findViewById( R.id.subject_level );
            teacherLevel = (RatingBar) v.findViewById( R.id.prof_level );
            subjectColorValue = v.findViewById( R.id.subject_color_value );
            subjectLevel.setOnRatingBarChangeListener( this );
            teacherLevel.setOnRatingBarChangeListener( this );
            subjectColorValue.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    new AmbilWarnaDialog( SubjectViewActivity.this, subjectColor, false, AddOrEditSubjectDialog.this ).show();
                }
            } );

            //Está no modo edição. Preenche os campos.
            if( materia != null ) {
                name.setText( materia.getNome() );
                teacher.setText( materia.getProfessor() );
                subjectLevel.setRating( materia.getDifMateria() / 2f );
                teacherLevel.setRating( materia.getDifProfessor() / 2f );
                subjectColor = materia.getCor();
            }

            ((GradientDrawable) subjectColorValue.getBackground()).setColor( subjectColor );
        }

        //Evento chamando quando o usuário clicar no botao OK.
        private final DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {

                //O nome da matéria é requerida.
                if( name.getText().length() == 0 ) {
                    name.requestFocus();
                    Toast.makeText( App.getContext(),
                                    App.getContext().getResources().getText( R.string.words_invalid_field ),
                                    Toast.LENGTH_SHORT ).show();
                    return;
                }

                //Modo adição. Adiciona a matéria ao banco de dados.
                if( materia == null ) {
                    App.getDatabase().addMateria( name.getText().toString(),
                                                  teacher.getText().toString(),
                                                  subjectColor,
                                                  (int) (teacherLevel.getRating() * 2),
                                                  (int) (subjectLevel.getRating() * 2) );
                } //Modo edição. Edita a máteria e atualiza no banco de dados.
                else {
                    materia.setNome( name.getText().toString() );
                    materia.setProfessor( teacher.getText().toString() );
                    materia.setDifMateria( (int) (subjectLevel.getRating() * 2) );
                    materia.setDifProfessor( (int) (teacherLevel.getRating() * 2) );
                    materia.setCor( subjectColor );
                    App.getDatabase().updateMateria( materia );
                }

                //Recarrega a lista de matérias.
                update();
            }
        };

        //Exibe a janela de dialogo.
        public void show() {
            builder.show();
        }

        @Override
        public void onRatingChanged( RatingBar ratingBar, float rating, boolean fromUser ) {
        }

        @Override
        public void onCancel( AmbilWarnaDialog dialog ) {
        }

        @Override
        public void onOk( AmbilWarnaDialog dialog, int color ) {
            subjectColor = color;
            ((GradientDrawable) subjectColorValue.getBackground()).setColor( subjectColor );
        }
    }
}
