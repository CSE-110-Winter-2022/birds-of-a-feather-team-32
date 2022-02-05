
package com.example.birdsofafeather;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsofafeather.model.Course;

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
                .inflate(R.layout.student_row, parent, false);
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
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView courseNameView;
        private Course course;

        ViewHolder(View itemView) {
            super(itemView);
            this.courseNameView = itemView.findViewById(R.id.course_row_name);
            itemView.setOnClickListener(this);
        }

        public void setCourse(Course course) {
            this.course = course;
            // for some reason, getCourseName() is returning null?
            // is the constructor not working properly?
            // or the method not returning properly?
            // cause it looks like there is at least a course object
            // right?
            // TODO
            this.courseNameView.setText(course.getCourseName());
        }

        // probably shouldn't have this here but I'll keep it here
        // as a placeholder since we need to override it
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("course_name", this.course.getCourseName()); // should we have an Id?
            context.startActivity(intent);
        }
    }
}
