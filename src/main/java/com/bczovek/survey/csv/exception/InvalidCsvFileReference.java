package com.bczovek.survey.csv.exception;

public class InvalidCsvFileReference extends RuntimeException {

    public InvalidCsvFileReference(String message, Throwable cause) {
        super(message, cause);
    }
}
