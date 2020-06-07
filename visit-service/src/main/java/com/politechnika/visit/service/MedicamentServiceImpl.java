package com.politechnika.visit.service;

import com.politechnika.visit.exception.MedicamentEnrollmentException;
import com.politechnika.visit.exception.VisitEnrollmentException;
import com.politechnika.visit.model.entity.Medicament;
import com.politechnika.visit.model.entity.Visit;
import com.politechnika.visit.repository.MedicamentRepository;
import com.politechnika.visit.repository.VisitRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentServiceImpl implements MedicamentService {

    private final MedicamentRepository medicamentRepository;
    private final VisitRepository visitRepository;

    public MedicamentServiceImpl(MedicamentRepository medicamentRepository, VisitRepository visitRepository) {
        this.medicamentRepository = medicamentRepository;
        this.visitRepository = visitRepository;
    }

    public List<Medicament> getMedicament() {
        return medicamentRepository.findAll();
    }

    public Medicament addMedicament(Medicament medicament) {
        return medicamentRepository.save(medicament);
    }

    public List<Medicament> getMedicamentFromVisit(Long visitId) {
        Visit visit = getVisitIfExist(visitId);
        List<Medicament> listMedicaments = visit.getListMedicament();
        return listMedicaments;
    }

    private Visit getVisitIfExist(Long visitId) {
        return visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitEnrollmentException(("Visit does not exist")));
    }

    @Override
    public void medicamentEnrollment(Long visitId, Long medicamentId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitEnrollmentException("Visit does not exist"));
        Medicament medicament = medicamentRepository.findById(medicamentId)
                .orElseThrow(() -> new MedicamentEnrollmentException("Medicament does not exist"));

        validationEnrollment(visit, medicament);

        medicament.setSignVisit(visit);
        medicamentRepository.save(medicament);
    }

    private void validationEnrollment(Visit visit, Medicament medicament) {
        if (StringUtils.isEmpty(medicament.getName())) {
            throw new MedicamentEnrollmentException("Medicament name is empty");
        }
        if (visit.getListMedicament().stream().anyMatch(drug -> medicament.getName().equals(drug.getName()))) {
            throw new MedicamentEnrollmentException("Medicament already enrolled on this visit");
        }
    }
}
