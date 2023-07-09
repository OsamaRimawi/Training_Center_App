package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseStudentsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recycler_view;

    private StudentAdapter studentAdapter;
    TextView title;
    ImageView courseImage;

    String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_students);
        databaseHelper = new DatabaseHelper(this);

        title = findViewById(R.id.text_view);

        // Get course ID from intent
        Intent intent = getIntent();
        courseId = getIntent().getStringExtra("courseId");

        Course course = databaseHelper.getCourse(courseId);

        title.setText(course.getTitle() + " Course Students:");

        recycler_view = findViewById(R.id.recycler_view);

        // Set the LayoutManager for the RecyclerView
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        List<String[]> courseList1 = databaseHelper.getCourseStudents(courseId);
        studentAdapter = new StudentAdapter(courseList1);
        recycler_view.setAdapter(studentAdapter);
    }
}
