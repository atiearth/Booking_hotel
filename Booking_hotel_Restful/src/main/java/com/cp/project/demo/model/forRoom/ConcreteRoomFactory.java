package com.cp.project.demo.model.forRoom;

public class ConcreteRoomFactory {
	public static Room createRoom(Room room) {
	    String type = room.getRoomType();
	   
	   
	    if (type.equals("Single")) { 
	    	 Room singleRoom = new SingleRoom();
	    	 singleRoom.setFloor(room.getFloor());
	    	 singleRoom.setBuilder(room.getBuilder());
	    	 singleRoom.setRoomNumber(room.getRoomNumber());
	        return singleRoom;
	    } else if (type.equals("Double")) {
	    	 Room doubleRoom = new DoubleRoom();
	    	 doubleRoom.setFloor(room.getFloor());
	    	 doubleRoom.setBuilder(room.getBuilder());
	         doubleRoom.setRoomNumber(room.getRoomNumber());
	        return doubleRoom;
	    }else {
	    	return null;
	    }
	}
}
