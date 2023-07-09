package com.example.androidproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class InstructorEditActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    EditText firstNameEditText, lastNameEditText, passwordEditText, confirmPasswordEditText, mobileNumberEditText,
            addressEditText, specializationEditText, degreeEditText, coursesEditText;
    Button saveButton, btnSelectPhoto;
    DatabaseHelper db;
    Instructor instructor;

    ImageView ivInstructorPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_edit);

        Intent intent_1 = getIntent();
        String email = getIntent().getStringExtra("email");

        firstNameEditText = findViewById(R.id.firstname_edittext);
        lastNameEditText = findViewById(R.id.lastname_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        confirmPasswordEditText = findViewById(R.id.conf_password_edittext);
        mobileNumberEditText = findViewById(R.id.mobile_edittext);
        addressEditText = findViewById(R.id.address_edittext);
        specializationEditText = findViewById(R.id.specialization_edittext);
        degreeEditText = findViewById(R.id.degree_edittext);
        coursesEditText = findViewById(R.id.courses_edittext);
        saveButton = findViewById(R.id.update_button);
        ivInstructorPhoto = findViewById(R.id.iv_inst_pic);
        btnSelectPhoto = findViewById(R.id.btn_selectphoto);
        int darkGreen = ContextCompat.getColor(this, R.color.dark_green);
        saveButton.setBackgroundColor(darkGreen);
        btnSelectPhoto.setBackgroundColor(darkGreen);


        db = new DatabaseHelper(this);
        instructor = db.getInstructor(email);

        // Pre-fill the EditText fields with the current course information
        firstNameEditText.setText(instructor.getFirstName());
        lastNameEditText.setText(instructor.getLastName());
        passwordEditText.setText(instructor.getPassword());
        confirmPasswordEditText.setText(instructor.getPassword());
        mobileNumberEditText.setText(instructor.getMobileNumber());
        addressEditText.setText(instructor.getAddress());
        specializationEditText.setText(instructor.getSpecialization());
        degreeEditText.setText(instructor.getDegree());
        coursesEditText.setText(instructor.getCoursesCanTeach().toString().replace("[", "").replace("]", "").replace(" ", ""));

        // Display the current course photo
        Bitmap bitmap = db.getInstructorPhoto(instructor);
        if (bitmap != null) {
            ivInstructorPhoto.setImageBitmap(bitmap);
        }

        btnSelectPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    instructor.setFirstName(firstNameEditText.getText().toString());
                    instructor.setLastName(lastNameEditText.getText().toString());
                    instructor.setPassword(passwordEditText.getText().toString());
                    instructor.setMobileNumber(mobileNumberEditText.getText().toString());
                    instructor.setAddress(addressEditText.getText().toString());
                    instructor.setSpecialization(specializationEditText.getText().toString());
                    instructor.setDegree(degreeEditText.getText().toString());
                    instructor.setCoursesCanTeach(Arrays.asList(coursesEditText.getText().toString().split(",")));

                    BitmapDrawable drawable = (BitmapDrawable) ivInstructorPhoto.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

                    instructor.setPhoto(byteArrayOutputStream.toByteArray());

                    db.updateInstructor(instructor);

                    // Go back to the previous Activity
                    Intent editIntent = new Intent(InstructorEditActivity.this, InstructorActivity.class);
                    editIntent.putExtra("email", email);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editIntent);
                }
            }
        });
    }

    private boolean validateFields() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String mobileNumber = mobileNumberEditText.getText().toString();
        String address = addressEditText.getText().toString();

        boolean isValid = true;

        // Set the initial background color to white
        firstNameEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        lastNameEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        passwordEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        confirmPasswordEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        mobileNumberEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        addressEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);


        if (firstName.isEmpty() || firstName.length() < 3 || firstName.length() > 20) {
            Toast.makeText(this, "First name must be between 3 and 20 characters", Toast.LENGTH_LONG).show();
            firstNameEditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        }

        if (lastName.isEmpty() || lastName.length() < 3 || lastName.length() > 20) {
            Toast.makeText(this, "Last name must be between 3 and 20 characters", Toast.LENGTH_LONG).show();
            lastNameEditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 15 || !password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}")) {
            Toast.makeText(this, "Password must be between 8 and 15 characters, and contain at least one number, one lowercase letter, and one uppercase letter", Toast.LENGTH_LONG).show();
            passwordEditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        }

        if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            Toast.makeText(this, "Confirm password must match password", Toast.LENGTH_LONG).show();
            confirmPasswordEditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        }

        if (mobileNumber.isEmpty()) {
            Toast.makeText(this, "Mobile number is required", Toast.LENGTH_LONG).show();
            mobileNumberEditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        }

        if (address.isEmpty()) {
            Toast.makeText(this, "Address is required", Toast.LENGTH_LONG).show();
            addressEditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        }

        return isValid;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                // Get the dimensions of the View
                int targetW = ivInstructorPhoto.getWidth();
                int targetH = ivInstructorPhoto.getHeight();

                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true; // if necessary purge pixels into disk

                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, bmOptions);
                ivInstructorPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}