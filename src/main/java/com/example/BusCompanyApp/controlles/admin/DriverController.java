package com.example.BusCompanyApp.controlles.admin;

import com.example.BusCompanyApp.models.Driver;
import com.example.BusCompanyApp.services.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/driver")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/list")
    public String listDrivers(Model model) {
        model.addAttribute("drivers", driverService.findAllDrivers());
        return "admin/driver/driver-list";
    }

    @GetMapping("/create")
    public String createDriverForm(Model model) {
        model.addAttribute("drivers", new Driver());
        return "admin/driver/driver-create";
    }

    @PostMapping("/create")
    public String createDriver(@Valid @ModelAttribute Driver driver, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("drivers", driver);
            return "admin/driver/driver-create";
        }
        driverService.saveDriver(driver);
        return "redirect:/admin/driver/list";
    }

    @GetMapping("/edit/{id}")
    public String editDriverForm(@PathVariable("id") Long id, Model model) {
        Optional<Driver> driver = driverService.findDriverById(id);
        if (driver.isPresent()) {
            model.addAttribute("drivers", driver.get());
            return "admin/driver/driver-edit";
        }
        return "redirect:/admin/driver/list";
    }

    @PostMapping("/edit")
    public String editDriver(@Valid @ModelAttribute Driver driver, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("drivers", driver);
            return "admin/driver/driver-edit";
        }
        driverService.saveDriver(driver);
        return "redirect:/admin/driver/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteDriver(@PathVariable("id") Long id) {
        driverService.deleteDriverById(id);
        return "redirect:/admin/driver/list";
    }

    @GetMapping("/details/{id}")
    public String getDriverDetails(@PathVariable("id") Long id, Model model) {
        Optional<Driver> driver = driverService.findDriverById(id);
        if (driver.isPresent()) {
            model.addAttribute("driver", driver.get());
            return "admin/driver/driver-details";
        }
        return "redirect:/admin/driver/list";
    }

    @GetMapping("/search")
    public String searchDrivers(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("drivers", driverService.searchDriversByKeyword(keyword));
        return "admin/driver/driver-list";
    }
}