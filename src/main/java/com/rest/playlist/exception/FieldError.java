package com.rest.playlist.exception;

import lombok.*;

/**
 * instead of using default error response provided by Spring Boot,
 * FieldError class is part of ErrorMessage class to definr error response message
 */

@Getter
@Builder
class FieldError {

    private String objectName;

    private String field;

    private String message;
}
