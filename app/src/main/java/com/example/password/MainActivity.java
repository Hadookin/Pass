package com.example.password;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private final ArrayList<PasswordEntry> originalPasswordEntries = new ArrayList<>();
    private PasswordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        EditText searchEditText = findViewById(R.id.searchEditText);
        Button buttonAddPassword = findViewById(R.id.buttonAddPassword);

        databaseHelper = new DatabaseHelper(this);
        loadPasswords();
        adapter = new PasswordAdapter(this, originalPasswordEntries);
        listView.setAdapter(adapter);

        if (originalPasswordEntries.isEmpty()) {
            Toast.makeText(this, "Нет сохранённых паролей", Toast.LENGTH_SHORT).show();
        }

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPasswords(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        buttonAddPassword.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPasswordActivity.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            PasswordEntry selectedEntry = originalPasswordEntries.get(position);
            Intent intent = new Intent(MainActivity.this, PasswordDetailActivity.class);
            intent.putExtra("passwordEntry", new Gson().toJson(selectedEntry));
            startActivity(intent);
        });
    }
    private void loadPasswords() {
        originalPasswordEntries.clear();
        List<PasswordEntry> passwords = databaseHelper.getAllPasswords();
        originalPasswordEntries.addAll(passwords);
    }

    private void filterPasswords(String query) {
        ArrayList<PasswordEntry> filteredList = new ArrayList<>();
        for (PasswordEntry entry : originalPasswordEntries) {
            if (entry.getServiceName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(entry);
            }
        }
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }
}
