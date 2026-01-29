package com.TechZone.TechZone.controller.api;

import com.TechZone.TechZone.dto.request.UserUpdateDto;
import com.TechZone.TechZone.dto.response.OrderDetailResponse;
import com.TechZone.TechZone.entity.User;
import com.TechZone.TechZone.service.OrderService;
import com.TechZone.TechZone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profil")
public class ProfilRestController {

    @Autowired
    private UserService utilisateurService;

    @Autowired
    private OrderService commandeService;

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getProfilInfo(Authentication authentication) {
        String email = authentication.getName();
        User user = utilisateurService.findByEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateProfil(
            @RequestBody UserUpdateDto dto,
            Authentication authentication) {
        String email = authentication.getName();
        User user = utilisateurService.findByEmail(email);
        
        utilisateurService.updateProfile(user.getId(), dto);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Profile updated successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDetailResponse>> getMyOrders(Authentication authentication) {
        String email = authentication.getName();
        User user = utilisateurService.findByEmail(email);

        List<OrderDetailResponse> orders = commandeService.getUserOrders(user.getId());
        return ResponseEntity.ok(orders);
    }
}
