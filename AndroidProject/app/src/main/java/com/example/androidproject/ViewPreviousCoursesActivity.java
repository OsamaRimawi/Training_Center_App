package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ViewPreviousCoursesActivity extends AppCompatActivity {


    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;//tv_welcome

    Button mySchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_previous_courses);

        mySchedule = findViewById(R.id.btn_my_schedule);

        Intent intent_1 = getIntent();
        String email = getIntent().getStringExtra("email");
        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.pr_courses);

        // Set the LayoutManager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_cources);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent editIntent;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    editIntent = new Intent(ViewPreviousCoursesActivity.this, InstructorActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_cources:
                    break;
                case R.id.nav_profiles:
                    editIntent = new Intent(ViewPreviousCoursesActivity.this, InstructorCoursesStudentsActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_settings:
                    editIntent = new Intent(ViewPreviousCoursesActivity.this, InstructorEditActivity.class);
                    editIntent.putExtra("email", email);
                    startActivity(editIntent);
                    break;
                case R.id.nav_logout:
                    Intent intent = new Intent(ViewPreviousCoursesActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
            }
            return true;
        });

        List<Pair<String, String>> courseList = databaseHelper.getInstructorPreviousCourses(email);
        courseAdapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(courseAdapter);

        mySchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(ViewPreviousCoursesActivity.this, ViewInstructorScheduleActivity.class);
                editIntent.putExtra("email", email);
                startActivity(editIntent);

            }
        });
    }
}
