package assembly.general.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotBlank @Email String email,
        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}",
                message = "must be at least 8 characters with uppercase, lowercase, number, and special character")
        String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank
        @Pattern(regexp = "^\\+?[0-9][0-9\\-(). ]{6,24}$", message = "must be a valid phone number")
        String phoneNumber

) {}
