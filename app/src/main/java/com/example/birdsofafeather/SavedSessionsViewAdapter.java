package com.example.birdsofafeather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsofafeather.model.db.Course;
import com.example.birdsofafeather.model.db.StudentWithCourses;

import java.util.List;

public class SavedSessionsViewAdapter extends RecyclerView.Adapter<SavedSessionsViewAdapter.ViewHolder>{
    private final List<String> testSavedSessions;

    public SavedSessionsViewAdapter(List<String> savedSessions) {
        super();
        this.testSavedSessions = savedSessions;
    }

    @NonNull
    @Override
    public com.example.birdsofafeather.SavedSessionsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.session_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setSessions(testSavedSessions.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {
        private final TextView savedSessionsView;

        ViewHolder(View itemView) {
            super(itemView);
            this.savedSessionsView = itemView.findViewById(R.id.saved_session_row_name);
        }

        public void setSessions(String session) {
            this.savedSessionsView.setText(session);
        }

    }
}

