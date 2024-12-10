package com.example.BusCompanyApp.controlles.admin;

import com.example.BusCompanyApp.models.*;
import com.example.BusCompanyApp.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        Schedule schedule = scheduleService.findScheduleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule ID: " + id));

        model.addAttribute("schedule", schedule);
        loadDropdownData(model);
        return "admin/schedule/schedule-edit";
    }

    @PostMapping("/edit")
    public String editSchedule(@Valid @ModelAttribute Schedule schedule, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            loadDropdownData(model);
            return "admin/schedule/schedule-edit";
        }

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
                .orElse(null);

        if (schedule == null) {
            model.addAttribute("errorMessage", "Schedule not found.");
            return "error";
        }

        // Проверки на null для связанных объектов
        String driverInfo = (schedule.getDriver() != null)
                ? schedule.getDriver().getFirstName() + " " + schedule.getDriver().getLastName()
                : "No Driver Assigned";

        String vehicleInfo = (schedule.getVehicle() != null)
                ? schedule.getVehicle().getVehicleNumber()
                : "No Vehicle Assigned";

        String tripInfo = (schedule.getTrip() != null)
                ? schedule.getTrip().getTripNumber()
                : "No Trip Assigned";

        model.addAttribute("schedule", schedule);
        model.addAttribute("driverInfo", driverInfo);
        model.addAttribute("vehicleInfo", vehicleInfo);
        model.addAttribute("tripInfo", tripInfo);

        return "admin/schedule/schedule-details";
    }
}