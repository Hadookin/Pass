package com.example.password;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PasswordAdapter extends ArrayAdapter<PasswordEntry> {
    public PasswordAdapter(Context context, List<PasswordEntry> entries) {
        super(context, 0, entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PasswordEntry entry = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_password, parent, false);
        }

        TextView serviceNameTextView = convertView.findViewById(R.id.serviceNameTextView);
        TextView loginTextView = convertView.findViewById(R.id.loginTextView);

        serviceNameTextView.setText(entry.getServiceName());
        loginTextView.setText(entry.getLogin());

        return convertView;
    }
}
