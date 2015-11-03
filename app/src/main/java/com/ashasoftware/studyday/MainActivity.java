package com.ashasoftware.studyday;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteHelper db;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        db = new SQLiteHelper( this );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate( R.menu.menu_main, menu );
        return true;
    }

    private int[] code_d = new int[]{ 1, 1 };

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {
            case R.id.menu_insert_materia:
                db.addMateria( new Materia(
                        1,
                        "EDO",
                        "Medina",
                        Color.RED,
                        10,
                        10
                ) );
                return true;

            case R.id.menu_delete_materia:
                db.deleteMateria( new Materia(
                        code_d[0]++,
                        "EDO",
                        "Medina",
                        Color.RED,
                        10,
                        10
                ) );
                return true;

            case R.id.menu_find_materia:
                for( Materia m : db.getAllMaterias() ) {
                    Toast.makeText( this, m.getCodigo() + " - " + m.getNome(), Toast.LENGTH_SHORT ).show();
                }
                return true;

            case R.id.menu_update_materia:
                db.updateMateria( new Materia(
                        1,
                        "EDO",
                        "Gisele",
                        Color.RED,
                        0,
                        10
                ) );
                return true;

            case R.id.menu_insert_ne:
                db.addNaoEscolar( new NaoEscolar(
                        1,
                        "Passear com o cachorro",
                        "",
                        101010,
                        101012
                ) );
                return true;

            case R.id.menu_delete_ne:
                db.deleteNaoEscolar( new NaoEscolar(
                        code_d[1]++,
                        "Passear com o cachorro",
                        "",
                        101010,
                        101012
                ) );
                return true;

            case R.id.menu_find_ne:
                for( NaoEscolar ne : db.getAllNaoEscolares() ) {
                    Toast.makeText( this, ne.getCodigo() + " - " + ne.getNome(), Toast.LENGTH_SHORT ).show();
                }
                return true;

            case R.id.menu_update_ne:
                db.updateNaoEscolar( new NaoEscolar(
                        1,
                        "Passear com o cachorro",
                        "",
                        101010,
                        101012
                ) );
                return true;
        }

        return super.onOptionsItemSelected( item );
    }
}
