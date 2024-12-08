//package com.example.BusCompanyApp.controlles.user;
//
//import com.example.BusCompanyApp.models.Trip;
//import com.example.BusCompanyApp.services.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("user/trip")
//public class UserTripController {
//
//    private final TripService tripService;
//
//    @Autowired
//    public UserTripController(TripService tripService) {
//        this.tripService = tripService;
//    }
//
//    // Отображение списка маршрутов на странице user-page
//    @GetMapping("/list")
//    public String listTrips(Model model) {
//        List<Trip> trips = tripService.findAllTrips();
//        model.addAttribute("trips", trips);
//        return "user/user-page"; // Страница user-page для отображения списка маршрутов
//    }
//
//    // Просмотр подробной информации о маршруте
//    @GetMapping("/details/{id}")
//    public String getTripDetails(@PathVariable Long id, Model model) {
//        Trip trip = tripService.findTripById(id)
//                .orElseThrow(() -> new RuntimeException("Trip not found"));
//        model.addAttribute("trip", trip);
//        return "user/trip/trip-details"; // Страница для отображения подробной информации о маршруте
//    }
//}