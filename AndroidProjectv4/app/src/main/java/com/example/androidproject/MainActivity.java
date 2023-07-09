package com.example.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonLogin, signupButton;
    CheckBox checkBoxRememberMe;
    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.emailInput);
        editTextPassword = findViewById(R.id.passwordInput);
        buttonLogin = findViewById(R.id.loginButton);
        checkBoxRememberMe = findViewById(R.id.rememberMe);
        signupButton = findViewById(R.id.signupButton);

        int darkGreen = ContextCompat.getColor(this, R.color.dark_green);

        buttonLogin.setBackgroundColor(darkGreen);
        signupButton.setBackgroundColor(darkGreen);

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(this);

        // Load email from shared preferences
        String savedEmail = sharedPreferences.getString("savedEmail", "");

        editTextEmail.setText(savedEmail);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Validate user
                String email = editTextEmail.getText().toString().toLowerCase();
                String password = editTextPassword.getText().toString();

                DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                db.updateCourseAvailability();
                int userType = db.validateUser(email, password);

                if (userType == -1) {
                    Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                    return;
                }

                // If "remember me" is checked, save the email
                if (checkBoxRememberMe.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("savedEmail", email);
                    editor.apply();
                }

                Intent intent;
                switch (userType) {
                    case 1: // Admin
                        intent = new Intent(MainActivity.this, AdminActivity.class);
                        intent.putExtra("email", email);
                        break;
                    case 2: // Student
                        intent = new Intent(MainActivity.this, InstructorActivity.class);
                        intent.putExtra("email", email);
                        break;
                    case 3: // Instructor
                        intent = new Intent(MainActivity.this, StudentActivity.class);
                        intent.putExtra("email", email);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Error! Unknown user type.", Toast.LENGTH_LONG).show();
                        return;
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear all the activities above in the stack
                startActivity(intent);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start UserTypeActivity
                Intent intent = new Intent(MainActivity.this, UserTypeActivity.class);
                // Start the new Activity
                startActivity(intent);
            }
        });
    }
}



