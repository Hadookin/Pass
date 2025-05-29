package com.example.password;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class PasswordAdapter extends ArrayAdapter<PasswordEntry> {
    public PasswordAdapter(Context context, List<PasswordEntry> entries) {
        super(context, 0, entries);
    }

    @SuppressLint("SetTextI18n")
    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        PasswordEntry entry = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        if (entry != null) {
            text1.setText(entry.getServiceName());
            text2.setText(entry.getLogin());
        }
        else {
            text1.setText("Неизвестный Service");
            text2.setText("Неизвестный Login");
        }

        return convertView;
    }

}
