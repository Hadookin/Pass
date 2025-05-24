package com.example.password;

import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "passwords";
    private final ArrayList<PasswordEntry> originalPasswordEntries = new ArrayList<>();
    private PasswordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        EditText searchEditText = findViewById(R.id.searchEditText);
        Button buttonAddPassword = findViewById(R.id.buttonAddPassword);

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
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        Gson gson = new Gson();

        originalPasswordEntries.clear();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String json = (String) entry.getValue();
            PasswordEntry passwordEntry = gson.fromJson(json, PasswordEntry.class);
            originalPasswordEntries.add(passwordEntry);
        }
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
