package com.cp.project.demo.model.forBooking;
import java.time.LocalDate;
import java.util.Date;

import com.cp.project.demo.model.forRoom.Room;
import com.cp.project.demo.model.forStrategy.PaymentStrategy;
import com.cp.project.demo.model.forUser.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToOne;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "booking")
public class Booking {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer bookingId;
	
	@OneToOne
    @JoinColumn(name = "room_id") // เชื่อมโยงกับ room
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private String paymentType;
    private LocalDate bookingDate;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    
    private String status;  // ใช้เพื่อแสดงสถานะของการจอง เช่น ว่าง , เต็ม
    
  //------------------------ สำหรับ Strategy  ------------------------\\
    private Double totalPrice;
    @Transient
    private PaymentStrategy paymentStrategy;
    //------------------------ สำหรับ Strategy  ------------------------\\
    
    public Booking() {
    	
    }
	public Booking(Room room, User user, String paymentType,LocalDate bookingDate, LocalDate checkinDate, LocalDate checkoutDate, String status, Double totalPrice) {
		this.room = room;
		this.user = user;
		this.bookingDate = bookingDate;
		this.paymentType = paymentType;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.status = status;
		this.totalPrice = totalPrice;
		
	}
	
	public Integer getBookingId() {
		return bookingId;
	}
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
    public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}
	

	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
    public String getStatus() {
        return status;
    }
    //------------------------ สำหรับ Strategy  ------------------------\\
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void processPayment() {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy not set!");
        }
        paymentStrategy.pay(totalPrice);
    }
    //------------------------ สำหรับ Strategy  ------------------------\\
}