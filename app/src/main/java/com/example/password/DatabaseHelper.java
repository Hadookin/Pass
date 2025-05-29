package com.example.password;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "password_manager.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PASSWORDS = "passwords";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SERVICE_NAME = "service_name";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NOTES = "notes";

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPasswordsTable = "CREATE TABLE " + TABLE_PASSWORDS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SERVICE_NAME + " TEXT, " +
                COLUMN_LOGIN + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_NOTES + " TEXT)";
        db.execSQL(createPasswordsTable);

        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT UNIQUE, " +
                COLUMN_USER_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean addUser(UserEntry userEntry) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, userEntry.getUsername());
            values.put(COLUMN_USER_PASSWORD, userEntry.getPassword());

            long result = db.insert(TABLE_USERS, null, values);
            return result != -1;  // true if insertion succeeded
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error adding user", e);
            return false;
        }
    }

    public boolean userExists(String username) {
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.query(TABLE_USERS,
                     new String[]{COLUMN_USERNAME},
                     COLUMN_USERNAME + " = ?",
                     new String[]{username},
                     null, null, null)) {
            return cursor.getCount() > 0;
        }
    }

    public long addPassword(PasswordEntry entry) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SERVICE_NAME, entry.getServiceName());
            values.put(COLUMN_LOGIN, entry.getLogin());
            values.put(COLUMN_PASSWORD, entry.getPassword());
            values.put(COLUMN_NOTES, entry.getNotes());

            return db.insert(TABLE_PASSWORDS, null, values);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error adding password", e);
            return -1;
        }
    }

    public List<PasswordEntry> getAllPasswords() {
        List<PasswordEntry> passwordList = new ArrayList<>();
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.query(TABLE_PASSWORDS, null, null, null, null, null, null)) {
            if (cursor.moveToFirst()) {
                do {
                    String serviceName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_NAME));
                    String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOGIN));
                    String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
                    String notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES));

                    PasswordEntry entry = new PasswordEntry(serviceName, username, password, notes);
                    passwordList.add(entry);
                } while (cursor.moveToNext());
            }
        }
        return passwordList;
    }

    public boolean validateUser (String username, String password) {
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.query(TABLE_USERS,
                     new String[]{COLUMN_USER_PASSWORD},
                     COLUMN_USERNAME + " = ?",
                     new String[]{username},
                     null, null, null)) {
            if (cursor.moveToFirst()) {
                String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD));
                return storedPassword.equals(password);
            }
            return false;
        }
    }

}
