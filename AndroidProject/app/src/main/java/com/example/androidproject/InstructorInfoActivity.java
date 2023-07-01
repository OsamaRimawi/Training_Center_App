package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class InstructorInfoActivity extends AppCompatActivity {

    private ImageView profilePicture;
    private TextView emailTextView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView mobileTextView;
    private TextView addressTextView;
    private TextView specializationTextView;
    private TextView degreeTextView;
    private TextView coursesTextView;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_info);

        databaseHelper = new DatabaseHelper(this);

        // Initialize TextViews
        profilePicture = findViewById(R.id.iv_inst_pic);
        emailTextView = findViewById(R.id.email_textview);
        firstNameTextView = findViewById(R.id.firstname_textview);
        lastNameTextView = findViewById(R.id.lastname_textview);
        mobileTextView = findViewById(R.id.mobile_textview);
        addressTextView = findViewById(R.id.address_textview);
        specializationTextView = findViewById(R.id.specialization_textview_info);
        degreeTextView = findViewById(R.id.degree_textview);
        coursesTextView = findViewById(R.id.courses_textview);

        // Get the student information from intent
        Intent intent = getIntent();
        String instructor_email = intent.getStringExtra("instructor_email");
        Instructor instructor = databaseHelper.getInstructor(instructor_email);
        String firstName = instructor.getFirstName();
        String lastName = instructor.getLastName();
        String mobileNumber = instructor.getMobileNumber();
        String address = instructor.getAddress();
        byte[] photo = instructor.getPhoto();
        String specialization = instructor.getSpecialization();
        String degree = instructor.getDegree();
        String courses = instructor.getCoursesCanTeach().toString();



        // Set the student information in the TextViews
        firstNameTextView.setText("First Name: " + firstName);
        lastNameTextView.setText("Last Name: " + lastName);
        emailTextView.setText("Email: " + instructor_email);
        mobileTextView.setText("Mobile Number: " + mobileNumber);
        addressTextView.setText("Address: " + address);
        specializationTextView.setText(specialization);
        degreeTextView.setText(degree);
        coursesTextView.setText(courses);

        if (photo != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            profilePicture.setImageBitmap(bitmap);
        }
    }
}
