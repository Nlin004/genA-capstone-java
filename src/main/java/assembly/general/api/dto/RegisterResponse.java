package assembly.general.api.dto;


import java.time.Instant;
import java.util.UUID;

//this is the json the client gets back, without password.
public record RegisterResponse(
        UUID userId,
        String email,
        String firstName,
        String lastName,
        String role,
        String membershipStatus,
        Instant createdAt,
        String message
) {
}
