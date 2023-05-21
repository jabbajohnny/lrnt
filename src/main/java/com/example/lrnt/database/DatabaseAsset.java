package com.example.lrnt.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "assets")
public class DatabaseAsset {

    @Id
    @Column(name = "id")
    String id;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "upload_date")
    String uploadDate;

    @Column(name = "user_id")
    String userId;

    

}
