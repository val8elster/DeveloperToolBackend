package com.devtool.developertoolbackend.repositories;

import com.devtool.developertoolbackend.valueobjects.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByEmail(String mail);
    public boolean existsByName(String name);
}

