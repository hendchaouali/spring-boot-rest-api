package com.rest.playlist.exception;

import lombok.*;

import java.util.Date;
import java.util.List;


/**
 * instead of using default error response provided by Spring Boot,
 * we define a specific error response message
 * response eg :
 * {
 *     "statusCode": 400,
 *     "timeStamp": "2021-12-11T22:35:50.035+00:00",
 *     "message": "Validation failed for argument [0] in public org.springframework.http.ResponseEntity<com.rest.playlist.model.Song> ...",
 *     "description": "uri=/api/songs",
 *     "fieldErrors": [
 *         {
 *             "objectName": "song",
 *             "field": "title",
 *             "message": "NotBlank: titre ne doit pas être null ou vide"
 *         }
 *     ]
 * }
 */


@Getter
@Builder
class ErrorMessage {

    private int statusCode;
    private Date timeStamp;
    private String message;
    private String description;
    private List<FieldError> fieldErrors;
}
