package com.devtool.developertoolbackend.controllers;

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
    private ProjectService projectService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllUsers();
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployeeById(@PathVariable Long employeeId){
        return employeeService.employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveUser(employee);
    }

    @GetMapping("{employeeId}/login/{password}")
    public boolean checkLogin(@PathVariable Long employeeId, @PathVariable String password) {
        Employee employee = getEmployeeById(employeeId);
        return employee.isMatchingPassword(password);
    }

    @GetMapping("/exists/{mail}")
    public boolean existsByMail(@PathVariable String mail){
        return employeeService.employeeRepository.existsByEmail(mail);
    }

    @GetMapping("/exists/{username}")
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
        employeeService.saveUser(employee);
    }

    @DeleteMapping("/delete/{employeeId}")
    public void deleteUser(@PathVariable Long employeeId){
        Employee employee = getEmployeeById(employeeId);
        employeeService.employeeRepository.delete(employee);
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