package com.example.BusCompanyApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Passenger ID is required")
    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private User user;

    @NotNull(message = "Trip ID is required")
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @Positive(message = "Price must be positive")
    private Double price;
}

