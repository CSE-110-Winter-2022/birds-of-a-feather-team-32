package com.example.birdsofafeather;

import com.example.birdsofafeather.model.db.Course;

import java.util.List;

public class SmallClassSizeScoreStrategy implements PrioritizationScoreStrategy {

    public SmallClassSizeScoreStrategy() {

    }

    @Override
    public double calculateScore(List<Course> courses) {
        double score = 0;
        for (Course c: courses) {
            score += classSizeToScore(c.getCourseSize());
        }
        return score;
    }

    private double classSizeToScore(String size) {
        switch (size) {
            case "TINY":
                return 1;
            case "SMALL":
                return 0.33;
            case "MEDIUM":
                return 0.18;
            case "LARGE":
                return 0.10;
            case "HUGE":
                return 0.06;
            case "GIGANTIC":
                return 0.03;
        }
        return 0;
    }
}

