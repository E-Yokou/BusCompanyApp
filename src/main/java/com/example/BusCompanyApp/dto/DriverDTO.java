package com.example.BusCompanyApp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DriverDTO {

    private Long id;

    @NotBlank(message = "Имя обязательно")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String firstName;

    @Size(max = 50, message = "Фамилия не должна превышать 50 символов")
    private String lastName;

    @NotBlank(message = "Номер водительского удостоверения обязателен")
    @Pattern(regexp = "^(\\d{2}[A-Z]{2}|\\d{4}) \\d{6}$", message = "Номер водительского удостоверения должен соответствовать формату: XX XX YYYYYY, где XX XX — 4-значная серия (цифры или цифры и буквы), а YYYYYY — 6-значный номер.")
    private String licenseNumber;
}
