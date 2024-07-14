package com.devtool.developertoolbackend.valueobjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    protected String password;

    private int level;
    private Long ownProjectId;

    @ElementCollection(targetClass = Skill.class)
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "skill")
    private List<Skill> skills = new ArrayList<>();

    @ManyToMany(mappedBy = "collaborators")
    private List<Project> projects = new ArrayList<>();

    // methods

    public boolean isMatchingPassword(String pw){
        return password.equals(pw);
    }

    public boolean hasRequiredSkill(Skill s){ return this.skills.contains(s); }

    public boolean isCollaborator(Long p) {
        for (Project ps : this.projects) {
            if(ps.getId().equals(p)) return true;
        }
        return false;
    }
}
