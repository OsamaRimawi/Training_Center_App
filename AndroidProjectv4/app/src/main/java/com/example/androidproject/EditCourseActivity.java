package com.example.androidproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class EditCourseActivity extends AppCompatActivity {
    EditText etCourseTitle, etCourseTopics, etCoursePrerequisites;
    Button btnUpdateCourse, btnSelectPhoto;
    DatabaseHelper db;
    Course course;
    ImageView ivCoursePhoto;
    public static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        etCourseTitle = findViewById(R.id.et_course_title);
        etCourseTopics = findViewById(R.id.et_course_topics);
        etCoursePrerequisites = findViewById(R.id.et_course_prerequisites);
        ivCoursePhoto = findViewById(R.id.iv_course_photo_edit);
        btnUpdateCourse = findViewById(R.id.btn_update_course);
        btnSelectPhoto = findViewById(R.id.btn_select_photo);

        int darkGreen = ContextCompat.getColor(this, R.color.dark_green);

        btnUpdateCourse.setBackgroundColor(darkGreen);
        btnSelectPhoto.setBackgroundColor(darkGreen);

        db = new DatabaseHelper(this);

        // Get the Course ID from the Intent
        String courseId = getIntent().getStringExtra("courseId");
        // Use the Course ID to get the Course from the database
        course = db.getCourse(courseId);

        // Pre-fill the EditText fields with the current course information
        etCourseTitle.setText(course.getTitle());
        etCourseTopics.setText(course.getMainTopics());
        String test = course.getPrerequisites().toString().replaceAll("[\\[\\]{}()]", "");
        etCoursePrerequisites.setText(test);

        // Display the current course photo
        Bitmap bitmap = db.getCoursePhoto(course);
        if (bitmap != null) {
            ivCoursePhoto.setImageBitmap(bitmap);
        }

        btnUpdateCourse.setOnClickListener(v -> {
            // Update the course information in the database
            course.setTitle(etCourseTitle.getText().toString());
            course.setMainTopics(etCourseTopics.getText().toString());
            course.setPrerequisites(Arrays.asList(etCoursePrerequisites.getText().toString().split(",")));
            BitmapDrawable drawable = (BitmapDrawable) ivCoursePhoto.getDrawable();
            Bitmap coursePhotoBitmap = drawable.getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            coursePhotoBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] coursePhoto = baos.toByteArray();
            course.setPhoto(coursePhoto);
            db.updateCourse(course);

            String notificationTitle = "Your Course Has Been Updated";
            String notificationMessage = "there was an Update on Course your registered to";

            boolean send = db.sendNotificationToStudentsInCourse(course.getId(), notificationTitle, notificationMessage);
            // Go back to the previous Activity intent.putExtra("courseId", coursesList.get(position).getId());
            Intent editIntent = new Intent(EditCourseActivity.this, CourseInfoActivity.class);
            editIntent.putExtra("courseId", courseId);
            editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(editIntent);
        });

        btnSelectPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                // Get the dimensions of the View
                int targetW = ivCoursePhoto.getWidth();
                int targetH = ivCoursePhoto.getHeight();

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
                ivCoursePhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
