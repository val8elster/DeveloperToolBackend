package com.devtool.developertoolbackend.valueobjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    private Long leaderId;

    private boolean completed;

    @ManyToMany
    @JoinTable(
            name = "collaborates_on",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> collaborators = new ArrayList<>();

    @ElementCollection(targetClass = Skill.class)
    @CollectionTable(name = "project_skills", joinColumns = @JoinColumn(name = "project_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "skill")
    private List<Skill> requiredSkills = new ArrayList<>();

    public void addCollaborator(Employee employee) {
        collaborators.add(employee);
        employee.getProjects().add(this);
    }
}
