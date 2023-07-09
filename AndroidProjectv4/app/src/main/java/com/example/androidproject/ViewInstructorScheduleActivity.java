package com.example.androidproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewInstructorScheduleActivity extends AppCompatActivity {


    private DatabaseHelper databaseHelper;
    private RecyclerView monday_wednesday_recycler_view;
    private RecyclerView tuesday_thursday_recycler_view;

    private CourseAdapter monday_wednesdayCourseAdapter;
    private CourseAdapter tuesday_thursdayCourseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instructor_schedule);

        Intent intent_1 = getIntent();
        String email = getIntent().getStringExtra("email");
        databaseHelper = new DatabaseHelper(this);
        monday_wednesday_recycler_view = findViewById(R.id.monday_wednesday_recycler_view);
        tuesday_thursday_recycler_view = findViewById(R.id.tuesday_thursday_recycler_view);

        // Set the LayoutManager for the RecyclerView
        monday_wednesday_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        tuesday_thursday_recycler_view.setLayoutManager(new LinearLayoutManager(this));


        List<Pair<String, String>> courseList1 = databaseHelper.getInstructorMondayCourses(email);
        monday_wednesdayCourseAdapter = new CourseAdapter(courseList1);

        List<Pair<String, String>> courseList2 = databaseHelper.getInstructorTuesdayCourses(email);
        tuesday_thursdayCourseAdapter = new CourseAdapter(courseList2);

        monday_wednesday_recycler_view.setAdapter(monday_wednesdayCourseAdapter);
        tuesday_thursday_recycler_view.setAdapter(tuesday_thursdayCourseAdapter);

        // Remember to run the database access code in a separate thread if it causes performance issues
    }
}
