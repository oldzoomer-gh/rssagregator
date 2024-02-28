package com.egor.rssaggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User not found!")
public class UserNotFound extends Exception {
    public UserNotFound() {
        super();
    }

    public UserNotFound(String message) {
        super(message);
    }

    public UserNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFound(Throwable cause) {
        super(cause);
    }

    protected UserNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
