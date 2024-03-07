package com.egor.rssaggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate feed!") // 409
public class DuplicateFeed extends RuntimeException {
    public DuplicateFeed() {
        super();
    }

    public DuplicateFeed(String message) {
        super(message);
    }

    public DuplicateFeed(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateFeed(Throwable cause) {
        super(cause);
    }

    protected DuplicateFeed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
