package com.rest.playlist.exception;


/**
 * AlreadyExistException class extends RuntimeException.
 * It's about a custom exception :
 * throwing an exception for resource already exist in Spring Boot Service
 * ResourceNotFoundException is thrown with Http 406
 */

public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String message) {
        super(message);
    }
}
