package com.example.BusCompanyApp.repositories;

import com.example.BusCompanyApp.models.Driver;
import com.example.BusCompanyApp.models.Ticket;
import com.example.BusCompanyApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUser(User user);
}