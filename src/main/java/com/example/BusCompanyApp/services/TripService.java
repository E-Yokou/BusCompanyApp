package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.*;
import com.example.BusCompanyApp.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public TripService(TripRepository tripRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<Trip> findAllTrips() {
        return tripRepository.findAll();
    }

    public Optional<Trip> findTripById(Long id) {
        return Optional.ofNullable(tripRepository.findById(id).orElse(null));
    }

    @Transactional
    public Trip saveTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Transactional
    public void deleteTripById(Long tripId) {
        // Удаляем Trip и связанные Schedule
        tripRepository.deleteById(tripId);
    }
}