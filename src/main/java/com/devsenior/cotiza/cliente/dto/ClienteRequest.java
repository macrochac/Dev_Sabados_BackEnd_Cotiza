package com.devsenior.cotiza.cliente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 120, message = "El nombre no puede exceder 120 caracteres")
        String nombre,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener 10 dígitos")
        String telefono,

        @Pattern(regexp = "^(|[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,})$", message = "El correo no es válido")
        String correo
) {
}
