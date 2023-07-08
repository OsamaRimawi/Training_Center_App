package com.example.androidproject;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    Button manageCourses, viewRegistration, viewPrevious;
    TextView tvWelcome;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;//tv_welcome

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        manageCourses = findViewById(R.id.btn_manage_courses);
        viewRegistration = findViewById(R.id.btn_view_registrations);
        viewPrevious= findViewById(R.id.btn_view_previous_offerings);

        int darkGreen = ContextCompat.getColor(this, R.color.dark_green);
        databaseHelper = new DatabaseHelper(this);

        manageCourses.setBackgroundColor(darkGreen);
        viewRegistration.setBackgroundColor(darkGreen);
        viewPrevious.setBackgroundColor(darkGreen);

        tvWelcome = findViewById(R.id.textview);
        String email = getIntent().getStringExtra("email");
        Admin admin = databaseHelper.getAdmin(email);
        tvWelcome.setText("Welcome Back, " + admin.getFullName());

        recyclerView = findViewById(R.id.pr_courses);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        ColorStateList iconColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_green));
        bottomNavigationView.setItemIconTintList(iconColor);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    break;
                case R.id.nav_cources:
                    Intent intent = new Intent(AdminActivity.this, ApplicationsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_profiles:
                    intent = new Intent(AdminActivity.this, ManageUsersActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_settings:
                    intent = new Intent(AdminActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
            }
            return true;
        });

        List<Pair<String, String>> courseList = databaseHelper.getLastFiveCourses();
        courseAdapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(courseAdapter);

        manageCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ManageCoursesActivity.class);
                startActivity(intent);
                new FetchCoursesTask().execute();
            }
        });

        viewRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ViewRegistrationsActivity.class);

                startActivity(intent);
            }
        });

        viewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, previousOfferingsActivity.class);

                startActivity(intent);
            }
        });

    }

    private class FetchCoursesTask extends AsyncTask<Void, Void, List<Pair<String, String>>> {
        @Override
        protected List<Pair<String, String>> doInBackground(Void... voids) {
            List<Pair<String, String>> courseList = databaseHelper.getLastFiveCourses();


            return courseList;
        }

        @Override
        protected void onPostExecute(List<Pair<String, String>> courseList) {
            courseAdapter = new CourseAdapter(courseList);
            recyclerView.setAdapter(courseAdapter);
            courseAdapter.notifyDataSetChanged();
        }
    }

}

