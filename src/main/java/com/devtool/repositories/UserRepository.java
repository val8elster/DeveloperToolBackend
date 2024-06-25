package com.devtool.repositories;

import com.devtool.valueobjects.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByEmail(String mail);
    public boolean existsByName(String name);
}

