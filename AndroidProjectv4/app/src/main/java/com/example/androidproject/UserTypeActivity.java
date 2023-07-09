package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class UserTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        ImageButton adminButton = findViewById(R.id.adminButton);
        ImageButton studentButton = findViewById(R.id.studentButton);
        ImageButton teacherButton = findViewById(R.id.teacherButton);

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start StudentSignUpActivity
                Intent intent = new Intent(UserTypeActivity.this, AdminSignUpActivity.class);

                // Start the new Activity
                startActivity(intent);
            }
        });
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start StudentSignUpActivity
                Intent intent = new Intent(UserTypeActivity.this, StudentSignUpActivity.class);

                // Start the new Activity
                startActivity(intent);
            }
        });
        teacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start StudentSignUpActivity
                Intent intent = new Intent(UserTypeActivity.this, InstructorSignUpActivity.class);

                // Start the new Activity
                startActivity(intent);
            }
        });
    }

}
