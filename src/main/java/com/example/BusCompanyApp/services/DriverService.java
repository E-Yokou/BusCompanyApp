package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.Driver;
import com.example.BusCompanyApp.repositories.DriverRepository;
import com.example.BusCompanyApp.repositories.ScheduleRepository;
import com.example.BusCompanyApp.repositories.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final TripRepository tripRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository, TripRepository tripRepository, ScheduleRepository scheduleRepository) {
        this.driverRepository = driverRepository;
        this.tripRepository = tripRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<Driver> findAllDrivers() {
        return driverRepository.findAll();
    }

    public Optional<Driver> findDriverById(Long id) {
        return driverRepository.findById(id);
    }

    @Transactional
    public Driver saveDriver(Driver driver) {
        if (driver.getId() != null) {
            Optional<Driver> existingDriver = driverRepository.findById(driver.getId());
            if (existingDriver.isPresent() && existingDriver.get().getLicenseNumber().equals(driver.getLicenseNumber())) {
                return driverRepository.save(driver);
            }
        }

        if (driverRepository.existsByLicenseNumber(driver.getLicenseNumber())) {
            throw new IllegalArgumentException("Номер водительского удостоверения уже существует.");
        }

        return driverRepository.save(driver);
    }

    @Transactional
    public void deleteDriverById(Long driverId) {
        // Отвязываем водителя от связанных Schedule
        scheduleRepository.setDriverToNull(driverId);

        // Удаляем водителя
        driverRepository.deleteById(driverId);
    }

    public List<Driver> searchDriversByKeyword(String keyword) {
        return driverRepository.searchByKeyword(keyword);
    }
}