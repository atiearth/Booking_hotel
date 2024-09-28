package com.cp.project.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cp.project.demo.model.forRoom.Room;
import com.cp.project.demo.service.WebClientRoomService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/web")
public class RoomController {
	@Autowired
	WebClientRoomService roomService;
	
	public RoomController(WebClientRoomService roomService) {
		this.roomService = roomService;
	}
	
	@GetMapping("/view/room")
	public String viewAllRoom(Model model) {
		List<Room> room = roomService.getAllRoom().collectList().block();
		if(room == null) {
	        model.addAttribute("rooms", new ArrayList<>());
		} else {
	        model.addAttribute("rooms", room);
	    }
	 	return "viewroom";
	}

	
    @GetMapping("/view/addroom")
    public String getAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "addroom";
    }
    
    @PostMapping("/add/room")
    public Mono<String> addRoom(@ModelAttribute Room room, Model model) {
        return roomService.checkRoomNumberExists(room.getRoomNumber())
            .flatMap(exists -> {
                if (exists) {
                    return Mono.just("redirect:/web/view/addroom?error=RoomNumber already exists");
                } else {
                    return roomService.addRoom(room)
                        .map(createdRoom -> {
                            model.addAttribute("room", createdRoom);
                            return "redirect:/web/view/room"; // Redirect ไปยังหน้าแสดงห้องเมื่อเพิ่มห้องสำเร็จ
                        });
                }
            })
            .onErrorResume(error -> {
                return Mono.just("redirect:/web/view/addroom?error=" + error.getMessage());
            });
    }

    
    @GetMapping("/edit/room")
    public String getEditRoomForm(Model model) {
        return "editRoomForm"; 
    }

    @PostMapping("/room/price")
    public Mono<String> updateRoomPrice(@RequestParam String roomType, @RequestParam Double newPrice) {
        return roomService.updateRoomPriceByType(roomType, newPrice)
            .then(Mono.just("redirect:/web/view/room"));
    }
    
    @GetMapping("/room/get_available")
    public String getAvailableRooms(Model model) {
        Flux<Room> availableRooms = roomService.getAvailableRooms();
        model.addAttribute("availableRooms", availableRooms.collectList().block());
        return "availableRooms"; // ชื่อหน้า HTML ที่จะแสดงห้องว่าง
    }

    @GetMapping("/room/get_un_available")
    public String getUnavailableRooms(Model model) {
        Flux<Room> unavailableRooms = roomService.getUnavailableRooms();
        model.addAttribute("unavailableRooms", unavailableRooms.collectList().block());
        return "unavailableRooms"; // ชื่อหน้า HTML ที่จะแสดงห้องที่ไม่ว่าง
    }

    
    @GetMapping("/web/view/dashboard")
    public String viewDashboard(Model model) {
        Flux<Room> availableRooms = roomService.getAvailableRooms();
        Flux<Room> unavailableRooms = roomService.getUnavailableRooms();

        model.addAttribute("availableRooms", availableRooms.collectList());
        model.addAttribute("unavailableRooms", unavailableRooms.collectList());

        return "dashboard"; // แสดงหน้าแดชบอร์ด
    }
    

    
}
