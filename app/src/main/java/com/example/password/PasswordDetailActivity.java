package com.example.password;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PasswordDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_detail);

        TextView textViewServiceName = findViewById(R.id.textViewServiceName);
        TextView textViewUsername = findViewById(R.id.textViewUsername);
        TextView textViewPassword = findViewById(R.id.textViewPassword);

        PasswordEntry passwordEntry = (PasswordEntry) getIntent().getSerializableExtra("passwordEntry");

        if (passwordEntry != null) {
            textViewServiceName.setText(passwordEntry.getServiceName());
            textViewUsername.setText(passwordEntry.getLogin());
            textViewPassword.setText(passwordEntry.getPassword());
        }
    }
}