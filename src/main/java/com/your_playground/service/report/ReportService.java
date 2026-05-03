package com.your_playground.service.report;

public interface ReportService {

    byte[] exportIncomePdf(Long ownerId);

    byte[] exportIncomeExcel(Long ownerId);
}