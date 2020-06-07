package com.politechnika.visit.model.custom;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class VisitPatient {
    private String emailPatient;
    private LocalDate enrollmentDate;
    private String firstNamePatient;
    private String lastNamePatient;
    private Long PESELPatient;
    private String username;


    public VisitPatient(String emailPatient, String firstNamePatient, String lastNamePatient,
                        long PESELPatient, String username) {
        this.emailPatient = emailPatient;
        this.firstNamePatient = firstNamePatient;
        this.lastNamePatient = lastNamePatient;
        this.PESELPatient = PESELPatient;
        this.username = username;
        this.enrollmentDate = LocalDate.now();
    }

    public VisitPatient() {
    }


    public String getEmailPatient() {
        return emailPatient;
    }

    public void setEmailPatient(String email) {
        this.emailPatient = email;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getFirstNamePatient() {
        return firstNamePatient;
    }

    public void setFirstNamePatient(String firstNamePatient) {
        this.firstNamePatient = firstNamePatient;
    }

    public String getLastNamePatient() {
        return lastNamePatient;
    }

    public void setLastNamePatient(String lastNamePatient) {
        this.lastNamePatient = lastNamePatient;
    }

    public long getPESELPatient() {
        return PESELPatient;
    }

    public void setPESELPatient(long PESELPatient) {
        this.PESELPatient = PESELPatient;
    }

    public void setPESELPatient(Long PESELPatient) {
        this.PESELPatient = PESELPatient;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
