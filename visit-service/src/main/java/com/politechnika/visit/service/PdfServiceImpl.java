package com.politechnika.visit.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.politechnika.visit.model.entity.Visit;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class PdfServiceImpl implements PdfService {
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font greenFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.GREEN);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
            Font.NORMAL, BaseColor.GRAY);

    public void generatePdf(Visit visit, HttpServletResponse response) {
        try {
            OutputStream o = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=" + visit.getVisitPatient().getEmailPatient() + ".pdf");
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, o);
            pdf.open();
            pdf.add(new Paragraph("Faktura do zaplaty za wizyte lekarska", subFont));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            PdfPTable table = new PdfPTable(2);
            table.addCell("First Name Patient");
            table.addCell(visit.getVisitPatient().getFirstNamePatient());
            table.addCell("Last Name Patient");
            table.addCell(visit.getVisitPatient().getLastNamePatient());
            table.addCell("Email");
            table.addCell(visit.getVisitPatient().getEmailPatient());
            table.addCell("Pesel");
            table.addCell(String.valueOf(visit.getVisitPatient().getPESELPatient()));
            table.addCell("Date of Enrollment visit");
            table.addCell(String.valueOf(visit.getVisitPatient().getEnrollmentDate()));
            table.addCell("Name visit");
            table.addCell(visit.getName());
            table.addCell("Description visit");
            table.addCell(visit.getDescription());
            table.addCell("Doctor");
            table.addCell(visit.getDoctor());
            table.addCell("Start visit");
            table.addCell(visit.getStartDate());
            table.addCell("End visit");
            table.addCell(visit.getEndDate());
            table.addCell("Cost visit");
            table.addCell(String.valueOf(visit.getCostVisit()));
            pdf.add(table);

            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph("Lista zamówionych leków: "));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            PdfPTable tableMedicament = new PdfPTable(2);
            double costMedicaments = 0;
            for (int i = 0; i < visit.getListMedicament().size(); i++) {

                tableMedicament.addCell(visit.getListMedicament().get(i).getName());
                tableMedicament.addCell(String.valueOf(visit.getListMedicament().get(i).getCost()));
                costMedicaments += visit.getListMedicament().get(i).getCost();
            }
            pdf.add(tableMedicament);

            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph("Razem do zaplaty za wizyte: ", greenFont));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            PdfPTable tableSum = new PdfPTable(2);
            tableSum.addCell("Suma PLN");
            tableSum.addCell(String.valueOf(costMedicaments + visit.getCostVisit()));
            pdf.add(tableSum);
            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph("Wlasnoreczny podpis:", smallBold));

            pdf.close();
            o.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

}
