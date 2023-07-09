package com.example.androidproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private static final String MY_CHANNEL_ID = "my_chanel_1";
    private static final String MY_CHANNEL_NAME = "My channel";
    private NotificationManagerCompat notificationManager;


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID, MY_CHANNEL_NAME, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

    public void createNotification(int id, String title, String body) {
        // Create an explicit intent for an activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE); // Add the required flags

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MY_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(id, builder.build());

    }


    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;//tv_welcome

    Button myApplictions;

    TextView tvWelcome;
    List<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        myApplictions = findViewById(R.id.btn_my_applications);
        int darkGreen = ContextCompat.getColor(this, R.color.dark_green);
        myApplictions.setBackgroundColor(darkGreen);
        databaseHelper = new DatabaseHelper(this);

        tvWelcome = findViewById(R.id.textview);
        String email = getIntent().getStringExtra("email");
        Student student = databaseHelper.getStudent(email);
        tvWelcome.setText("Welcome Back, " + student.getName());

        recyclerView = findViewById(R.id.pr_courses);

        createNotificationChannel();

        notifications = databaseHelper.getNotificationsForStudent(email);
        for (Notification notification : notifications) {
            int notificationId = notification.getId();
            String notificationTitle = notification.getTitle();
            String notificationMessage = notification.getMessage();

            createNotification(notificationId, notificationTitle, notificationMessage);

        }
        //databaseHelper.edit();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        ColorStateList iconColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_green));
        bottomNavigationView.setItemIconTintList(iconColor);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent editIntent;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    break;
                case R.id.nav_search:
                    editIntent = new Intent(StudentActivity.this, StudentSearchCoursesActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_history:
                    editIntent = new Intent(StudentActivity.this, StudentViewCoursesActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                    break;
                case R.id.nav_settings:
                    editIntent = new Intent(StudentActivity.this, StudentEditActivity.class);
                    editIntent.putExtra("email", email);
                    startActivity(editIntent);
                    break;
                case R.id.nav_logout:
                    Intent intent = new Intent(StudentActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
            }
            return true;
        });

        List<Pair<String, String>> courseList = databaseHelper.getLastFiveCourses();
        courseAdapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(courseAdapter);

        myApplictions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(StudentActivity.this, StudentApplicationsActivity.class);
                editIntent.putExtra("email", email);
                startActivity(editIntent);

            }
        });
    }
}
