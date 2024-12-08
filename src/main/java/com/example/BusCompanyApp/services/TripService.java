package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.Driver;
import com.example.BusCompanyApp.models.Route;
import com.example.BusCompanyApp.models.Trip;
import com.example.BusCompanyApp.models.Vehicle;
import com.example.BusCompanyApp.repositories.RouteRepository;
import com.example.BusCompanyApp.repositories.TripRepository;
import com.example.BusCompanyApp.repositories.VehicleRepository;
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

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
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
    public void deleteTripById(Long id) {
        // Найти Trip по ID, если не найден, выбросить исключение
        Trip tripToDelete = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        // Удалить Trip из базы данных
        tripRepository.delete(tripToDelete);
    }
}