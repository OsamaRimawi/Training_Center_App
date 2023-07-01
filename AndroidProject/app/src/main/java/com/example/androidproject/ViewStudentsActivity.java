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

public class ViewStudentsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        databaseHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.students_list_view);

        List<Student> studentsList = databaseHelper.getAllStudents();
        List<String> studentNames = new ArrayList<>();
        for (Student student : studentsList) {
            studentNames.add(student.getName()); // assuming Student class has a getName() method
        }

        ArrayAdapter<String> studentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
        listView.setAdapter(studentAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Student clickedStudent = studentsList.get(position);
            Intent intent = new Intent(ViewStudentsActivity.this, StudentInfoActivity.class);
            intent.putExtra("student_email", clickedStudent.getEmail());
            startActivity(intent);
        });
    }
}
