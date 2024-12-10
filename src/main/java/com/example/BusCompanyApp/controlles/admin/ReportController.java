package com.example.BusCompanyApp.controlles.admin;

import com.example.BusCompanyApp.component.ExcelReportGenerator;
import com.example.BusCompanyApp.models.Trip;
import com.example.BusCompanyApp.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/reports")
public class ReportController {

    private final ReportService reportService;
    private final ExcelReportGenerator excelReportGenerator;

    @Autowired
    public ReportController(ReportService reportService, ExcelReportGenerator excelReportGenerator) {
        this.reportService = reportService;
        this.excelReportGenerator = excelReportGenerator;
    }

    @GetMapping("/trips")
    public ResponseEntity<ByteArrayResource> generateTripsReport(@RequestParam("startDate") String startDate,
                                                                 @RequestParam("endDate") String endDate) throws IOException {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<Trip> trips = reportService.getTripsByDateRange(start, end);

        ByteArrayOutputStream outputStream = excelReportGenerator.generateTripsReport(trips);
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=trips_report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/ticket-sales")
    public ResponseEntity<ByteArrayResource> generateTicketSalesReport() throws IOException {
        List<Object[]> ticketSales = reportService.getTicketSalesByTrip();

        ByteArrayOutputStream outputStream = excelReportGenerator.generateTicketSalesReport(ticketSales);
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=ticket_sales_report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/most-popular-routes")
    public ResponseEntity<ByteArrayResource> generateMostPopularRoutesReport() throws IOException {
        List<Object[]> popularRoutes = reportService.getMostPopularRoutes();

        ByteArrayOutputStream outputStream = excelReportGenerator.generateMostPopularRoutesReport(popularRoutes);
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=most_popular_routes_report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}