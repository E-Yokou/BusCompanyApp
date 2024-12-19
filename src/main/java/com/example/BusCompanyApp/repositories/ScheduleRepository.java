package com.example.BusCompanyApp.repositories;

import com.example.BusCompanyApp.models.Schedule;
import com.example.BusCompanyApp.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE " +
            "s.vehicle.id = :vehicleId AND " +
            "(s.trip.departureDatetime BETWEEN :start AND :end OR " +
            "s.trip.arrivalDatetime BETWEEN :start AND :end)")
    List<Schedule> findByVehicleIdAndTimeRange(@Param("vehicleId") Long vehicleId,
                                               @Param("start") LocalDateTime start,
                                               @Param("end") LocalDateTime end);

    Schedule findByTrip(Trip trip);

    @Modifying
    @Query("UPDATE Schedule s SET s.driver = NULL WHERE s.driver.id = :driverId")
    void setDriverToNull(@Param("driverId") Long driverId);

    List<Schedule> findByTrip_DepartureDatetimeBetween(LocalDateTime start, LocalDateTime end);
}