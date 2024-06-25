package com.devtool.services;

import com.devtool.valueobjects.Project;
import com.devtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    public ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project saveProject(Project devProject) {
        return projectRepository.save(devProject);
    }
}

