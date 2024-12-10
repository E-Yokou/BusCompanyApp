package com.example.BusCompanyApp.repositories;

//import com.example.BusCompanyApp.dto.TripReportDTO;
import com.example.BusCompanyApp.models.Driver;
import com.example.BusCompanyApp.models.Route;
import com.example.BusCompanyApp.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("SELECT t FROM Trip t WHERE " +
            "LOWER(t.tripNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.departureLocation) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.destinationLocation) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Trip> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT t.departureLocation FROM Trip t WHERE t.departureLocation LIKE %:query%")
    List<String> findDepartureLocationsByQuery(@Param("query") String query);

    @Query("SELECT DISTINCT t.destinationLocation FROM Trip t WHERE t.destinationLocation LIKE %:query%")
    List<String> findArrivalLocationsByQuery(@Param("query") String query);

    @Modifying
    @Query("UPDATE Trip t SET t.driver = NULL WHERE t.driver.id = :driverId")
    void setDriverToNull(@Param("driverId") Long driverId);

    @Query("SELECT t FROM Trip t WHERE t.departureDatetime BETWEEN :startDate AND :endDate")
    List<Trip> findTripsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t.trip.route.startLocation, t.trip.route.endLocation, COUNT(t), SUM(t.price) " +
            "FROM Ticket t " +
            "WHERE t.purchaseDate BETWEEN :startDate AND :endDate " +
            "GROUP BY t.trip.route.startLocation, t.trip.route.endLocation")
    List<Object[]> findTripSummaryByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT r.startLocation, r.endLocation, COUNT(t) as ticketCount FROM Ticket t JOIN t.trip tr JOIN tr.route r GROUP BY r.startLocation, r.endLocation ORDER BY ticketCount DESC")
    List<Object[]> findMostPopularRoutes();
}