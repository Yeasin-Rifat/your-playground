package com.your_playground.service.report;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.your_playground.entity.Payment;
import com.your_playground.service.payment.PaymentService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final PaymentService paymentService;

    public ReportServiceImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // 🔥 PDF EXPORT (FULL DATA)
    @Override
    @Transactional
    public byte[] exportIncomePdf(Long ownerId) {

        List<Payment> payments = paymentService.getOwnerPayments(ownerId);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Owner Income Report\n\n"));

            for (Payment p : payments) {

                String row = "Booking ID: " + p.getBooking().getBookingId() +
                        " | Player: " + p.getBooking().getUser().getName() +
                        " | Turf: " + p.getBooking().getTurf().getLocation() +
                        " | Date: " + p.getBooking().getDate() +
                        " | Time: " + p.getBooking().getTimeSlot() +
                        " | Amount: " + p.getAmount() +
                        " | Method: " + p.getPaymentMethod() +
                        " | Status: " + p.getStatus();

                document.add(new Paragraph(row));
            }

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF export failed");
        }
    }

    // 🔥 EXCEL EXPORT (FULL DATA)
    @Override
    @Transactional
    public byte[] exportIncomeExcel(Long ownerId) {

        List<Payment> payments = paymentService.getOwnerPayments(ownerId);

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Income Report");

            // HEADER
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Booking ID");
            header.createCell(1).setCellValue("Player");
            header.createCell(2).setCellValue("Turf");
            header.createCell(3).setCellValue("Date");
            header.createCell(4).setCellValue("Time");
            header.createCell(5).setCellValue("Amount");
            header.createCell(6).setCellValue("Method");
            header.createCell(7).setCellValue("Status");

            int rowNum = 1;

            for (Payment p : payments) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(p.getBooking().getBookingId());
                row.createCell(1).setCellValue(p.getBooking().getUser().getName());
                row.createCell(2).setCellValue(p.getBooking().getTurf().getLocation());
                row.createCell(3).setCellValue(p.getBooking().getDate().toString());
                row.createCell(4).setCellValue(p.getBooking().getTimeSlot());
                row.createCell(5).setCellValue(p.getAmount());
                row.createCell(6).setCellValue(p.getPaymentMethod());
                row.createCell(7).setCellValue(p.getStatus());
            }

            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Excel export failed");
        }
    }
}