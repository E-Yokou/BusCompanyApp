package com.example.BusCompanyApp.controlles;

import com.example.BusCompanyApp.models.*;
import com.example.BusCompanyApp.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/schedules")
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
        return "schedule-list";
    }

    @GetMapping("/create")
    public String createScheduleForm(Model model) {
        model.addAttribute("schedule", new Schedule());
        loadDropdownData(model);
        return "schedule-create";
    }

    @PostMapping("/create")
    public String createSchedule(@Valid @ModelAttribute Schedule schedule, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            loadDropdownData(model);
            return "schedule-create";
        }

        scheduleService.saveSchedule(schedule);
        return "redirect:/schedules/list";
    }

    @GetMapping("/edit/{id}")
    public String editScheduleForm(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleService.findScheduleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule ID: " + id));

        model.addAttribute("schedule", schedule);
        loadDropdownData(model);
        return "schedule-edit";
    }

    @PostMapping("/edit")
    public String editSchedule(@Valid @ModelAttribute Schedule schedule, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            loadDropdownData(model);
            return "schedule-edit";
        }

        scheduleService.saveSchedule(schedule);
        return "redirect:/schedules/list";
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
        return "redirect:/schedules/list";
    }

    @GetMapping("/details/{id}")
    public String getScheduleDetails(@PathVariable Long id, Model model) {
        Optional<Schedule> schedule = scheduleService.findScheduleById(id);
        if (schedule.isPresent()) {
            model.addAttribute("schedule", schedule.get());
        } else {
            return "error";
        }
        return "schedule-details";
    }
}