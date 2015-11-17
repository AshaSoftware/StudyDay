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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * Created by tiago on 06/11/15.
 */
public class EstudoViewActivity extends AppCompatActivity implements NaoEscolarView.OnCommandListener {

    private ListView list;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        //Define o layout.
        setContentView( R.layout.layout_naoescolar_view_activity );

        //Recupera a barra de ação.
        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null ) {
            actionBar.setTitle( R.string.words_non_school );
        }

        //Define o evento de clique para o botao flutuante.
        findViewById( R.id.fab ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                new AddOrEditNaoEscolarDialog( EstudoViewActivity.this, null ).show();
            }
        } );

        //Recupera o controle do ListView.
        list = (ListView) findViewById( R.id.naoescolar_view_list );

        //Carrega a lista de atividades não-escolares.
        update();
    }

    private void update() {
        Toast.makeText( App.getContext(), "" + App.getDatabase().getAllEstudos().size(), Toast.LENGTH_SHORT ).show();
        //Carrega a lista de atividades não-escolares e a exibe.
        list.setAdapter( new EstudoViewAdapter( App.getDatabase().getAllEstudos() ) );
    }

    @Override
    public void onEdit( View v, NaoEscolar ne ) {
        //new AddOrEditNaoEscolarDialog( this, ne ).show();
    }

    @Override
    public void onDelete( View v, final NaoEscolar ne ) {
        //Cria o construtor da janela de dialogo.
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        //Define o titulo e os botoes.
        builder.setTitle( "Delete: " + ne.getNome() );
        builder.setNegativeButton( R.string.words_no, null );
        builder.setPositiveButton( R.string.words_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                App.getDatabase().deleteNaoEscolar( ne.getCodigo() );
                update();
            }
        } );
        //Exibe a janela de dialogo.
        builder.show();
    }

    @Override
    public void onClick( View v, NaoEscolar ne ) {

    }

    //"Converte" cada atividade não-escolar em uma view.
    private class EstudoViewAdapter extends BaseAdapter {

        private List<Estudo> naoEscolares;

        public EstudoViewAdapter( List<Estudo> naoEscolares ) {
            this.naoEscolares = naoEscolares;
        }

        @Override
        public int getCount() {
            return naoEscolares.size();
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
            Estudo ne = naoEscolares.get( position );

            if( convertView == null ) {
                EstudoView nev = new EstudoView();
                nev.setNaoEscolar( ne );
                return nev;
            }

            return convertView;
        }
    }

    public class AddOrEditNaoEscolarDialog {

        private final Estudo naoEscolar;
        private final AlertDialog.Builder builder;
        private final EditText title, description;
        private final TextView startStatus, endStatus;
        private Calendar start, end;

        public AddOrEditNaoEscolarDialog( Context context, Estudo naoEscolar ) {
            this.naoEscolar = naoEscolar;

            //Cria o construtor da janela de dialogo.
            builder = new AlertDialog.Builder( context );
            //Habilita dois botões.
            builder.setNegativeButton( R.string.words_cancel, null );
            builder.setPositiveButton( R.string.words_ok, ok );

            //Define o titulo da janela.
            builder.setTitle( naoEscolar == null ? R.string.words_add_naoescolar : R.string.words_edit_naoescolar );
            //Infla o conteudo da janela e adiciona-o.
            LayoutInflater li = (LayoutInflater) App.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View v = li.inflate( R.layout.layout_add_naoescolar_dialog, null );
            builder.setView( v );

            //Obtem os controles da janela.
            title = (EditText) v.findViewById( R.id.naoescolar_title );
            description = (EditText) v.findViewById( R.id.naoescolar_desc );
            startStatus = (TextView) v.findViewById( R.id.start_datetime_status );
            endStatus = (TextView) v.findViewById( R.id.end_datetime_status );

            //Modo edição. Preenche os campos.
            if( naoEscolar != null ) {
                title.setText( "" + naoEscolar.getCodigoMateria() );
                description.setText( naoEscolar.getDescricao() );
                start = (Calendar) naoEscolar.getDiaIni().clone();
                end = (Calendar) naoEscolar.getDiaFim().clone();
                //Escreve o texto nos TextViews.
                setDateTime( start, end );
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
        }

        private void setDateTime( Calendar start, Calendar end ) {
            startStatus.setText( App.getDateFormated( start.getTime() ) + " - " +
                                 App.getTimeFormated( start.getTime() ) );
            endStatus.setText( App.getDateFormated( end.getTime() ) + " - " +
                               App.getTimeFormated( end.getTime() ) );
        }

        //Exibe a janela de diálogo.
        public void show() {
            builder.show();
        }

        //Evento chamando quando o usuário clicar no botao OK.
        private final DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {

                //Recarrega a lista de atividades não-escolares.
                update();
            }
        };
    }
}
