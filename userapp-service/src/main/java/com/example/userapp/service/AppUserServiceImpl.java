package com.example.userapp.service;

import com.example.userapp.model.AppUser;
import com.example.userapp.model.ERole;
import com.example.userapp.model.Notification;
import com.example.userapp.repository.AppUserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public void sendUserNotification(AppUser appUser) {
        Notification notification = createNotification(appUser);
        rabbitTemplate.convertAndSend("232998", notification);

    }
    //Todo Jeśli Panowie będą mieli czas to proszę zmienić, żeby wyszukiwanie po rolach było na bazie
    // Czyli nie pobieramy wszystkich userów i nie filtrujemy w JVM tylko robimy to już na bazie danych (nowe metody w repozytorium).
    // Ale to już na koniec jak wszystkie funkcjonalności na zaliczenie będą i czasu zostanie :)
    @Override
    public List<AppUser> getUserByRoleDoctor() {
        List<AppUser> allUser = appUserRepository.findAll();
        List<AppUser> usersDoctor = allUser.stream()
                .filter(all -> all.getAppRoles()
                        .stream()
                        .anyMatch(role -> role.name.equals(ERole.ROLE_DOCTOR)))
                .collect(Collectors.toList());
        return usersDoctor;
    }

    public List<AppUser> getUserByRoleAdmin() {
        List<AppUser> allUser = appUserRepository.findAll();
        List<AppUser> usersAdmin = allUser.stream()
                .filter(all -> all.getAppRoles()
                        .stream()
                        .anyMatch(role -> role.name.equals(ERole.ROLE_ADMIN)))
                .collect(Collectors.toList());
        return usersAdmin;
    }

    public List<AppUser> getUserByRolePatient() {
        List<AppUser> allUser = appUserRepository.findAll();
        List<AppUser> usersPatient = allUser.stream()
                .filter(all -> all.getAppRoles()
                        .stream()
                        .anyMatch(role -> role.name.equals(ERole.ROLE_PATIENT)))
                .collect(Collectors.toList());
        return usersPatient;
    }

    private Notification createNotification(AppUser appUser) {
        Notification notification = new Notification();
        notification.setEmail(appUser.getEmail());
        notification.setTitle("Witaj " + appUser.getUsername() + " !");
        notification.setContent("Wymagane potwierdzenie rejestracji. Kliknij w poniższy link aby aktywować konto: " +
                "http://localhost:8081/api/users/email/activatelink/" + appUser.getActivationCode());
        return notification;
    }

    @Transactional
    public void updateUserActivation(boolean activeCode, String activationCode) {
        appUserRepository.updateActivation(activeCode, activationCode);
    }
}
