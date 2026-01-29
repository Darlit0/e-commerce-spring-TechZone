package com.TechZone.TechZone.controller.api;

import org.springframework.web.bind.annotation.*;
import com.TechZone.TechZone.service.UserService;
import com.TechZone.TechZone.dto.response.UserResponse;
import com.TechZone.TechZone.dto.request.UserCreateDto;
import com.TechZone.TechZone.entity.Role;
import com.TechZone.TechZone.dto.request.UserLoginDto;
import com.TechZone.TechZone.dto.response.UserLoginResponse;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/role/{role}")
    public List<UserResponse> getByRole(@PathVariable String role) {
    
    try {
        Role roleEnum = Role.valueOf(role.toUpperCase()); 
        return userService.getByRole(roleEnum);
    } catch (IllegalArgumentException e) {
        throw new RuntimeException("Invalid role. Use 'admin' or 'user'.");
    }
}

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/create")
    public UserResponse createUser(@RequestBody UserCreateDto dto) {
        return userService.createUser(dto);
    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginDto loginRequest) {
        return userService.connect(loginRequest);
    }

    @PutMapping("/update/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserCreateDto dto) {
        return userService.updateUser(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
