/**
 * File: CourseViewAdapter.Java
 * Description: Class that binds our Course object data to each item in the recycler view in
 * our layout file activity_course.xml
 */

package com.example.birdsofafeather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsofafeather.model.db.Course;

import java.util.List;
import java.util.function.Consumer;

public class CoursesViewAdapter extends RecyclerView.Adapter<CoursesViewAdapter.ViewHolder> {
    private final List<Course> courses;
    private final Consumer<Course> onCourseRemoved;

    /**
     * Parameterized Constructor: Instantiates CoursesViewAdapter with passed in list of courses
     * and a Consumer Object used when removing elements in the View Adapter
     * Utilizes super class default constructor
     * @param courses
     * @param onCourseRemoved
     */

    public CoursesViewAdapter(List<Course> courses, Consumer<Course> onCourseRemoved) {
        super();
        this.courses = courses;
        this.onCourseRemoved = onCourseRemoved;
    }

    /**
     * Handles the ViewHolder by creating a copy of each course row
     * and wrapping it in our ViewHolder
     * @param parent
     * @param viewType
     * @return new ViewHolder object with passed in removeCourse method
     */

    @NonNull
    @Override
    public CoursesViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.courses_row, parent, false);

        return new ViewHolder(view, this::removeCourse, onCourseRemoved);
    }

    /**
     * Handles how to connect our Course objects data to our ViewHolder at a given position
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(@NonNull CoursesViewAdapter.ViewHolder holder, int position) {
        holder.setCourse(courses.get(position));
    }

    /**
     * Returns the total number of Course Objects we have
     * @return number of Courses
     */

    @Override
    public int getItemCount() { return this.courses.size(); }

    /**
     * Adds passed in Course into the ViewHolder
     * @param course
     */

    public void addCourse(Course course) {
        this.courses.add(course);
        this.notifyItemInserted(this.courses.size() - 1);
    }

    /**
     * Removes Course at specified position in ViewHolder
     * @param position
     */

    public void removeCourse(int position) {
        this.courses.remove(position);
        this.notifyItemRemoved(position);
    }

    /**
     * ViewHolder inner class responsible for managing the rows of Textviews for Courses
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseView;
        private Course course;

        /**
         * Parameterized Constructor: Instantiates ViewHolder object with passed in View
         * and Consumer objects
          * @param itemView
         * @param removeCourse
         * @param onCourseRemoved
         */

        ViewHolder(View itemView, Consumer<Integer> removeCourse, Consumer<Course> onCourseRemoved) {
            super(itemView);
            this.courseView = itemView.findViewById(R.id.course_row_name);

            Button removeButton = itemView.findViewById(R.id.remove_course_button);
            removeButton.setOnClickListener((view) -> {
                removeCourse.accept(this.getAdapterPosition());
                onCourseRemoved.accept(course);
            });
        }

        /**
         * Sets the Textview with the passed in Course's data fields
         * @param course
         */

        public void setCourse(Course course) {
            this.course = course;
            String courseString = course.dept + " " + course.num + " " + course.qtr + " " + course.year;
            this.courseView.setText(courseString);
        }
    }
}
