package com.example.androidproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplicationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApplicationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);

        DatabaseHelper db = new DatabaseHelper(this);

        List<Application> applications = db.getAllApplications();

        recyclerView = findViewById(R.id.applications_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApplicationsAdapter(this, applications, db);
        recyclerView.setAdapter(adapter);
    }
}
