package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class StudentSearchCoursesActivity extends AppCompatActivity {

    ArrayList<Course> coursesList = new ArrayList<>();
    ArrayAdapter<Course> adapter;
    SearchView searchView;
    ListView lvCourses;
    DatabaseHelper databaseHelper;
    Button myApplictions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search_courses);

        lvCourses = findViewById(R.id.lv_courses);

        myApplictions = findViewById(R.id.btn_my_application);

        Intent intent_1 = getIntent();
        String email = getIntent().getStringExtra("email");
        databaseHelper = new DatabaseHelper(this);

        coursesList = databaseHelper.getAvailableCourses();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, coursesList);
        lvCourses.setAdapter(adapter);


        lvCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StudentSearchCoursesActivity.this, StudentApplyForCourseActivity.class);
                // Assuming your Course class has a method getId that returns the course id
                intent.putExtra("courseId", coursesList.get(position).getId());
                intent.putExtra("email", email);

                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_search);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent editIntent;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    editIntent = new Intent(StudentSearchCoursesActivity.this, StudentActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_search:
                    break;
                case R.id.nav_history:
                    editIntent = new Intent(StudentSearchCoursesActivity.this, StudentViewCoursesActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_settings:
                    editIntent = new Intent(StudentSearchCoursesActivity.this, StudentEditActivity.class);
                    editIntent.putExtra("email", email);
                    startActivity(editIntent);
                    break;
                case R.id.nav_logout:
                    Intent intent = new Intent(StudentSearchCoursesActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
            }
            return true;
        });

        myApplictions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(StudentSearchCoursesActivity.this, StudentApplicationsActivity.class);
                editIntent.putExtra("email", email);
                startActivity(editIntent);
            }
        });

        // Initialize the SearchView
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCourses(newText);
                return true;
            }
        });
    }

    private void filterCourses(String query) {
        coursesList = databaseHelper.getAvailableCourses();

        if (TextUtils.isEmpty(query)) {
            // Show all courses if the query is empty
            adapter.clear();
            adapter.addAll(coursesList);
        } else {
            // Filter the course list based on the query
            ArrayList<Course> filteredList = new ArrayList<>();
            for (Course course : coursesList) {
                if (course.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(course);
                }
            }
            adapter.clear();
            adapter.addAll(filteredList);
        }
        adapter.notifyDataSetChanged(); // Notify the adapter about the dataset change
    }
}