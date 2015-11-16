package com.ashasoftware.studyday;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by natanmorais on 16/11/15.
 */
public class DayManager {
    ArrayList <HourArrayManager> days = new ArrayList<>();
    int Days;

    public DayManager(int pDays) {
        Days = pDays;
        for(int i=0;i<Days;i++){
            days.add(new HourArrayManager());
        }
    }

}
