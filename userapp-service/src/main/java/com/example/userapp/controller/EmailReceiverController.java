package com.example.userapp.controller;


import com.example.userapp.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/email")
public class EmailReceiverController {

    @Autowired
    private AppUserService appUserService;

//    @GetMapping("/notification")
//    public String sendNotificationToStudent(@RequestBody AppUser appUser) {
//        appUserService.sendUserNotification(appUser);
//        return "Wiadomość powitalna wysłana na RabbitMq! ";
//    }

    @GetMapping(value = "/activatelink/{activationCode}")
    public String activateAccount(@PathVariable("activationCode") String activationCode) {
        appUserService.updateUserActivation(true, activationCode);
        return "Konto zostało akytwowane pomyślnie!";
    }
}
