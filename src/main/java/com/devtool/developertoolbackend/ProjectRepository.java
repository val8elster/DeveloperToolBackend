package com.devtool.developertoolbackend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    public boolean existsByLeaderId(Long leaderId);
    public boolean existsByName(String name);
}

