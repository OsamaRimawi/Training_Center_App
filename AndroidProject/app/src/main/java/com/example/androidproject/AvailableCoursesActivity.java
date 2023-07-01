package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AvailableCoursesActivity extends AppCompatActivity {
    /*
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_courses);

        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.rv_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<AvailableCourse> courseList = databaseHelper.getAllAvailableCourses();
        courseAdapter = new CourseAdapter(courseList, clickedCourse -> {
            // Add onClick actions here
        });

        recyclerView.setAdapter(courseAdapter);
    }*/
}
