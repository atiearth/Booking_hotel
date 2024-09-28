package com.cp.project.demo.model.forRoom;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) 
@DiscriminatorColumn(name = "room_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "room")
public class Room implements RoomInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "room_type", insertable = false, updatable = false)  // กำหนดเป็น insertable=false, updatable=false
    protected String roomType;

    @Column(name = "room_price")
    protected Double roomPrice;

    @Column(name = "room_status")
    protected String roomStatus;
    
    @Column(name = "room_size")
    protected String roomSize;
    
    private int floor;
    private String builder;
    private int roomNumber;
    
    protected String imgURL;

    // Default constructor
    public Room() {}

    // Constructor
    public Room(String roomType, Double roomPrice, String roomSize, String roomStatus, String imgURL) {
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomSize = roomSize;
        this.roomStatus = roomStatus;
        this.imgURL = imgURL;
    }

    // Getters and Setters
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getBuilder() {
        return builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}