package com.example.password;

public class PasswordEntry {
    private final String serviceName;
    private final String login;
    private final String password;
    private final String notes;

    public PasswordEntry(String serviceName, String login, String password, String notes) {
        this.serviceName = serviceName;
        this.login = login;
        this.password = password;
        this.notes = notes;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNotes() {
        return notes;
    }
}
