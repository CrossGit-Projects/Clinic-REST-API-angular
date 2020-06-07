package com.example.userapp.controller;

import com.example.userapp.model.AppRole;
import com.example.userapp.model.AppUser;
import com.example.userapp.model.ERole;
import com.example.userapp.payload.request.LoginRequest;
import com.example.userapp.payload.request.SignupRequest;
import com.example.userapp.payload.response.JwtResponse;
import com.example.userapp.payload.response.MessageResponse;
import com.example.userapp.repository.AppUserRepository;
import com.example.userapp.repository.RoleRepository;
import com.example.userapp.security.jwt.JwtUtils;
import com.example.userapp.service.AppUserService;
import com.example.userapp.service.UserDetailsImpl;
import com.example.userapp.utils.AppDemoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AppUserService appUserService;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.getUsername());
        System.out.println(loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (appUserRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (appUserRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        AppUser appUser = new AppUser(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getPESEL());

        String strRoles1 = signUpRequest.getRoles();
        System.out.println(strRoles1);
        Set<String> strRoles = Collections.singleton(strRoles1);
        Set<AppRole> appRoles = new HashSet<>();
        // poprawimy
        if (strRoles == null) {
            AppRole userAppRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            appRoles.add(userAppRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {

                    case "admin":
                        AppRole adminAppRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        appRoles.add(adminAppRole);

                        break;
                    case "doctor":
                        AppRole modAppRole = roleRepository.findByName(ERole.ROLE_DOCTOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        appRoles.add(modAppRole);

                        break;
                    default:
                        AppRole userAppRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        appRoles.add(userAppRole);
                }
            });
        }
        appUser.setActivationCode(AppDemoUtils.randomStringGenerator());
        appUser.setEnabled(false);
        appUser.setAppRoles(appRoles);
        appUserRepository.save(appUser);
        appUserService.sendUserNotification(appUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
