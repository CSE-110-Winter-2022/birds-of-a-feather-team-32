package com.example.birdsofafeather;

import com.example.birdsofafeather.model.db.Course;

import java.util.List;

public interface PrioritizationScoreStrategy {

    double calculateScore(List<Course> courses);

}
