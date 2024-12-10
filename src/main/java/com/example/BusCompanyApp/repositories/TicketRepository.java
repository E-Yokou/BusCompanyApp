package com.example.BusCompanyApp.repositories;

import com.example.BusCompanyApp.models.Driver;
import com.example.BusCompanyApp.models.Ticket;
import com.example.BusCompanyApp.models.Trip;
import com.example.BusCompanyApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUser(User user);

    void deleteByTrip(Trip trip);

    @Query("SELECT t FROM Ticket t WHERE t.purchaseDate BETWEEN :startDate AND :endDate")
    List<Ticket> findTicketsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t.trip.id, COUNT(t), SUM(t.price) FROM Ticket t GROUP BY t.trip.id")
    List<Object[]> findTicketSalesByTrip();
}