package com.ashasoftware.studyday;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by natanmorais on 13/11/15.
 */
public class EstudoGenerate {
    Calendar diaFim, horaIni, horaFim;
    int Pesos = 0, horasEstudo = 20;
    float horaPorPeso;
    //Exemplo de Toast       Toast.makeText(App.getContext(), "AAAAAAAAAAAAA", Toast.LENGTH_SHORT).show();
    ArrayList <MateriaEstudo> materias = new ArrayList<>();

    public EstudoGenerate(){
        App.getDatabase().deleteAllEstudos();

        for(Materia materia: App.getDatabase().getAllMaterias()){
            Pesos += materia.getDifMateria() + materia.getDifProfessor();
            materias.add(new MateriaEstudo(materia,materia.getDifMateria() + materia.getDifProfessor()));
        }
        //Para a quantidade de horas de estudo: Total de Pesos / horas de Estudo
        horaPorPeso = horasEstudo / Pesos;

        Calendar inicio = Calendar.getInstance();
        Calendar fim = Calendar.getInstance();
        fim.set(Calendar.HOUR,fim.get(Calendar.HOUR)+1);
        for(MateriaEstudo EstudoAux: materias) {
            while (!App.getYears().addTaskStudy(inicio.getTimeInMillis(), fim.getTimeInMillis())) {
                inicio.set(Calendar.HOUR,fim.get(Calendar.HOUR)+1);
                fim.set(Calendar.HOUR,fim.get(Calendar.HOUR)+1);
            }
            App.getDatabase().addEstudo(EstudoAux.getSubject().getCodigo(),"",inicio.getTimeInMillis(),fim.getTimeInMillis());
        }
    }

    public class MateriaEstudo {
        Materia subject;
        int Peso;

        public MateriaEstudo(Materia subject, int peso) {
            this.subject = subject;
            Peso = peso;
        }

        public void setSubject(Materia subject) {
            this.subject = subject;
        }

        public void setPeso(int peso) {
            Peso = peso;
        }

        public Materia getSubject() {
            return subject;
        }

        public int getPeso() {
            return Peso;
        }

    }
}
