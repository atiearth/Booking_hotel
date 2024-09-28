package com.cp.project.demo.model.forBooking;

import java.time.LocalDate;

public class BookingRequest {

	private String roomType;
	private Integer userId;
	private String paymentType;
	private LocalDate checkinDate;
	private LocalDate checkoutDate;

	public BookingRequest() {
		super();
	}

	public BookingRequest(String roomType, Integer userId, String paymentType, LocalDate checkinDate,
			LocalDate checkoutDate) {
		super();
		this.roomType = roomType;
		this.userId = userId;
		this.paymentType = paymentType;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public LocalDate getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
}
