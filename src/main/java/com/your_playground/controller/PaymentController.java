package com.your_playground.controller;

import com.your_playground.service.payment.PaymentService;
import com.your_playground.service.report.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final ReportService reportService;

    public PaymentController(PaymentService paymentService,
                             ReportService reportService) {
        this.paymentService = paymentService;
        this.reportService = reportService;
    }

    // 🔥 TOTAL INCOME
    @GetMapping("/income/{ownerId}")
    public Double getIncome(@PathVariable Long ownerId) {
        return paymentService.getOwnerIncome(ownerId);
    }

    // 📄 PDF EXPORT
    @GetMapping("/income/export/pdf/{ownerId}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long ownerId) {

        byte[] data = reportService.exportIncomePdf(ownerId);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=income.pdf")
                .body(data);
    }

    // 📊 EXCEL EXPORT
    @GetMapping("/income/export/excel/{ownerId}")
    public ResponseEntity<byte[]> downloadExcel(@PathVariable Long ownerId) {

        byte[] data = reportService.exportIncomeExcel(ownerId);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=income.xlsx")
                .body(data);
    }
}