package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    Button manageCourses;
    TextView tvWelcome;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;//tv_welcome

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        manageCourses = findViewById(R.id.btn_manage_courses);

        tvWelcome = findViewById(R.id.textview);
        Intent intent_1 = getIntent();
        String first_name = getIntent().getStringExtra("first_name");
        tvWelcome.setText("Welcome Back, " + first_name.toUpperCase());
        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.pr_courses);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


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
            }
        });

    }
}

