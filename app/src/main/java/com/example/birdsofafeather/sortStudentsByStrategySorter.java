package com.example.birdsofafeather;

import com.example.birdsofafeather.model.db.StudentWithCourses;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class sortStudentsByStrategySorter {

    private List<StudentWithCourses> students;
    PrioritizationScoreStrategy strategy;

    public sortStudentsByStrategySorter(List<StudentWithCourses> students) {
        this.students = students;
    }

    public void setStrategy(PrioritizationScoreStrategy strategy) {
        this.strategy = strategy;
    }

    public List<StudentWithCourses> sort() {
        Collections.sort(students, new CustomComparator());
        return students;
    }

    class CustomComparator implements Comparator<StudentWithCourses> {
        @Override
        public int compare(StudentWithCourses s1, StudentWithCourses s2) {
            // Checking for wave: add 100000 to score if has wave
            double s1Score = strategy.calculateScore(s1.getCourses());
            double s2Score = strategy.calculateScore(s2.getCourses());
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
