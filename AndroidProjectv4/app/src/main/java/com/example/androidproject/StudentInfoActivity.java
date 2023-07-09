package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentInfoActivity extends AppCompatActivity {

    private ImageView profilePicture;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView emailTextView;
    private TextView mobileNumberTextView;
    private TextView addressTextView;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        databaseHelper = new DatabaseHelper(this);

        // Initialize TextViews
        profilePicture = findViewById(R.id.iv_inst_pic);
        firstNameTextView = findViewById(R.id.firstname_textview);
        lastNameTextView = findViewById(R.id.lastname_textview);
        emailTextView = findViewById(R.id.email_textview);
        mobileNumberTextView = findViewById(R.id.mobile_textview);
        addressTextView = findViewById(R.id.address_textview);

        // Get the student information from intent
        Intent intent = getIntent();
        String student_email = intent.getStringExtra("student_email");
        Student student = databaseHelper.getStudent(student_email);
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String mobileNumber = student.getMobileNumber();
        String address = student.getAddress();
        byte[] photo = student.getPhoto(); // Assuming Student class has a getPhoto() method

        // Set the student information in the TextViews
        firstNameTextView.setText("First Name: " + firstName);
        lastNameTextView.setText("Last Name: " + lastName);
        emailTextView.setText("Email: " + student_email);
        mobileNumberTextView.setText("Mobile Number: " + mobileNumber);
        addressTextView.setText("Address: " + address);

        // Convert the byte array to a Bitmap and set it to the ImageView
        if (photo != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            profilePicture.setImageBitmap(bitmap);
        }
    }

}
