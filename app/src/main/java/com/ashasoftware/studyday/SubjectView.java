package com.ashasoftware.studyday;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tiago on 05/11/15.
 */
public class SubjectView extends View {

    private Materia materia;

    public SubjectView( Context context ) {
        super( context );
    }

    public SubjectView( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria( Materia materia ) {
        this.materia = materia;
    }
}
