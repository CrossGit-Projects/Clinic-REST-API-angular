package com.politechnika.visit.controller;

import com.politechnika.visit.exception.PdfGenerateException;
import com.politechnika.visit.model.entity.Visit;
import com.politechnika.visit.service.PdfService;
import com.politechnika.visit.service.VisitServiceImpl;
import com.politechnika.visit.utils.AppDemoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/visits/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private VisitServiceImpl visitServiceImpl;

    @GetMapping("/{visitId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public ResponseEntity<?> generatePdf(@PathVariable long visitId, HttpServletResponse response) {
        Visit visit = visitServiceImpl.getVisitIfExist(visitId);
        try {
            pdfService.generatePdf(visit, response);
            return ResponseEntity.ok().build();
        } catch (PdfGenerateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/patient/{visitId}")
    @RolesAllowed({"ROLE_PATIENT"})
    public ResponseEntity<?> generatePdfByPatient(@PathVariable long visitId, HttpServletResponse response) {
        Visit visit = visitServiceImpl.getVisitIfExist(visitId);
        String loggedUser = AppDemoUtils.getLoggedUser();
        try {
            if(visit.getVisitPatient().getUsername().equals(loggedUser)) {
                pdfService.generatePdf(visit, response);
                return ResponseEntity.ok().build();
            }
        } catch (PdfGenerateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }
}
