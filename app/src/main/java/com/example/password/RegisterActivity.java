package com.example.password;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextConfirmPassword;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "User  Prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        buttonRegister.setOnClickListener(v -> register());
    }

    private void register() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sharedPreferences.contains(username)) {
            Toast.makeText(this, "Логин уже занят", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            return;
        }

        sharedPreferences.edit().putString(username, username + ":" + password).apply();

        SharedPreferences passwordsPrefs = getSharedPreferences("passwords", MODE_PRIVATE);
        Gson gson = new Gson();
        PasswordEntry newEntry = new PasswordEntry("User  Account", username, password, "");
        String json = gson.toJson(newEntry);
        passwordsPrefs.edit().putString(username, json).apply();

        Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
}
