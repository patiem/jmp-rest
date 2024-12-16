package com.epa.m.multi.jmprest.controller;

public class UserMinimumAgeException extends RuntimeException {
    public UserMinimumAgeException() {
        this("Minimum age must be greater than zero");
    }

    public UserMinimumAgeException(String message) {
        super(message);
    }

    public UserMinimumAgeException(double age) {
        this("Minimum age must be greater than zero, but was " + age);
    }
}
