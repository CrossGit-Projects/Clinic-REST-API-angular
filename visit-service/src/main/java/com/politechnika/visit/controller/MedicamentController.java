package com.politechnika.visit.controller;


import com.politechnika.visit.exception.VisitEnrollmentException;
import com.politechnika.visit.model.entity.Medicament;
import com.politechnika.visit.repository.MedicamentRepository;
import com.politechnika.visit.service.MedicamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/visits/medicament")
public class MedicamentController {

    private final MedicamentService medicamentService;
    private final MedicamentRepository medicamentRepository;

    public MedicamentController(MedicamentService medicamentService, MedicamentRepository medicamentRepository) {
        this.medicamentService = medicamentService;
        this.medicamentRepository = medicamentRepository;
    }


    @GetMapping
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public List<Medicament> getMedicament() {
        return medicamentService.getMedicament();
    }

    @GetMapping("/{medicamentId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public ResponseEntity<Medicament> getMedicament(@PathVariable Long medicamentId) {
        return medicamentRepository.findById(medicamentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public Medicament addMedicament(@Valid @RequestBody Medicament medicament) {
        return medicamentService.addMedicament(medicament);
    }

    @DeleteMapping("/{medicamentId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public ResponseEntity<?> deleteVisit(@PathVariable Long medicamentId) {
        return medicamentRepository.findById(medicamentId)
                .map(medicament -> {
                    medicamentRepository.delete(medicament);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{medicamentId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public ResponseEntity<?> updateVisit(@Valid @PathVariable("medicamentId") Long id,
                                         @RequestBody Medicament medicament) {
        return medicamentRepository.findById(id)
                .map(record -> {
                    record.setName(medicament.getName());
                    record.setDescription(medicament.getDescription());
                    record.setCost(medicament.getCost());
                    record.setDetails(medicament.getDetails());
                    Medicament updated = medicamentRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{visitId}/medicament/{medicamentId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public ResponseEntity<?> medicamentEnrollment(@PathVariable("visitId") Long visitId,
                                                  @PathVariable("medicamentId") Long medicamentId) {
        try {
            medicamentService.medicamentEnrollment(visitId, medicamentId);
            return ResponseEntity.ok().build();
        } catch (VisitEnrollmentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{visitId}/medicament")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public List<Medicament> getMedicamentVisit(@PathVariable Long visitId) {
        return medicamentService.getMedicamentFromVisit(visitId);
    }

}
