package coop.tecso.examen.exceptions;

import org.springframework.http.HttpStatus;

public class ApiErrorFactory {
    public static ApiError getApiError(HttpStatus status, Throwable ex, String profile) {
        switch (profile) {
            case "dev":
                return new ApiErrorDebug(status, ex);
            default:
                return new ApiError(status, ex);
        }
    }
}
