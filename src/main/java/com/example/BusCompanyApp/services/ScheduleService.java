package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.Driver;
import com.example.BusCompanyApp.models.Schedule;
import com.example.BusCompanyApp.models.Trip;
import com.example.BusCompanyApp.repositories.DriverRepository;
import com.example.BusCompanyApp.repositories.ScheduleRepository;
import com.example.BusCompanyApp.repositories.TicketRepository;
import com.example.BusCompanyApp.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public Optional<Schedule> findScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public List<Schedule> findAllSchedules() {
        return scheduleRepository.findAll();
    }


    public Schedule findScheduleByTrip(Trip trip) {
        return scheduleRepository.findByTrip(trip);
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