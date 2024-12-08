package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.Ticket;
import com.example.BusCompanyApp.models.Trip;
import com.example.BusCompanyApp.models.User;
import com.example.BusCompanyApp.repositories.TicketRepository;
import com.example.BusCompanyApp.repositories.TripRepository;
import com.example.BusCompanyApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final MailSender mailSender; // Заменено на JavaMailSender

    @Autowired
    public TicketService(TicketRepository ticketRepository, TripRepository tripRepository, UserRepository userRepository, MailSender mailSender) {
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Transactional
    public Ticket buyTicket(Long tripId, Long userId, Double price) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setTrip(trip);
        ticket.setPrice(price);

        trip.decreaseOccupiedSeats();
        tripRepository.save(trip);

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByUser(User user) {
        return ticketRepository.findByUser(user);
    }

    public void sendTicketEmail(String email, Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Ticket");
        message.setText("Your ticket has been purchased successfully!\n" +
                "Ticket ID: " + ticket.getId() + "\n" +
                "Trip Number: " + ticket.getTrip().getTripNumber() + "\n" +
                "Price: " + ticket.getPrice());

        mailSender.send(message);
    }
}