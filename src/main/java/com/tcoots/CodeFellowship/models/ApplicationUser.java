package com.tcoots.CodeFellowship.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;

@Entity
public class ApplicationUser implements UserDetails {


    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
            long id;
    String username;
    String password;
    String firstName;
    String lastName;
    String dateOfBirth;
    String bio;

    public ApplicationUser(){

    }

    public ApplicationUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }


    public Collection<?extends GrantedAuthority> getAuthorities(){
        return null;
    }

    public String getPassword(){
        return password;
    }

    public String getUsername(){
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
