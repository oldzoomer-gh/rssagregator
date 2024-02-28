package com.egor.rssaggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate user!")
public class DuplicateUser extends Exception {
    public DuplicateUser() {
        super();
    }

    public DuplicateUser(String message) {
        super(message);
    }

    public DuplicateUser(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateUser(Throwable cause) {
        super(cause);
    }

    protected DuplicateUser(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
