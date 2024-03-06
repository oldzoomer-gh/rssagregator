package com.egor.rssaggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect input data")
public class IncorrectInputData extends Exception {
    public IncorrectInputData() {
        super();
    }

    public IncorrectInputData(String message) {
        super(message);
    }

    public IncorrectInputData(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectInputData(Throwable cause) {
        super(cause);
    }

    protected IncorrectInputData(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
