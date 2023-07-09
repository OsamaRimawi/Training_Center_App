package com.example.androidproject;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class InstructorActivity extends AppCompatActivity {


    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;

    Button mySchedule;

    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        mySchedule = findViewById(R.id.btn_my_schedule);
        int darkGreen = ContextCompat.getColor(this, R.color.dark_green);
        mySchedule.setBackgroundColor(darkGreen);

        databaseHelper = new DatabaseHelper(this);

        tvWelcome = findViewById(R.id.textview);
        String email = getIntent().getStringExtra("email");
        Instructor instructor = databaseHelper.getInstructor(email);
        tvWelcome.setText("Welcome Back, " + instructor.getFullName());

        recyclerView = findViewById(R.id.pr_courses);

        // Set the LayoutManager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        ColorStateList iconColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_green));
        bottomNavigationView.setItemIconTintList(iconColor);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent editIntent;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    break;
                case R.id.nav_cources:
                    editIntent = new Intent(InstructorActivity.this, ViewPreviousCoursesActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_profiles:
                    editIntent = new Intent(InstructorActivity.this, InstructorCoursesStudentsActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_settings:
                    editIntent = new Intent(InstructorActivity.this, InstructorEditActivity.class);
                    editIntent.putExtra("email", email);
                    startActivity(editIntent);
                    break;
                case R.id.nav_logout:
                    Intent intent = new Intent(InstructorActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
            }
            return true;
        });

        List<Pair<String, String>> courseList = databaseHelper.getLastFiveCourses();
        courseAdapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(courseAdapter);

        mySchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(InstructorActivity.this, ViewInstructorScheduleActivity.class);
                editIntent.putExtra("email", email);
                startActivity(editIntent);

            }
        });
    }
}
