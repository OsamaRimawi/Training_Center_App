package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ViewInstructorsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instructors);

        databaseHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.instructors_list_view);

        List<Instructor> instructorsList = databaseHelper.getAllInstructors();
        List<String> instructorNames = new ArrayList<>();
        for (Instructor instructor : instructorsList) {
            instructorNames.add(instructor.getFirstName() + " " + instructor.getLastName());
        }

        ArrayAdapter<String> instructorAdapter = new ArrayAdapter<>(this, R.layout.custom_list_item_1, instructorNames);
        listView.setAdapter(instructorAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Instructor clickedInstructor = instructorsList.get(position);
            Intent intent = new Intent(ViewInstructorsActivity.this, InstructorInfoActivity.class);
            intent.putExtra("instructor_email", clickedInstructor.getEmail());
            startActivity(intent);
        });
    }
}
