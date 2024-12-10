package com.example.BusCompanyApp.component;

import com.example.BusCompanyApp.models.Trip;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelReportGenerator {

    public ByteArrayOutputStream generateTripsReport(List<Trip> trips) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Trips");

        // Create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Trip Number");
        header.createCell(1).setCellValue("Departure Location");
        header.createCell(2).setCellValue("Destination Location");
        header.createCell(3).setCellValue("Occupied Seats");
        header.createCell(4).setCellValue("Departure Datetime");
        header.createCell(5).setCellValue("Arrival Datetime");

        // Create data rows
        int rowNum = 1;
        for (Trip trip : trips) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(trip.getTripNumber());
            row.createCell(1).setCellValue(trip.getDepartureLocation());
            row.createCell(2).setCellValue(trip.getDestinationLocation());
            row.createCell(3).setCellValue(trip.getOccupied_seats());
            row.createCell(4).setCellValue(trip.getDepartureDatetime().toString());
            row.createCell(5).setCellValue(trip.getArrivalDatetime().toString());
        }

        // Auto-size columns
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream;
    }

    public ByteArrayOutputStream generateTicketSalesReport(List<Object[]> ticketSales) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ticket Sales");

        // Create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Trip ID");
        header.createCell(1).setCellValue("Tickets Sold");
        header.createCell(2).setCellValue("Total Revenue");

        // Create data rows
        int rowNum = 1;
        for (Object[] sale : ticketSales) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((Long) sale[0]);
            row.createCell(1).setCellValue((Long) sale[1]);
            row.createCell(2).setCellValue((Double) sale[2]);
        }

        // Auto-size columns
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream;
    }

    public ByteArrayOutputStream generateMostPopularRoutesReport(List<Object[]> popularRoutes) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Most Popular Routes");

        // Create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Route Name");
        header.createCell(1).setCellValue("Trip Count");

        // Create data rows
        int rowNum = 1;
        for (Object[] route : popularRoutes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((String) route[0]);
            row.createCell(1).setCellValue((Long) route[1]);
        }

        // Auto-size columns
        for (int i = 0; i < 2; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream;
    }
}
