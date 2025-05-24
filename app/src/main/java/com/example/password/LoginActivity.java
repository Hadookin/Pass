package com.example.password;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "User  Prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        buttonLogin.setOnClickListener(v -> login());
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, введите логин и пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        String savedData = sharedPreferences.getString(username, null);
        if (savedData != null) {
            String[] parts = savedData.split(":");
            if (parts.length == 2 && parts[1].equals(password)) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
        }
    }
}
