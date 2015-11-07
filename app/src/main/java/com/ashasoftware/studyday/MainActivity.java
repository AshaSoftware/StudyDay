package com.ashasoftware.studyday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null ) {
            actionBar.setDisplayShowTitleEnabled( false );
            actionBar.setHomeAsUpIndicator( R.drawable.ic_menu_white );
            actionBar.setDisplayHomeAsUpEnabled( true );
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch( item.getItemId() ) {
            case R.id.show_subject:
                Intent i = new Intent( this, SubjectViewActivity.class );
                startActivity( i );
                return true;
            case R.id.show_naoescolar:
                Intent j = new Intent( this, NaoEscolarViewActivity.class );
                startActivity( j );
                return true;
        }

        return super.onOptionsItemSelected( item );
    }
}
