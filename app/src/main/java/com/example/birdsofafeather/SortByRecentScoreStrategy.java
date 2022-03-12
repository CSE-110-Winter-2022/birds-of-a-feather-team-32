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

    /**
     * calculateScore(List<Course> courses)
     * parameters: courses(List<Course>)
     * Adds all of the course scores to the students score
     * return: double
     */
    @Override
    public double calculateScore(List<Course> courses) {
        double score = 0;
        for (Course c: courses) {
            score += Score(c.qtr,c.year);
        }
        return score;
    }

    /**
     * setQuarter()
     * parameters: none
     * sets the quarter to the current quarter
     * return: void
     */
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

    /**
     * calc(String year, String quarter)
     * parameters: year(string), quarter(string)
     * calls calc to get the score
     * return: int
     */
    public int Score(String quarter, String year){
        int s = 0;
        //sets all the summer session quarters to one summer quarter
        if(quarter == "SS1" || quarter == "SS2" || quarter == "SSS"){
            quarter = "SU";
        }
        s = calc(year, quarter);    //calculates the score based on recent classes
        return s;
    }
    /**
     * calc(String year, String q)
     * parameters: year(string), q(string)
     * calculate the weight of the class based on how recent it was
     * return: int
     */

    public int calc(String year, String q){
        int i = 0;
        if(currYear.equals(year)){      //class was within the year
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
        //class was last year
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
        //class was more than a year ago
        else{
            i = 1;
        }
        return i;
    }
}