package com.example.java_backend_zest.Controller;

import com.example.java_backend_zest.Dto.AuthRequest;
import com.example.java_backend_zest.Repository.RefreshTokenRepository;
import com.example.java_backend_zest.Repository.RoleRepository;
import com.example.java_backend_zest.Repository.UserRepository;
import com.example.java_backend_zest.Util.JwtUtil;
import com.example.java_backend_zest.entity.RefreshToken;
import com.example.java_backend_zest.entity.Role;
import com.example.java_backend_zest.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;
    private final RefreshTokenRepository refreshRepo;
    private final RoleRepository roleRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()
                    )
            );

            User user = userRepo.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Include roles in JWT
            String jwt = jwtUtil.generateToken(user);

            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setUser(user);
            refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
            refreshRepo.save(refreshToken);

            return ResponseEntity.ok(Map.of(
                    "accessToken", jwt,
                    "refreshToken", refreshToken.getToken()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        // Check if username already exists
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Assign role from request or fallback to ROLE_USER
        String roleName = request.getRole() != null ? request.getRole() : "ROLE_USER";
        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        user.setRoles(List.of(role));

        userRepo.save(user);

        return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "username", user.getUsername(),
                "roles", user.getRoles().stream().map(Role::getName).toList()
        ));
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String,String> body) {
        String token = body.get("refreshToken");
        RefreshToken refresh = refreshRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if(refresh.getExpiryDate().isBefore(LocalDateTime.now())){
            refreshRepo.delete(refresh);
            throw new RuntimeException("Refresh token expired");
        }

        String jwt = jwtUtil.generateToken(refresh.getUser());
        // Rotate refresh token
        refreshRepo.delete(refresh);

        RefreshToken newRefresh = new RefreshToken();
        newRefresh.setToken(UUID.randomUUID().toString());
        newRefresh.setUser(refresh.getUser());
        newRefresh.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshRepo.save(newRefresh);

        return ResponseEntity.ok(Map.of(
                "accessToken", jwt,
                "refreshToken", newRefresh.getToken()
        ));
    }
}