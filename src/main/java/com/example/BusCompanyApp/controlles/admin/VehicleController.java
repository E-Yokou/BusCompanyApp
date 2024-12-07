package com.example.BusCompanyApp.controlles.admin;

import com.example.BusCompanyApp.models.Vehicle;
import com.example.BusCompanyApp.services.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/list")
    public String listVehicles(Model model) {
        model.addAttribute("vehicles", vehicleService.findAllVehicles());
        return "admin/vehicle/vehicle-list";
    }

    @GetMapping("/create")
    public String createVehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "admin/vehicle/vehicle-create";
    }

    @PostMapping("/create")
    public String createVehicle(@Valid @ModelAttribute Vehicle vehicle, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vehicle", vehicle);
            return "admin/vehicle/vehicle-create";
        }
        vehicleService.saveVehicle(vehicle);
        return "redirect:/admin/vehicle/list";
    }

    @GetMapping("/edit/{id}")
    public String editVehicleForm(@PathVariable("id") Long id, Model model) {
        Optional<Vehicle> vehicle = vehicleService.findVehicleById(id);
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
            return "admin/vehicle/vehicle-edit";
        }
        return "redirect:/admin/vehicle/list";
    }

    @PostMapping("/edit")
    public String editVehicle(@Valid @ModelAttribute Vehicle vehicle, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vehicle", vehicle);
            return "vehicle-edit";
        }
        vehicleService.saveVehicle(vehicle);
        return "redirect:/admin/vehicle/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable("id") Long id) {
        vehicleService.deleteVehicleById(id);
        return "redirect:/admin/vehicle/list";
    }

    @GetMapping("/search")
    public String searchVehicle(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("vehicles", vehicleService.searchVehiclesByKeyword(keyword));
        return "admin/vehicle/vehicle-list";
    }

    @GetMapping("/details/{id}")
    public String getVehicleDetails(@PathVariable Long id, Model model) {
        Optional<Vehicle> vehicle = vehicleService.findVehicleById(id);
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
        } else {
            return "error";
        }
        return "admin/vehicle/vehicle-details";
    }
}