package com.politechnika.visit.controller;

import com.politechnika.visit.exception.VisitEnrollmentException;
import com.politechnika.visit.model.custom.AppUser;
import com.politechnika.visit.model.entity.Visit;
import com.politechnika.visit.repository.VisitRepository;
import com.politechnika.visit.service.VisitService;
import com.politechnika.visit.service.VisitServiceImpl;
import com.politechnika.visit.utils.AppDemoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/visits/web")
public class VisitController {

    private final VisitService visitService;
    private final VisitRepository visitRepository;

    public VisitController(VisitService visitservice, VisitRepository visitRepository) {
        this.visitService = visitservice;
        this.visitRepository = visitRepository;
    }

    @Autowired
    private VisitServiceImpl visitServiceImpl;

    @GetMapping
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public List<Visit> getVisits() {
        return visitService.getVisits();
    }

    @GetMapping("/{visitId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public ResponseEntity<Visit> getVisit(@PathVariable Long visitId) {
        return visitRepository.findById(visitId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{visitId}")
    @RolesAllowed({"ROLE_PATIENT"})
    public ResponseEntity<Visit> getVisitByPatient(@PathVariable Long visitId) {
        Visit visit = visitServiceImpl.getVisitIfExist(visitId);
        String loggedUser = AppDemoUtils.getLoggedUser();
        if (visit.getVisitPatient().getUsername().equals(loggedUser)) {

            return ResponseEntity.accepted().body(visit);
        }
        return ResponseEntity.status((HttpStatus.CONFLICT)).build();
    }

    @PostMapping
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public Visit addVisit(@Valid @RequestBody Visit visit) {
        return visitService.addVisit(visit);
    }

    @DeleteMapping("/{visitId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public ResponseEntity<?> deleteVisit(@PathVariable Long visitId) {
        return visitRepository.findById(visitId)
                .map(visit -> {
                    visitRepository.delete(visit);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{visitId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public ResponseEntity<?> updateVisit(@Valid @PathVariable("visitId") Long id,
                                         @RequestBody Visit visit) {
        return visitRepository.findById(id)
                .map(record -> {
                    record.setName(visit.getName());
                    record.setDescription(visit.getDescription());
                    record.setEndDate(visit.getEndDate());
                    record.setStartDate(visit.getStartDate());
                    record.setCostVisit(visit.getCostVisit());
                    Visit updated = visitRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{visitId}/patient/{appUserId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public ResponseEntity<?> visitEnrollment(@PathVariable("visitId") Long visitId,
                                             @PathVariable("appUserId") Long appUserId) {
        try {
            visitService.visitEnrollment(visitId, appUserId);
            return ResponseEntity.ok().build();
        } catch (VisitEnrollmentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{visitId}/patient")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public List<AppUser> getVisitPatient(@PathVariable Long visitId) {
        return visitService.getUserFromUserService(visitId);
    }
}
