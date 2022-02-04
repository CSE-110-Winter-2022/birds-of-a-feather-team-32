package com.example.birdsofafeather.model;

import java.util.HashSet;

public class Student {

    private String name;
    private String photoURL;
    private HashSet<Course> courses;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
