package com.politechnika.visit.service;

import com.politechnika.visit.exception.VisitEnrollmentException;
import com.politechnika.visit.model.custom.AppUser;
import com.politechnika.visit.model.custom.VisitPatient;
import com.politechnika.visit.model.entity.Visit;
import com.politechnika.visit.repository.VisitRepository;
import com.politechnika.visit.utils.AppDemoUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final UserServiceClient userServiceClient;

    public VisitServiceImpl(VisitRepository visitRepository, UserServiceClient userServiceClient) {
        this.visitRepository = visitRepository;
        this.userServiceClient = userServiceClient;
    }

    public List<Visit> getVisits() {
        return visitRepository.findAll();
    }

    public Visit addVisit(Visit visit) {
        String username = AppDemoUtils.getLoggedUser();
        visit.setDoctor(username);
        return visitRepository.save(visit);
    }

    public List<AppUser> getUserFromUserService(Long visitId) {
        Visit visit = getVisitIfExist(visitId);
        String patient = visit.getVisitPatient().getEmailPatient();

        return userServiceClient.getUsers(patient);
    }

    public Visit getVisitIfExist(Long visitId) {
        return visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitEnrollmentException(("Visit does not exist")));
    }

    public void visitEnrollment(Long visitId, Long appUserId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitEnrollmentException(("Visit does not exist")));
        AppUser appUser = userServiceClient.getUserById(appUserId);

        validationEnrollmentVisit(visit, appUser);

        visit.setVisitPatient(new VisitPatient(
                appUser.getEmail(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getPESEL(),
                appUser.getUsername()));
        visitRepository.save(visit);

    }

    private void validationEnrollmentVisit(Visit visit, AppUser appUser) {
        if (StringUtils.isEmpty(appUser.getEmail())) {
            throw new VisitEnrollmentException("AppUser email is empty");
        }
    }
}
