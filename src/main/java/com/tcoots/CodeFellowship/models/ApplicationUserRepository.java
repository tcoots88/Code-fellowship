package com.tcoots.CodeFellowship.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    static ApplicationUser findByUsername(String username) {
        return null;
    }
    List findAll();
    static ApplicationUser findById(long id) {
        return null;
    }
}

