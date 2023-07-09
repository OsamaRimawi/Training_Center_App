package com.example.androidproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


//public class RegistrationAdapter extends RecyclerView.Adapter<RegistrationAdapter.ViewHolder> {
/*
    private List<Registration> registrationList;
    private OnItemClickListener listener;
    private static DatabaseHelper databaseHelper;

    public interface OnItemClickListener {
        void onItemClick(AvailableCourse availableCourse);
    }

    public RegistrationAdapter(List<Registration> registrationList, OnItemClickListener listener, DatabaseHelper databaseHelper) {
        this.registrationList = registrationList;
        this.listener = listener;
        this.databaseHelper = databaseHelper;  // Initialize it here
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registration_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(registrationList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return registrationList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //TextView courseId;
        TextView instructorEmail;
        TextView registrationDeadline;
        TextView startDate;
        TextView schedule;
        TextView venue;
        TextView studentsRegistered;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //courseId = itemView.findViewById(R.id.tv_course_id);
            instructorEmail = itemView.findViewById(R.id.tv_instructor_email);
            registrationDeadline = itemView.findViewById(R.id.tv_registration_deadline);
            startDate = itemView.findViewById(R.id.tv_start_date);
            schedule = itemView.findViewById(R.id.tv_schedule);
            venue = itemView.findViewById(R.id.tv_venue);
            studentsRegistered = itemView.findViewById(R.id.tv_students_registered);
        }

        public void bind(final Registration registration, final OnItemClickListener listener) {
            // Assuming DatabaseHelper object db is available here
            AvailableCourse availableCourse = databaseHelper.getAvailableCourse(registration.getCourseId());

            if (availableCourse != null) {
                //courseId.setText(availableCourse.getCourseId());
                instructorEmail.setText(availableCourse.getInstructorEmail());
                registrationDeadline.setText(availableCourse.getRegistrationDeadline());
                startDate.setText(availableCourse.getStartDate());
                schedule.setText(availableCourse.getSchedule());
                venue.setText(availableCourse.getVenue());

                // Assuming getStudentsRegisteredInCourse() returns a list of students
                List<Student> registeredStudents = databaseHelper.getStudentsRegisteredInCourse(availableCourse.getCourseId());
                //studentsRegistered.setText(registeredStudents.size());

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(availableCourse);
                    }
                });
            }
        }

    }*/
//}
