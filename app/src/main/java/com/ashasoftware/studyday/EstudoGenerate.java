package com.ashasoftware.studyday;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by natanmorais on 24/11/15.
 */
public class EstudoGenerate {
    int horasPorSemana = 20;
    boolean Sabado = false, Domingo = false;
    int mesesDeEstudo = 2;

    public EstudoGenerate() {
        if (App.getDatabase().getAllEstudos().size() > 0) {
            App.getDatabase().deleteAllEstudos();
        }
        List<Materia> materias = App.getDatabase().getAllMaterias();
        int totalPesos = 0, minPorPeso;
        for (Materia subject : materias) {
            totalPesos += subject.getDifMateria() + subject.getDifMateria();
        }
        minPorPeso = (horasPorSemana * 60) / totalPesos;
        Calendar c = Calendar.getInstance();
        for (Materia subject : materias) {
            int dias = Math.round(minPorPeso * (subject.getDifMateria() + subject.getDifProfessor())/60)*mesesDeEstudo;
            for(int i=0;i<dias;i++){
                if(c.get(Calendar.DAY_OF_WEEK)==0 && !Domingo || c.get(Calendar.DAY_OF_WEEK)==6 && !Sabado){

                }else{
                    App.getDatabase().addEstudo(subject.getCodigo(),"",c.getTimeInMillis(),c.getTimeInMillis());
                }
                c.add(Calendar.DAY_OF_MONTH,1);
            }
        }
    }
}
