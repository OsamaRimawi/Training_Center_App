package com.example.androidproject;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplicationReviewAdapter extends RecyclerView.Adapter<ApplicationReviewAdapter.ApplicationReviewViewHolder> {

    private List<Application> applicationsList;
    private DatabaseHelper databaseHelper;

    public ApplicationReviewAdapter(List<Application> applicationsList, DatabaseHelper databaseHelper) {
        this.applicationsList = applicationsList;
        this.databaseHelper = databaseHelper;
    }

    public static class ApplicationReviewViewHolder extends RecyclerView.ViewHolder {
        TextView courseTitle;
        TextView applicationStatus;
        Button withdrawButton;

        public ApplicationReviewViewHolder(View v) {
            super(v);
            courseTitle = v.findViewById(R.id.appcourse_name);
            applicationStatus = v.findViewById(R.id.aplication_status);
            withdrawButton = v.findViewById(R.id.withdraw_button);
        }
    }

    @NonNull
    @Override
    public ApplicationReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicationreviewcard, parent, false);
        return new ApplicationReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplicationReviewViewHolder holder, int position) {
        // Replace with actual student and course names
        Course course = databaseHelper.getCourse(applicationsList.get(position).getCourseId());
        holder.courseTitle.setText(course.getTitle());

        if (applicationsList.get(position).getApproved() == 0) {
            holder.applicationStatus.setText("Waiting For Response");
            holder.applicationStatus.setTextColor(Color.YELLOW);

        } else if (applicationsList.get(position).getApproved() == 1) {
            holder.applicationStatus.setText("Accepted");
            holder.applicationStatus.setTextColor(Color.GREEN);

        } else if (applicationsList.get(position).getApproved() == 2) {
            holder.applicationStatus.setText("Rejected");
            holder.applicationStatus.setTextColor(Color.RED);

        }

        holder.withdrawButton.setOnClickListener(v -> {
            // Update the application object and persist the change in the database
            Application application = applicationsList.get(holder.getAdapterPosition());
            databaseHelper.deleteApplication(application);
            applicationsList.remove(application);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return applicationsList.size();
    }
}

