package com.egor.rssaggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect input data")
public class IncorrectInputDataException extends RuntimeException {
    public IncorrectInputDataException() {
        super();
    }

    public IncorrectInputDataException(String message) {
        super(message);
    }

    public IncorrectInputDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectInputDataException(Throwable cause) {
        super(cause);
    }

    protected IncorrectInputDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
