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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class StudentSignUpActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText emailEditText, firstNameEditText, lastNameEditText,
            passwordEditText, confirmPasswordEditText, mobileNumberEditText, addressEditText;
    private Button photoButton, signUpButton;

    ImageView ivProfilePicture;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        // initialize views
        emailEditText = findViewById(R.id.emailEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        addressEditText = findViewById(R.id.addressEditText);
        photoButton = findViewById(R.id.photoButton);
        signUpButton = findViewById(R.id.signUpButton);
        ivProfilePicture = findViewById(R.id.iv_profile_picture);

        db = new DatabaseHelper(this);

        photoButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        signUpButton.setOnClickListener(v -> {
            // validate fields
            if (validateFields()) {
                // handle sign up
                Student student = new Student();

                student.setEmail(emailEditText.getText().toString());
                student.setFirstName(firstNameEditText.getText().toString());
                student.setLastName(lastNameEditText.getText().toString());
                student.setPassword(passwordEditText.getText().toString());
                student.setMobileNumber(mobileNumberEditText.getText().toString());
                student.setAddress(addressEditText.getText().toString());

                DatabaseHelper db = new DatabaseHelper(this);
                if (db.emailExists("Students", student.getEmail())) {
                    Toast.makeText(this, "Email already exists!", Toast.LENGTH_SHORT).show();
                    return;
                }

                BitmapDrawable drawable = (BitmapDrawable) ivProfilePicture.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] photo = byteArrayOutputStream.toByteArray();

                student.setPhoto(photo);

                db.insertStudent(student);

                Toast.makeText(this, "Successfully Signed Up!", Toast.LENGTH_LONG).show();

                Intent editIntent = new Intent(StudentSignUpActivity.this, MainActivity.class);
                editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear all the activities above in the stack
                startActivity(editIntent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                // Get the dimensions of the View
                int targetW = ivProfilePicture.getWidth();
                int targetH = ivProfilePicture.getHeight();

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
                ivProfilePicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateFields() {
        String email = emailEditText.getText().toString();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String mobileNumber = mobileNumberEditText.getText().toString();
        String address = addressEditText.getText().toString();

        boolean isValid = true;

        // Set the initial background color to white
        emailEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        firstNameEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        lastNameEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        passwordEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        confirmPasswordEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        mobileNumberEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        addressEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_LONG).show();
            emailEditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        }

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

}
