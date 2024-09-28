package com.cp.project.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.project.demo.model.forRoom.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
	List<Room> findByRoomType(String roomType);

	Room saveAndFlush(Room existingRoom);
}
