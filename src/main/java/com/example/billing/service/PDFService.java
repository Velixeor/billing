package com.example.billing.service;


import com.example.billing.entity.Commission;
import com.example.billing.repository.CommissionRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PDFService {

    private final CommissionRepository commissionRepository;

    public byte[] generatePdf() {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            List<Commission> commissions = commissionRepository.findAll();


            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();


            document.add(new Paragraph("Commission Data Report"));

            PdfPTable table = new PdfPTable(5);  // 5 колонок
            table.addCell("ID");
            table.addCell("From Whom");
            table.addCell("To Whom");
            table.addCell("Amount");
            table.addCell("Currency");

            for (Commission commission : commissions) {
                table.addCell(String.valueOf(commission.getId()));
                table.addCell(String.valueOf(commission.getFromWhom()));
                table.addCell(String.valueOf(commission.getToWhom()));
                table.addCell(String.valueOf(commission.getAmount()));
                table.addCell(commission.getCurrency());
            }

            document.add(table);
            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
