package com.example.BusCompanyApp.repositories;

import com.example.BusCompanyApp.models.Driver;
import com.example.BusCompanyApp.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}