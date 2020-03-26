package coop.tecso.examen.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class ApiErrorDebug extends ApiError {
    private String debugMessage;

    ApiErrorDebug(HttpStatus status, Throwable ex) {
        super(status, ex);
        debugMessage = Arrays.toString(ex.getStackTrace());
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

}
