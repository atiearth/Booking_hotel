package com.cp.project.demo.controller;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cp.project.demo.model.forBooking.Booking;
import com.cp.project.demo.model.forBooking.BookingRequest;
import com.cp.project.demo.model.forRoom.Room;
import com.cp.project.demo.model.forUser.User;
import com.cp.project.demo.service.WebClientBookingService;
import com.cp.project.demo.service.WebClientRoomService;
import com.cp.project.demo.service.WebClientUserService;

import reactor.core.publisher.Mono;

@Controller
public class BookingController {

	@Autowired
	WebClientBookingService bookingService;

	@Autowired
	WebClientUserService userService;

	@Autowired
	WebClientRoomService roomService;

	@GetMapping("/web/view/booking")
	public String getViewBooking(Model model) {
		model.addAttribute("bookings", bookingService.getAllBooking().collectList().block());
		return "viewbooking";
	}

	@GetMapping("/web/view/booking/{id}")
	public String getBooking(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("booking", bookingService.getBookingById(id).block());
		return "viewbookingById";
	}

	@GetMapping("/web/view/user/booking/{id}")
	public String getBookingForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("bookingCreate", new BookingRequest());
		model.addAttribute("userId", id);
		return "BookingForm"; // This should match the name of your template
	}
	
	@PostMapping("/web/booking/{id}")
	public Mono<String> submitBooking(@PathVariable("id") Integer id, @ModelAttribute BookingRequest booking, Model model) {
	    booking.setUserId(id);
	    return bookingService.createBooking(booking).map(bookings -> {
	    	model.addAttribute("bookingCreate", bookings);
	    	return "redirect:/web/booking/success";
	    }).onErrorResume(error -> {
	    	return Mono.just("redirect:/web/view/user/booking/" + id);
	    });
	}
	
	@GetMapping("/web/booking/success")
	public String confirmBooking(Model model) {
		model.addAttribute("booking", bookingService.getLastBooking().block());
		return "successbooking";
	}
	
	@GetMapping("/web/view/booking/detail/{id}")
	public String detailBooking(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("booking", bookingService.getBookingById(id).block());
		return "bookingDetail";
	}
	
	@GetMapping("/web/view/listBooking/{id}")
	public String bookingDetailForUser(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("bookings", bookingService.getBookingByUserId(id).collectList().block());
		model.addAttribute("users", userService.getUserById(id).block());
		return "bookingDetailForUser";
	}

}
