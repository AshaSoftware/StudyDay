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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tiago on 09/11/15.
 */
public class AvaliacaoViewActivity extends AppCompatActivity implements AvaliacaoView.OnCommandListener {

    private ListView list;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        //Define o layout.
        setContentView( R.layout.layout_avaliacao_view_activity );

        //Recupera a barra de ação.
        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null ) {
            actionBar.setTitle( R.string.words_exam );
        }

        //Define o evento de clique para o botao flutuante.
        findViewById( R.id.fab ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                new AddOrEditAvaliacaoDialog( AvaliacaoViewActivity.this, null ).show();
            }
        } );

        //Recupera o controle do ListView.
        list = (ListView) findViewById( R.id.avaliacao_view_list );

        //Carrega a lista de avaliacoes.
        update();
    }

    private void update() {
        list.setAdapter( new AvaliacaoViewAdapter( App.getDatabase().getAllAvaliacoes() ) );
    }

    @Override
    public void onEdit( View v, Avaliacao avaliacao ) {
        new AddOrEditAvaliacaoDialog( this, avaliacao ).show();
    }

    @Override
    public void onDelete( View v, final Avaliacao avaliacao ) {
        //Cria o construtor da janela de dialogo.
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        //Define o titulo e os botoes.
        builder.setTitle( "Delete: " + avaliacao.getNome() );
        builder.setNegativeButton( R.string.words_no, null );
        builder.setPositiveButton( R.string.words_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                App.getDatabase().deleteAvaliacao( avaliacao.getCodigo() );
                update();
            }
        } );
        //Exibe a janela de dialogo.
        builder.show();
    }

    @Override
    public void onClick( View v, Avaliacao avaliacao ) {

    }

    //"Converte" cada avaliacao em uma view.
    private class AvaliacaoViewAdapter extends BaseAdapter {

        private List<Avaliacao> avaliacoes;

        public AvaliacaoViewAdapter( List<Avaliacao> avaliacoes ) {
            this.avaliacoes = avaliacoes;
        }

        @Override
        public int getCount() {
            return avaliacoes.size();
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
            AvaliacaoView av;

            if( convertView == null ) {
                av = new AvaliacaoView();
                av.setOnCommandListener( AvaliacaoViewActivity.this );
            } else {
                av = (AvaliacaoView) convertView;
            }

            av.setAvaliacao( avaliacoes.get( position ) );

            return av;
        }
    }

    public class AddOrEditAvaliacaoDialog {

        private final Avaliacao avaliacao;
        private final AlertDialog.Builder builder;
        private Spinner spinner;
        private final EditText nome, desc, nota, peso;
        private final HashMap<String, Materia> subjects;

        public AddOrEditAvaliacaoDialog( Context context, Avaliacao avaliacao ) {

            this.avaliacao = avaliacao;

            //Cria o construtor da janela de dialogo.
            builder = new AlertDialog.Builder( context );
            //Habilita dois botões.
            builder.setNegativeButton( R.string.words_cancel, null );
            builder.setPositiveButton( R.string.words_ok, ok );

            //Define o titulo da janela.
            builder.setTitle( avaliacao == null ? R.string.words_add_exam : R.string.words_edit_exam );
            //Infla o conteudo da janela e adiciona-o.
            LayoutInflater li = (LayoutInflater) App.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View v = li.inflate( R.layout.layout_add_avaliacao_dialog, null );
            builder.setView( v );

            //Obtem os controles da janela.
            spinner = (Spinner) v.findViewById( R.id.avaliacao_subjects );
            nome = (EditText) v.findViewById( R.id.avaliacao_nome );
            desc = (EditText) v.findViewById( R.id.avaliacao_desc );
            nota = (EditText) v.findViewById( R.id.avaliacao_nota );
            peso = (EditText) v.findViewById( R.id.avaliacao_peso );

            subjects = new HashMap<>();
            List<String> adapterValue = new ArrayList<>();
            for( Materia materia : App.getDatabase().getAllMaterias() ) {
                subjects.put( materia.getNome(), materia );
                adapterValue.add( materia.getNome() );
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>( context, android.R.layout.simple_list_item_1, adapterValue );
            adapter.setDropDownViewResource( R.layout.spinner_item_black_white );
            spinner.setAdapter( adapter );

            //Modo edição. Preenche os campos.
            if( avaliacao != null ) {
                spinner.setSelection( adapterValue.indexOf( avaliacao.getMateria().getNome() ) );
                nome.setText( avaliacao.getNome() );
                desc.setText( avaliacao.getDescricao() );
                nota.setText( String.valueOf( avaliacao.getNota() ) );
                peso.setText( String.valueOf( avaliacao.getPeso() ) );
            }
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
                int notaValor, pesoValor;

                try {
                    notaValor = Integer.parseInt( nota.getText().toString() );
                    pesoValor = Integer.parseInt( peso.getText().toString() );
                } catch( Exception e ) {
                    Toast.makeText( App.getContext(),
                                    App.getContext().getResources().getText( R.string.words_invalid_field ),
                                    Toast.LENGTH_SHORT ).show();
                    return;
                }

                if( nome.getText().length() == 0 ||
                    notaValor < 0 || notaValor > 100 ||
                    pesoValor < 0 || pesoValor > 100 ) {
                    Toast.makeText( App.getContext(),
                                    App.getContext().getResources().getText( R.string.words_invalid_field ),
                                    Toast.LENGTH_SHORT ).show();
                    return;
                }

                //Modo adição. Adiciona a avaliacao ao banco de dados.
                if( avaliacao == null ) {
                    App.getDatabase().addAvaliacao( subjects.get( spinner.getSelectedItem() ).getCodigo(),
                                                    nome.getText().toString(),
                                                    desc.getText().toString(),
                                                    notaValor,
                                                    pesoValor );
                } //Modo edição. Edita a avaliacao e atualiza no banco de dados.
                else {
                    avaliacao.setMateria( subjects.get( spinner.getSelectedItem() ) );
                    avaliacao.setNome( nome.getText().toString() );
                    avaliacao.setDescricao( desc.getText().toString() );
                    avaliacao.setNota( notaValor );
                    avaliacao.setPeso( pesoValor );
                    App.getDatabase().updateAvaliacao( avaliacao );
                }

                //Recarrega a lista de avaliacoes.
                update();
            }
        };
    }
}
