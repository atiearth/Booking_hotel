package com.cp.project.demo.model.forRoom;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Single")
public class SingleRoom extends Room {

    public SingleRoom() {
        super("Single", 3000.0, "6x25", "Available", "./tt.jpg");    
    }
}