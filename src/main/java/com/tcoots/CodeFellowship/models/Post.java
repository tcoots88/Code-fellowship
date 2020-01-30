package com.tcoots.CodeFellowship.models;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    public ApplicationUser creator;

    String body;
    Timestamp createdAt;


    public Post(){

    }

    public Post(String body){
//        this.creator = creator;
        this.body = body;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public void setId(long id){
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public ApplicationUser getCreator() {
        return creator;
    }

    public void setCreator(ApplicationUser creator) {
        this.creator = creator;
    }

    public String getBody() {
        return body;
    }

    public long getId() {
        return id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
