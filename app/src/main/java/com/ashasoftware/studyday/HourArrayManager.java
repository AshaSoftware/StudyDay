package com.ashasoftware.studyday;

import java.util.ArrayList;

/**
 * Created by natanmorais on 16/11/15.
 */
public class HourArrayManager {
    ArrayList <Integer> tasks = new ArrayList<>();

    public HourArrayManager(int task) {
        tasks.add(task);
    }

    public boolean VerificarDisponibilidade (int minIni, int minFim){
        for(int i=minIni; i<=minFim; i++){
            if (tasks.contains(i)){
                return false;
            }
        }
        return true;
    }
}
