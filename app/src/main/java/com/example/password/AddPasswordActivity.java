package com.example.password;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddPasswordActivity extends AppCompatActivity {

    private EditText editTextService, editTextLogin, editTextPassword, editTextNotes;
    private DatabaseHelper databaseHelper;
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

        databaseHelper = new DatabaseHelper(this);

        buttonTogglePassword.setOnClickListener(v -> togglePasswordVisibility());

        buttonSave.setOnClickListener(v -> savePassword());
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isPasswordVisible = false;
        } else {
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isPasswordVisible = true;
        }
        editTextPassword.setSelection(editTextPassword.length());
    }

    private void savePassword() {
        String service = editTextService.getText().toString().trim();
        String login = editTextLogin.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String notes = editTextNotes.getText().toString().trim();

        if (validateInputs(service, login, password)) {
            PasswordEntry newEntry = new PasswordEntry(service, login, password, notes);
            long id = databaseHelper.addPassword(newEntry);
            if (id != -1) {
                Toast.makeText(this, "Пароль успешно сохранён", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ошибка при сохранении пароля", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInputs(String service, String login, String password) {
        return !service.isEmpty() && !login.isEmpty() && !password.isEmpty();
    }
}
