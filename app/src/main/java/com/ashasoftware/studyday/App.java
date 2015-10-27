package com.ashasoftware.studyday;

import android.app.Application;
import android.content.Context;

/**
 * Created by tiago on 25/10/15.
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getBaseContext();
    }

    public static Context getContext() {
        return context;
    }
}
