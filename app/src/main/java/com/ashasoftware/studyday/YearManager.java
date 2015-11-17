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

    public void addTaskNonSchool(long ini, long fim){
        Calendar Start = Calendar.getInstance();
        Calendar End = Calendar.getInstance();
        Start.setTimeInMillis(ini);
        End.setTimeInMillis(fim);
        if(Start.get(Calendar.YEAR) == tY.get(Calendar.YEAR)){
            thisYear.months.get(Start.MONTH -1).days.get(Start.DAY_OF_MONTH).addTasks(Start.get(Calendar.MINUTE)
                                                        +Start.get(Calendar.HOUR)*60
                                                        ,End.get(Calendar.MINUTE)
                                                        +End.get(Calendar.HOUR)*60);
        } else {
            NextYear.months.get(Start.MONTH -1).days.get(Start.DAY_OF_MONTH).addTasks(Start.get(Calendar.MINUTE)
                                                        +Start.get(Calendar.HOUR)*60
                                                        ,End.get(Calendar.MINUTE)
                                                        +End.get(Calendar.HOUR)*60);
        }
    }

    public void rmTaskNonSchool(long ini, long fim){
        Calendar Start = Calendar.getInstance();
        Calendar End = Calendar.getInstance();
        Start.setTimeInMillis(ini);
        End.setTimeInMillis(fim);
        if(Start.get(Calendar.YEAR) == tY.get(Calendar.YEAR)){
            thisYear.months.get(Start.MONTH -1).days.get(Start.DAY_OF_MONTH).rmTasks(Start.get(Calendar.MINUTE)
                    + Start.get(Calendar.HOUR) * 60
                    , End.get(Calendar.MINUTE)
                    + End.get(Calendar.HOUR) * 60);
        } else {
            NextYear.months.get(Start.MONTH -1).days.get(Start.DAY_OF_MONTH).rmTasks(Start.get(Calendar.MINUTE)
                    + Start.get(Calendar.HOUR) * 60
                    , End.get(Calendar.MINUTE)
                    + End.get(Calendar.HOUR) * 60);
        }
    }

    public void addTaskClass(long ini, long fim, int dia){
        Calendar Start = Calendar.getInstance();
        Calendar End = Calendar.getInstance();
        Start.setTimeInMillis(ini);
        End.setTimeInMillis(fim);
        Start.set(Calendar.DAY_OF_WEEK, dia);
        if(Start.get(Calendar.YEAR) == tY.get(Calendar.YEAR)){
            for(int i=Start.get(Calendar.MONTH);i<=12;i++){
                for(int j=Start.get(Calendar.DAY_OF_MONTH);j<Start.getActualMaximum(Calendar.DAY_OF_MONTH);j += 7){
                    thisYear.months.get(i-1).days.get(j).addTasks(Start.get(Calendar.MINUTE)
                            +Start.get(Calendar.HOUR)*60
                            ,End.get(Calendar.MINUTE)
                            +End.get(Calendar.HOUR)*60);
                }
            }
        } else {
            for(int i=Start.get(Calendar.MONTH);i<=12;i++){
                for(int j=Start.get(Calendar.DAY_OF_MONTH);j<Start.getActualMaximum(Calendar.DAY_OF_MONTH);j += 7){
                    NextYear.months.get(i-1).days.get(j).addTasks(Start.get(Calendar.MINUTE)
                            +Start.get(Calendar.HOUR)*60
                            ,End.get(Calendar.MINUTE)
                            +End.get(Calendar.HOUR)*60);
                }
            }
        }
    }

    public void rmTaskClass(long ini, long fim, int dia){
        Calendar Start = Calendar.getInstance();
        Calendar End = Calendar.getInstance();
        Start.setTimeInMillis(ini);
        End.setTimeInMillis(fim);
        Start.set(Calendar.DAY_OF_WEEK, dia);
        if(Start.get(Calendar.YEAR) == tY.get(Calendar.YEAR)){
            for(int i=Start.get(Calendar.MONTH);i<=12;i++){
                for(int j=Start.get(Calendar.DAY_OF_MONTH);j<Start.getActualMaximum(Calendar.DAY_OF_MONTH);j += 7){
                    thisYear.months.get(i-1).days.get(j).rmTasks(Start.get(Calendar.MINUTE)
                            + Start.get(Calendar.HOUR) * 60
                            , End.get(Calendar.MINUTE)
                            + End.get(Calendar.HOUR) * 60);
                }
            }
        } else {
            for(int i=Start.get(Calendar.MONTH);i<=12;i++){
                for(int j=Start.get(Calendar.DAY_OF_MONTH);j<Start.getActualMaximum(Calendar.DAY_OF_MONTH);j += 7){
                    NextYear.months.get(i-1).days.get(j).rmTasks(Start.get(Calendar.MINUTE)
                            + Start.get(Calendar.HOUR) * 60
                            , End.get(Calendar.MINUTE)
                            + End.get(Calendar.HOUR) * 60);
                }
            }
        }
    }

    public boolean addTaskStudy(long ini, long fim){
        Calendar Start = Calendar.getInstance();
        Calendar End = Calendar.getInstance();
        Start.setTimeInMillis(ini);
        End.setTimeInMillis(fim);
        if(Start.get(Calendar.YEAR) == tY.get(Calendar.YEAR)){
            if(thisYear.months.get(Start.MONTH -1).days.get(Start.DAY_OF_MONTH).VerificarDisponibilidade(Start.MINUTE,End.MINUTE)){
                thisYear.months.get(Start.MONTH -1).days.get(Start.DAY_OF_MONTH).addTasks(Start.get(Calendar.MINUTE)
                        +Start.get(Calendar.HOUR)*60
                        ,End.get(Calendar.MINUTE)
                        +End.get(Calendar.HOUR)*60);
                return true;
            }
        } else {
            if(NextYear.months.get(Start.MONTH -1).days.get(Start.DAY_OF_MONTH).VerificarDisponibilidade(Start.MINUTE,End.MINUTE)) {
                NextYear.months.get(Start.MONTH - 1).days.get(Start.DAY_OF_MONTH).addTasks(Start.get(Calendar.MINUTE)
                        + Start.get(Calendar.HOUR) * 60
                        , End.get(Calendar.MINUTE)
                        + End.get(Calendar.HOUR) * 60);
                return true;
            }
        }
        return false;
    }

    public boolean rmTaskStudy(long ini, long fim){
        Calendar Start = Calendar.getInstance();
        Calendar End = Calendar.getInstance();
        Start.setTimeInMillis(ini);
        End.setTimeInMillis(fim);
        if(Start.get(Calendar.YEAR) == tY.get(Calendar.YEAR)){
            if(thisYear.months.get(Start.MONTH -1).days.get(Start.DAY_OF_MONTH).VerificarDisponibilidade(Start.MINUTE,End.MINUTE)){
                thisYear.months.get(Start.MONTH -1).days.get(Start.DAY_OF_MONTH).rmTasks(Start.get(Calendar.MINUTE)
                        +Start.get(Calendar.HOUR)*60
                        ,End.get(Calendar.MINUTE)
                        +End.get(Calendar.HOUR)*60);
                return true;
            }
        } else {
            if(NextYear.months.get(Start.MONTH -1).days.get(Start.DAY_OF_MONTH).VerificarDisponibilidade(Start.MINUTE,End.MINUTE)) {
                NextYear.months.get(Start.MONTH - 1).days.get(Start.DAY_OF_MONTH).rmTasks(Start.get(Calendar.MINUTE)
                        + Start.get(Calendar.HOUR) * 60
                        , End.get(Calendar.MINUTE)
                        + End.get(Calendar.HOUR) * 60);
                return true;
            }
        }
        return false;
    }
}
