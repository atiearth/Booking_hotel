package com.cp.project.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.cp.project.demo.model.forRoom.Room;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebClientRoomService {

	@Autowired
	WebClient webClient;

	public WebClientRoomService(WebClient webClient) {
		this.webClient = webClient;
	}

	public Flux<Room> getAllRoom() {
		return webClient.get().uri("/room").retrieve().bodyToFlux(Room.class);
	}

	public Mono<Room> getRoomById(Integer roomId) {
		return webClient.get().uri("/room/{id}", roomId).retrieve().bodyToMono(Room.class);
	}

	public Mono<Room> addRoom(Room room) {
		return webClient.post().uri("/room").bodyValue(room).retrieve().bodyToMono(Room.class);
	}

	public Flux<Room> updateRoomPriceByType(String roomType, Double newPrice) {
	    Map<String, Object> requestBody = new HashMap<>();
	    requestBody.put("roomType", roomType);
	    requestBody.put("newPrice", newPrice);

	    return webClient.put()
	        .uri("/room/price")
	        .body(BodyInserters.fromValue(requestBody)) // ส่งข้อมูลผ่าน request body
	        .retrieve()
	        .bodyToFlux(Room.class)
	        .flatMap(room -> {
	            // ตรวจสอบประเภทห้อง ถ้าเป็นประเภทที่ต้องการจะอัปเดต, อัปเดตราคาใหม่
	            if (room.getRoomType().equals(roomType)) {
	                room.setRoomPrice(newPrice);
	            }
	            // ส่งห้องที่ถูกอัปเดตกลับไป
	            return Mono.just(room);
	        });
	}


	public Mono<Boolean> checkRoomNumberExists(int roomNumber) {
        return getAllRoom()
            .filter(room -> room.getRoomNumber() == roomNumber)
            .hasElements(); 
    }
	
	public Flux<Room> getAvailableRooms() {
        return webClient.get()
                .uri("/room/get_available")
                .retrieve()
                .bodyToFlux(Room.class);
    }

    public Flux<Room> getUnavailableRooms() {
        return webClient.get()
                .uri("/room/get_un_available")
                .retrieve()
                .bodyToFlux(Room.class);
    }
    
    public Flux<Room> getRoomByType(String roomType) {
    	return webClient.get()
    			.uri("/room/type/{roomType}", roomType)
    			.retrieve()
    			.bodyToFlux(Room.class);
    }


}
