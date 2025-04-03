package com.example.tlustudents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlustudents.R;
import com.example.tlustudents.Student;
import com.example.tlustudents.StudentViewHolder;

public class StudentsAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    private Student[] students;

    public StudentsAdapter(Student[] students){
        this.students = students;
    }



    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_students, parent, false);
        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(students[position]);

    }

    @Override
    public int getItemCount() {
        return students.length;
    }
}
