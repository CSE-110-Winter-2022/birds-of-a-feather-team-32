package com.example.birdsofafeather;

import com.example.birdsofafeather.model.db.Course;

import java.util.Calendar;
import java.util.List;

public class SortByRecentScoreStrategy implements PrioritizationScoreStrategy{


    private List<Course> courses;
    private final Calendar calendar = Calendar.getInstance();
    public int month = calendar.get(Calendar.MONTH);    //current month
    public String currYear = Integer.toString(calendar.get(Calendar.YEAR));      //current year
    public String currQuarter;      //current quarter

    public SortByRecentScoreStrategy() {
        setQuarter();
    }
    
    @Override
    public double calculateScore(List<Course> courses) {
        double score = 0;
        for (Course c: courses) {
            score += Score(c.qtr,c.year);
        }
        return score;
    }

    //sets the current quarter
    public void setQuarter(){
        switch (month) {
            case 0:
            case 1:
            case 2: currQuarter = "WI";
                    break;
            case 3:
            case 4:
            case 5: currQuarter = "SP";
                    break;
            case 6:
            case 7: currQuarter = "SU";
                    break;
            case 8:
            case 9:
            case 10:
            case 11: currQuarter = "FA";
                     break;
        }

    }

    public int Score(String quarter, String year){
        int s = 0;

        if(quarter == "SS1" || quarter == "SS2" || quarter == "SSS"){
            quarter = "SU";
        }
        s = comp(year, quarter);
        return s;
    }

    public int comp(String year, String q){
        int i = 0;
        if(currYear.equals(year)){
            switch (currQuarter){
                case "FA":
                    switch (q){
                        case "FA":
                        case "SU":
                            i = 5;
                            break;
                        case "SP":
                            i = 4;
                            break;
                        case "WI":
                            i = 3;
                            break;
                    }
                case "SU":
                    switch (q){
                        case "SU":
                        case "SP":
                            i = 5;
                            break;
                        case "WI":
                            i = 4;
                            break;
                    }
                case "SP":
                    switch (q){
                        case "SP":
                        case "WI":
                            i = 5;
                            break;
                    }
                case "WI":
                    if (q.equals("WI")) {
                        i = 5;
                    }
            }
        }
        else if(Integer.parseInt(currYear) == (Integer.parseInt(year) + 1)){
            switch (currQuarter){
                case "FA":
                    switch (q){
                        case "FA":
                            i = 2;
                            break;
                        case "SU":
                        case "SP":
                        case "WI":
                            i = 1;
                            break;
                    }
                case "SU":
                    switch (q){
                        case "FA":
                            i = 3;
                            break;
                        case "SU":
                            i = 2;
                            break;
                        case "SP":
                        case "WI":
                            i = 1;
                            break;
                    }
                case "SP":
                    switch (q){
                        case "FA":
                            i = 4;
                            break;
                        case "SU":
                            i = 3;
                            break;
                        case "SP":
                            i = 2;
                            break;
                        case "WI":
                            i = 1;
                            break;
                    }
                case "WI":
                    switch (q){
                        case "FA":
                            i = 5;
                            break;
                        case "SU":
                            i = 4;
                            break;
                        case "SP":
                            i = 3;
                            break;
                        case "WI":
                            i = 2;
                            break;
                    }
            }
        }
        else{
            i = 1;
        }
        return i;
    }
}