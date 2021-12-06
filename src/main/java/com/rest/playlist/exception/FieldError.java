package com.rest.playlist.exception;
/**
*   TODO: 
        - missing documentation
        - use a builder to avoid the complexity of constructors
        - suggestion: use lombok to replace get, constructors with arguments
        - define a private default constructor to make this class emutable
**/
public class FieldError {

    private String objectName;

    private String field;

    private String message;

    FieldError(String objectName, String field, String message) {
        this.objectName = objectName;
        this.field = field;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
