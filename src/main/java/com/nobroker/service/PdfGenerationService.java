package com.nobroker.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nobroker.entity.User;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfGenerationService {

    public byte[] generatePdfReport(List<User> users) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Create a table with column headers
        PdfPTable table = new PdfPTable(6); // 6 columns for the User entity fields
        addTableHeader(table);

        // Add user data to the table
        for (User user : users) {
            addTableRow(table, user);
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Email");
        table.addCell("Mobile");
        table.addCell("Password");
        table.addCell("Email Verified");
    }

    private void addTableRow(PdfPTable table, User user) {
        table.addCell(String.valueOf(user.getId()));
        table.addCell(user.getName());
        table.addCell(user.getEmail());
        table.addCell(user.getMobile());
        table.addCell(user.getPassword());
        table.addCell(String.valueOf(user.isEmailVerified()));
    }
}
