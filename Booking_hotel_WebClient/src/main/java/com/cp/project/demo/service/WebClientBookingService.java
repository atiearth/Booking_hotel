package com.cp.project.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.cp.project.demo.model.forBooking.Booking;
import com.cp.project.demo.model.forBooking.BookingRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebClientBookingService {
	
	@Autowired
	WebClient webClient;
	
	public WebClientBookingService(WebClient webClient) {
		this.webClient = webClient;
	}
	
	public Flux<Booking> getAllBooking() {
		return webClient.get().uri("/booking").retrieve().bodyToFlux(Booking.class);
	}
	
	public Mono<Booking> getBookingById(Integer id) {
		return webClient.get().uri("/booking/{id}", id).retrieve().bodyToMono(Booking.class);
	}
	
	public Mono<Booking> getLastBooking() {
		return webClient.get().uri("/booking/last").retrieve().bodyToMono(Booking.class);
	}
	
	public Mono<Booking> createBooking(BookingRequest booking) {
	    return webClient.post().uri("/booking/add").bodyValue(booking).retrieve().bodyToMono(Booking.class);
	}
	
	public Flux<Booking> getBookingByUserId(Integer id) {
		return webClient.get().uri("/booking/user/{id}", id).retrieve().bodyToFlux(Booking.class);
	}
	

}