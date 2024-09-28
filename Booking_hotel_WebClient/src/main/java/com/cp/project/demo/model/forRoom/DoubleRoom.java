package com.cp.project.demo.model.forRoom;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Double")
public class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double", 5000.0, "8x30", "Available", "./double.jpg");    
    }
}