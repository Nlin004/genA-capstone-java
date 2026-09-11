package assembly.general.api.dto;

import java.util.UUID;

//what the response to a login is to the USER ITSELF (info about the user logged in)
public record LoginUserResponse(
        UUID userId,
        String email,
        String firstName,
        String lastName,
        String role
) {
}
