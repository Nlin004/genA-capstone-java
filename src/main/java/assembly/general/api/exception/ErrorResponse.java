package assembly.general.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String error,
        String message,
        Instant timestamp,
        Integer currentReservations,
        Integer availableCopies,
        String currentStatus
) {

    public static ErrorResponse of(String error, String message) {
        return new ErrorResponse(error, message, Instant.now(), null,null,null);
    }

}
