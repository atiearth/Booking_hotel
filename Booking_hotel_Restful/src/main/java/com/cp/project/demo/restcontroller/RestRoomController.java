package com.cp.project.demo.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cp.project.demo.model.forRoom.ConcreteRoomFactory;
import com.cp.project.demo.model.forRoom.Room;
import com.cp.project.demo.service.RoomService;
import com.cp.project.demo.service.UpdateRoomPriceRequest;

@RestController
@RequestMapping("/api")
public class RestRoomController {
	@Autowired
	RoomService roomService;

    @GetMapping("/room")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping("/room")
	public ResponseEntity<Room> createRoom(@RequestBody Room room) {
	    Room createdRoom = ConcreteRoomFactory.createRoom(room);
	    if (createdRoom != null) {
	        // Save the createdRoom to the database
	        createdRoom = roomService.createAndSaveRoom(createdRoom);  // ตรวจสอบว่าการบันทึกถูกต้อง
	        return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
	    } else {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // หากประเภทห้องไม่ถูกต้อง
	    }
	}


    @GetMapping("/room/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Integer id) {
        Room room = roomService.getRoomById(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
    
	@PutMapping("/room/price")
	public ResponseEntity<List<Room>> updateRoomPrice(@RequestBody UpdateRoomPriceRequest request) {
	    List<Room> updatedRooms = roomService.updateRoomPriceByType(request.getRoomType(), request.getNewPrice());
	    return new ResponseEntity<>(updatedRooms, HttpStatus.OK);
	}
	
	@GetMapping("/room/get_available")
	public ResponseEntity<List<Room>> getAvailable() {
	    List<Room> roomList = roomService.getAllRooms();
	    List<Room> availableRoomList = new ArrayList<>();

	    for (Room room : roomList) {
	        if ("Available".equalsIgnoreCase(room.getRoomStatus())) {
	            availableRoomList.add(room);
	        }
	    }
	    return new ResponseEntity<>(availableRoomList, HttpStatus.OK);
	}
	
	@GetMapping("/room/get_un_available")
	public ResponseEntity<List<Room>> getUnAvailable() {
	    List<Room> roomList = roomService.getAllRooms();
	    List<Room> UnavailableRoomList = new ArrayList<>();

	    for (Room room : roomList) {
	        if ("Unavailable".equalsIgnoreCase(room.getRoomStatus())) {
	        	UnavailableRoomList.add(room);
	        }
	    }
	    return new ResponseEntity<>(UnavailableRoomList, HttpStatus.OK);
	}
	
	@GetMapping("/room/type/{roomType}")
	public ResponseEntity<List<Room>> getRoomByType(@PathVariable String roomType) {
		List<Room> roomTypeList = roomService.getRoomByType(roomType);
		return new ResponseEntity<>(roomTypeList, HttpStatus.OK);
	}
	
}
