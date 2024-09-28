package com.cp.project.demo.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.project.demo.model.forBooking.Booking;
import com.cp.project.demo.model.forBooking.ConcreteBookingBuilder;
import com.cp.project.demo.model.forRoom.Room;
import com.cp.project.demo.model.forUser.User;
import com.cp.project.demo.repository.BookingRepository;
import com.cp.project.demo.repository.RoomRepository;
import com.cp.project.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookingService {
	@Autowired
	private BookingRepository bookingRepo;

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserService userService;

	public List<Booking> getAllBooking() {
		return (List<Booking>) bookingRepo.findAll();
	}

	public Booking getBookingById(Integer id) {
		return bookingRepo.findById(id).orElseThrow(() -> new RuntimeException("error bro id not found!!!"));
	}

	public Booking createBooking(String roomType, Integer id, String paymentType, LocalDate checkinDate, LocalDate checkoutDate) throws InvalidBookingDateException, UserNotFoundException {
	    User user = userService.getUserById(id);
	    if (user == null) {
	        throw new UserNotFoundException("User not found with ID: " + id);
	    }
	    
	    List<Room> listRoom = roomService.getRoomByType(roomType);
	    Room firstRoom = listRoom.stream()
	                             .filter(room -> "Available".equals(room.getRoomStatus()))
	                             .findFirst()
	                             .orElseThrow();

	    long differenceInDays = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
	    if (differenceInDays <= 0) {
	        throw new InvalidBookingDateException("Check-out date must be after check-in date.");
	    }

	    Double totalPrice = firstRoom.getRoomPrice() * differenceInDays;

	    ConcreteBookingBuilder builder = new ConcreteBookingBuilder();
	    Booking booking = builder.setRoom(firstRoom)
	                             .setUser(user)
	                             .setPaymentType(paymentType != null ? paymentType : "Unknown")
	                             .setBookingDate(LocalDate.now())
	                             .setCheckinDate(checkinDate)
	                             .setCheckoutDate(checkoutDate)
	                             .setStatus(paymentType.equals("Cash") ? "Waiting pay" : "payed")
	                             .setTotalPrice(totalPrice)
	                             .build();

	    firstRoom.setRoomStatus("Unavailable");
	    roomService.updateRoomStatus(firstRoom); // ใช้ method ที่จัดการการเปลี่ยนสถานะห้อง

	    return bookingRepo.saveAndFlush(booking); // ใช้ saveAndFlush เพื่อบันทึกในทันที
	}
	
	public List<Booking> getBookingByUserId(Integer id) {
		List<Booking> bookings = (List<Booking>) bookingRepo.findAll();
		List<Booking> bookingForUser = bookings.stream()
			    .filter(booking -> id.equals(booking.getUserId()))
			    .collect(Collectors.toList());
		return bookingForUser;
	}


}
