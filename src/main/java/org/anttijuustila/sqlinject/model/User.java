package org.anttijuustila.sqlinject.model;

import java.util.UUID;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;

    public User(String name, String pw, String mail) {
        id = UUID.randomUUID().toString();
        username = name;
        password = pw;
        email = mail;
    }

    public User(String id, String name, String pw, String mail) {
        this.id = id;
        username = name;
        password = pw;
        email = mail;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String passwd) {
        this.password = passwd;
	}

    @Override
    public String toString() {
        return String.format("%s (%s)", username, email);
    }

}
