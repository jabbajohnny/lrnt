package com.example.lrnt.account;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "lrnt.users")
public class DatabaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;
    public boolean confirmed;
    private String name;
    private String email;
    private String password;
    private String joinDate;

    public DatabaseUser(String key, boolean confirmed, String name, String email, String password, String joinDate) {
        this.key = key;
        this.confirmed = confirmed;
        this.name = name;
        this.email = email;
        this.password = password;
        this.joinDate = joinDate;
    }

    public DatabaseUser() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
