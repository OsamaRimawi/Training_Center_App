package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentApplicationsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private ApplicationReviewAdapter adapter;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_applications);

        databaseHelper = new DatabaseHelper(this);
        // Get course ID from intent
        Intent intent = getIntent();
        email = getIntent().getStringExtra("email");
        recyclerView = findViewById(R.id.applications_recycler_view);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Application> applications = databaseHelper.getStudentApplications(email);
        adapter = new ApplicationReviewAdapter(applications, databaseHelper);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
