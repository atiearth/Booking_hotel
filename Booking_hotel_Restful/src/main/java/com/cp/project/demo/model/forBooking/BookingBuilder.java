package com.cp.project.demo.model.forBooking;


import java.time.LocalDate;
import java.util.Date;
import com.cp.project.demo.model.forRoom.Room;
import com.cp.project.demo.model.forUser.User;

public interface BookingBuilder {
    BookingBuilder setRoom(Room room);
    BookingBuilder setUser(User user);
    BookingBuilder setPaymentType(String paymentType);
    BookingBuilder setBookingDate(LocalDate bookingDate);
    BookingBuilder setCheckinDate(LocalDate checkinDate);
    BookingBuilder setCheckoutDate(LocalDate checkoutDate);
    BookingBuilder setStatus(String status);
    BookingBuilder setTotalPrice(Double totalPrice);
    
    Booking build();
}
