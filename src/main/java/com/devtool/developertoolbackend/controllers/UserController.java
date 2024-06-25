package com.devtool.developertoolbackend.controllers;

import com.devtool.developertoolbackend.valueobjects.Project;
import com.devtool.developertoolbackend.valueobjects.User;
import com.devtool.developertoolbackend.services.ProjectService;
import com.devtool.developertoolbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId){
        return userService.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("{userId}/login/{password}")
    public boolean checkLogin(@PathVariable Long userId, @PathVariable String password) {
        User user = getUserById(userId);
        return user.isMatchingPassword(password);
    }

    @GetMapping("/exists/{mail}")
    public boolean existsByMail(@PathVariable String mail){
        return userService.userRepository.existsByEmail(mail);
    }

    @GetMapping("/exists/{username}")
    public boolean existsByUsername(@PathVariable String username){
        return userService.userRepository.existsByName(username);
    }

    @GetMapping("/{userId}/project")
    public Project getLeadersProject(@PathVariable Long userId){
        User user = getUserById(userId);
        return projectService.projectRepository.findById(user.ownProjectId).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @PutMapping("/{userId}/levelUp")
    public void levelUp(@PathVariable Long userId){
        User user = getUserById(userId);
        user.level++;
        userService.saveUser(user);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable Long userId){
        User user = getUserById(userId);
        userService.userRepository.delete(user);
    }
}