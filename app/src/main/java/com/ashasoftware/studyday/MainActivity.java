package com.ashasoftware.studyday;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    SQLiteHelper db;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null )
            actionBar.setDisplayShowTitleEnabled( false );

        db = new SQLiteHelper( this );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        return super.onOptionsItemSelected( item );
    }
}
