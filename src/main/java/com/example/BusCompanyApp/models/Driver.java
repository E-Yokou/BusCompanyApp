package com.example.BusCompanyApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "driver")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя обязательное")
    @Size(max = 50, message = "Имя не должна превышать 50 символов")
    private String firstName;

    @Size(max = 50, message = "Фамилия не должна превышать 50 символов")
    private String lastName;

    @NotBlank(message = "Номер водительского удостоверения обязателен")
    @Pattern(regexp = "^(\\d{2}[A-Z]{2}|\\d{4}) \\d{6}$", message = "Номер водительского удостоверения должен соответствовать формату: " +
            "XX XX YYYYYY, где XX XX — 4-значная серия (цифры или цифры и буквы), а YYYYYY — 6-значный номер.")
    private String licenseNumber;

    @OneToMany(mappedBy = "driver")
    private List<Trip> trips;

    @OneToMany(mappedBy = "driver")
    private List<Schedule> schedules;

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                '}';
    }
}

