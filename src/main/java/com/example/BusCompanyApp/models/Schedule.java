package com.example.BusCompanyApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Trip is required")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @NotNull(message = "Vehicle is required")
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @NotNull(message = "Driver is required")
    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = true)
    private Driver driver;
}