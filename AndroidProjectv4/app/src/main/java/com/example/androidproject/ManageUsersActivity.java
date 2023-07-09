package com.example.androidproject;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManageUsersActivity extends AppCompatActivity {

    private ImageButton instructorButton, studentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        instructorButton = findViewById(R.id.view_instructors_btn);
        studentButton = findViewById(R.id.view_students_btn);
        String email = getIntent().getStringExtra("email");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        ColorStateList iconColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_green));
        bottomNavigationView.setItemIconTintList(iconColor);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent editIntent;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    editIntent = new Intent(ManageUsersActivity.this, AdminActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_cources:
                    editIntent = new Intent(ManageUsersActivity.this, ApplicationsActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_profiles:
                    break;
                case R.id.nav_settings:
                    editIntent = new Intent(ManageUsersActivity.this, MainActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
            }
            return true;
        });

        instructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageUsersActivity.this, ViewInstructorsActivity.class);
                startActivity(intent);
            }
        });

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageUsersActivity.this, ViewStudentsActivity.class);
                startActivity(intent);
            }
        });
    }
}
