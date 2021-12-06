package com.rest.playlist.exception;

/**
*   TODO: 
        -  missing documentation: whith which HTTP code  we can throw it?
        
**/
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
