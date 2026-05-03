package com.your_playground.exception;


public class SlotAlreadyExist extends RuntimeException {

    public SlotAlreadyExist(String message) {
        super(message);
    }
}