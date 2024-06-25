package com.devtool.developertoolbackend.valueobjects;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String description;

    public Long leaderId;

    public boolean completed;

    @ManyToMany
    @JoinTable(
            name = "collaborates_on",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    public List<User> collaborators = new ArrayList<>();
}
