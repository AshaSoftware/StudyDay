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
        for(Materia materia: App.getDatabase().getAllMaterias()){
            Pesos += materia.getDifMateria() + materia.getDifProfessor();
            materias.add(new MateriaEstudo(materia,materia.getDifMateria() + materia.getDifProfessor()));
        }
        //Para a quantidade de horas de estudo: Total de Pesos / horas de Estudo
        horaPorPeso = horasEstudo / Pesos;
        for( int i=0; i<7;i++){
            alocaEstudo(i,horasEstudo/5);
        }


    }

    public void alocaEstudo(int dia, float horasPorDia) {
        List<Aula> aulas = App.getDatabase().getAllAulas();
        for (Aula aula: aulas){
            if(aula.dia == dia){
                App.getDatabase().addEstudo(aula.getMateria().getCodigo(),"It's recommended that you study " + aula.getMateria().getNome() + " every " + aula.dayToString(aula.getDia()), 0, 0);
            }
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
