package com.example.androidproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MakeCourseAvailable extends AppCompatActivity {

    private EditText mInstructorName;
    private Button mRegistrationDeadline;
    private Button mStartDate;
    private Spinner mCourseSchedule;
    private EditText mVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_course_available);

        Course c = (Course) getIntent().getSerializableExtra("CourseObject");

        mInstructorName = findViewById(R.id.instructor_name);
        mRegistrationDeadline = findViewById(R.id.registration_deadline);
        mStartDate = findViewById(R.id.start_date);
        mCourseSchedule = findViewById(R.id.course_schedule);
        mVenue = findViewById(R.id.venue);
        Button makeAvailableButton = findViewById(R.id.btn_make_available);

        // Set up the DatePicker dialog
        mRegistrationDeadline.setOnClickListener(v -> showDatePickerDialog(mRegistrationDeadline));
        mStartDate.setOnClickListener(v -> showDatePickerDialog(mStartDate));

        // Set up the Spinner (DropDown)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.course_schedule_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCourseSchedule.setAdapter(adapter);

        makeAvailableButton.setOnClickListener(v -> makeCourseAvailable(c));
    }

    private void showDatePickerDialog(final Button button) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> button.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1), year, month, day);
        datePickerDialog.show();
    }

    private void makeCourseAvailable(Course course) {
        String instructorName = mInstructorName.getText().toString().trim();
        String registrationDeadline = mRegistrationDeadline.getText().toString().trim();
        String startDate = mStartDate.getText().toString().trim();
        String schedule = mCourseSchedule.getSelectedItem().toString().trim();
        String venue = mVenue.getText().toString().trim();

        DatabaseHelper db = new DatabaseHelper(this);
        String[] words = instructorName.split(" ");
        // Check if any of the entered data is empty
        if (instructorName.isEmpty() || words.length != 2) {
            Toast.makeText(this, "Instructor name cannot be empty and should contain first and last names", Toast.LENGTH_SHORT).show();
            return;
        }
        if (registrationDeadline.isEmpty()) {
            Toast.makeText(this, "Registration deadline cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (startDate.isEmpty()) {
            Toast.makeText(this, "Start date cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (venue.isEmpty()) {
            Toast.makeText(this, "Venue cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String instructorEmail = db.getInstructorIdByName(instructorName);
        if (instructorEmail == null) {
            Toast.makeText(this, "Instructor not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the selected dates are in the past
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date selectedDate = sdf.parse(startDate);
            Date currentDate = new Date();
            if (selectedDate != null && selectedDate.compareTo(currentDate) < 0) {
                Toast.makeText(this, "Start date cannot be in the past", Toast.LENGTH_SHORT).show();
                return;
            }

            selectedDate = sdf.parse(registrationDeadline);
            if (selectedDate != null && selectedDate.compareTo(currentDate) < 0) {
                Toast.makeText(this, "Registration deadline cannot be in the past", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        course.setAvailable(1);
        db.updateCourse(course);

        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setCourseId(course.getId());
        courseOffering.setInstructorEmail(instructorEmail);
        courseOffering.setRegistrationDeadline(registrationDeadline);
        courseOffering.setStartDate(startDate);
        courseOffering.setSchedule(schedule);
        courseOffering.setVenue(venue);

        db.insertCourseOffering(courseOffering);

        String notificationTitle = "New Course Available";
        String notificationMessage = "a new course is now available for registration";

        boolean send = db.sendNotificationToAllStudents(notificationTitle, notificationMessage);
        Intent editIntent = new Intent(MakeCourseAvailable.this, ManageCoursesActivity.class);
        editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear all the activities above in the stack
        startActivity(editIntent);
    }


}
