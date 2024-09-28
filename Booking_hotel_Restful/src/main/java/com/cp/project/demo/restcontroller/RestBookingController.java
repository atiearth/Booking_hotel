package com.cp.project.demo.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cp.project.demo.model.forBooking.Booking;
import com.cp.project.demo.model.forBooking.BookingRequest;
import com.cp.project.demo.service.BookingService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class RestBookingController {
	private static final Logger logger = LoggerFactory.getLogger(RestBookingController.class);
	@Autowired
	BookingService bookingService;
	
	@PostMapping("/booking/add")
	public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest booking){
        try {
            logger.info("Received booking request: " + booking);
            Booking createBooking = bookingService.createBooking(
                booking.getRoomType(),
                booking.getUserId(),
                booking.getPaymentType(),
                booking.getCheckinDate(),
                booking.getCheckoutDate());
            logger.info("Booking created successfully: " + createBooking);
            return new ResponseEntity<>(createBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating booking", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
	
	@GetMapping("/booking")
	public ResponseEntity<List<Booking>> getAllBooking(){
		List<Booking> listBooking = bookingService.getAllBooking();
		return new ResponseEntity<>(listBooking,HttpStatus.OK);
	}
	
	@GetMapping("/booking/{id}")
	public ResponseEntity<Booking> getbookingById(@PathVariable("id") Integer id){
		Booking booking = bookingService.getBookingById(id);
		return new ResponseEntity<>(booking,HttpStatus.OK);
	}
	
	@GetMapping("/booking/last")
	public ResponseEntity<Booking> getLastBooking() {
		List<Booking> booking = bookingService.getAllBooking();
		Booking lastBooking = booking.getLast();
		return new ResponseEntity<>(lastBooking, HttpStatus.OK);
	}
	
	@GetMapping("/booking/user/{id}")
	public ResponseEntity<List<Booking>> getBookingByUserId(@PathVariable("id") Integer id) {
		List<Booking> bookings = bookingService.getBookingByUserId(id);
		return new ResponseEntity<>(bookings, HttpStatus.OK);
	}
	
	
	
}
