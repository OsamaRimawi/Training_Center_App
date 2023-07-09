package com.example.androidproject;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ApplicationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApplicationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);

        String email = getIntent().getStringExtra("email");

        DatabaseHelper db = new DatabaseHelper(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        ColorStateList iconColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_green));
        bottomNavigationView.setItemIconTintList(iconColor);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent editIntent;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    editIntent = new Intent(ApplicationsActivity.this, AdminActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_cources:
                    break;
                case R.id.nav_profiles:
                    editIntent = new Intent(ApplicationsActivity.this, ManageUsersActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_settings:
                    editIntent = new Intent(ApplicationsActivity.this, MainActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
            }
            return true;
        });


        List<Application> applications = db.getAllApplications();

        recyclerView = findViewById(R.id.applications_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApplicationsAdapter(this, applications, db);
        recyclerView.setAdapter(adapter);

    }
}
