package assembly.general.api.dto;

//what the response to a login attempt to the BACKEND returns.
public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        LoginUserResponse user
) {
}
