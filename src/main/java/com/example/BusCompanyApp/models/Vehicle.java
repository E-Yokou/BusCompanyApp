package com.example.BusCompanyApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Тип транспорта обязателен")
    private String vehicleType;

    @NotBlank(message = "Гос. номер ТС обязателен")
    @Pattern(
            regexp = "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}$",
            message = "Номер водительского удостоверения должен соответствовать формату: А000ВВ000 или А000ВВ00"
    )
    private String vehicleNumber;

    @Positive(message = "Вместительность должна быть положительная")
    private Integer capacity;
}


