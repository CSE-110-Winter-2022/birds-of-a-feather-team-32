
package com.example.birdsofafeather;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsofafeather.model.db.Course;

import java.util.List;

public class ProfileActivityViewAdapter extends RecyclerView.Adapter<ProfileActivityViewAdapter.ViewHolder> {

    private final List<Course> courses;

    public ProfileActivityViewAdapter(List<Course> courses) {
        super();
        this.courses = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.course_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileActivityViewAdapter.ViewHolder holder, int position) {
        holder.setCourse(courses.get(position));
    }

    @Override
    public int getItemCount() {
        return this.courses.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {
        private final TextView courseView;
        private Course course;

        ViewHolder(View itemView) {
            super(itemView);
            this.courseView = itemView.findViewById(R.id.course_row_name);
        }

        public void setCourse(Course course) {
            this.course = course;
            this.courseView.setText(course.dept + " " + course.num + " " + course.year + " " + course.qtr);

        }

    }
}
