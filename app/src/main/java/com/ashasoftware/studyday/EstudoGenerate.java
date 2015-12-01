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
    int pesoParaEstudo = 2;
    int materiasPorDia = horasPorSemana/5;

    public EstudoGenerate() {
        if (App.getDatabase().getAllEstudos().size() > 0) {
            App.getDatabase().deleteAllEstudos();
        }
        int[] ocup = new int[pesoParaEstudo*31];
        for(int Ocup: ocup){
            Ocup = 0;
        }
        List<Materia> materias = App.getDatabase().getAllMaterias();
        int totalPesos = 0, minPorPeso;
        for (Materia subject : materias) {
            totalPesos += subject.getDifMateria() + subject.getDifMateria();
        }
        minPorPeso = (horasPorSemana * 60) / totalPesos;
        Calendar c = Calendar.getInstance();
        for (Materia subject : materias) {
            c = Calendar.getInstance();
            int dias = (int) Math.round((minPorPeso * (subject.getDifMateria() + subject.getDifProfessor())/60)*pesoParaEstudo + 0.5);
            for(int i=0;i<dias;i++){
                if(c.get(Calendar.DAY_OF_WEEK)==1 && !Domingo || c.get(Calendar.DAY_OF_WEEK)==7 && !Sabado || ocup[i] >= materiasPorDia){
                    dias++;
                    Log.v("Tag_Estudo_Create", "Dia inválido");
                }else{
                    App.getDatabase().addEstudo(subject.getCodigo(),"",c.getTimeInMillis(),c.getTimeInMillis());
                    ocup[i] += 1;
                    Log.v("Tag_Estudo_Create", "Dia válido. Matéria: " + subject.getNome() + " | Dia: " + c.get(Calendar.DATE));
                }
                c.add(Calendar.DAY_OF_MONTH,1);
            }
        }
    }
}
