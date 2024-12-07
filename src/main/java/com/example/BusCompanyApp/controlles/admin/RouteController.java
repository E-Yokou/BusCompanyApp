package com.example.BusCompanyApp.controlles.admin;

import com.example.BusCompanyApp.models.Route;
import com.example.BusCompanyApp.services.RouteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    // Список маршрутов
    @GetMapping("/list")
    public String listRoutes(Model model) {
        model.addAttribute("routes", routeService.findAllRoutes());
        return "route-list";
    }

    // Создание нового маршрута
    @GetMapping("/create")
    public String createRouteForm(Model model) {
        model.addAttribute("route", new Route());
        return "route-create";
    }

    @PostMapping("/create")
    public String createRoute(@Valid @ModelAttribute Route route, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("route", route);
            return "route-create";
        }
        routeService.saveRoute(route);
        return "redirect:/routes/list";
    }

    // Редактирование маршрута
    @GetMapping("/edit/{id}")
    public String editRouteForm(@PathVariable("id") Long id, Model model) {
        Optional<Route> route = routeService.findRouteById(id);
        if (route.isPresent()) {
            model.addAttribute("route", route.get());
            return "route-edit";
        }
        return "redirect:/routes/list";
    }

    @PostMapping("/edit")
    public String editRoute(@Valid @ModelAttribute Route route, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("route", route);
            return "route-edit";
        }
        routeService.saveRoute(route);
        return "redirect:/routes/list";
    }

    // Удаление маршрута
    @GetMapping("/delete/{id}")
    public String deleteRoute(@PathVariable("id") Long id) {
        routeService.deleteRouteById(id);
        return "redirect:/routes/list";
    }

    // Поиск маршрутов
    @GetMapping("/search")
    public String searchRoute(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("routes", routeService.searchRoutesByKeyword(keyword));
        return "route-list";
    }

    // Детали маршрута
    @GetMapping("/details/{id}")
    public String getRouteDetails(@PathVariable Long id, Model model) {
        Optional<Route> route = routeService.findRouteById(id);
        if (route.isPresent()) {
            model.addAttribute("route", route.get());
        } else {
            return "error";
        }
        return "route-details";
    }
}
