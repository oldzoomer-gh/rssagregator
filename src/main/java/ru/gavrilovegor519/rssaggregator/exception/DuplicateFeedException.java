package ru.gavrilovegor519.rssaggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate feed!") // 409
public class DuplicateFeedException extends RuntimeException {
    public DuplicateFeedException() {
        super();
    }

    public DuplicateFeedException(String message) {
        super(message);
    }

    public DuplicateFeedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateFeedException(Throwable cause) {
        super(cause);
    }

    protected DuplicateFeedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
