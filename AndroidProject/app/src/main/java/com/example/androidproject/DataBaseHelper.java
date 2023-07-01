package com.example.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 17;
    // Database Name
    private static final String DATABASE_NAME = "training_center_db";

    // User table name
    private static final String TABLE_USERS = "users";

    // User Table Columns names
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_USER_TYPE = "user_type";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAdminsTable = "CREATE TABLE Admins (" +
                "email TEXT PRIMARY KEY," +
                "first_name TEXT," +
                "last_name TEXT," +
                "password_hash TEXT," +
                "photo BLOB," +
                "user_type INTEGER);";

        String createStudentsTable = "CREATE TABLE Students (" +
                "email TEXT PRIMARY KEY," +
                "first_name TEXT," +
                "last_name TEXT," +
                "password_hash TEXT," +
                "photo BLOB," +
                "mobile_number TEXT," +
                "address TEXT," +
                "user_type INTEGER);";

        String createInstructorsTable = "CREATE TABLE Instructors (" +
                "email TEXT PRIMARY KEY," +
                "first_name TEXT," +
                "last_name TEXT," +
                "password_hash TEXT," +
                "photo BLOB," +
                "mobile_number TEXT," +
                "address TEXT," +
                "specialization TEXT," +
                "courses TEXT," +
                "degree TEXT," +
                "user_type INTEGER);";


        String createCoursesTable = "CREATE TABLE Courses (" +
                "id TEXT PRIMARY KEY," +
                "title TEXT," +
                "main_topics TEXT," +
                "prerequisites TEXT," +
                "photo BLOB," +
                "availability INTEGER);";

        String createAvailableCoursesTable = "CREATE TABLE AvailableCourses (" +
                "id INTEGER PRIMARY KEY," +
                "course_id TEXT REFERENCES Courses(id)," +
                "instructor_email TEXT REFERENCES Instructors(email)," +
                "registration_deadline TEXT," +
                "start_date TEXT," +
                "schedule TEXT," +
                "venue TEXT);";

        String createApplicationsTable = "CREATE TABLE Applications (" +
                "id INTEGER PRIMARY KEY," +
                "course_id TEXT REFERENCES Courses(id)," +
                "student_email TEXT REFERENCES Students(email)," +
                "approved INTEGER);";

        String createRegistrationsTable = "CREATE TABLE Registrations (" +
                "id INTEGER PRIMARY KEY," +
                "course_id Text REFERENCES Courses(id)," +
                "student_email TEXT REFERENCES Students(email)," +
                "status INTEGER);";

        String createNotificationsTable = "CREATE TABLE Notifications (" +
                "id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "message TEXT," +
                "student_email TEXT REFERENCES Students(email));";


        db.execSQL(createAdminsTable);
        db.execSQL(createStudentsTable);
        db.execSQL(createInstructorsTable);
        db.execSQL(createCoursesTable);
        db.execSQL(createAvailableCoursesTable);
        db.execSQL(createApplicationsTable);
        db.execSQL(createRegistrationsTable);
        db.execSQL(createNotificationsTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete old table(s)createRegistrationsTable
        db.execSQL("DROP TABLE IF EXISTS Admins");
        db.execSQL("DROP TABLE IF EXISTS Students");
        db.execSQL("DROP TABLE IF EXISTS Instructors");
        db.execSQL("DROP TABLE IF EXISTS Courses");
        db.execSQL("DROP TABLE IF EXISTS AvailableCourses");
        db.execSQL("DROP TABLE IF EXISTS Applications");
        db.execSQL("DROP TABLE IF EXISTS Registrations");
        db.execSQL("DROP TABLE IF EXISTS Notifications");

        // Recreate table(s)
        onCreate(db);
    }

    public void edit() {
        /*
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS Admins");
        db.execSQL("DROP TABLE IF EXISTS Students");
        db.execSQL("DROP TABLE IF EXISTS Instructors");
        db.execSQL("DROP TABLE IF EXISTS Courses");
        db.execSQL("DROP TABLE IF EXISTS AvailableCourses");
        db.execSQL("DROP TABLE IF EXISTS Applications");
        db.execSQL("DROP TABLE IF EXISTS Registrations");
        db.execSQL("DROP TABLE IF EXISTS Notifications");

        // Recreate table(s)
        onCreate(db);
        */

        /* doesn't work because of photo
        String insertAdmin = "INSERT INTO Admins (email, first_name, last_name, password_hash, photo, user_type)VALUES "+
                "('osama@gmail.com', 'Osama', 'Rimawi', 'Password123', null, 1), "+
                "('omar@gmail.com', 'Omar', 'Kamal', 'Password123', null, 1);";

        String insertStudent = "INSERT INTO Students (email, first_name, last_name, password_hash, photo, mobile_number, address, user_type) VALUES "+
                "('abd@gmail.com', 'Abd', 'Gamal', 'Password123', null, '123456789', '123 Street, City', 3), "
                +"('kamel@gmail.com', 'kamel', 'Ahmad', 'Password123', null, '975456789', '527 Street, City', 3), "
                +"('lama@gmail.com', 'lama', 'Mohammad', 'Password123', null, '417456789', '132 Street, City', 3), "
                +"('roa@gmail.com', 'roa', 'Sami', 'Password123', null, '789456789', '458 Street, City', 3), "
                +"('dana@gmail.com', 'dana', 'Asd', 'Password123', null, '658456789', '189 Street, City', 3), "
                +"('rwan@gmail.com', 'rwan', 'Nimer', 'Password123', null, '489456789', '891 Street, City', 3);";

        String insertInstructor = "INSERT INTO Instructors (email, first_name, last_name, password_hash, photo, mobile_number,"+
                " address, specialization, courses, degree, user_type) VALUES "+
                "('asd@gmail.com', 'Asd', 'Samhan', 'Password123', null, '564654321', '456 Street, City', 'Computer Engineer', null, 'BSc', 2), "
                +"('nada@gmail.com', 'Nada', 'Wajdi', 'Password123', null, '852654321', '852 Street, City', 'Computer Engineer', null, 'PhD', 2), "
                +"('sami@gmail.com', 'Sami', 'Rami', 'Password123', null, '478654321', '145 Street, City', 'Computer Science', null, 'MSc', 2);";

        String insertCourse = "INSERT INTO Courses (id, title, main_topics, prerequisites, photo, availability) VALUES "+
                "('84816637-28f6-4dae-b5e0-e80a4866cd4d', 'Java', 'Programming fundamentals, Algorithms ,javafx ', '[C]', null, 0), "+
                "('e1685d7e-c2df-452b-8d9f-c37a81271daf', 'C', 'Programming fundamentals, Algorithms ,opengl ', '[]', null, 1), "+
                "('1c98c34c-faa9-4335-8ace-fbd7813f69f5', 'Python', 'Programming fundamentals, Algorithms ,pytorch ', '[]', null, 0), "+
                "('db570b4c-2704-4d96-a3fe-42299c26be54', 'AI', 'NLP , ML ', '[Python]', null, 0), "+
                "('4d91d0e1-f30a-4e51-a4dd-e6b57b05cd73', 'Microsoft Office', 'Excel , Word ', '[]', null, 0), "+
                "('fd16a665-c69e-44cf-b764-b0b66264b482', 'Project Management', 'Software Engineering fundamentals ', '[C,Java]', null, 0);";

        String insertAvailableCourse = "INSERT INTO AvailableCourses (id, course_id, instructor_email, registration_deadline, start_date, schedule, venue) VALUES "+
                "(1, '4d91d0e1-f30a-4e51-a4dd-e6b57b05cd73', 'asd@gmail.com', '5-4-2020', '1-3-2020', 'Monday Wednesday', 'Building A, Room 101'), " +
                "(2, 'e1685d7e-c2df-452b-8d9f-c37a81271daf', 'nada@gmail.com', '9-12-2023', '1-6-2023', 'Tuesday Thursday', 'Building B, Room 201');";

        String insertApplication = "INSERT INTO Applications (id, course_id, student_email, approved) VALUES "+
                "(1, '4d91d0e1-f30a-4e51-a4dd-e6b57b05cd73', 'abd@gmail.com', 1), "+
                "(2, '4d91d0e1-f30a-4e51-a4dd-e6b57b05cd73', 'kamel@gmail.com', 1), "+
                "(3, '4d91d0e1-f30a-4e51-a4dd-e6b57b05cd73', 'lama@gmail.com', 1), "+
                "(4, '4d91d0e1-f30a-4e51-a4dd-e6b57b05cd73', 'rwan@gmail.com', 1), "+
                "(5, 'e1685d7e-c2df-452b-8d9f-c37a81271daf', 'dana@gmail.com', 0);";

        String insertRegistration = "INSERT INTO Registrations (id, course_id, student_email, status) VALUES "+
                "(1, '4d91d0e1-f30a-4e51-a4dd-e6b57b05cd73', 'abd@gmail.com', 0), "+
                "(2, '4d91d0e1-f30a-4e51-a4dd-e6b57b05cd73', 'kamel@gmail.com', 0), "+
                "(3, '4d91d0e1-f30a-4e51-a4dd-e6b57b05cd73', 'lama@gmail.com', 0), "+
                "(4, '4d91d0e1-f30a-4e51-a4dd-e6b57b05cd73', 'rwan@gmail.com', 0);";
        db.execSQL(insertAdmin);
        db.execSQL(insertStudent);
        db.execSQL(insertInstructor);
        db.execSQL(insertCourse);
        db.execSQL(insertAvailableCourse);
        db.execSQL(insertApplication);
        db.execSQL(insertRegistration);

        SQLiteDatabase db = this.getWritableDatabase();

            String updateQuery = "UPDATE AvailableCourses " +
                "SET start_date = '25-6-2023' " +
                "WHERE id = 1";
        db.execSQL(updateQuery);
        db.close();
         */
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public int validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Check in each table (Admin, Student, Instructor) one by one.
        // Assuming the user_type is stored as 0 for Admin, 1 for Student, and 2 for Instructor
        String[] tableNames = {"Admin", "Student", "Instructor"};

        for (int i = 0; i < tableNames.length; i++) {
            if (i == 0) {
                Cursor cursor = db.rawQuery("SELECT * FROM " + "Admins" + " WHERE email=? AND password_hash=?", new String[]{email, password});
                if (cursor.getCount() > 0) {
                    return i + 1;
                }
                cursor.close();
            } else if (i == 1) {
                Cursor cursor = db.rawQuery("SELECT * FROM " + "Instructors" + " WHERE email=? AND password_hash=?", new String[]{email, password});
                if (cursor.getCount() > 0) {
                    return i + 1;
                }
                cursor.close();
            } else if (i == 2) {
                Cursor cursor = db.rawQuery("SELECT * FROM " + "Students" + " WHERE email=? AND password_hash=?", new String[]{email, password});
                if (cursor.getCount() > 0) {
                    return i + 1;
                }
                cursor.close();
            }
        }
        return -1;
    }

    public boolean emailExists(String table, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table + " WHERE email=?", new String[]{email});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean insertStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", student.getEmail());
        contentValues.put("first_name", student.getFirstName());
        contentValues.put("last_name", student.getLastName());
        contentValues.put("password_hash", student.getPassword());
        contentValues.put("photo", student.getPhoto());
        contentValues.put("mobile_number", student.getMobileNumber());
        contentValues.put("address", student.getAddress());
        contentValues.put("user_type", student.getUserType());
        long result = db.insert("Students", null, contentValues);
        db.close();
        return result != -1;
    }

    public Student getStudent(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Students WHERE email=?", new String[]{email});
        Student student = null;
        int emailIndex = cursor.getColumnIndex("email");
        int first_nameIndex = cursor.getColumnIndex("first_name");
        int last_nameIndex = cursor.getColumnIndex("last_name");
        int password_hashIndex = cursor.getColumnIndex("password_hash");
        int mobile_numberIndex = cursor.getColumnIndex("mobile_number");
        int addressIndex = cursor.getColumnIndex("address");
        int user_typeIndex = cursor.getColumnIndex("user_type");
        int photoIndex = cursor.getColumnIndex("photo");
        if (cursor.moveToFirst() && emailIndex != -1 && first_nameIndex != -1 && last_nameIndex != -1 && password_hashIndex != -1 && photoIndex != -1
                && mobile_numberIndex != -1 && addressIndex != -1 && user_typeIndex != -1) {
            student = new Student();
            student.setEmail(cursor.getString(emailIndex));
            student.setFirstName(cursor.getString(first_nameIndex));
            student.setLastName(cursor.getString(last_nameIndex));
            student.setPassword(cursor.getString(password_hashIndex));
            student.setMobileNumber(cursor.getString(mobile_numberIndex));
            student.setAddress(cursor.getString(addressIndex));
            student.setPhoto(cursor.getBlob(photoIndex));
            student.setUserType(cursor.getInt(user_typeIndex));
        }

        cursor.close();
        return student;
    }

    public boolean insertAdmin(Admin admin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", admin.getEmail());
        contentValues.put("first_name", admin.getFirstName());
        contentValues.put("last_name", admin.getLastName());
        contentValues.put("password_hash", admin.getPassword());
        contentValues.put("photo", admin.getPhoto());
        contentValues.put("user_type", admin.getUserType());
        long result = db.insert("Admins", null, contentValues);
        db.close();
        return result != -1;
    }

    public Cursor getAdmin(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Admins WHERE email=?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        return cursor;
    }

    public Cursor getAllAdmins() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Admins";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean updateAdmin(String email, String first_name, String last_name, byte[] photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", first_name);
        contentValues.put("last_name", last_name);
        contentValues.put("photo", photo);
        int result = db.update("Admins", contentValues, "email = ?", new String[]{email});
        db.close();
        return result != 0;
    }

    public boolean deleteAdmin(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Admins", "email = ?", new String[]{email});
        db.close();
        return result != 0;
    }

    public boolean insertInstructor(Instructor instructor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id", instructor.getId());
        contentValues.put("email", instructor.getEmail());
        contentValues.put("first_name", instructor.getFirstName());
        contentValues.put("last_name", instructor.getLastName());
        contentValues.put("password_hash", instructor.getPassword());
        contentValues.put("photo", instructor.getPhoto());
        contentValues.put("mobile_number", instructor.getMobileNumber());
        contentValues.put("address", instructor.getAddress());
        contentValues.put("specialization", instructor.getSpecialization());
        contentValues.put("degree", instructor.getDegree());
        contentValues.put("courses", instructor.getCoursesCanTeach().toString());
        contentValues.put("user_type", instructor.getUserType());

        long result = db.insert("Instructors", null, contentValues);
        db.close();
        return result != -1;
    }

    public Instructor getInstructor(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Instructors WHERE email=?", new String[]{email});

        Instructor instructor = null;

        int emailIndex = cursor.getColumnIndex("email");
        int first_nameIndex = cursor.getColumnIndex("first_name");
        int last_nameIndex = cursor.getColumnIndex("last_name");
        int password_hashIndex = cursor.getColumnIndex("password_hash");
        int mobile_numberIndex = cursor.getColumnIndex("mobile_number");
        int addressIndex = cursor.getColumnIndex("address");
        int specializationIndex = cursor.getColumnIndex("specialization");
        int coursesIndex = cursor.getColumnIndex("courses");
        int degreeIndex = cursor.getColumnIndex("degree");
        int user_typeIndex = cursor.getColumnIndex("user_type");
        int photoIndex = cursor.getColumnIndex("photo");

        if (cursor.moveToFirst() && emailIndex != -1 && first_nameIndex != -1 && last_nameIndex != -1 && password_hashIndex != -1 && photoIndex != -1
                && mobile_numberIndex != -1 && addressIndex != -1 && specializationIndex != -1 && coursesIndex != -1 && degreeIndex != -1
                && user_typeIndex != -1) {
            instructor = new Instructor();
            instructor.setEmail(cursor.getString(emailIndex));
            instructor.setFirstName(cursor.getString(first_nameIndex));
            instructor.setLastName(cursor.getString(last_nameIndex));
            instructor.setPassword(cursor.getString(password_hashIndex));
            instructor.setMobileNumber(cursor.getString(mobile_numberIndex));
            instructor.setAddress(cursor.getString(addressIndex));
            instructor.setSpecialization(cursor.getString(specializationIndex));
            instructor.setCoursesCanTeach(Arrays.asList(cursor.getString(coursesIndex).split(",")));
            instructor.setDegree(cursor.getString(degreeIndex));
            instructor.setPhoto(cursor.getBlob(photoIndex));
            instructor.setUserType(cursor.getInt(user_typeIndex));
        }

        cursor.close();
        return instructor;
    }

    public String getInstructorIdByName(String fullName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] nameParts = fullName.split(" ");
        String firstName = nameParts[0];
        String lastName = nameParts[1];

        Cursor cursor = db.query("Instructors", // The table to query
                new String[]{"email"},
                "first_name = ? AND last_name = ?",
                new String[]{firstName, lastName},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int emailColumnIndex = cursor.getColumnIndex("email");
            if (emailColumnIndex >= 0) {
                String instructorEmail = cursor.getString(emailColumnIndex);
                cursor.close();
                return instructorEmail;
            }
        }
        return null; // Return null if the instructor is not found or the column doesn't exist
    }

    public Cursor getAllInstructors() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Instructors";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean updateInstructor(Instructor instructor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", instructor.getFirstName());
        contentValues.put("last_name", instructor.getLastName());
        contentValues.put("password_hash", instructor.getPassword());
        contentValues.put("photo", instructor.getPhoto());
        contentValues.put("mobile_number", instructor.getMobileNumber());
        contentValues.put("address", instructor.getAddress());
        contentValues.put("specialization", instructor.getSpecialization());
        contentValues.put("degree", instructor.getDegree());
        contentValues.put("courses", instructor.getCoursesCanTeach().toString());
        contentValues.put("user_type", instructor.getUserType());

        int result = db.update("Instructors", contentValues, "email = ?", new String[]{instructor.getEmail()});
        db.close();
        return result != 0;
    }


    public boolean updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("first_name", student.getFirstName());
        contentValues.put("last_name", student.getLastName());
        contentValues.put("password_hash", student.getPassword());
        contentValues.put("photo", student.getPhoto());
        contentValues.put("mobile_number", student.getMobileNumber());
        contentValues.put("address", student.getAddress());
        contentValues.put("user_type", student.getUserType());

        int result = db.update("Students", contentValues, "email = ?", new String[]{student.getEmail()});
        db.close();
        return result != 0;
    }

    public boolean deleteInstructor(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Instructor", "email = ?", new String[]{email});
        db.close();
        return result != 0;
    }

    public long insertCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", course.getId());
        contentValues.put("title", course.getTitle());
        contentValues.put("main_topics", course.getMainTopics());
        contentValues.put("prerequisites", course.getPrerequisites().toString());
        contentValues.put("photo", course.getPhoto());
        contentValues.put("availability", course.getAvailable());

        long result = db.insert("Courses", null, contentValues);
        db.close();
        return result;
    }

    public Course getCourse(String courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM courses WHERE id=?", new String[]{courseId});

        Course course = null;

        int idIndex = cursor.getColumnIndex("id");
        int titleIndex = cursor.getColumnIndex("title");
        int mainTopicsIndex = cursor.getColumnIndex("main_topics");
        int prerequisitesIndex = cursor.getColumnIndex("prerequisites");
        int photoIndex = cursor.getColumnIndex("photo");
        int AvailableIndex = cursor.getColumnIndex("availability");

        if (cursor.moveToFirst() && idIndex != -1 && titleIndex != -1 && mainTopicsIndex != -1 && prerequisitesIndex != -1 && photoIndex != -1
                && AvailableIndex != -1) {
            course = new Course();
            course.setId(cursor.getString(idIndex));
            course.setTitle(cursor.getString(titleIndex));
            course.setMainTopics(cursor.getString(mainTopicsIndex));
            course.setPrerequisites(Arrays.asList(cursor.getString(prerequisitesIndex).split(",")));
            course.setPhoto(cursor.getBlob(photoIndex));
            course.setAvailable(cursor.getInt(AvailableIndex));
        }

        cursor.close();
        return course;
    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM Courses";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    Course course = new Course();

                    int idIndex = cursor.getColumnIndex("id");
                    int titleIndex = cursor.getColumnIndex("title");
                    int mainTopicsIndex = cursor.getColumnIndex("main_topics");
                    int prerequisitesIndex = cursor.getColumnIndex("prerequisites");
                    int photoIndex = cursor.getColumnIndex("photo");
                    int AvailableIndex = cursor.getColumnIndex("availability");

                    if (idIndex != -1) {
                        course.setId(cursor.getString(idIndex));
                    }
                    if (titleIndex != -1) {
                        course.setTitle(cursor.getString(titleIndex));
                    }
                    if (mainTopicsIndex != -1) {
                        course.setMainTopics(cursor.getString(mainTopicsIndex));
                    }
                    if (prerequisitesIndex != -1) {
                        course.setPrerequisites(Arrays.asList(cursor.getString(prerequisitesIndex).split(",")));
                    }
                    if (photoIndex != -1) {
                        byte[] photoBlob = cursor.getBlob(photoIndex);
                        if (photoBlob != null) {
                            course.setPhoto(photoBlob);
                        }
                    }
                    if (AvailableIndex != -1) {
                        course.setAvailable(cursor.getInt(AvailableIndex));
                    }
                    courses.add(course);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error retrieving courses", e);
        }
        return courses;
    }


    public ArrayList<Course> getInstructorCourses(String email) {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String query = "SELECT Courses.id, Courses.title, Courses.main_topics, Courses.prerequisites, " +
                    "Courses.photo, Courses.availability " +
                    "FROM Courses " +
                    "INNER JOIN AvailableCourses ON Courses.id = AvailableCourses.course_id " +
                    "WHERE AvailableCourses.instructor_email = '" + email + "' ";

            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    Course course = new Course();

                    int idIndex = cursor.getColumnIndex("id");

                    int titleIndex = cursor.getColumnIndex("title");
                    int mainTopicsIndex = cursor.getColumnIndex("main_topics");
                    int prerequisitesIndex = cursor.getColumnIndex("prerequisites");
                    int photoIndex = cursor.getColumnIndex("photo");
                    int AvailableIndex = cursor.getColumnIndex("availability");

                    if (idIndex != -1) {
                        course.setId(cursor.getString(idIndex));

                    }
                    if (titleIndex != -1) {
                        course.setTitle(cursor.getString(titleIndex));
                    }
                    if (mainTopicsIndex != -1) {
                        course.setMainTopics(cursor.getString(mainTopicsIndex));
                    }
                    if (prerequisitesIndex != -1) {
                        course.setPrerequisites(Arrays.asList(cursor.getString(prerequisitesIndex).split(",")));
                    }
                    if (photoIndex != -1) {
                        byte[] photoBlob = cursor.getBlob(photoIndex);
                        if (photoBlob != null) {
                            course.setPhoto(photoBlob);
                        }
                    }
                    if (AvailableIndex != -1) {
                        course.setAvailable(cursor.getInt(AvailableIndex));
                    }
                    courses.add(course);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error retrieving courses", e);
        }
        return courses;
    }


    public ArrayList<Course> getAvailableCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String query = "SELECT Courses.id, Courses.title, Courses.main_topics, Courses.prerequisites, " +
                    "Courses.photo, Courses.availability " +
                    "FROM Courses " +
                    "INNER JOIN AvailableCourses ON Courses.id = AvailableCourses.course_id " +
                    "WHERE Courses.availability = 1 ";

            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    Course course = new Course();

                    int idIndex = cursor.getColumnIndex("id");
                    int titleIndex = cursor.getColumnIndex("title");
                    int mainTopicsIndex = cursor.getColumnIndex("main_topics");
                    int prerequisitesIndex = cursor.getColumnIndex("prerequisites");
                    int photoIndex = cursor.getColumnIndex("photo");
                    int AvailableIndex = cursor.getColumnIndex("availability");

                    if (idIndex != -1) {
                        course.setId(cursor.getString(idIndex));
                    }
                    if (titleIndex != -1) {
                        course.setTitle(cursor.getString(titleIndex));
                    }
                    if (mainTopicsIndex != -1) {
                        course.setMainTopics(cursor.getString(mainTopicsIndex));
                    }
                    if (prerequisitesIndex != -1) {
                        course.setPrerequisites(Arrays.asList(cursor.getString(prerequisitesIndex).split(",")));
                    }
                    if (photoIndex != -1) {
                        byte[] photoBlob = cursor.getBlob(photoIndex);
                        if (photoBlob != null) {
                            course.setPhoto(photoBlob);
                        }
                    }
                    if (AvailableIndex != -1) {
                        course.setAvailable(cursor.getInt(AvailableIndex));
                    }
                    courses.add(course);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error retrieving courses", e);
        }
        return courses;
    }

    public boolean updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title", course.getTitle());
        contentValues.put("main_topics", course.getMainTopics());
        contentValues.put("prerequisites", course.getPrerequisites().toString());
        contentValues.put("photo", course.getPhoto());
        contentValues.put("availability", course.getAvailable());
        Log.d("course name3: ", String.valueOf(course.getAvailable()));

        int result = db.update("Courses", contentValues, "id = ?", new String[]{course.getId()});
        db.close();
        // If data is updated incorrectly, it will return 0
        return result != 0;
    }

    public boolean deleteCourse(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Courses", "id = ?", new String[]{id});
        db.close();
        // If data is deleted incorrectly, it will return 0
        return result != 0;
    }

    public Bitmap getCoursePhoto(Course course) {
        // SQL query to get the course photo
        String query = "SELECT photo FROM courses WHERE id = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(course.getId())});

        Bitmap bitmap = null;
        if (cursor.moveToFirst()) {
            byte[] imgByte = cursor.getBlob(0);
            bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        cursor.close();
        return bitmap;
    }

    public Bitmap getInstructorPhoto(Instructor instructor) {
        // SQL query to get the course photo
        String query = "SELECT photo FROM Instructors WHERE email=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(instructor.getEmail())});

        Bitmap bitmap = null;
        if (cursor.moveToFirst()) {
            byte[] imgByte = cursor.getBlob(0);
            bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        cursor.close();
        return bitmap;
    }

    public Bitmap getStudentPhoto(Student student) {
        // SQL query to get the course photo
        String query = "SELECT photo FROM Students WHERE email=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(student.getEmail())});

        Bitmap bitmap = null;
        if (cursor.moveToFirst()) {
            byte[] imgByte = cursor.getBlob(0);
            bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        cursor.close();
        return bitmap;
    }

    public void setCoursePhoto(Course course, Bitmap bitmap) {
        // Convert the Bitmap into a byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imgByte = bos.toByteArray();

        // SQL query to set the course photo
        String query = "UPDATE courses SET photo = ? WHERE id = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query, new Object[]{imgByte, course.getId()});
    }

    public void insertCourseOffering(CourseOffering courseOffering) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("course_id", courseOffering.getCourseId());
        values.put("instructor_email", courseOffering.getInstructorEmail());
        values.put("registration_deadline", courseOffering.getRegistrationDeadline());
        values.put("start_date", courseOffering.getStartDate());
        values.put("schedule", courseOffering.getSchedule());
        values.put("venue", courseOffering.getVenue());

        db.insert("AvailableCourses", null, values);
        db.close();
    }

    public CourseOffering getCourseOffering(String courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM AvailableCourses WHERE course_id=?", new String[]{courseId});

        CourseOffering courseOffering = null;

        int idIndex = cursor.getColumnIndex("id");
        int course_id = cursor.getColumnIndex("course_id");
        int instructor_email = cursor.getColumnIndex("instructor_email");
        int registration_deadline = cursor.getColumnIndex("registration_deadline");
        int start_date = cursor.getColumnIndex("start_date");
        int schedule = cursor.getColumnIndex("schedule");
        int venue = cursor.getColumnIndex("venue");

        if (cursor.moveToFirst() && idIndex != -1 && course_id != -1 && instructor_email != -1 && registration_deadline != -1 && start_date != -1
                && schedule != -1 && venue != -1) {
            courseOffering = new CourseOffering();
            courseOffering.setId(cursor.getInt(idIndex));
            courseOffering.setCourseId(cursor.getString(course_id));
            courseOffering.setInstructorEmail(cursor.getString(instructor_email));
            courseOffering.setRegistrationDeadline(cursor.getString(registration_deadline));
            courseOffering.setStartDate(cursor.getString(start_date));
            courseOffering.setSchedule(cursor.getString(schedule));
            courseOffering.setVenue(cursor.getString(venue));

        }

        cursor.close();
        return courseOffering;
    }

    public void updateCourseOffering(CourseOffering courseOffering) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("course_id", courseOffering.getCourseId());
        values.put("instructor_email", courseOffering.getInstructorEmail());
        values.put("registration_deadline", courseOffering.getRegistrationDeadline());
        values.put("start_date", courseOffering.getStartDate());
        values.put("schedule", courseOffering.getSchedule());
        values.put("venue", courseOffering.getVenue());

        db.update("CourseOfferings", values, "id = ?", new String[]{String.valueOf(courseOffering.getId())});
        db.close();
    }

    public void deleteCourseOffering(CourseOffering courseOffering) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("CourseOfferings", "id = ?", new String[]{String.valueOf(courseOffering.getId())});
        db.close();
    }

    public boolean insertApplication(Application application) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_id", application.getCourseId());
        contentValues.put("student_email", application.getStudentId());
        contentValues.put("approved", application.getApproved());
        long result = db.insert("Applications", null, contentValues);
        db.close();

        return result != -1;
    }

    public List<Application> getAllApplications() {
        List<Application> applications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Applications WHERE approved = 0";
        Cursor cursor = db.rawQuery(query, null);
        int idColumnIndex = cursor.getColumnIndex("id");
        int courseIdColumnIndex = cursor.getColumnIndex("course_id");
        int studentIdColumnIndex = cursor.getColumnIndex("student_email");
        int approvedColumnIndex = cursor.getColumnIndex("approved");

        while (cursor.moveToNext()) {
            Application application = new Application();
            application.setId(cursor.getInt(idColumnIndex));
            application.setCourseId(cursor.getString(courseIdColumnIndex));
            application.setStudentId(cursor.getString(studentIdColumnIndex));
            application.setApproved(cursor.getInt(approvedColumnIndex));
            applications.add(application);
        }

        cursor.close();
        return applications;
    }

    public List<Application> getStudentApplications(String email) {
        List<Application> applications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Applications WHERE student_email = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        int idColumnIndex = cursor.getColumnIndex("id");
        int courseIdColumnIndex = cursor.getColumnIndex("course_id");
        int studentIdColumnIndex = cursor.getColumnIndex("student_email");
        int approvedColumnIndex = cursor.getColumnIndex("approved");

        while (cursor.moveToNext()) {
            Application application = new Application();
            application.setId(cursor.getInt(idColumnIndex));
            application.setCourseId(cursor.getString(courseIdColumnIndex));
            application.setStudentId(cursor.getString(studentIdColumnIndex));
            application.setApproved(cursor.getInt(approvedColumnIndex));
            applications.add(application);
        }

        cursor.close();
        return applications;
    }

    public List<Pair<String, String>> getLastFiveCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Pair<String, String>> courses = new ArrayList<>();

        // Perform a JOIN operation to get course names and instructor names
        String query = "SELECT Courses.title, Instructors.first_name, Instructors.last_name " +
                "FROM AvailableCourses " +
                "INNER JOIN Courses ON AvailableCourses.course_id = Courses.id " +
                "INNER JOIN Instructors ON AvailableCourses.instructor_email = Instructors.email " +
                "ORDER BY AvailableCourses.id DESC " +
                "LIMIT 5";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToLast()) {
            do {
                String courseName = cursor.getString(0); // get course name
                String instructorName = String.join(" ", cursor.getString(1), cursor.getString(2)); // get instructor name
                courses.add(new Pair<>(courseName, instructorName));
            } while (cursor.moveToPrevious());
        }

        cursor.close();

        return courses;
    }


    public List<Pair<String, String>> getInstructorPreviousCourses(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Pair<String, String>> courses = new ArrayList<>();

        String query = "SELECT Courses.title, AvailableCourses.registration_deadline " +
                "FROM AvailableCourses " +
                "INNER JOIN Courses ON AvailableCourses.course_id = Courses.id " +
                "INNER JOIN Instructors ON AvailableCourses.instructor_email = Instructors.email " +
                "WHERE Courses.availability = 0 AND AvailableCourses.instructor_email = '" + email + "' " +
                "ORDER BY AvailableCourses.id DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToLast()) {
            do {
                String courseName = cursor.getString(0); // get course name
                String EndDate = cursor.getString(1); // get end date

                courses.add(new Pair<>(courseName, EndDate));
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return courses;
    }

    public List<Pair<String, String>> getInstructorMondayCourses(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Pair<String, String>> courses = new ArrayList<>();

        String query = "SELECT Courses.title, AvailableCourses.venue " +
                "FROM AvailableCourses " +
                "INNER JOIN Courses ON AvailableCourses.course_id = Courses.id " +
                "INNER JOIN Instructors ON AvailableCourses.instructor_email = Instructors.email " +
                "WHERE Courses.availability = 1 " +
                "AND AvailableCourses.instructor_email = '" + email + "' " +
                "AND AvailableCourses.schedule = 'Monday Wednesday' " +
                "ORDER BY AvailableCourses.id DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToLast()) {
            do {
                String courseName = cursor.getString(0); // get course name
                String venue = cursor.getString(1); // get end date

                courses.add(new Pair<>(courseName, venue));
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return courses;
    }

    public List<Pair<String, String>> getInstructorTuesdayCourses(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Pair<String, String>> courses = new ArrayList<>();

        String query = "SELECT Courses.title, AvailableCourses.venue " +
                "FROM AvailableCourses " +
                "INNER JOIN Courses ON AvailableCourses.course_id = Courses.id " +
                "INNER JOIN Instructors ON AvailableCourses.instructor_email = Instructors.email " +
                "WHERE Courses.availability = 1 " +
                "AND AvailableCourses.instructor_email = '" + email + "' " +
                "AND AvailableCourses.schedule = 'Tuesday Thursday' " +
                "ORDER BY AvailableCourses.id DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToLast()) {
            do {
                String courseName = cursor.getString(0); // get course name
                String venue = cursor.getString(1); // get end date

                courses.add(new Pair<>(courseName, venue));
                Log.d("course Pair: ", courseName + " " + venue);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return courses;
    }

    public List<Pair<String, String>> sortCoursesByStartDate(List<Pair<String, String>> courses) {
        Collections.sort(courses, new Comparator<Pair<String, String>>() {
            @Override
            public int compare(Pair<String, String> course1, Pair<String, String> course2) {
                String startDate1 = extractStartDate(course1.second);
                String startDate2 = extractStartDate(course2.second);

                return startDate2.compareTo(startDate1);
            }

            private String extractStartDate(String courseDateString) {
                // Assuming the courseDateString has the format "Start date: DD-MM-YYYY End Date: DD-MM-YYYY"
                String[] parts = courseDateString.split(" ");
                return parts[2];  // Extract the start date part
            }
        });

        return courses;
    }

    public List<Pair<String, String>> getAllAvailableCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Pair<String, String>> courses = new ArrayList<>();


        String query = "SELECT Courses.title, AvailableCourses.start_date, AvailableCourses.registration_deadline " +
                "FROM AvailableCourses " +
                "INNER JOIN Courses ON AvailableCourses.course_id = Courses.id ";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToLast()) {
            do {
                String courseName = cursor.getString(0); // get course name
                String startDate = cursor.getString(1); // get end date
                String EndDate = cursor.getString(2); // get end date

                String date = "Start date: " + startDate + " End Date: " + EndDate;

                courses.add(new Pair<>(courseName, date));
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        List<Pair<String, String>> sortedCourses = sortCoursesByStartDate(courses);

        return sortedCourses;
    }

    public List<Pair<String, String>> getStudentPreviousCourses(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Pair<String, String>> courses = new ArrayList<>();

        String query = "SELECT Courses.title, AvailableCourses.start_date, AvailableCourses.registration_deadline " +
                "FROM Registrations " +
                "INNER JOIN Courses ON Registrations.course_id = Courses.id " +
                "INNER JOIN AvailableCourses ON Registrations.course_id = AvailableCourses.course_id " +
                "INNER JOIN Students ON Registrations.student_email = Students.email " +
                "WHERE Registrations.student_email = '" + email + "' ";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToLast()) {
            do {
                String courseName = cursor.getString(0); // get course name
                String strartDate = cursor.getString(1); // get end date
                String EndDate = cursor.getString(2); // get end date

                String date = "Start date: " + strartDate + " End Date: " + EndDate;

                courses.add(new Pair<>(courseName, date));
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        List<Pair<String, String>> sortedCourses = sortCoursesByStartDate(courses);
        return sortedCourses;
    }

    public List<String[]> getCourseStudents(String courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String[]> students = new ArrayList<>();


        String query = "SELECT Students.first_name, Students.last_name, Students.mobile_number, Students.address " +
                "FROM Students " +
                "INNER JOIN Registrations ON Students.email = Registrations.student_email " +
                "WHERE Registrations.course_id = '" + courseId + "' ";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToLast()) {
            do {
                String studentName = cursor.getString(0); // get course name
                String studentLastName = cursor.getString(1); // get end date
                String studentMobile = cursor.getString(2); // get course name
                String studentAddress = cursor.getString(3); // get end date

                String[] student1 = {studentName, studentLastName, studentMobile, studentAddress};
                students.add(student1);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return students;
    }

    public int updateApplication(Application application) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_id", application.getCourseId());
        contentValues.put("student_email", application.getStudentId());
        contentValues.put("approved", application.getApproved());
        return db.update("Applications", contentValues, "id = ? ", new String[]{String.valueOf(application.getId())});
    }

    public void deleteApplication(Application application) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Applications", "id = ? ", new String[]{String.valueOf(application.getId())});
        String registrationsQuery = "DELETE FROM Registrations WHERE student_email = ? AND course_id = ?";
        String[] registrationsArgs = {application.getStudentId(), application.getCourseId()};
        db.execSQL(registrationsQuery, registrationsArgs);
        db.close();
    }

    public boolean insertRegistration(String course_id, String student_email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("course_id", course_id);
        contentValues.put("student_email", student_email);

        long result = db.insert("Registrations", null, contentValues);
        db.close();
        // If data is inserted incorrectly, it will return -1
        return result != -1;
    }

    public Cursor getRegistration(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Registrations WHERE id=?";
        Cursor cursor = db.rawQuery(query, new String[]{Integer.toString(id)});
        return cursor;
    }

    public Cursor getAllRegistrations(String student_email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Registrations WHERE student_email=?";
        Cursor cursor = db.rawQuery(query, new String[]{student_email});
        return cursor;
    }

    public boolean updateRegistration(int id, int course_id, String student_email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("course_id", course_id);
        contentValues.put("student_email", student_email);

        int result = db.update("Registrations", contentValues, "id = ?", new String[]{Integer.toString(id)});
        db.close();
        // If data is updated incorrectly, it will return 0
        return result != 0;
    }

    public boolean deleteRegistration(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Registrations", "id = ?", new String[]{Integer.toString(id)});
        db.close();
        // If data is deleted incorrectly, it will return 0
        return result != 0;
    }

    public ArrayList<Student> getRegisteredStudents(int courseId) {
        ArrayList<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Students WHERE id IN " +
                "(SELECT student_id FROM Registrations WHERE course_id = ?)";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courseId)});

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();

                int emailIndex = cursor.getColumnIndex("email");
                int firstNameIndex = cursor.getColumnIndex("first_name");
                int lastNameIndex = cursor.getColumnIndex("last_name");
                int passwordHashIndex = cursor.getColumnIndex("password_hash");
                int photoIndex = cursor.getColumnIndex("photo");
                int mobileNumberIndex = cursor.getColumnIndex("mobile_number");
                int addressIndex = cursor.getColumnIndex("address");

                if (emailIndex != -1) {
                    student.setEmail(cursor.getString(emailIndex));
                }
                if (firstNameIndex != -1) {
                    student.setFirstName(cursor.getString(firstNameIndex));
                }
                if (lastNameIndex != -1) {
                    student.setLastName(cursor.getString(lastNameIndex));
                }
                if (passwordHashIndex != -1) {
                    student.setPassword(cursor.getString(passwordHashIndex));
                }
                if (photoIndex != -1) {
                    byte[] photoBlob = cursor.getBlob(photoIndex);
                    if (photoBlob != null) {
                        student.setPhoto(photoBlob);
                    }
                }
                if (mobileNumberIndex != -1) {
                    student.setMobileNumber(cursor.getString(mobileNumberIndex));
                }
                if (addressIndex != -1) {
                    student.setAddress(cursor.getString(addressIndex));
                }

                students.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return students;
    }

    // Check if the student already applied for the course
    public boolean checkIfStudentAppliedBefore(String email, String courseId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM Applications WHERE student_email = ? AND course_id = ?";
        String[] selectionArgs = {email, courseId};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean hasApplied = cursor.moveToFirst();
        cursor.close();
        if (!hasApplied) {
            // Check if the student has the course in the registrations table
            String registrationsQuery = "SELECT * FROM Registrations WHERE student_email = ? AND course_id = ?";
            Cursor registrationsCursor = db.rawQuery(registrationsQuery, selectionArgs);

            boolean hasRegistered = registrationsCursor.moveToFirst();
            registrationsCursor.close();

            return hasRegistered;
        }
        return hasApplied;
    }

    // Check for time conflicts with other enrolled courses
    public boolean checkForTimeConflict(String email, String courseSchedule) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM Registrations " +
                "JOIN AvailableCourses ON Registrations.course_id = AvailableCourses.course_id " +
                "JOIN Courses ON Registrations.course_id = Courses.id " +
                "WHERE Courses.availability = 1 AND Registrations.student_email = ? AND AvailableCourses.schedule = ?";
        String[] selectionArgs = {email, courseSchedule};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean hasTimeConflict = cursor.moveToFirst();
        cursor.close();

        return hasTimeConflict;
    }

    // Check if the student has completed the prerequisites
    public boolean checkPrerequisiteCompletion(String email, List<String> prerequisites) {
        SQLiteDatabase db = this.getReadableDatabase();

        if (prerequisites.get(0) == "") {
            // No prerequisites to check
            return true;
        }

        // Build the query dynamically based on the number of prerequisites
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM Registrations WHERE student_email = ? AND course_id IN (");

        // Prepare the selection arguments with student email
        String[] selectionArgs = new String[prerequisites.size() + 1];

        selectionArgs[0] = email;

        for (int i = 0; i < prerequisites.size(); i++) {
            // Get the course ID for the prerequisite from the title
            String prerequisiteTitle = prerequisites.get(i);

            String courseId = getCourseIdFromTitle(prerequisiteTitle);

            queryBuilder.append("?");
            if (i < prerequisites.size() - 1) {
                queryBuilder.append(",");
            }

            // Add the course ID to the selection arguments
            selectionArgs[i + 1] = courseId;
        }

        queryBuilder.append(")");
        String query = queryBuilder.toString();

        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean hasCompletedPrerequisites = cursor.getCount() == prerequisites.size();
        cursor.close();

        return hasCompletedPrerequisites;
    }

    private String getCourseIdFromTitle(String title) {
        // Query the database to fetch the course ID based on the title
        // Replace this with your actual database query logic
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT id FROM Courses WHERE Courses.title = ?";
        String[] selectionArgs = {title};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        int courseIdIndex = cursor.getColumnIndex("id");

        String courseId = null;
        if (cursor.moveToFirst()) {
            if (courseIdIndex != -1) {
                courseId = cursor.getString(courseIdIndex);
            }
        }

        cursor.close();

        return courseId;
    }

    public boolean sendNotificationToStudent(String student_email, String title, String message) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("message", message);
        values.put("student_email", student_email);

        long newRowId = db.insert("Notifications", null, values);
        return newRowId != -1;
    }

    public boolean sendNotificationToAllStudents(String title, String message) {
        SQLiteDatabase db = getWritableDatabase();

        List<String> studentEmails = new ArrayList<>();

        Cursor cursor = db.query("Students", new String[]{"email"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String studentEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            studentEmails.add(studentEmail);
        }

        cursor.close();

        long insertedRows = 0;

        for (String studentEmail : studentEmails) {
            ContentValues values = new ContentValues();
            values.put("title", title);
            values.put("message", message);
            values.put("student_email", studentEmail);

            long newRowId = db.insert("Notifications", null, values);

            if (newRowId != -1) {
                insertedRows++;
            }
        }

        return insertedRows == studentEmails.size();
    }

    public boolean sendNotificationToStudentsInCourse(String courseId, String title, String message) {
        SQLiteDatabase db = getWritableDatabase();

        List<String> studentEmails = new ArrayList<>();

        // Retrieve student emails from the Registrations table for the given course ID
        String[] projection = {"student_email"};
        String selection = "course_id = ?";
        String[] selectionArgs = {courseId};

        Cursor cursor = db.query("Registrations", projection, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            String studentEmail = cursor.getString(cursor.getColumnIndexOrThrow("student_email"));
            studentEmails.add(studentEmail);
        }

        cursor.close();

        long insertedRows = 0;

        for (String studentEmail : studentEmails) {
            ContentValues values = new ContentValues();
            values.put("title", title);
            values.put("message", message);
            values.put("student_email", studentEmail);

            long newRowId = db.insert("Notifications", null, values);

            if (newRowId != -1) {
                insertedRows++;
            }
        }

        return insertedRows == studentEmails.size();
    }

    public List<Notification> getNotificationsForStudent(String email) {
        List<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT id, title, message, student_email FROM Notifications WHERE student_email = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        while (cursor.moveToNext()) {
            int notificationId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String notificationTitle = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String notificationMessage = cursor.getString(cursor.getColumnIndexOrThrow("message"));
            String notificationEmail = cursor.getString(cursor.getColumnIndexOrThrow("student_email"));

            Notification notification = new Notification(notificationId, notificationTitle, notificationMessage, notificationEmail);
            notifications.add(notification);

            String deleteQuery = "DELETE FROM Notifications WHERE id = ?";
            String[] deleteArgs = {String.valueOf(notificationId)};
            db.execSQL(deleteQuery, deleteArgs);
        }

        cursor.close();
        return notifications;
    }

    public void updateCourseAvailability() {
        SQLiteDatabase db = getWritableDatabase();

        String currentDate = getCurrentDate();

        String selectQuery = "SELECT course_id, registration_deadline FROM AvailableCourses";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int courseIDIndex = cursor.getColumnIndex("course_id");
                String courseId = cursor.getString(courseIDIndex);
                int deadlineDateIndex = cursor.getColumnIndex("registration_deadline");
                String deadlineDate = cursor.getString(deadlineDateIndex);

                boolean isExpired = compareDates(deadlineDate, currentDate);

                // Update the availability in the Courses table based on the comparison result
                updateCourseAvailabilityById(courseId, isExpired, db);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }


    private boolean compareDates(String deadlineDate, String currentDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy", Locale.getDefault());
        try {
            Date deadline = dateFormat.parse(deadlineDate);
            Date current = dateFormat.parse(currentDate);
            return deadline.before(current);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updateCourseAvailabilityById(String courseId, boolean isExpired, SQLiteDatabase db) {
        String updateQuery = "UPDATE Courses SET availability = ? WHERE id = ?";
        int availability = isExpired ? 0 : 1;
        db.execSQL(updateQuery, new Object[]{availability, courseId});
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }
}