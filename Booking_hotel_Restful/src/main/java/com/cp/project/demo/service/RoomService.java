package com.cp.project.demo.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.project.demo.model.forRoom.ConcreteRoomFactory;

import com.cp.project.demo.model.forRoom.Room;

import com.cp.project.demo.repository.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomService {
	@Autowired
	RoomRepository roomRepo;
	private ConcreteRoomFactory roomFactory = new ConcreteRoomFactory();

    @Transactional
    public Room createAndSaveRoom(Room room) {
        Room newroom = roomFactory.createRoom(room);
        if (newroom == null) {
            throw new IllegalArgumentException("Invalid room type: " + newroom.getRoomType());
        }
        return roomRepo.save(room);
    }
    
    public List<Room> getAllRooms() {
    	return (List<Room>) roomRepo.findAll();
    }
    
    public Room getRoomById(Integer id) {
    	return roomRepo.findById(id)
    	    .orElseThrow(() -> new RuntimeException("Error: Room with ID " + id + " not found!"));
    }
    
    @Transactional
    public List<Room> updateRoomPriceByType(String roomType, Double newPrice) {
        List<Room> rooms = roomRepo.findByRoomType(roomType);
        if (rooms.isEmpty()) {
            throw new RuntimeException("Error: Room type " + roomType + " not found!");
        }
        for (Room room : rooms) {
            room.setRoomPrice(newPrice);
            roomRepo.save(room); 
        }
        return rooms; 
    }
    
    public List<Room> getRoomByType(String roomType) {
    	return (List<Room>) roomRepo.findByRoomType(roomType);
    }

	public Room updateRoomStatus(Room room) {
		Room existingRoom = roomRepo.findById(room.getRoomId())
				.orElseThrow(() -> new RuntimeException("Error: Room with ID " + room.getRoomId() + " not found!"));
		existingRoom.setRoomStatus(room.getRoomStatus());
		return roomRepo.saveAndFlush(existingRoom);
	}
}