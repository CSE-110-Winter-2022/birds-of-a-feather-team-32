package com.example.birdsofafeather.model.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SessionWithStudents {
    @Embedded
    public Session session;

    @Relation(parentColumn = "sessionId",
            entityColumn = "sessionId",
            entity = Student.class)
    public List<Student> students;

    public String getSessionName() {
        return this.session.name;
    }
    public List<Student> getStudents() {
        return this.students;
    }
    public int getId() {
        return this.session.sessionId;
    }
}
