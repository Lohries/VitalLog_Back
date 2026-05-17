package com.vitallog.vitalog_back.application.dto.company;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InviteRequest(@NotBlank @Email String email) {}
