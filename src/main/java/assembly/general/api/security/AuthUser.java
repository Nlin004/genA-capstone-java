package assembly.general.api.security;

import java.util.UUID;

public record AuthUser(UUID userId, String email, String role) {
}
