package com.devtool.developertoolbackend.controllers;

import com.devtool.developertoolbackend.repositories.EmployeeRepository;
import com.devtool.developertoolbackend.repositories.ProjectRepository;
import com.devtool.developertoolbackend.valueobjects.Project;
import com.devtool.developertoolbackend.valueobjects.Skill;
import com.devtool.developertoolbackend.valueobjects.Employee;
import com.devtool.developertoolbackend.services.ProjectService;
import com.devtool.developertoolbackend.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeController employeeController;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectRepository projectRepository;

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

    @GetMapping("/exists/name/{name}")
    public boolean existsByName(@PathVariable String name){
        List<Project> projects = projectService.getAllProjects();
        for (Project project : projects) {
            if(Objects.equals(project.getName(), name)){
                return true;
            }
        }
        return false;
    }

    @GetMapping("/exists/leader/{leaderId}")
    public boolean existsByLeader(@PathVariable Long leaderId){
        List<Project> projects = projectService.getAllProjects();
        for (Project project : projects) {
            if(Objects.equals(project.getLeaderId(), leaderId)){
                return true;
            }
        }
        return false;    }

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

    @PutMapping("/{projectId}/users/{userId}")
    public Project addEmployeeToProject(@PathVariable Long projectId, @PathVariable Long userId) {
        Project project = getProjectById(projectId);
        Employee employee = employeeService.employeeRepository.findById(userId).orElse(null);
        project.addCollaborator(employee);
        projectService.projectRepository.save(project);
        return null;
    }

    @DeleteMapping("/delete/{projectId}")
    public void deleteProject(@PathVariable Long projectId){
        Project project = getProjectById(projectId);
        projectService.projectRepository.delete(project);
    }

    @GetMapping("/{projectId}/hasCollaborator/{employeeId}")
    public boolean hasCollaborator(@PathVariable Long projectId, @PathVariable Long employeeId){
        Project project = getProjectById(projectId);
        Employee employee = employeeService.employeeRepository.findById(employeeId).orElse(null);
        return project.getCollaborators().contains(employee);
    }

    @PutMapping("/{projectId}/complete")
    public void completeProject(@PathVariable Long projectId){
        Project p = getProjectById(projectId);
        p.setCompleted(true);
        for(Employee e : p.getCollaborators()){
            e.levelUp();
            employeeRepository.save(e);
        }
        Employee em = employeeController.getEmployeeById(p.getLeaderId());
        em.levelUp();
        em.levelUp();
        employeeRepository.save(em);

        projectRepository.save(p);
    }

    @PutMapping("/{projectId}/edit/name/{name}")
    public void editName(@PathVariable Long projectId, @PathVariable String newName){
        Project p = getProjectById(projectId);
        p.setName(newName);
        projectRepository.save(p);
    }

    @PutMapping("/{projectId}/edit/description/{description}")
    public void editDescription(@PathVariable Long projectId, @PathVariable String newDescription){
        Project p = getProjectById(projectId);
        p.setDescription(newDescription);
        projectRepository.save(p);
    }

    @PutMapping("/{projectId}/edit/skills")
    public void editName(@PathVariable Long projectId, @RequestBody List<Skill> newSkills){
        Project p = getProjectById(projectId);
        p.setRequiredSkills(newSkills);
        projectRepository.save(p);
    }
}