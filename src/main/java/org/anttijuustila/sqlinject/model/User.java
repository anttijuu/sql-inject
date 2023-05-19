package org.anttijuustila.sqlinject.model;

public class User {
    private String username;
    private String password;
    private String email;

    public User(String name, String pw, String mail) {
        username = name;
        password = pw;
        email = mail;
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
}
