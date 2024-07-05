package com.bczovek.survey.csv.exception;

public class InvalidCsvFileContent extends RuntimeException {

    public InvalidCsvFileContent(String message, Throwable cause) {
        super(message, cause);
    }
}
