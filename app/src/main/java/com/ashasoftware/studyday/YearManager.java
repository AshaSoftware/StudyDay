package com.ashasoftware.studyday;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by natanmorais on 16/11/15.
 */
public class YearManager {
    MonthManager thisYear, NextYear;
    GregorianCalendar tY = (GregorianCalendar) GregorianCalendar.getInstance();
    GregorianCalendar nY = (GregorianCalendar) GregorianCalendar.getInstance();

    public YearManager() {
        nY.add(Calendar.YEAR,1);
        thisYear = new MonthManager(tY.isLeapYear(tY.get(Calendar.YEAR)));
        NextYear = new MonthManager(nY.isLeapYear(nY.get(Calendar.YEAR)));
    }
}
