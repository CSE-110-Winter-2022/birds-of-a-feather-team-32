package com.example.birdsofafeather;

import com.example.birdsofafeather.model.db.StudentWithCourses;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class sortStudentsByStrategySorter {

    private List<StudentWithCourses> students;
    PrioritizationScoreStrategy strategy;

    /**
     * Set the list of students to be sorted
     * @param students the list of students
     */
    public sortStudentsByStrategySorter(List<StudentWithCourses> students) {
        this.students = students;
    }

    /**
     * Set the sorting strategy to be used by the student sorter
     * @param strategy the strategy to be used
     */
    public void setStrategy(PrioritizationScoreStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Performs the sorting of the list of students
     * @return students the final sorted list
     */
    public List<StudentWithCourses> sort() {
        Collections.sort(students, new CustomComparator());
        return students;
    }

    /**
     * A custom comparator to be used to sort the list of the students
     */
    class CustomComparator implements Comparator<StudentWithCourses> {
        @Override
        public int compare(StudentWithCourses s1, StudentWithCourses s2) {
            // Checking for wave: add 1000 to score if has wave
            double s1Score = strategy.calculateScore(s1.getCourses());
            double s2Score = strategy.calculateScore(s2.getCourses());
            if(s1.student.wavedFrom){
                s1Score = s1Score + 1000;
            }
            if(s2.student.wavedFrom){
                s2Score = s2Score + 1000;
            }
            if (s1Score == s2Score) {
                return 0;
            }
            if (s1Score < s2Score) {
                return 1;
            }
            else {
                return -1;
            }
        }
    }
}
