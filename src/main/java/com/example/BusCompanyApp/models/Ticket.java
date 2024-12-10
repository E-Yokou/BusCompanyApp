package com.example.BusCompanyApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime purchaseDate;

    @Positive(message = "Цена должна быть положительной")
    private Double price;
}

