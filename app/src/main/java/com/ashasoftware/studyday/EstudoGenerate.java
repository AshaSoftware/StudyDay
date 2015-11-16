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
        }
        //Para a quantidade de horas de estudo: Total de Pesos / horas de Estudo
        horaPorPeso = horasEstudo / Pesos;
        int minPorPeso = (int) (horaPorPeso * 60);
        for(Materia materia: App.getDatabase().getAllMaterias()){
            int Horas = (materia.getDifMateria() + materia.getDifProfessor())*minPorPeso;
            materias.add(new MateriaEstudo(materia,Horas));
        }

        for(MateriaEstudo EstudoAux: materias) {
            Calendar inicio = Calendar.getInstance();
            Calendar fim = Calendar.getInstance();
            if(EstudoAux.getPeso()>1){
                fim.set(Calendar.HOUR, fim.get(Calendar.HOUR) + 1);
            }
            else{
                fim.set(Calendar.MINUTE, fim.get(Calendar.MINUTE) + EstudoAux.getPeso());
            }
            while (!App.getYears().addTaskStudy(inicio.getTimeInMillis(), fim.getTimeInMillis()) || EstudoAux.getPeso()>0) {
                if(!App.getYears().addTaskStudy(inicio.getTimeInMillis(), fim.getTimeInMillis())) {
                    if(EstudoAux.getPeso()>1){
                        inicio.set(Calendar.HOUR, inicio.get(Calendar.HOUR) + 1);
                        fim.set(Calendar.HOUR, fim.get(Calendar.HOUR) + 1);
                    }
                    else{
                        inicio.set(Calendar.HOUR, inicio.get(Calendar.HOUR) + 1);
                        fim.set(Calendar.MINUTE, fim.get(Calendar.MINUTE) + EstudoAux.getPeso());
                    }
                } else {
                    App.getDatabase().addEstudo(EstudoAux.getSubject().getCodigo(),"",inicio.getTimeInMillis(),fim.getTimeInMillis());
                    EstudoAux.setPeso(EstudoAux.getPeso() - 1);
                    inicio.set(Calendar.DAY_OF_MONTH, inicio.get(Calendar.DAY_OF_MONTH)+1);
                    inicio.set(Calendar.DAY_OF_MONTH, inicio.get(Calendar.DAY_OF_MONTH)+1);
                }
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
