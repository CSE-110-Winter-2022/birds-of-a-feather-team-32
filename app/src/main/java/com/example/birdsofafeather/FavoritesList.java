package com.example.birdsofafeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.birdsofafeather.model.db.AppDatabase;
import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FavoritesList extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView studentRecyclerView;
    private RecyclerView.LayoutManager studentLayoutManager;
    private ListOfBoFViewAdapter studentViewAdapter;
    private List<StudentWithCourses> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        // Set up UI
        studentRecyclerView = findViewById(R.id.favorite_view);
        studentLayoutManager = new LinearLayoutManager(this);
        studentRecyclerView.setLayoutManager(studentLayoutManager);

        studentViewAdapter = new ListOfBoFViewAdapter(students);
        studentRecyclerView.setAdapter(studentViewAdapter);


    }

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

        /**
         * Parameterized Constructor: Instantiates ViewHolder object with passed in View
         * @param itemView
         */

        ViewHolder(View itemView) {
            super(itemView);
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
                    Student tempStudent = student.getStudentObject();
                    Student favStudent = new Student(tempStudent.getStudentId(), -1, tempStudent.getName(), tempStudent.getPhotoURL(), Integer.parseInt(tempStudent.getNumOverlap()), tempStudent.getUUID(), tempStudent.getWavedFrom(), tempStudent.getWavedAt());

                }/* else {
                    favButton.setImageResource(R.mipmap.star_hollow);
                    favButton.setTag(R.mipmap.star_hollow);
                    Toast.makeText((Activity) itemView.getContext(), "Favorite Removed! </3", Toast.LENGTH_SHORT).show();
                }

                 */
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
    }
}