package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.Vehicle;
import com.example.BusCompanyApp.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> findVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public void deleteVehicleById(Long id) {
        vehicleRepository.deleteById(id);
    }

    public List<Vehicle> searchVehiclesByKeyword(String keyword) {
        return vehicleRepository.searchByKeyword(keyword);
    }
}