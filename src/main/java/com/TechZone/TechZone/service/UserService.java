package com.TechZone.TechZone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TechZone.TechZone.dto.request.UserCreateDto;
import com.TechZone.TechZone.dto.request.UserLoginDto;
import com.TechZone.TechZone.dto.request.UserUpdateDto;
import com.TechZone.TechZone.dto.response.UserResponse;
import com.TechZone.TechZone.entity.User;
import com.TechZone.TechZone.repository.UserRepository;
import com.TechZone.TechZone.entity.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.TechZone.TechZone.security.JwtUtils;
import com.TechZone.TechZone.dto.response.UserLoginResponse;


@Service
public class UserService {

    private UserRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository utilisateurRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public UserResponse createUser(UserCreateDto request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword); 
        
        
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER);

        User savedUser = utilisateurRepository.save(user);
        return mapToResponse(savedUser);
    }

    

    public UserLoginResponse connect(UserLoginDto request) {
        
        
        User user = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        
        String token = jwtUtils.generateToken(user.getEmail());

        
        UserResponse userDto = mapToResponse(user);

        return new UserLoginResponse(token, userDto);
    }

    public List<UserResponse> getAll() {
        return utilisateurRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<UserResponse> getByRole(Role role) {
        return utilisateurRepository.findByRole(role).stream()
                .map(this::mapToResponse)
                .toList();
    }   

    public UserResponse findById(Long id) {
        User user = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        return mapToResponse(user);
    }

    public UserResponse updateUser(Long id, UserCreateDto dto) {
        
        User user = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        
        
        if (dto.getPassword() != null && !dto.getPassword().equals("dummy")) {
            String hashedPassword = passwordEncoder.encode(dto.getPassword());
            user.setPassword(hashedPassword);
        }
        
        user.setRole(dto.getRole());

        User updatedUser = utilisateurRepository.save(user);

        return mapToResponse(updatedUser);
    }

    public void deleteUser(Long id) {
        utilisateurRepository.deleteById(id);
    }

    public UserResponse updateProfile(Long id, UserUpdateDto dto) {
        User user = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        
        if (dto.getUsername() != null && !dto.getUsername().isEmpty()) {
            user.setUsername(dto.getUsername());
        }
        
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            user.setEmail(dto.getEmail());
        }
        
        
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(dto.getPassword());
            user.setPassword(hashedPassword);
        }

        User updatedUser = utilisateurRepository.save(user);
        return mapToResponse(updatedUser);
    }

    public User findByEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    
    public User findUserEntity(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
