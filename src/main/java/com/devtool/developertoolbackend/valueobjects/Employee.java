package com.devtool.developertoolbackend.valueobjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    protected String password;

    private int level;
    private Long ownProjectId;

    @ElementCollection(targetClass = Skill.class)
    @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "skill")
    private List<Skill> skills = new ArrayList<>();

    @ElementCollection(targetClass = Long.class)
    @CollectionTable(name = "employee_projects", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "projects")
    private List<Long> projects = new ArrayList<>();

    // methods

    public boolean isMatchingPassword(String pw){
        return password.equals(pw);
    }

    public boolean hasRequiredSkill(Skill s){ return this.skills.contains(s); }

    public boolean isCollaborator(Long p) {
        return this.projects.contains(p);
    }

    public void levelUp() { this.level++; }
}
