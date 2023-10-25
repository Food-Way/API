package com.foodway.api.record;

import com.foodway.api.model.ETypeUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCustomerData(
        @NotBlank String name,
        @NotBlank String lastName,
        @NotBlank @Email(message = "Email inválido") String email,
        @NotBlank String password,
        @NotNull ETypeUser typeUser,
        @NotBlank String profilePhoto,
        @NotBlank String cpf,
        @NotBlank String bio
) {
}
