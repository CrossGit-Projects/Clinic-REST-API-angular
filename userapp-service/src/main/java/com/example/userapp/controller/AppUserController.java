package com.example.userapp.controller;

import com.example.userapp.model.AppUser;
import com.example.userapp.repository.AppUserRepository;
import com.example.userapp.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class AppUserController {

    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private AppUserService appUserService;

    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    @RolesAllowed({"ROLE_ADMIN"})
    public AppUser addAppUser(@Valid @RequestBody AppUser appUser) {
        return userRepository.save(appUser);
    }

    @GetMapping("/{appUserId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public ResponseEntity<AppUser> getUser(@PathVariable Long appUserId) {
        return userRepository.findById(appUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{appUserId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public ResponseEntity<?> deleteUser(@PathVariable Long appUserId) {
        return userRepository.findById(appUserId)
                .map(appUser -> {
                    userRepository.delete(appUser);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_PATIENT"})
    public ResponseEntity<?> updateUser(@PathVariable("id") long id,
                                        @RequestBody AppUser appUser) {
        return userRepository.findById(id)
                .map(record -> {
                    record.setUsername(appUser.getUsername());
                    record.setEmail(appUser.getEmail());
                    record.setPassword(appUser.getPassword());
                    AppUser updated = userRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/doctors")
    public List<AppUser> getUsersDoctor() {
        return appUserService.getUserByRoleDoctor();
    }

    @GetMapping("/patients")
    public List<AppUser> getUsersPatient() {
        return appUserService.getUserByRolePatient();
    }

    @GetMapping("/admins")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_PATIENT"})
    public List<AppUser> getUsersAdmin() {
        return appUserService.getUserByRoleAdmin();
    }

    //angular views
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/patient")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public String patientAccess() {
        return "Patient Content.";
    }

    @GetMapping("/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public String doctorAccess() {
        return "Doctor Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

}
