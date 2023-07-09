package com.example.androidproject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.List;

public class StudentApplyForCourseActivity extends AppCompatActivity {

    TextView textViewCourseTitle, textViewTopics, textViewPrerequisites, textViewInstructorEmail, textViewStartDate,
            textViewEndDate, textViewVenue, textViewSchedule;
    Button buttonApply;
    DatabaseHelper db;
    Course course;
    CourseOffering courseOffering;
    ImageView imageViewCoursePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_apply_for_course);


        textViewCourseTitle = findViewById(R.id.textViewCourseTitle);
        textViewTopics = findViewById(R.id.textViewTopics);
        textViewPrerequisites = findViewById(R.id.textViewPrerequisites);
        textViewInstructorEmail = findViewById(R.id.textViewInstructorEmail);
        textViewStartDate = findViewById(R.id.textViewStartDate);
        textViewEndDate = findViewById(R.id.textViewEndDate);
        textViewVenue = findViewById(R.id.textViewVenue);
        textViewSchedule = findViewById(R.id.textViewSchedule);
        buttonApply = findViewById(R.id.buttonApply);
        imageViewCoursePhoto = findViewById(R.id.imageViewCoursePhoto);

        int darkGreen = ContextCompat.getColor(this, R.color.dark_green);
        buttonApply.setBackgroundColor(darkGreen);

        db = new DatabaseHelper(this);

        // Get the Course ID from the Intent
        String courseId = getIntent().getStringExtra("courseId");
        String email = getIntent().getStringExtra("email");
        // Use the Course ID to get the Course from the database
        course = db.getCourse(courseId);
        courseOffering = db.getCourseOffering(courseId);

        textViewCourseTitle.setText(course.getTitle());
        textViewTopics.setText(course.getMainTopics());
        textViewPrerequisites.setText(course.getPrerequisites().toString().replace("[", "").replace("]", "").replace(",", " "));
        textViewInstructorEmail.setText(courseOffering.getInstructorEmail());
        textViewStartDate.setText(courseOffering.getStartDate());
        textViewEndDate.setText(courseOffering.getRegistrationDeadline());
        textViewVenue.setText(courseOffering.getVenue());
        textViewSchedule.setText(courseOffering.getSchedule());
        // Display the current course photo

        Bitmap bitmap = db.getCoursePhoto(course);
        if (bitmap != null) {
            imageViewCoursePhoto.setImageBitmap(bitmap);
        }

        buttonApply.setOnClickListener(v -> {
            boolean hasApplied = db.checkIfStudentAppliedBefore(email, courseId);

            // Check for time conflicts with other enrolled courses
            boolean hasTimeConflict = db.checkForTimeConflict(email, courseOffering.getSchedule());
            boolean hasCompletedPrerequisites;

            List<String> prerequisites = course.getPrerequisites();
            for (int i = 0; i < prerequisites.size(); i++) {
                prerequisites.set(i, prerequisites.get(i).replace("[", "").replace("]", ""));
            }
            if (prerequisites.isEmpty()) {

                hasCompletedPrerequisites = true;
            } else {
                // Check if the student has completed the prerequisites
                hasCompletedPrerequisites = db.checkPrerequisiteCompletion(email, prerequisites);
            }
            if (hasApplied) {
                Toast.makeText(this, "You have already applied for this course.", Toast.LENGTH_SHORT).show();
                return;
            } else if (hasTimeConflict) {
                Toast.makeText(this, "There is a time conflict with your enrolled courses.", Toast.LENGTH_SHORT).show();
                return;
            } else if (!hasCompletedPrerequisites) {
                Toast.makeText(this, "You have not completed the prerequisites for this course.", Toast.LENGTH_SHORT).show();
                return;
            }
            // Apply for the course if all conditions are met
            else if (!hasApplied && !hasTimeConflict && hasCompletedPrerequisites) {
                Application application = new Application();
                application.setApproved(0);
                application.setCourseId(courseId);
                application.setStudentId(email);
                Boolean result = db.insertApplication(application);
                if (result) {
                    Toast.makeText(this, "You have Successfully applied for this course.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You have failed to apply for this course.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
