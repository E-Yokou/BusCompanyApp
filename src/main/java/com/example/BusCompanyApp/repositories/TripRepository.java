package com.example.BusCompanyApp.repositories;

import com.example.BusCompanyApp.models.Driver;
import com.example.BusCompanyApp.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("SELECT t FROM Trip t WHERE " +
            "LOWER(t.tripNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.departureLocation) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.destinationLocation) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Trip> searchByKeyword(@Param("keyword") String keyword);
}