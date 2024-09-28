package com.cp.project.demo.model.forBooking;

import java.time.LocalDate;
import java.util.Date;

import com.cp.project.demo.model.forRoom.Room;
import com.cp.project.demo.model.forUser.User;
public class ConcreteBookingBuilder implements BookingBuilder {
	private Room room;
    private User user;
    private String paymentType;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private LocalDate bookingDate;
    private String status;
    private Double totalPrice;
    
	public ConcreteBookingBuilder() {
		super(); 
    }

    @Override
    public BookingBuilder setRoom( Room room) {
    	this.room = room;
        return this;
    }

    @Override
    public BookingBuilder setUser(User user) {
    	this.user = user;
        return this; 
    }

    @Override
    public BookingBuilder setPaymentType(String paymentType) {
    	this.paymentType = paymentType;
        return this;
    }

    @Override
    public BookingBuilder setBookingDate(LocalDate bookingDate) {
    	this.bookingDate = bookingDate;
    	return this;
    }
    
    @Override
    public BookingBuilder setCheckinDate(LocalDate checkinDate) {
    	this.checkinDate = checkinDate;
        return this; 
    }

    @Override
    public BookingBuilder setCheckoutDate(LocalDate checkoutDate) {
    	this.checkoutDate = checkoutDate;
        return this;
    }
    
    @Override
    public BookingBuilder setStatus(String status) {
    	this.status = status;
    	return this;
    }
    
    @Override
    public BookingBuilder setTotalPrice(Double totalPrice) {
    	this.totalPrice = totalPrice;
    	return this;
    }
    

    @Override
    public Booking build() {
        return new Booking(room, user, paymentType, bookingDate ,checkinDate, checkoutDate, status, totalPrice);
    }
}
