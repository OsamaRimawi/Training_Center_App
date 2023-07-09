package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class PreviousOfferingsInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PreviousOfferingsAdapter adapter;
    private List<PreviousOffering> previousOfferingsList;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_offerings_info);

        recyclerView = findViewById(R.id.rv_previous_offerings);
        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        String course_id = intent.getStringExtra("course_id");
        Log.d("course id",course_id);
        Course course = db.getCourse(course_id);

        // Fetch the previous offerings from the database
        previousOfferingsList = db.getPreviousOfferings(course);

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PreviousOfferingsAdapter(previousOfferingsList);
        recyclerView.setAdapter(adapter);
    }
}
