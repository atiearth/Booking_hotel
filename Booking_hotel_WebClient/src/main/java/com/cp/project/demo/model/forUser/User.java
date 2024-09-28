package com.cp.project.demo.model.forUser;

import java.util.ArrayList;
import java.util.List;

import com.cp.project.demo.model.forBooking.Booking;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId; 

    @Column(name = "firstname", nullable = false) 
    private String userFirstName; 
    
    @Column(name = "user_lastname", nullable = false)
    private String userLastName; 
    
    @Column(name = "user_email", nullable = false, unique = true) 
    private String userEmail;
    
    @Column(name = "password", nullable = false)
    private String userPassword; 
    
    private String confirmPassword;
    
    @Column(name = "user_role")
    private String userRole; 
    
    @Column(name = "phone_number")
    private String phoneNumber; 
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>(); 

    public User(String userFirstName, String userLastName, String userEmail, 
                String userPassword, String confirmPassword, String userRole, String phoneNumber) { // อัปเดตคอนสตรัคเตอร์
        super();
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.confirmPassword = confirmPassword; // ตั้งค่า confirmPassword
        this.userRole = userRole;
        this.phoneNumber = phoneNumber;
    }

    public User() {
        super();
    }
    
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
    
    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getConfirmPassword() { // เพิ่ม getter
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) { // เพิ่ม setter
        this.confirmPassword = confirmPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
