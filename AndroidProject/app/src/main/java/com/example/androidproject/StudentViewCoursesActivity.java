package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class StudentViewCoursesActivity extends AppCompatActivity {


    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;//tv_welcome
    private RadioGroup radioGroupViewMode;

    Button myApplictions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_courses);

        myApplictions = findViewById(R.id.btn_my_applications);
        Intent intent_1 = getIntent();
        String email = getIntent().getStringExtra("email");
        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.pr_courses);

        // Set the LayoutManager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Pair<String, String>> courseList = databaseHelper.getAllAvailableCourses();
        courseAdapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(courseAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_history);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent editIntent;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    editIntent = new Intent(StudentViewCoursesActivity.this, StudentActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_search:
                    editIntent = new Intent(StudentViewCoursesActivity.this, StudentSearchCoursesActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_history:
                    break;
                case R.id.nav_settings:
                    editIntent = new Intent(StudentViewCoursesActivity.this, StudentEditActivity.class);
                    editIntent.putExtra("email", email);
                    startActivity(editIntent);
                    break;
                case R.id.nav_logout:
                    Intent intent = new Intent(StudentViewCoursesActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
            }
            return true;
        });


        radioGroupViewMode = findViewById(R.id.radioGroupViewMode);

// Set the first RadioButton as checked
        radioGroupViewMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle radio button selection change event
                if (checkedId == R.id.radioButtonAllCourses) {
                    List<Pair<String, String>> courseList = databaseHelper.getAllAvailableCourses();
                    courseAdapter = new CourseAdapter(courseList);
                    recyclerView.setAdapter(courseAdapter);

                } else if (checkedId == R.id.radioButtonStudentCourses) {
                    List<Pair<String, String>> courseList = databaseHelper.getStudentPreviousCourses(email);
                    courseAdapter = new CourseAdapter(courseList);
                    recyclerView.setAdapter(courseAdapter);
                }
            }
        });


        myApplictions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(StudentViewCoursesActivity.this, StudentApplicationsActivity.class);
                editIntent.putExtra("email", email);
                startActivity(editIntent);

            }
        });

    }
}
