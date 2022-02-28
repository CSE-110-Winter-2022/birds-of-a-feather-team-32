package com.example.birdsofafeather;

import com.example.birdsofafeather.model.db.Course;

import java.util.List;

public class SmallClassSizeScoreStategy implements PrioritizationScoreStrategy {

    private List<Course> courses;

    public SmallClassSizeScoreStategy(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public double calculateScore() {
        double score = 0;
        for (Course c: courses) {
            score += classSizeToScore(c.getClassSize());
        }
        return score;
    }

    private double classSizeToScore(String size) {
        switch (size) {
            case "tiny":
                return 1;
            case "small":
                return 0.33;
            case "medium":
                return 0.18;
            case "large":
                return 0.10;
            case "huge":
                return 0.06;
            case "gigantic":
                return 0.03;
        }
        return 0;
    }
}

