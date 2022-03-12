/**
 * File: ListOfBoFViewAdapter.Java
 * Description: Class that binds our StudentWithCourses object data to each item in the recycler view in
 * our layout file activity_boflist.xml
 */
package com.example.birdsofafeather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;
import com.example.birdsofafeather.model.db.StudentWithCoursesDao;

import java.util.List;

public class ListOfBoFViewAdapter extends RecyclerView.Adapter<ListOfBoFViewAdapter.ViewHolder> {

    private final List<StudentWithCourses> students;

    /**
     * Parameterized Constructor: Instantiates ListOfBoFViewAdapter with passed in list of students
     * @param students
     */

    public ListOfBoFViewAdapter(List<StudentWithCourses> students) {
        super();
        this.students = students;
    }

    /**
     * Handles the ViewHolder by creating a copy of each student row
     * and wrapping it in our ViewHolder
     * @param parent
     * @param viewType
     * @return new ViewHolder object
     */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.student_row, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Handles how to connect our StudentWithCourses objects data to our ViewHolder at a given position
     * @param holder
     * @param position
     */


    @Override
    public void onBindViewHolder(@NonNull ListOfBoFViewAdapter.ViewHolder holder, int position) {
        Log.d("OnBindViewHolder", students.get(position).getName());
        holder.setPerson(students.get(position));

        if (students.get(position).student.getFavorite()){
            holder.setFavorite();
        }
    }

    /**
     * Returns the total number of StudentWithCourses Objects we have
     * @return number of Courses
     */

    @Override
    public int getItemCount() {
        return this.students.size();
    }

    /**
     * ViewHolder inner class responsible for managing the rows of StudentWithCourses
     */

    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView studentNameView;
        private TextView numClassesOverlap;
        private StudentWithCourses student;
        private ImageView imageView;
        private ImageView waveView;
        private View itemView;
        private ImageButton favButton;
        private AppDatabase db;

        /**
         * Parameterized Constructor: Instantiates ViewHolder object with passed in View
         * @param itemView
         */

        ViewHolder(View itemView) {
            super(itemView);
            db = AppDatabase.singleton((Activity) itemView.getContext());
            this.studentNameView = itemView.findViewById(R.id.student_row_name);
            this.numClassesOverlap = itemView.findViewById(R.id.numOverlap);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.imageView);
            waveView = itemView.findViewById(R.id.waveView);
            favButton = itemView.findViewById(R.id.star_hollow_button);
            favButton.setTag(R.mipmap.star_hollow);
            favButton.setOnClickListener(view -> {

                if (!((Integer) favButton.getTag()).equals((Integer) R.mipmap.star_filled)) {
                    favButton.setImageResource(R.mipmap.star_filled);
                    favButton.setTag(R.mipmap.star_filled);
                    Toast.makeText((Activity) itemView.getContext(), "Favorite Added! <3", Toast.LENGTH_SHORT).show();
                    db.studentWithCoursesDao().updateFavorite(true, student.getStudentObject().getStudentId());

                } else {
                    favButton.setImageResource(R.mipmap.star_hollow);
                    favButton.setTag(R.mipmap.star_hollow);
                    Toast.makeText((Activity) itemView.getContext(), "Favorite Removed! </3", Toast.LENGTH_SHORT).show();
                    db.studentWithCoursesDao().updateFavorite(false, student.getStudentObject().getStudentId());
                }


            });
            this.itemView = itemView;

        }


        /**
         * Sets the views with the passed in StudentWithCourses object's information
         * @param student
         */

        public void setPerson(StudentWithCourses student) {
            this.student = student;
            this.studentNameView.setText(student.getName().trim());
            this.numClassesOverlap.setText(student.student.getNumOverlap());
            if (student.student.getWavedFrom()) {
                this.waveView.setImageResource(R.mipmap.wave_filled);
                this.waveView.setTag(R.mipmap.wave_filled);
            }
            String url = student.student.getPhotoURL();
            url = url.trim();
            Glide.with(itemView)
                    .load(url)
                    .into(imageView);
        }

        /**
         * Handles behavior when a row in the view is clicked
         * @param view
         */

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("student_name", this.student.getName());// should we have an Id?
            intent.putExtra("student_id", this.student.getId());
            context.startActivity(intent);
        }

        public void setFavorite(){
            favButton = itemView.findViewById(R.id.star_hollow_button);
            favButton.setImageResource(R.mipmap.star_filled);
            favButton.setTag(R.mipmap.star_filled);
        }
    }
}
