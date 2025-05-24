package com.example.password;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

public class AddPasswordActivity extends AppCompatActivity {

    private EditText editTextService, editTextLogin, editTextPassword, editTextNotes;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "passwords";
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        editTextService = findViewById(R.id.editTextService);
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextNotes = findViewById(R.id.editTextNotes);
        Button buttonSave = findViewById(R.id.buttonSave);
        ImageView buttonTogglePassword = findViewById(R.id.buttonTogglePassword);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        buttonTogglePassword.setOnClickListener(v -> togglePasswordVisibility());

        buttonSave.setOnClickListener(v -> savePassword());
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            editTextPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isPasswordVisible = false;
        } else {
            editTextPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isPasswordVisible = true;
        }
        editTextPassword.setSelection(editTextPassword.length());
    }

    private void savePassword() {
        String service = editTextService.getText().toString();
        String login = editTextLogin.getText().toString();
        String password = editTextPassword.getText().toString();
        String notes = editTextNotes.getText().toString();

        if (validateInputs(service, login, password)) {
            PasswordEntry newEntry = new PasswordEntry(service, login, password, notes);
            String json = new Gson().toJson(newEntry);
            sharedPreferences.edit().putString(service, json).apply();
            Toast.makeText(this, "Пароль успешно сохранён", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validateInputs(String service, String login, String password) {
        if (service.isEmpty()) {
            Toast.makeText(this, "Введите название сервиса", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (login.isEmpty()) {
            Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
