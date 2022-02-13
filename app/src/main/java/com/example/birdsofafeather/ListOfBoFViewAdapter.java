
package com.example.birdsofafeather;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.birdsofafeather.model.db.Student;
import com.example.birdsofafeather.model.db.StudentWithCourses;
import com.example.birdsofafeather.model.db.StudentWithCoursesDao;

import java.util.List;

public class ListOfBoFViewAdapter extends RecyclerView.Adapter<ListOfBoFViewAdapter.ViewHolder> {

    private final List<StudentWithCourses> students;

    public ListOfBoFViewAdapter(List<StudentWithCourses> students) {
        super();
        this.students = students;
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
    public void onBindViewHolder(@NonNull ListOfBoFViewAdapter.ViewHolder holder, int position) {
        holder.setPerson(students.get(position));
    }

    @Override
    public int getItemCount() {
        return this.students.size();
    }

    public void addStudent(StudentWithCourses student){
        this.students.add(student);
        this.notifyItemInserted(this.students.size() - 1);
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView studentNameView;
        private TextView numClassesOverlap;
        private StudentWithCourses student;
        private ImageView imageView;
        private View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            this.studentNameView = itemView.findViewById(R.id.student_row_name);
            this.numClassesOverlap = itemView.findViewById(R.id.numOverlap);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.imageView);
            this.itemView = itemView;
        }

        public void setPerson(StudentWithCourses student) {
            this.student = student;
            this.studentNameView.setText(student.getName());
            this.numClassesOverlap.setText(student.student.getNumOverlap());
            String url = student.student.getPhotoURL();
            url = url.trim();
            Glide.with(itemView)
                    .load(url)
                    .into(imageView);
        }

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
