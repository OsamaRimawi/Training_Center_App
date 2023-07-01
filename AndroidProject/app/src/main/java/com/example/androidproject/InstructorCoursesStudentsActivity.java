package com.example.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class InstructorCoursesStudentsActivity extends AppCompatActivity {

    ArrayList<Course> coursesList = new ArrayList<>();
    ArrayAdapter<Course> adapter;


    DatabaseHelper databaseHelper;

    Button mySchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_courses_students);

        ListView lvCourses = findViewById(R.id.lv_courses);

        mySchedule = findViewById(R.id.btn_my_schedule);

        Intent intent_1 = getIntent();
        String email = getIntent().getStringExtra("email");
        databaseHelper = new DatabaseHelper(this);

        new FetchCoursesTask().execute();
        coursesList = databaseHelper.getInstructorCourses(email);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, coursesList);
        lvCourses.setAdapter(adapter);


        lvCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InstructorCoursesStudentsActivity.this, CourseStudentsActivity.class);
                intent.putExtra("courseId", coursesList.get(position).getId());
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_profiles);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent editIntent;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    editIntent = new Intent(InstructorCoursesStudentsActivity.this, InstructorActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_cources:
                    editIntent = new Intent(InstructorCoursesStudentsActivity.this, ViewPreviousCoursesActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_profiles:
                    break;
                case R.id.nav_settings:
                    editIntent = new Intent(InstructorCoursesStudentsActivity.this, InstructorEditActivity.class);
                    editIntent.putExtra("email", email);
                    startActivity(editIntent);
                    break;
                case R.id.nav_logout:
                    Intent intent = new Intent(InstructorCoursesStudentsActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
            }
            return true;
        });


        mySchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(InstructorCoursesStudentsActivity.this, ViewInstructorScheduleActivity.class);
                editIntent.putExtra("email", email);
                startActivity(editIntent);

            }
        });

    }


    private class FetchCoursesTask extends AsyncTask<Void, Void, ArrayList<Course>> {
        @Override
        protected ArrayList<Course> doInBackground(Void... voids) {
            ArrayList<Course> courses = new ArrayList<>();
            String email = getIntent().getStringExtra("email");

            courses = databaseHelper.getInstructorCourses(email);

            return courses;
        }

        @Override
        protected void onPostExecute(ArrayList<Course> courses) {
            coursesList.clear();
            coursesList.addAll(courses);
            adapter.notifyDataSetChanged();
        }
    }

}
