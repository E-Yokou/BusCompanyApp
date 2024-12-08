package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.Ticket;
import com.example.BusCompanyApp.models.Trip;
import com.example.BusCompanyApp.models.User;
import com.example.BusCompanyApp.repositories.TicketRepository;
import com.example.BusCompanyApp.repositories.TripRepository;
import com.example.BusCompanyApp.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, TripRepository tripRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Ticket buyTicket(Long tripId, Long userId, Double price) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setTrip(trip);
        ticket.setPrice(price);

        trip.decreaseOccupiedSeats();
        tripRepository.save(trip);

        return ticketRepository.save(ticket);
    }
}
