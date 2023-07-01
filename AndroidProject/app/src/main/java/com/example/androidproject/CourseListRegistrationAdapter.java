package com.example.androidproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseListRegistrationAdapter extends RecyclerView.Adapter<CourseListRegistrationAdapter.CourseViewHolder> {

    private List<AvailableCourse> courses;
    private OnItemClickListener listener;
    private DatabaseHelper databaseHelper;


    public interface OnItemClickListener {
        void onItemClick(AvailableCourse availableCourse);
    }

    public CourseListRegistrationAdapter(List<AvailableCourse> courses, OnItemClickListener listener, DatabaseHelper databaseHelper) {
        this.courses = courses;
        this.listener = listener;
        this.databaseHelper = databaseHelper; // Initialize it here
    }


    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        AvailableCourse availableCourse = courses.get(position);
        // Assuming getCourse() returns a Course object given its ID
        Course course = databaseHelper.getCourse(availableCourse.getCourseId());
        if (course != null) {
            holder.courseTitle.setText(course.getTitle());
        }
        holder.itemView.setOnClickListener(v -> listener.onItemClick(availableCourse));
    }


    @Override
    public int getItemCount() {
        return courses.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseTitle;

        CourseViewHolder(View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.course_title);
        }
    }
}

