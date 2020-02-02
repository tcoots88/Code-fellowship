package com.tcoots.CodeFellowship.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    public ApplicationUser findByUsername(String username);
    public ApplicationUser findById(long id);
}

