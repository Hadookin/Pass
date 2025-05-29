package com.example.password;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PasswordEntry that = (PasswordEntry) obj;
        return serviceName.equals(that.serviceName) && login.equals(that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName, login);
    }


}
