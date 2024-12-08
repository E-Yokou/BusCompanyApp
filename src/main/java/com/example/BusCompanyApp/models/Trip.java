package com.example.BusCompanyApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trip")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Trip number is required")
    private String tripNumber;

    @NotBlank(message = "Departure location is required")
    private String departureLocation;

    @NotBlank(message = "Destination location is required")
    private String destinationLocation;

    private Integer occupied_seats;

    @NotNull(message = "Departure date and time are required")
    private LocalDateTime departureDatetime;

    @NotNull(message = "Arrival date and time are required")
    private LocalDateTime arrivalDatetime;

    @NotNull(message = "Vehicle is required")
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @NotNull(message = "Route is required")
    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @NotNull(message = "Driver is required")
    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)  // Добавлено каскадное удаление
    private List<Schedule> schedules;

    public void decreaseOccupiedSeats() {
        if (this.occupied_seats != null) {
            this.occupied_seats += 1;
        }
    }
}