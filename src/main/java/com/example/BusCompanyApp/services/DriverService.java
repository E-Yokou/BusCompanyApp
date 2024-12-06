package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.Driver;
import com.example.BusCompanyApp.repositories.DriverRepository;
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

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> findAllDrivers() {
        return driverRepository.findAll();
    }

    public Optional<Driver> findDriverById(Long id) {
        return driverRepository.findById(id);
    }

    @Transactional
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Transactional
    public void deleteDriverById(Long id) {
        driverRepository.deleteById(id);
    }

    public List<Driver> searchDriversByKeyword(String keyword) {
        return driverRepository.searchByKeyword(keyword);
    }
}