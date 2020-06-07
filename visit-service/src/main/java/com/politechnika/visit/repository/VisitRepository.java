package com.politechnika.visit.repository;

import com.politechnika.visit.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    Visit findById(long id);
}
