package com.example.userapp;

import com.example.userapp.model.AppRole;
import com.example.userapp.model.ERole;
import com.example.userapp.repository.RoleRepository;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableEurekaClient
public class UserApplication {

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @PostConstruct
    public void createRole() {
        //wstrzyknięcie roli
        AppRole appRoleAdmin = new AppRole();
        appRoleAdmin.setName(ERole.ROLE_ADMIN);
        roleRepository.save(appRoleAdmin);

        AppRole rolemod = new AppRole();
        rolemod.setName(ERole.ROLE_DOCTOR);
        roleRepository.save(rolemod);

        AppRole roleuser = new AppRole();
        roleuser.setName(ERole.ROLE_PATIENT);
        roleRepository.save(roleuser);

    }

}


