package com.example.BusCompanyApp.controlles.admin;

import com.example.BusCompanyApp.models.Route;
import com.example.BusCompanyApp.models.Schedule;
import com.example.BusCompanyApp.models.Trip;
import com.example.BusCompanyApp.models.Vehicle;
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
@RequestMapping("admin/trip")
public class TripController {

    private final TripService tripService;
    private final VehicleService vehicleService;
    private final RouteService routeService;
    private final DriverService driverService;
    private final ScheduleService scheduleService;

    @Autowired
    public TripController(TripService tripService, VehicleService vehicleService, RouteService routeService, DriverService driverService, ScheduleService scheduleService) {
        this.tripService = tripService;
        this.vehicleService = vehicleService;
        this.routeService = routeService;
        this.driverService = driverService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/list")
    public String listTrips(Model model) {
        model.addAttribute("trips", tripService.findAllTrips());
        return "admin/trip/trip-list";
    }

    @GetMapping("/create")
    public String createTripForm(Model model) {
        model.addAttribute("trip", new Trip());
        model.addAttribute("vehicles", vehicleService.findAllVehicles());
        model.addAttribute("routes", routeService.findAllRoutes());
        model.addAttribute("drivers", driverService.findAllDrivers()); // Новый сервис
        return "admin/trip/trip-create";
    }


    @PostMapping("/create")
    public String createTrip(@Valid @ModelAttribute Trip trip, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vehicles", vehicleService.findAllVehicles());
            model.addAttribute("routes", routeService.findAllRoutes());
            model.addAttribute("drivers", driverService.findAllDrivers());
            return "admin/trip/trip-create";
        }

        trip.setOccupied_seats(0); // Начальное значение
        tripService.saveTrip(trip);

        Schedule schedule = new Schedule();
        schedule.setTrip(trip);
        schedule.setVehicle(trip.getVehicle());
        schedule.setDriver(trip.getDriver());
        scheduleService.saveSchedule(schedule);

        return "redirect:/admin/trip/list";
    }

    @GetMapping("/edit/{id}")
    public String editTrip(@PathVariable Long id, Model model) {
        Optional<Trip> trip = tripService.findTripById(id);
        List<Vehicle> vehicles = vehicleService.findAllVehicles();
        List<Route> routes = routeService.findAllRoutes();

        model.addAttribute("trip", trip);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("routes", routes);

        return "admin/trip/trip-edit";
    }


    @PostMapping("/edit")
    public String editTrip(@Valid @ModelAttribute Trip trip, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("trip", trip);
            return "admin/trip/trip-edit";
        }
        tripService.saveTrip(trip);
        return "redirect:/admin/trip/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrip(@PathVariable("id") Long id) {
        tripService.deleteTripById(id);
        return "redirect:/admin/trip/list";
    }

    @GetMapping("/details/{id}")
    public String getTripDetails(@PathVariable Long id, Model model) {
        Optional<Trip> trip = tripService.findTripById(id);
        if (trip.isPresent()) {
            model.addAttribute("trip", trip.get());
        } else {
            return "error";
        }
        return "admin/trip/trip-details";
    }
}