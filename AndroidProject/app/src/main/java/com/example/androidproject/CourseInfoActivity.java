package com.example.androidproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CourseInfoActivity extends AppCompatActivity {
    TextView title, mainTopics, prerequisites;
    ImageView courseImage;
    Button editButton, deleteButton, availableButton;

    DatabaseHelper db;
    String courseId;

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        // Retrieve the course from the database
        Course course = db.getCourse(courseId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);

        title = findViewById(R.id.tv_course_title);
        mainTopics = findViewById(R.id.tv_course_topics);
        prerequisites = findViewById(R.id.tv_course_prerequisites);
        courseImage = findViewById(R.id.iv_course_photo);
        editButton = findViewById(R.id.btn_edit_course);
        deleteButton = findViewById(R.id.btn_delete_course);
        availableButton = findViewById(R.id.btn_make_available);

        db = new DatabaseHelper(this);

        // Get course ID from intent
        Intent intent = getIntent();
        courseId = getIntent().getStringExtra("courseId");

        loadData();
        // Retrieve the course from the database
        Course course = db.getCourse(courseId);
        title.setText(course.getTitle());
        mainTopics.setText(course.getMainTopics());
        prerequisites.setText(course.getPrerequisites().toString());
        byte[] photoData = course.getPhoto(); // Assuming course.getPhoto() returns the byte[] photo data

        if (photoData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);
            courseImage.setImageBitmap(bitmap);
        }

        editButton.setOnClickListener(v -> {
            Intent editIntent = new Intent(CourseInfoActivity.this, EditCourseActivity.class);
            editIntent.putExtra("courseId", courseId);
            startActivity(editIntent);
        });

        deleteButton.setOnClickListener(v -> {
            // Delete the course from the database
            db.deleteCourse(courseId);
            // Close this activity
            Intent editIntent = new Intent(CourseInfoActivity.this, ManageCoursesActivity.class);
            editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear all the activities above in the stack
            startActivity(editIntent);
            //finish();
        });

        availableButton.setOnClickListener(v -> {
            Course c = db.getCourse(courseId);
            if (c.getAvailable() == 0) {
                Intent editIntent = new Intent(CourseInfoActivity.this, MakeCourseAvailable.class);
                editIntent.putExtra("CourseObject", c);
                startActivity(editIntent);
            } else {
                Toast.makeText(CourseInfoActivity.this, "This course is already available.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

