package com.example.BusCompanyApp.controlles.user;

import com.example.BusCompanyApp.models.Ticket;
import com.example.BusCompanyApp.models.Trip;
import com.example.BusCompanyApp.models.User;
import com.example.BusCompanyApp.services.TicketService;
import com.example.BusCompanyApp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TicketService ticketService;

    public UserController(UserService userService, TicketService tripService) {
        this.userService = userService;
        this.ticketService = tripService;
    }

    @GetMapping
    public String index(Model model) {
        return "user/user-page"; // Название шаблона
    }

    @GetMapping("/search")
    public String searchTrips(@RequestParam("departure") String departureLocation,
                              @RequestParam("arrival") String destinationLocation, Model model) {
        List<Trip> trips = userService.findTrips(departureLocation, destinationLocation);
        model.addAttribute("trips", trips);
        return "user/user-page";
    }

    @GetMapping("/departure-locations")
    @ResponseBody
    public List<String> getDepartureLocations() {
        return userService.getAllDepartureLocations();
    }

    @GetMapping("/arrival-locations")
    @ResponseBody
    public List<String> getArrivalLocations() {
        return userService.getAllArrivalLocations();
    }

    @GetMapping("/user-profile")
    public String userProfile(Model model) {
        User user = userService.getCurrentUser(); // Предполагается, что этот метод возвращает текущего пользователя
        model.addAttribute("user", user);
        return "user/user-profile";
    }

    @PostMapping("/buy-ticket")
    public String buyTicket(@RequestParam("tripId") Long tripId, @RequestParam("price") Double price, Model model) {
        User user = userService.getCurrentUser(); // Предполагается, что этот метод возвращает текущего пользователя
        Ticket ticket = ticketService.buyTicket(tripId, user.getUserId(), price);
        model.addAttribute("ticket", ticket);
        return "user/ticket-confirmation"; // Предполагается, что у вас есть шаблон для подтверждения покупки
    }
}
