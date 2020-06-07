package com.politechnika.visit.service;

import com.politechnika.visit.model.custom.AppUser;
import com.politechnika.visit.model.entity.Visit;

import java.util.List;

public interface VisitService {

    List<Visit> getVisits();

    Visit addVisit(Visit visit);

    List<AppUser> getUserFromUserService(Long visitId);

    void visitEnrollment(Long visitId, Long appUserId);

}
