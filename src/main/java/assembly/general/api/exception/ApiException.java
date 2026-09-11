package assembly.general.api.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String error;
    private final Integer currentReservations;
    private final Integer availableCopies;
    private final String currentStatus;

    public ApiException(HttpStatus status, String error, String message) {

        this(status, error, message, null, null, null);
    }

    public ApiException(HttpStatus status, String error, String message, Integer currentReservations, Integer availableCopies, String currentStatus) {
        super(message);
        this.status = status;
        this.error = error;
        this.currentReservations = currentReservations;
        this.availableCopies = availableCopies;
        this.currentStatus = currentStatus;
    }

    public static ApiException validation(String message) {
        return new ApiException(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", message);
    }

    public static ApiException notFound(String message) {
        return new ApiException(HttpStatus.NOT_FOUND, "NOT_FOUND", message);
    }
    public static ApiException authFailed() {
        return new ApiException(HttpStatus.UNAUTHORIZED, "AUTHENTICATION_FAILED", "Invalid email or password");
    }

    public HttpStatus getStatus() { return status; }
    public String getError() { return error; }
    public String getCurrentStatus() { return currentStatus; }

    public Integer getCurrentReservations() { return currentReservations; }
    public Integer getAvailableCopies() { return availableCopies; }
}
