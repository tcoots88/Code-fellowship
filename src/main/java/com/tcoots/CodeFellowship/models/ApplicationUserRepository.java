package com.tcoots.CodeFellowship.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    static ApplicationUser findByUsername(String username) {
        return null;
    }

    static ApplicationUser findById(long id) {
        return null;
    }
}
