package com.politechnika.visit.repository;

import com.politechnika.visit.model.entity.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
}
