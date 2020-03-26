package coop.tecso.examen.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class ApiError {

    private HttpStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;
    private String message;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = ex.getMessage();
    }


    public HttpStatus getStatus() {
        return status;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public String getMessage() {
        return message;
    }


}
