package com.ashasoftware.studyday;

import java.util.ArrayList;

/**
 * Created by natanmorais on 16/11/15.
 */
public class MonthManager {
    ArrayList <DayManager> months = new ArrayList<>();
    boolean Bissexto;

    public MonthManager(boolean pBissexto) {
        Bissexto = pBissexto;
        for(int i=0; i<12; i++){
            switch (i){
                case 0:
                    months.add(new DayManager(31));
                    break;
                case 1:
                    if(Bissexto){
                        months.add(new DayManager(29));
                    }else {
                        months.add(new DayManager(28));
                    }
                    break;
                case 2:
                    months.add(new DayManager(31));
                    break;
                case 3:
                    months.add(new DayManager(30));
                    break;
                case 4:
                    months.add(new DayManager(31));
                    break;
                case 5:
                    months.add(new DayManager(30));
                    break;
                case 6:
                    months.add(new DayManager(31));
                    break;
                case 7:
                    months.add(new DayManager(31));
                    break;
                case 8:
                    months.add(new DayManager(30));
                    break;
                case 9:
                    months.add(new DayManager(31));
                    break;
                case 10:
                    months.add(new DayManager(30));
                    break;
                case 11:
                    months.add(new DayManager(31));
                    break;
                default:
                    break;
            }
        }
    }
}
