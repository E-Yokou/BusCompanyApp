package com.example.BusCompanyApp.controlles.admin;

import com.example.BusCompanyApp.component.ExcelReportGenerator;
import com.example.BusCompanyApp.models.*;
import com.example.BusCompanyApp.repositories.TicketRepository;
import com.example.BusCompanyApp.services.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final TripService tripService;
    private final VehicleService vehicleService;
    private final DriverService driverService;

    @Autowired
    private ExcelReportGenerator excelReportGenerator;

    @Autowired
    private TicketService ticketService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, TripService tripService, VehicleService vehicleService, DriverService driverService) {
        this.scheduleService = scheduleService;
        this.tripService = tripService;
        this.vehicleService = vehicleService;
        this.driverService = driverService;
    }

    @GetMapping("/list")
    public String listSchedules(Model model) {
        model.addAttribute("schedules", scheduleService.findAllSchedules());
        return "admin/schedule/schedule-list";
    }

    @GetMapping("/create")
    public String createScheduleForm(Model model) {
        model.addAttribute("schedule", new Schedule());
        loadDropdownData(model);
        return "admin/schedule/schedule-create";
    }

    @PostMapping("/create")
    public String createSchedule(@Valid @ModelAttribute Schedule schedule, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            loadDropdownData(model);
            return "admin/schedule/schedule-create";
        }

        scheduleService.saveSchedule(schedule);
        return "redirect:/admin/schedule/list";
    }

    @GetMapping("/edit/{id}")
    public String editScheduleForm(@PathVariable Long id, Model model) {
        Optional<Schedule> scheduleOptional = scheduleService.findScheduleById(id);
        if (scheduleOptional.isPresent()) {
            Schedule schedule = scheduleOptional.get();
            model.addAttribute("schedule", schedule);

            // Fetch related entities
            List<Trip> trips = tripService.findAllTrips();
            List<Vehicle> vehicles = vehicleService.findAllVehicles();
            List<Driver> drivers = driverService.findAllDrivers();

            model.addAttribute("trips", trips);
            model.addAttribute("vehicles", vehicles);
            model.addAttribute("drivers", drivers);

            return "admin/schedule/edit-schedule";
        } else {
            return "redirect:/admin/schedule/list";
        }
    }

    @PostMapping("/edit")
    public String editSchedule(@Valid @ModelAttribute Schedule schedule, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Fetch related entities
            List<Trip> trips = tripService.findAllTrips();
            List<Vehicle> vehicles = vehicleService.findAllVehicles();
            List<Driver> drivers = driverService.findAllDrivers();

            model.addAttribute("trips", trips);
            model.addAttribute("vehicles", vehicles);
            model.addAttribute("drivers", drivers);

            return "admin/schedule/edit-schedule";
        }

        // Ensure the Schedule entity is correctly populated
        Trip trip = tripService.findTripById(schedule.getTrip().getId()).orElseThrow(() -> new RuntimeException("Маршрут не найден"));
        Vehicle vehicle = vehicleService.findVehicleById(schedule.getVehicle().getId()).orElseThrow(() -> new RuntimeException("ТС не найдено"));
        Driver driver = driverService.findDriverById(schedule.getDriver().getId()).orElseThrow(() -> new RuntimeException("Водитель не найдет"));

        schedule.setTrip(trip);
        schedule.setVehicle(vehicle);
        schedule.setDriver(driver);

        scheduleService.saveSchedule(schedule);

        return "redirect:/admin/schedule/list";
    }

    // Вспомогательный метод для загрузки данных выпадающих списков
    private void loadDropdownData(Model model) {
        List<Driver> drivers = driverService.findAllDrivers();
        List<Vehicle> vehicles = vehicleService.findAllVehicles();
        List<Trip> trips = tripService.findAllTrips();

        model.addAttribute("drivers", drivers);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("trips", trips);
    }

    @GetMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable("id") Long id) {
        scheduleService.deleteScheduleById(id);
        return "redirect:/admin/schedule/list";
    }

    @GetMapping("/details/{id}")
    public String getScheduleDetails(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleService.findScheduleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule ID: " + id));

        model.addAttribute("schedule", schedule);
        return "admin/schedule/schedule-details";
    }

    @GetMapping("/tickets-sold")
    public void generateTicketsSoldExcel(@RequestParam("tripId") Long tripId,
                                         @RequestParam("date") LocalDate date,
                                         HttpServletResponse response) throws IOException {
        List<Ticket> tickets = ticketService.getTicketsSoldByTripAndDate(tripId, date);
        excelReportGenerator.generateTicketsSoldExcel(tickets, response);
    }
}
