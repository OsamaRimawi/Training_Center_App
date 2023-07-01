package com.example.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ApplicationsAdapter extends RecyclerView.Adapter<ApplicationsAdapter.ApplicationViewHolder> {

    private List<Application> applicationsList;
    private Context context;
    private DatabaseHelper databaseHelper;

    public ApplicationsAdapter(Context context, List<Application> applicationsList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.applicationsList = applicationsList;
        this.databaseHelper = databaseHelper;
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        TextView courseName;
        Button acceptButton;
        Button rejectButton;

        public ApplicationViewHolder(View v) {
            super(v);
            studentName = v.findViewById(R.id.student_name);
            courseName = v.findViewById(R.id.course_name);
            acceptButton = v.findViewById(R.id.accept_button);
            rejectButton = v.findViewById(R.id.reject_button);
        }
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_card, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder holder, int position) {
        // Replace with actual student and course names
        holder.studentName.setText(applicationsList.get(position).getStudentId());
        Course course = databaseHelper.getCourse(applicationsList.get(position).getCourseId());
        holder.courseName.setText(course.getTitle());

        holder.acceptButton.setOnClickListener(v -> {
            // Update the application object and persist the change in the database
            Application application = applicationsList.get(holder.getAdapterPosition());
            application.setApproved(1);
            databaseHelper.updateApplication(application);
            databaseHelper.insertRegistration(application.getCourseId(), application.getStudentId());
            applicationsList.remove(application);
            notifyDataSetChanged();

            String studentEmail = application.getStudentId(); // Replace with the actual student's email
            String notificationTitle = "Your Application Has Been Updated";
            String notificationMessage = "Admin has responded to your application!";

            boolean send = databaseHelper.sendNotificationToStudent(studentEmail, notificationTitle, notificationMessage);

        });

        holder.rejectButton.setOnClickListener(v -> {
            // Update the application object and persist the change in the database
            Application application = applicationsList.get(holder.getAdapterPosition());
            application.setApproved(2);
            databaseHelper.updateApplication(application);
            applicationsList.remove(application);
            notifyDataSetChanged();

            String studentEmail = application.getStudentId(); // Replace with the actual student's email
            String notificationTitle = "Your Application Has Been Updated";
            String notificationMessage = "Admin has responded to your application!";

            boolean send = databaseHelper.sendNotificationToStudent(studentEmail, notificationTitle, notificationMessage);

        });

    }

    @Override
    public int getItemCount() {
        return applicationsList.size();
    }
}

