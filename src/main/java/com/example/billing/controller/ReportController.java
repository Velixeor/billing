package com.example.billing.controller;


import com.example.billing.service.PDFService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {
    private final PDFService pdfService;

    @GetMapping("/commissions/pdf")
    public ResponseEntity<byte[]> generateCommissionReport() {

        byte[] pdfBytes = pdfService.generatePdf();

        if (pdfBytes == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "commission_report.pdf");


        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
