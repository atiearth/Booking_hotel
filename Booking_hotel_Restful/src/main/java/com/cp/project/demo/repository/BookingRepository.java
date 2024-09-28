package com.cp.project.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.project.demo.model.forBooking.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer>{

	Booking saveAndFlush(Booking booking);

}
