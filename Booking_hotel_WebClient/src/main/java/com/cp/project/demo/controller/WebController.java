package com.cp.project.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cp.project.demo.model.forBooking.Booking;
import com.cp.project.demo.model.forRoom.Room;
import com.cp.project.demo.service.WebClientBookingService;
import com.cp.project.demo.service.WebClientRoomService;
import com.cp.project.demo.service.WebClientUserService;

@Controller
public class WebController {
	
	@Autowired
	WebClientUserService userService;
	
	@Autowired
	WebClientBookingService bookingService;
	
	@Autowired
	WebClientRoomService roomService;
	
	@GetMapping("/")
	public String getHomePage() {
		return "homepage";
	}
	
	@GetMapping("/web/logout")
	public String afterLogout() {
		return "homepage";
	}

	@GetMapping("/homepage_login/{id}")
	public String getHomePageLogin(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("users", userService.getUserById(id).block());
		return "homepage_login";
	}

	@GetMapping("/web/view/dashboard")
	public String getViewDashboard(Model model) {
		List<Booking> bookings = bookingService.getAllBooking().collectList().block();
		Double total_income = bookings.stream()
			    .mapToDouble(Booking::getTotalPrice)
			    .sum();
		model.addAttribute("total_income", total_income);
		
		List<Room> room_available = roomService.getAvailableRooms().collectList().block();
		long total_available = room_available.size();
		model.addAttribute("total_available", total_available);
		
		List<Room> room_unAvailable = roomService.getUnavailableRooms().collectList().block();
		long total_unAvailable = room_unAvailable.size();
		model.addAttribute("total_unAvailable", total_unAvailable);
		
		return "dashboard";
	}	

}
