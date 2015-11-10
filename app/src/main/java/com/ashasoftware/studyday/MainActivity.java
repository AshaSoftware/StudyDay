package com.ashasoftware.studyday;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

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

        drawer = (DrawerLayout) findViewById( R.id.drawer_layout );

        NavigationView navigationView = (NavigationView) findViewById( R.id.drawer_menu_main );
        navigationView.setNavigationItemSelectedListener( this );
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
            case android.R.id.home:
                if( !drawer.isDrawerOpen( GravityCompat.START ) )
                    drawer.openDrawer( GravityCompat.START );
                else
                    drawer.closeDrawer( GravityCompat.START );
                return true;

        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem menuItem ) {
        Intent i;
        switch( menuItem.getItemId() ) {
            case R.id.drawer_item_subject:
                i = new Intent( this, SubjectViewActivity.class );
                startActivity( i );
                break;
            case R.id.drawer_item_non_school:
                i = new Intent( this, NaoEscolarViewActivity.class );
                startActivity( i );
                break;
            case R.id.drawer_item_aula:
                i = new Intent( this, AulaViewActivity.class );
                startActivity( i );
                break;
            case R.id.drawer_item_avaliacao:
                i = new Intent( this, AvaliacaoViewActivity.class );
                startActivity( i );
                break;
            default:
                return false;
        }

        drawer.closeDrawers();

        return true;
    }
}
