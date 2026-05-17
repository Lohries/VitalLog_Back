package com.vitallog.vitalog_back.application.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Email String email,
        @NotBlank @Size(max = 20) String phone,
        @Pattern(regexp = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})?$", message = "CPF inválido")
        String cpf,
        @Size(max = 9) String cep,
        @Size(max = 200) String street,
        @Size(max = 100) String city,
        @Size(max = 2) String state
) {}
