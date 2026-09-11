package assembly.general.api.dto;


import java.time.Instant;
import java.util.UUID;

public record ProfileResponse(
        UUID userId,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        String role,
        String membershipStatus,
        Instant memberSince,
        long activeReservations,
        long borrowingHistory
) {
}
