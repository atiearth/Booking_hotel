package com.cp.project.demo.service;

public class InvalidBookingDateException extends Exception {
	public InvalidBookingDateException(String message) {
        super(message);
    }
}
