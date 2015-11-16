package com.ashasoftware.studyday;

import android.app.Application;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by tiago on 25/10/15.
 */
public class App extends Application {

    private static Context context;

    private static SQLiteHelper database;

    private static SimpleDateFormat dateFormat;

    private static SimpleDateFormat timeFormat;

    private static YearManager Years = new YearManager();


    @Override
    public void onCreate() {
        super.onCreate();

        context = getBaseContext();
        database = new SQLiteHelper( context );
        dateFormat = new SimpleDateFormat( context.getResources().getString( R.string.date_pattern ) );
        timeFormat = new SimpleDateFormat( context.getResources().getString( R.string.short_time_pattern ) );
    }

    public static Context getContext() {
        return context;
    }

    public static SQLiteHelper getDatabase() {
        return database;
    }

    public static String getTimeFormated( Date date ) {
        return timeFormat.format( date );
    }

    public static String getDateFormated( Date date ) {
        return dateFormat.format( date );
    }

    public static YearManager getYears() {return Years; }
}
