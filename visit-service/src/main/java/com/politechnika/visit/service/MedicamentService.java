package com.politechnika.visit.service;

import com.politechnika.visit.model.entity.Medicament;

import java.util.List;

public interface MedicamentService {

    List<Medicament> getMedicament();

    Medicament addMedicament(Medicament medicament);

    List<Medicament> getMedicamentFromVisit(Long visitId);

    void medicamentEnrollment(Long visitId, Long medicamentId);
}
