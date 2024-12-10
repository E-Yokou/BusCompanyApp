package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.Ticket;
import com.example.BusCompanyApp.models.Trip;
import com.example.BusCompanyApp.repositories.TicketRepository;
import com.example.BusCompanyApp.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final TripRepository tripRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public ReportService(TripRepository tripRepository, TicketRepository ticketRepository) {
        this.tripRepository = tripRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<Trip> getTripsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return tripRepository.findTripsByDateRange(startDate, endDate);
    }

    public List<Object[]> getMostPopularRoutes() {
        return tripRepository.findMostPopularRoutes();
    }

    public List<Ticket> getTicketsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return ticketRepository.findTicketsByDateRange(startDate, endDate);
    }

    public List<Object[]> getTicketSalesByTrip() {
        return ticketRepository.findTicketSalesByTrip();
    }
}