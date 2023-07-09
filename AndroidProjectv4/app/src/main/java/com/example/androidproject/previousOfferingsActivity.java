package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class previousOfferingsActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_offerings);

        databaseHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.ended_courses_list);

        List<Course> endedCourses = databaseHelper.getEndedCourses();
        ArrayAdapter<Course> adapter = new ArrayAdapter<>(this, R.layout.custom_list_item_1, endedCourses);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Course clickedCourse = endedCourses.get(position);
            Intent intent = new Intent(previousOfferingsActivity.this, PreviousOfferingsInfoActivity.class);
            Log.d("course id",endedCourses.get(position).getId());

            intent.putExtra("course_id", endedCourses.get(position).getId());
            startActivity(intent);
        });
    }
}
