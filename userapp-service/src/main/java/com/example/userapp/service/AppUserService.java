package com.example.userapp.service;

import com.example.userapp.model.AppUser;

import java.util.List;

public interface AppUserService {
    void sendUserNotification(AppUser appUser);

    List<AppUser> getUserByRoleDoctor();

    List<AppUser> getUserByRoleAdmin();

    List<AppUser> getUserByRolePatient();

    void updateUserActivation(boolean activeCode, String activationCode);
}
