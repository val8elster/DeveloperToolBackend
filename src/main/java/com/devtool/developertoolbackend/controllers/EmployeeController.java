package com.devtool.developertoolbackend.controllers;

import com.devtool.developertoolbackend.repositories.EmployeeRepository;
import com.devtool.developertoolbackend.valueobjects.Employee;
import com.devtool.developertoolbackend.valueobjects.Project;
import com.devtool.developertoolbackend.valueobjects.Skill;
import com.devtool.developertoolbackend.services.ProjectService;
import com.devtool.developertoolbackend.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllUsers();
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployeeById(@PathVariable Long employeeId){
        return employeeService.employeeRepository.findById(employeeId).orElse(null);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/get/name/{name}")
    public Employee getEmployeeByName(@PathVariable String name){
        return employeeService.employeeRepository.findByName(name);
    }

    @PostMapping("/create")
    public Employee createEmployee(@RequestBody Employee employee) {
        System.out.println(employee.toString());
        return employeeService.employeeRepository.save(employee);
    }

    @GetMapping("{employeeId}/login/{password}")
    public boolean checkLogin(@PathVariable Long employeeId, @PathVariable String password) {
        Employee employee = getEmployeeById(employeeId);
        return employee.isMatchingPassword(password);
    }

    @GetMapping("/exists/mail/{mail}")
    public boolean existsByMail(@PathVariable String mail){
        return employeeService.employeeRepository.existsByEmail(mail);
    }

    @GetMapping("/exists/name/{username}")
    public boolean existsByUsername(@PathVariable String username){
        return employeeService.employeeRepository.existsByName(username);
    }

    @GetMapping("/{employeeId}/project")
    public Project getLeadersProject(@PathVariable Long employeeId){
        Employee employee = getEmployeeById(employeeId);
        return projectService.projectRepository.findById(employee.getOwnProjectId()).orElse(null);
    }

    @PutMapping("/{employeeId}/levelUp")
    public void levelUp(@PathVariable Long employeeId){
        Employee employee = getEmployeeById(employeeId);
        int level = employee.getLevel() + 1;
        employee.setLevel(level);
        employeeService.employeeRepository.save(employee);
    }

    @DeleteMapping("/delete/{employeeId}")
    public void deleteUser(@PathVariable Long employeeId){
        Employee employee = getEmployeeById(employeeId);
        employeeService.employeeRepository.delete(employee);

        Project project = projectService.projectRepository.findById(employee.getOwnProjectId()).orElse(null);
        if(project != null){
            projectService.projectRepository.delete(project);
        }
    }

    @GetMapping("/{employeeId}/collaborates/{projectId}")
    public boolean collaborate(@PathVariable Long employeeId, @PathVariable Long projectId){
        return getEmployeeById(employeeId).isCollaborator(projectId);
    }

    @GetMapping("/{employeeId}/hasSkill/{skill}")
    public boolean hasSkill(@PathVariable Long employeeId, @PathVariable Skill skill){
        return getEmployeeById(employeeId).getSkills().contains(skill);
    }
}