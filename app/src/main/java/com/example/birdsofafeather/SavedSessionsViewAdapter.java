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
import com.example.birdsofafeather.model.db.SessionWithStudents;
import com.example.birdsofafeather.model.db.StudentWithCourses;

import java.util.List;

public class SavedSessionsViewAdapter extends RecyclerView.Adapter<SavedSessionsViewAdapter.ViewHolder>{
    private final List<SessionWithStudents> savedSessions;

    public SavedSessionsViewAdapter(List<SessionWithStudents> savedSessions) {
        super();
        this.savedSessions = savedSessions;
    }

    @NonNull
    @Override
    public SavedSessionsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.session_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedSessionsViewAdapter.ViewHolder holder, int position) {
        holder.setSessions(savedSessions.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return savedSessions.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView savedSessionsView;

        ViewHolder(View itemView) {
            super(itemView);
            this.savedSessionsView = itemView.findViewById(R.id.saved_session_row_name);
        }

        public void setSessions(String session) {
            this.savedSessionsView.setText(session);
        }

        @Override
        public void onClick(View view) {

        }
    }
}

