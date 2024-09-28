package com.cp.project.demo.service;

public class UpdateRoomPriceRequest {
    private String roomType;
    private Double newPrice;

    // Default constructor
    public UpdateRoomPriceRequest() {}

    // Getter และ Setter
    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }
}
