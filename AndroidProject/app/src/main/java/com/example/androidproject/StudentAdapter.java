package com.example.androidproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<String[]> students;

    public StudentAdapter(List<String[]> students) {
        this.students = students;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_card, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        String[] student = students.get(position);
        holder.studentName.setText(student[0]);
        holder.studentLastName.setText(student[1]);
        holder.studentMobile.setText(student[2]);
        holder.studentAddress.setText(student[3]);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        TextView studentLastName;
        TextView studentAddress;
        TextView studentMobile;

        StudentViewHolder(View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name);
            studentLastName = itemView.findViewById(R.id.student_lastname);
            studentAddress = itemView.findViewById(R.id.student_address);
            studentMobile = itemView.findViewById(R.id.student_mobile);

        }
    }
}

