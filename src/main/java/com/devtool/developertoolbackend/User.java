package com.devtool.developertoolbackend;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String email;

    private String password;

    public int level;
    public Long ownProjectId;

    @ManyToMany(mappedBy = "collaborators")
    private List<Project> projects = new ArrayList<>();

    // methods

    public boolean isMatchingPassword(String pw){
        return password.equals(pw);
    }
}
