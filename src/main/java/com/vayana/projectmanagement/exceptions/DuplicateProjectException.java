package com.vayana.projectmanagement.exceptions;

public class DuplicateProjectException extends RuntimeException{

    public DuplicateProjectException(String message) {
        super(message);
    }

    public DuplicateProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
