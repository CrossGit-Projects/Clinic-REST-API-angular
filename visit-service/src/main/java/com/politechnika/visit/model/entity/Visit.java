package com.politechnika.visit.model.entity;

import com.politechnika.visit.model.custom.VisitPatient;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String name;
    private String description;
    private String doctor;
    private Double costVisit;
    private String startDate;
    private String endDate;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "signVisit")
    private List<Medicament> listMedicament = new ArrayList<>();;

    @Embedded()
    private VisitPatient visitPatient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public VisitPatient getVisitPatient() {
        return visitPatient;
    }

    public void setVisitPatient(VisitPatient visitPatient) {
        this.visitPatient = visitPatient;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public List<Medicament> getListMedicament() {
        return listMedicament;
    }

    public void setListMedicament(List<Medicament> listMedicament) {
        this.listMedicament = listMedicament;
    }

    public Double getCostVisit() {
        return costVisit;
    }

    public void setCostVisit(Double costVisit) {
        this.costVisit = costVisit;
    }
}
