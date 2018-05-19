package com.stephantasy;

public class BadDataForThisConstructor extends Exception {

    private String message;

    public BadDataForThisConstructor(String msg) {
        message = msg;
    }

    public BadDataForThisConstructor() {
    }

    public BadDataForThisConstructor(String msg, Exception e) {
    }

    @Override
    public String getMessage() {
        return message;
    }
}
