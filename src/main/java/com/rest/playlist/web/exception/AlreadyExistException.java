package com.rest.playlist.web.exception;


/**
 * AlreadyExistException class extends RuntimeException.
 * It's about a custom exception :
 * throwing an exception for resource already exist (conflict) in Spring Boot Service
 * AlreadyExistException is thrown with Http 409
 */

public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String message) {
        super(message);
    }
}
