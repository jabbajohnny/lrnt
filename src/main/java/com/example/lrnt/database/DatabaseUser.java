package com.example.lrnt.database;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users")
public class DatabaseUser implements UserDet{

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "confirmed")
    public int confirmed;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "join_date")
    private String joinDate;

    public DatabaseUser(String id, int confirmed, String name, String email, String password, String joinDate) {
        this.id = id;
        this.confirmed = confirmed;
        this.username = name;
        this.email = email;
        this.password = password;
        this.joinDate = joinDate;
    }

    public DatabaseUser() {

    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
