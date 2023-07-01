package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;

import java.util.List;

public class PreviousOfferingsAdapter extends RecyclerView.Adapter<PreviousOfferingsAdapter.ViewHolder> {
    private List<PreviousOffering> previousOfferingsList;

    public PreviousOfferingsAdapter(List<PreviousOffering> previousOfferingsList) {
        this.previousOfferingsList = previousOfferingsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_offering_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PreviousOffering offering = previousOfferingsList.get(position);
        //holder.courseNumberTextView.setText(offering.getCourseNumber());
        holder.courseTitleTextView.setText("Title: " + offering.getCourseTitle());
        holder.dateTextView.setText("Date: " + offering.getDate());
        holder.timeTextView.setText("Time: " + offering.getTime());
        holder.numberOfStudentsTextView.setText("Number of students: " + String.valueOf(offering.getNumberOfStudents()));
        holder.venueTextView.setText("Venue: " + offering.getVenue());
        holder.instructorNameTextView.setText("Instructor Name: " + offering.getInstructorName());
    }

    @Override
    public int getItemCount() {
        return previousOfferingsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView courseTitleTextView;
        private TextView dateTextView;
        private TextView timeTextView;
        private TextView numberOfStudentsTextView;
        private TextView venueTextView;
        private TextView instructorNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            courseTitleTextView = itemView.findViewById(R.id.tv_course_title);
            dateTextView = itemView.findViewById(R.id.tv_date);
            timeTextView = itemView.findViewById(R.id.tv_time);
            numberOfStudentsTextView = itemView.findViewById(R.id.tv_number_of_students);
            venueTextView = itemView.findViewById(R.id.tv_venue);
            instructorNameTextView = itemView.findViewById(R.id.tv_instructor_name);
        }
    }
}
