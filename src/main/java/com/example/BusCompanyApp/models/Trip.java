package com.example.BusCompanyApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private Integer occupiedSeats;

    @NotNull(message = "Departure date and time are required")
    private LocalDateTime departureDatetime;

    @NotNull(message = "Arrival date and time are required")
    private LocalDateTime arrivalDatetime;

    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Vehicle is required")
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @NotNull(message = "Route is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Driver driver;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    private List<Schedule> schedules;

    public void incrementOccupiedSeats() {
        if (this.occupiedSeats != null) {
            this.occupiedSeats += 1;
        }
    }

    public String getFormattedDepartureDatetime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(departureDatetime);
    }

    public String getFormattedArrivalDatetime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(arrivalDatetime);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", tripNumber='" + tripNumber + '\'' +
                ", departureLocation='" + departureLocation + '\'' +
                ", destinationLocation='" + destinationLocation + '\'' +
                ", occupiedSeats=" + occupiedSeats +
                ", departureDatetime=" + departureDatetime +
                ", arrivalDatetime=" + arrivalDatetime +
                ", price=" + price +
                ", vehicle=" + vehicle +
                ", route=" + route +
                ", driver=" + driver +
                ", schedules=" + schedules +
                '}';
    }
}
