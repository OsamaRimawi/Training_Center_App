package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {
    private Context context;
    private ArrayList<Course> courses;

    public CoursesAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.tvCourseName.setText(course.getTitle());
        byte[] photoBytes = course.getPhoto();
            Bitmap bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
            holder.ivCourseImage.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CourseInfoActivity.class);
            intent.putExtra("courseId", courses.get(position).getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName;
        ImageView ivCourseImage;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tv_course_name);
            ivCourseImage = itemView.findViewById(R.id.iv_course_image);

        }
    }
}
