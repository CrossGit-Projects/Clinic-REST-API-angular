package com.politechnika.visit.service;

import com.politechnika.visit.model.entity.Visit;

import javax.servlet.http.HttpServletResponse;

public interface PdfService {
    void generatePdf(Visit visit, HttpServletResponse response);
}
