package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RegistrationInfoActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_info);

        databaseHelper = new DatabaseHelper(this);
        //recyclerView = findViewById(R.id.rv_students);
        ListView listView = findViewById(R.id.listview_registrations);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String courseId = getIntent().getStringExtra("course_id");

        // Display course information
        Course course = databaseHelper.getCourse(courseId);
        TextView tvCourseTitle = findViewById(R.id.tv_course_title);
        tvCourseTitle.setText(course.getTitle());

        List<Student> registeredStudents = databaseHelper.getStudentsRegisteredInCourse(courseId);

        List<String> studentNames = new ArrayList<>();
        for (Student student : registeredStudents) {
            studentNames.add(student.getName());
        }

        ArrayAdapter<String> studentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
        listView.setAdapter(studentAdapter);



        /*// Display the list of registered students
        List<Student> registeredStudents = databaseHelper.getStudentsRegisteredInCourse(courseId);
        studentAdapter = new StudentAdapter(registeredStudents);
        recyclerView.setAdapter(studentAdapter);*/
    }
}
