package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class ViewRegistrationsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listView;
    private ArrayAdapter<String> courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registrations);

        databaseHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.lv_courses);

        List<AvailableCourse> courseList = databaseHelper.getAllAllAvailableCourses();
        List<String> courseNames = new ArrayList<>();

        for (AvailableCourse course : courseList) {
            courseNames.add(databaseHelper.getCourse(course.getCourseId()).getTitle());
        }

        courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseNames);
        listView.setAdapter(courseAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            AvailableCourse clickedCourse = courseList.get(position);
            Intent intent = new Intent(ViewRegistrationsActivity.this, RegistrationInfoActivity.class);
            intent.putExtra("course_id", clickedCourse.getCourseId());
            startActivity(intent);
        });
    }
}
