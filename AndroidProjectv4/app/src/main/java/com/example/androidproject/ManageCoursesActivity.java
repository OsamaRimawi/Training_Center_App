package com.example.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManageCoursesActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    ArrayList<Course> coursesList = new ArrayList<>();
    CoursesAdapter adapter;
    de.hdodenhof.circleimageview.CircleImageView ivCoursePhoto;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courses);

        EditText etCourseTitle = findViewById(R.id.et_course_title);
        EditText etCourseTopics = findViewById(R.id.et_course_topics);
        EditText etCoursePrerequisites = findViewById(R.id.et_course_prerequisites);
        Button btnAddCourse = findViewById(R.id.btn_add_course);
        Button btnSelectPhoto = findViewById(R.id.btn_select_photo);
        RecyclerView rvCourses = findViewById(R.id.rv_courses);
        ivCoursePhoto = findViewById(R.id.iv_course_photo);

        int darkGreen = ContextCompat.getColor(this, R.color.dark_green);

        btnAddCourse.setBackgroundColor(darkGreen);
        btnSelectPhoto.setBackgroundColor(darkGreen);

        db = new DatabaseHelper(this);

        new FetchCoursesTask().execute();

        coursesList = db.getAllCourses();

        adapter = new CoursesAdapter(this, coursesList);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));
        rvCourses.setAdapter(adapter);

        btnSelectPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnAddCourse.setOnClickListener(v -> {
            if (etCourseTitle.getText().toString().isEmpty() || etCourseTopics.getText().toString().isEmpty()) {
                Toast.makeText(ManageCoursesActivity.this, "Please fill all the information", Toast.LENGTH_SHORT).show();
                return;
            }

            String courseTitle = etCourseTitle.getText().toString();
            String courseTopics = etCourseTopics.getText().toString();
            List<String> coursePrerequisites = Arrays.asList(etCoursePrerequisites.getText().toString().replaceAll("[\\[\\]{}()]", "").split(","));
            ivCoursePhoto.setDrawingCacheEnabled(true);
            Bitmap bitmap = ivCoursePhoto.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] photo = baos.toByteArray();

            Drawable drawable = ivCoursePhoto.getDrawable();
            if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap2 = ((BitmapDrawable) drawable).getBitmap();
                if (bitmap2 != null && bitmapHasTransparentPixels(bitmap2)) {
                    ivCoursePhoto.setBackgroundColor(Color.WHITE);
                }
            }


                Course newCourse = new Course(courseTitle, courseTopics, coursePrerequisites, photo);

            // Insert the course into the database
            try {
                db.insertCourse(newCourse);
            } catch (Exception e) {
                // Log and/or display an error message
                Log.e("ManageCoursesActivity", "Error adding course", e);
            }

            new FetchCoursesTask().execute(); // Refresh the list

            etCourseTitle.setText("");
            etCourseTopics.setText("");
            etCoursePrerequisites.setText("");
            ivCoursePhoto.setImageResource(R.drawable.course);

        });

        // Rest of your code is unchanged
    }

    // Rest of your code is unchanged
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
    private class FetchCoursesTask extends AsyncTask<Void, Void, ArrayList<Course>> {
        @Override
        protected ArrayList<Course> doInBackground(Void... voids) {
            ArrayList<Course> courses = new ArrayList<>();

            // Populate coursesList from the database
            courses = db.getAllCourses();

            return courses;
        }

        @Override
        protected void onPostExecute(ArrayList<Course> courses) {
            coursesList.clear();
            coursesList.addAll(courses);
            adapter.notifyDataSetChanged();
        }
    }

    private boolean bitmapHasTransparentPixels(Bitmap bitmap) {
        for (int x = 0; x < bitmap.getWidth(); x++) {
            for (int y = 0; y < bitmap.getHeight(); y++) {
                int pixel = bitmap.getPixel(x, y);
                if (Color.alpha(pixel) != 255) {
                    return true;
                }
            }
        }
        return false;
    }
}


