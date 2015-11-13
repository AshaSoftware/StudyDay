package com.ashasoftware.studyday;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tiago on 09/11/15.
 */
public class AulaViewActivity extends AppCompatActivity implements AulaView.OnCommandListener {

    private ExpandableListView list;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        //Define o layout.
        setContentView( R.layout.layout_aula_view_activity );

        //Recupera a barra de ação.
        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null ) {
            actionBar.setTitle( R.string.words_class );
        }

        //Define o evento de clique para o botao flutuante.
        findViewById( R.id.fab ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                new AddOrEditAulaDialog( AulaViewActivity.this, null ).show();
            }
        } );

        //Recupera o controle do ListView.
        list = (ExpandableListView) findViewById( R.id.aula_view_list );

        //Carrega a lista de aulas.
        update();
    }

    private void update() {
        //Carrega a lista de aulas e a exibe.
        HashMap<Integer, List<Aula>> aulas = new HashMap<>();

        Toast.makeText( getBaseContext(), "before", Toast.LENGTH_SHORT ).show();
        for( Aula aula : App.getDatabase().getAllAulas() ) {
            int week = aula.getDia();
            Toast.makeText( getBaseContext(), "after", Toast.LENGTH_SHORT ).show();
            if( !aulas.containsKey( week ) ) aulas.put( week, new ArrayList<Aula>() );
            aulas.get( week ).add( aula );
        }

        list.setAdapter( new AulaViewAdapter( aulas ) );
    }

    @Override
    public void onEdit( View v, Aula aula ) {
        new AddOrEditAulaDialog( this, aula ).show();
    }

    @Override
    public void onDelete( View v, final Aula aula ) {
        //Cria o construtor da janela de dialogo.
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        //Define o titulo e os botoes.
        builder.setTitle( "Delete: " + aula.getMateria().getNome() );
        builder.setNegativeButton( R.string.words_no, null );
        builder.setPositiveButton( R.string.words_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                App.getDatabase().deleteAula( aula.getCodigo() );
                update();
            }
        } );
        //Exibe a janela de dialogo.
        builder.show();
    }

    @Override
    public void onClick( View v, Aula aula ) {

    }

    //"Converte" cada aula em uma view.
    private class AulaViewAdapter extends BaseExpandableListAdapter {

        private HashMap<Integer, List<Aula>> aulas;
        private String[] weeks;

        public AulaViewAdapter( HashMap<Integer, List<Aula>> aulas ) {
            this.aulas = aulas;
            weeks = App.getContext().getResources().getStringArray( R.array.weeks );
        }

        @Override
        public int getGroupCount() {
            return weeks.length;
        }

        @Override
        public int getChildrenCount( int groupPosition ) {
            return aulas.containsKey( groupPosition ) ?
                    aulas.get( groupPosition ).size() :
                    0;
        }

        @Override
        public Object getGroup( int groupPosition ) {
            return weeks[groupPosition];
        }

        @Override
        public Object getChild( int groupPosition, int childPosition ) {
            return aulas.get( groupPosition ).get( childPosition );
        }

        @Override
        public long getGroupId( int groupPosition ) {
            return 0;
        }

        @Override
        public long getChildId( int groupPosition, int childPosition ) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView( int gp, boolean isExpanded, View convertView, ViewGroup parent ) {
            if( convertView == null ) {
                View v = getLayoutInflater().inflate( R.layout.layout_aula_list_group, null );
                ((TextView) v.findViewById( R.id.aula_list_group_header )).setText( weeks[gp].toUpperCase() );
                list.expandGroup( gp );
                return v;
            }

            return convertView;
        }

        @Override
        public View getChildView( int gp, int cp, boolean isLastChild, View convertView, ViewGroup parent ) {
            AulaView av;
            if( convertView == null ) {
                av = new AulaView();
                av.setOnCommandListener( AulaViewActivity.this );
            } else {
                av = (AulaView) convertView;
            }

            av.setAula( aulas.get( gp ).get( cp ) );

            return av;
        }

        @Override
        public boolean isChildSelectable( int groupPosition, int childPosition ) {
            return true;
        }
    }

    public class AddOrEditAulaDialog {

        private final Aula aula;
        private final AlertDialog.Builder builder;
        private final TextView startStatus, endStatus;
        private Spinner spinner, weekdays;
        private Calendar start, end;
        private final HashMap<String, Materia> subjects;

        public AddOrEditAulaDialog( Context context, Aula aula ) {

            this.aula = aula;

            //Cria o construtor da janela de dialogo.
            builder = new AlertDialog.Builder( context );
            //Habilita dois botões.
            builder.setNegativeButton( R.string.words_cancel, null );
            builder.setPositiveButton( R.string.words_ok, ok );

            //Define o titulo da janela.
            builder.setTitle( aula == null ? R.string.words_add_class : R.string.words_edit_class );
            //Infla o conteudo da janela e adiciona-o.
            LayoutInflater li = (LayoutInflater) App.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View v = li.inflate( R.layout.layout_add_aula_dialog, null );
            builder.setView( v );

            //Obtem os controles da janela.
            startStatus = (TextView) v.findViewById( R.id.start_datetime_status );
            endStatus = (TextView) v.findViewById( R.id.end_datetime_status );
            spinner = (Spinner) v.findViewById( R.id.aula_subjects );
            weekdays = (Spinner) v.findViewById( R.id.aula_weekdays );

            subjects = new HashMap<>();
            List<String> adapterValue = new ArrayList<>();
            for( Materia materia : App.getDatabase().getAllMaterias() ) {
                subjects.put( materia.getNome(), materia );
                adapterValue.add( materia.getNome() );
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>( context, android.R.layout.simple_list_item_1, adapterValue );
            adapter.setDropDownViewResource( R.layout.spinner_item_black_white );
            spinner.setAdapter( adapter );

            adapter = new ArrayAdapter<>( context, android.R.layout.simple_list_item_1, getResources().getStringArray( R.array.weeks ) );
            adapter.setDropDownViewResource( R.layout.spinner_item_black_white );
            weekdays.setAdapter( adapter );

            //Modo edição. Preenche os campos.
            if( aula != null ) {
                start = (Calendar) aula.getIni().clone();
                end = (Calendar) aula.getFim().clone();
                //Escreve o texto nos TextViews.
                setDateTime( start, end );
                spinner.setSelection( adapterValue.indexOf( aula.getMateria().getNome() ) );
                weekdays.setSelection( aula.getDia() );
            } //Modo adição.
            else {
                //Recupera a data e hora atual.
                Calendar calendar = Calendar.getInstance();
                calendar.set( Calendar.MILLISECOND, 0 );
                calendar.set( Calendar.SECOND, 0 );
                //Atrubui ao fim a data atual.
                end = calendar;
                //Atribui ao inicio a data atual.
                start = (Calendar) calendar.clone();
                //Acrescenta 1 hora.
                end.add( Calendar.HOUR, 1 );
                //Escreve o texto nos TextViews.
                setDateTime( start, end );
            }

            startStatus.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    new SlideDateTimePicker.Builder( getSupportFragmentManager() )
                            .setListener( startDateTimeListener )
                            .setInitialDate( start.getTime() )
                            .setIs24HourTime( true )
                            .build()
                            .show( 1 );
                }
            } );

            endStatus.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    new SlideDateTimePicker.Builder( getSupportFragmentManager() )
                            .setListener( endDateTimeListener )
                            .setInitialDate( end.getTime() )
                            .setIs24HourTime( true )
                            .build()
                            .show( 1 );
                }
            } );
        }

        private void setDateTime( Calendar start, Calendar end ) {
            startStatus.setText( App.getTimeFormated( start.getTime() ) );
            endStatus.setText( App.getTimeFormated( end.getTime() ) );
        }

        //Exibe a janela de diálogo.
        public void show() {
            if( App.getDatabase().getAllMaterias().size() == 0 ) {
                Toast.makeText( getBaseContext(),
                                getBaseContext().getResources().getString( R.string.words_add_a_new_subject ),
                                Toast.LENGTH_SHORT ).show();
                return;
            }

            builder.show();
        }

        //Evento chamando quando o usuário clicar no botao OK.
        private final DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {

                //O tempo inicial tem que ser menor que o tempo final.
                if( start.getTimeInMillis() >= end.getTimeInMillis() ) {
                    Toast.makeText( App.getContext(),
                                    App.getContext().getResources().getText( R.string.words_invalid_field ),
                                    Toast.LENGTH_SHORT ).show();
                    return;
                }

                if( weekdays.getSelectedItemPosition() == -1 ) {
                    Toast.makeText( App.getContext(),
                                    App.getContext().getResources().getText( R.string.words_invalid_field ),
                                    Toast.LENGTH_SHORT ).show();
                    return;
                }

                //Modo adição. Adiciona a aula ao banco de dados.
                if( aula == null ) {
                    App.getDatabase().addAula( subjects.get( spinner.getSelectedItem() ).getCodigo(),
                                               start.getTimeInMillis(),
                                               end.getTimeInMillis(),
                                               weekdays.getSelectedItemPosition()
                                             );
                } //Modo edição. Edita a aula e atualiza no banco de dados.
                else {
                    aula.setMateria( subjects.get( spinner.getSelectedItem() ) );
                    aula.setIni( start.getTimeInMillis() );
                    aula.setFim( end.getTimeInMillis() );
                    aula.setDia( weekdays.getSelectedItemPosition() );
                    App.getDatabase().updateAula( aula );
                }

                //Recarrega a lista de aulas.
                update();
            }
        };

        //Evento chamado quando o usuario seleciona uma data e hora inicial.
        private final SlideDateTimeListener startDateTimeListener = new SlideDateTimeListener() {
            @Override
            public void onDateTimeSet( Date date ) {
                start.setTimeInMillis( date.getTime() );
                setDateTime( start, end );
            }

            @Override
            public void onDateTimeCancel() {
            }
        };

        //Evento chamado quando o usuario seleciona uma data e hora final.
        private final SlideDateTimeListener endDateTimeListener = new SlideDateTimeListener() {
            @Override
            public void onDateTimeSet( Date date ) {
                end.setTimeInMillis( date.getTime() );
                setDateTime( start, end );
            }

            @Override
            public void onDateTimeCancel() {
            }
        };
    }
}
