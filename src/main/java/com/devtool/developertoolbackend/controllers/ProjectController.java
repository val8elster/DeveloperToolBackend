package com.devtool.developertoolbackend.controllers;

import com.devtool.developertoolbackend.valueobjects.Project;
import com.devtool.developertoolbackend.valueobjects.Skill;
import com.devtool.developertoolbackend.valueobjects.Employee;
import com.devtool.developertoolbackend.services.ProjectService;
import com.devtool.developertoolbackend.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeController employeeController;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{projectId}")
    public Project getProjectById(@PathVariable Long projectId){
        return projectService.projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        return projectService.saveProject(project);
    }

    @GetMapping("/exists/{name}")
    public boolean existsByName(@PathVariable String name){
        return projectService.projectRepository.existsByName(name);
    }

    @GetMapping("/exists/{leaderId}")
    public boolean existsByLeader(@PathVariable Long leaderId){
        return projectService.projectRepository.existsByLeaderId(leaderId);
    }

    @GetMapping("check/{projectId}")
    public boolean isCompleted(@PathVariable Long projectId){
        Project project = getProjectById(projectId);
        return project.isCompleted();
    }

    @GetMapping("/{projectId}/users")
    public List<Employee> getAllCollaborators(@PathVariable Long projectId){
        Project project = getProjectById(projectId);
        return project.getCollaborators();
    }

    @GetMapping("{projectId}/skills")
    public List<Skill> getAllSkills(@PathVariable Long projectId){
        return getProjectById(projectId).getRequiredSkills();
    }

    @PostMapping("/{projectId}/users/{userId}")
    public Project addEmployeeToProject(@PathVariable Long projectId, @PathVariable Long userId) {
        Project project = getProjectById(projectId);
        Employee employee = employeeService.employeeRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        project.getCollaborators().add(employee);
        return projectService.saveProject(project);
    }

    @DeleteMapping("/delete/{projectId}")
    public void deleteProject(@PathVariable Long projectId){
        Project project = getProjectById(projectId);
        projectService.projectRepository.delete(project);
    }

    @PutMapping("/{projectId}/addcollab/{userId}")
    public boolean addCollaborator(@PathVariable Long projectId, @PathVariable Long userId){
        Employee employee = employeeController.getEmployeeById(userId);

        Project project = getProjectById(projectId);

        if(project.getCollaborators().contains(employee)){
            return false;
        }
        else {
            project.getCollaborators().add(employee);
            return true;
        }
    }
}