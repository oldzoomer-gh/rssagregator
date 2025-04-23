package ru.gavrilovegor519.rssaggregator.exception;

public class FeedNotFoundException extends RuntimeException {
    public FeedNotFoundException(String s) {
        super(s);
    }
}
