package com.example.androidproject;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Pair<String, String>> courses;

    public CourseAdapter(List<Pair<String, String>> courses) {
        this.courses = courses;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Pair<String, String> course = courses.get(position);
        holder.courseTitle.setText(course.first);
        holder.instructorName.setText(course.second);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseTitle;
        TextView instructorName;

        CourseViewHolder(View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.course_title);
            instructorName = itemView.findViewById(R.id.instructor_name);
        }
    }
}

