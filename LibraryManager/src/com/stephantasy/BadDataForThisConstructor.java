package com.stephantasy;

public class BadDataForThisConstructor extends Exception {

    private String message;

    public BadDataForThisConstructor(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
