package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.Driver;
import com.example.BusCompanyApp.models.Schedule;
import com.example.BusCompanyApp.models.Trip;
import com.example.BusCompanyApp.repositories.DriverRepository;
import com.example.BusCompanyApp.repositories.ScheduleRepository;
import com.example.BusCompanyApp.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    private TripRepository tripRepository;
    private DriverRepository driverRepository;

    public Optional<Schedule> findScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public List<Schedule> findAllSchedules() {
        return scheduleRepository.findAll();
    }

    public boolean isConflict(Trip trip) {
        return !scheduleRepository.findByVehicleIdAndTimeRange(
                trip.getVehicle().getId(),
                trip.getDepartureDatetime(),
                trip.getArrivalDatetime()
        ).isEmpty();
    }

    public void assignDriverToSchedule(Long tripId, Long driverId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        // Проверяем, существует ли уже расписание для данного рейса
        Schedule existingSchedule = scheduleRepository.findByTrip(trip);
        if (existingSchedule != null) {
            throw new RuntimeException("Driver already assigned to this trip.");
        }

        // Создаем новое расписание
        Schedule schedule = new Schedule();
        schedule.setTrip(trip);
        schedule.setDriver(driver);
        schedule.setVehicle(trip.getVehicle());

        scheduleRepository.save(schedule);
    }

    @Transactional
    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void deleteScheduleById(Long id) {
        scheduleRepository.deleteById(id);
    }
}