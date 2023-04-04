package com.example.lrnt.account;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users")
public class DatabaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    public boolean confirmed;
    private String name;
    private String email;
    private String password;
    private String joinDate;

    public DatabaseUser(String id, boolean confirmed, String name, String email, String password, String joinDate) {
        this.id = id;
        this.confirmed = confirmed;
        this.name = name;
        this.email = email;
        this.password = password;
        this.joinDate = joinDate;
    }

    public DatabaseUser() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
